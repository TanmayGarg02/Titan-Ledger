package com.titan.ledger.controller;

import com.titan.ledger.dto.CreateTransactionRequest;
import com.titan.ledger.service.LedgerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerService ledgerService;

    @PostMapping
    public UUID createTransaction(@Valid @RequestBody CreateTransactionRequest transactionRequest){
        return ledgerService.createTransaction(transactionRequest);
    }
}
