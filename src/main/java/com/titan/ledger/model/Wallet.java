package com.titan.ledger.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "wallets")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Wallet {
    @Id
    @Column(name = "wallet_id", nullable = false, updatable = false)
    private UUID walletId;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Wallet(UUID walletId, UUID ownerId, String currency) {

        this.walletId = walletId;
        this.ownerId = ownerId;
        this.currency = currency;
        this.balance = 0L;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void updateBalance(Long balance) {
        this.balance = balance;
        this.updatedAt = Instant.now();
    }

}
