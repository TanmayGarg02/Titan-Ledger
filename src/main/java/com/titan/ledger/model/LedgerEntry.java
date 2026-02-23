package com.titan.ledger.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "ledger_entries",
        indexes = {
                @Index(name = "idx_wallet_id", columnList = "wallet_id"),
                @Index(name = "idx_transaction_id", columnList = "transaction_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LedgerEntry {

    @Id
    @Column(name = "entry_id", nullable = false, updatable = false)
    private UUID entryId;

    @Column(name = "transaction_id", nullable = false, updatable = false)
    private UUID transactionId;

    @Column(name = "wallet_id", nullable = false, updatable = false)
    private UUID walletId;

    @Column(name = "amount", nullable = false, updatable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "entry_type", nullable = false, updatable = false)
    private LedgerEntryType entryType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;


    public LedgerEntry(
            UUID entryId,
            UUID transactionId,
            UUID walletId,
            Long amount,
            LedgerEntryType entryType
    ) {

        this.entryId = entryId;
        this.transactionId = transactionId;
        this.walletId = walletId;
        this.amount = amount;
        this.entryType = entryType;
        this.createdAt = Instant.now();
    }
}
