package com.titan.ledger.service;

import com.titan.ledger.exception.WalletNotFoundException;
import com.titan.ledger.model.Wallet;
import com.titan.ledger.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;

    public UUID createWallet(UUID ownerId, String currency){
        UUID walletId = UUID.randomUUID();

        Wallet wallet = new Wallet(walletId, ownerId, currency);
        walletRepository.save(wallet);
        return walletId;

    }

    public Long getBalance(UUID walletId) {
        Wallet wallet = walletRepository
                        .findById(walletId)
                        .orElseThrow(() ->
                                new WalletNotFoundException(
                                        "Wallet not found: " + walletId
                                )
                        );
        return wallet.getBalance();

    }

    public Wallet getWallet(UUID walletId) {
        return walletRepository
                .findById(walletId)
                .orElseThrow(() ->
                        new WalletNotFoundException(
                                "Wallet not found: " + walletId
                        )
                );
    }
}
