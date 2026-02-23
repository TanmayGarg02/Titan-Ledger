package com.titan.ledger.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateTransactionRequest {
    private String idempotencyKey;
    private String reference;
    private List<LedgerEntryRequest> ledgerEntryRequestList;
}
