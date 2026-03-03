package com.titan.ledger.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class LedgerEntryRequest {
    private UUID walletId;
    private Long amount;
}
