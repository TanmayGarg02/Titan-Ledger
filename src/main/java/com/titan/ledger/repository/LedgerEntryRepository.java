package com.titan.ledger.repository;

import com.titan.ledger.model.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, UUID> {

    List<LedgerEntry> findByWalletId(UUID walletId);

    List<LedgerEntry> findByTransactionId(UUID transactionId);

}