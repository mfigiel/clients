package com.clients.api.controller;

import com.clients.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/transaction/{id}")
    public List<Object> getClientTransactions(@PathVariable("id") Long id) {
        return transactionService.getAllClientTransaction(id);
    }

    @GetMapping(value = "/transaction/{id}/{dateFrom}/{dateTo}")
    public List<Object> getClientInformation(@PathVariable("id") long id, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) {
        return transactionService.getAllClientTransactionWithDatePeriod(id, dateFrom.concat(" 00:00:00"), dateTo.concat(" 00:00:00"));
    }

}
