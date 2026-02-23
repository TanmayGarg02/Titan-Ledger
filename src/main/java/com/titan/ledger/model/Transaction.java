package com.titan.ledger.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "transactions",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_idempotency_key", columnNames = "idempotency_key")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction {

    @Id
    @Column(name = "transaction_id", nullable = false, updatable = false)
    private UUID transactionId;

    @Column(name = "idempotency_key", nullable = false, length = 100)
    private String idempotencyKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private TransactionState state;

    @Column(name = "reference")
    private String reference;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public Transaction(UUID transactionId, String idempotencyKey, String reference) {

        this.transactionId = transactionId;
        this.idempotencyKey = idempotencyKey;
        this.state = TransactionState.PENDING;
        this.reference = reference;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }


    public void updateState(TransactionState newState) {
        this.state = newState;
        this.updatedAt = Instant.now();
    }
}
