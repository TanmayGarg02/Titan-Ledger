package com.titan.ledger.controller;

import com.titan.ledger.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping
    public UUID createWallet(@RequestParam UUID ownerId, @RequestParam String currency) {
        return walletService.createWallet(ownerId, currency);
    }

    @GetMapping("/{walletId}/balance")
    public Long getBalance(@PathVariable UUID walletId) {
        return walletService.getBalance(walletId);
    }

}
