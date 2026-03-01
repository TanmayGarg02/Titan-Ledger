package com.titan.ledger.service;

import com.titan.ledger.dto.CreateTransactionRequest;
import com.titan.ledger.dto.LedgerEntryRequest;
import com.titan.ledger.model.*;
import com.titan.ledger.repository.LedgerEntryRepository;
import com.titan.ledger.repository.TransactionRepository;
import com.titan.ledger.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LedgerService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final LedgerEntryRepository ledgerEntryRepository;


    @Transactional
    public UUID createTransaction(CreateTransactionRequest request) {

        UUID transactionId = UUID.randomUUID();

        Transaction transaction =
                new Transaction(
                        transactionId,
                        request.getIdempotencyKey(),
                        request.getReference()
                );


        try {
            transactionRepository.save(transaction);
        } catch (DataIntegrityViolationException ex) {
            Transaction existing =
                    transactionRepository
                            .findByIdempotencyKey(request.getIdempotencyKey())
                            .orElseThrow();
            return existing.getTransactionId();

        }

        long total =
                request.getLedgerEntryRequestList()
                        .stream()
                        .mapToLong(LedgerEntryRequest::getAmount)
                        .sum();

        if (total != 0) {
            throw new RuntimeException("Invalid transaction: sum must be zero");

        }

        List<UUID> walletIds =
                request.getLedgerEntryRequestList()
                        .stream()
                        .map(LedgerEntryRequest::getWalletId)
                        .distinct()
                        .sorted()
                        .toList();

        Map<UUID, Wallet> walletMap = new HashMap<>();
        for (UUID walletId : walletIds) {
            Wallet wallet =
                    walletRepository
                            .findByWalletId(walletId)
                            .orElseThrow(() ->
                                    new RuntimeException("Wallet not found"));

            walletMap.put(walletId, wallet);

        }

        for (LedgerEntryRequest entry : request.getLedgerEntryRequestList()) {
            if (entry.getAmount() < 0) {
                Wallet wallet = walletMap.get(entry.getWalletId());
                long newBalance = wallet.getBalance() + entry.getAmount();
                if (newBalance < 0) {
                    throw new RuntimeException("Insufficient balance");
                }
            }
        }

        for (LedgerEntryRequest entry : request.getLedgerEntryRequestList()) {
            LedgerEntry ledgerEntry =
                    new LedgerEntry(
                            UUID.randomUUID(),
                            transactionId,
                            entry.getWalletId(),
                            entry.getAmount(),
                            entry.getAmount() < 0 ? LedgerEntryType.DEBIT : LedgerEntryType.CREDIT
                    );

            ledgerEntryRepository.save(ledgerEntry);

        }


        for (LedgerEntryRequest entry : request.getLedgerEntryRequestList()){
            Wallet wallet = walletRepository.findByWalletId(entry.getWalletId()).get();
            wallet.updateBalance(wallet.getBalance() + entry.getAmount());
        }


        transaction.updateState(TransactionState.POSTED);
        return transactionId;

    }

}