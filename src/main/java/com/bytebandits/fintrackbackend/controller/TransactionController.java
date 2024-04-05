package com.bytebandits.fintrackbackend.controller;

import com.bytebandits.fintrackbackend.dto.CalendarDayTransactionDTO;
import com.bytebandits.fintrackbackend.dto.TotalDTO;
import com.bytebandits.fintrackbackend.dto.TransactionDTO;
import com.bytebandits.fintrackbackend.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "${frontend.url}")
@RequestMapping("/transaction")
public class TransactionController {
    TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/save")
    public ResponseEntity<TransactionDTO> saveTransaction(@RequestBody TransactionDTO transaction) {
        TransactionDTO savedTransaction = transactionService.saveTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
    }

    @GetMapping("/{year}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsYear(@PathVariable int year, @RequestParam UUID userId) {
        System.out.println(userId);
        List<TransactionDTO> transactionsDTO = transactionService.getTransactionsYear(year, userId);
        return ResponseEntity.ok(transactionsDTO);
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsYearAndMonth(@PathVariable int year, @PathVariable int month,@RequestParam UUID userId) {
        List<TransactionDTO> transactionsDTO = transactionService.getTransactionsYearAndMonth(year,month, userId);
        return ResponseEntity.ok(transactionsDTO);
    }

    @GetMapping("/{year}/{month}/total")
    public ResponseEntity<TotalDTO> getTransactionsYearAndMonthTotal(@PathVariable int year, @PathVariable int month, @RequestParam UUID userId) {
        TotalDTO transactionsDtos = transactionService.getTransactionsYearAndMonthTotal(year,month, userId);
        return ResponseEntity.ok(transactionsDtos);
    }

    @GetMapping("/calendar/{year}/{month}")
    public ResponseEntity<List<CalendarDayTransactionDTO>> getCalendarTransactionsYearAndMonth(@PathVariable int year, @PathVariable int month, @RequestParam UUID userId) {
        List<CalendarDayTransactionDTO> calendarTransactionsDtos = transactionService.getCalendarTransactionsYearAndMonth(year,month, userId);
        return ResponseEntity.ok(calendarTransactionsDtos);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getTransactionsTimePeriod(@RequestParam String startDate, @RequestParam String endDate, @RequestParam UUID userId) {
        List<TransactionDTO> transactionsDtos = transactionService.getTransactionsTimePeriod(startDate,endDate,userId);
        return ResponseEntity.ok(transactionsDtos);
    }
}
