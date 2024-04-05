package com.bytebandits.fintrackbackend.dto;

import com.bytebandits.fintrackbackend.model.Transaction;

import java.time.LocalDate;
import java.util.List;

public class CalendarDayTransactionDTO {
    private LocalDate transactionDate;
    private Long totalIncome;
    private Long totalExpense;
    private Long totalAmount;
    private List<TransactionDTO> allTransactions;

    public CalendarDayTransactionDTO(LocalDate transactionDate, Long totalIncome, Long totalExpense, Long totalAmount) {
        this.transactionDate = transactionDate;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.totalAmount = totalAmount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Long totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Long getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Long totalExpense) {
        this.totalExpense = totalExpense;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }


    public List<TransactionDTO> getAllTransactions() {
        return allTransactions;
    }

    public void setAllTransactions(List<TransactionDTO> allTransactions) {
        this.allTransactions = allTransactions;
    }

    public void setTransactions(List<Transaction> transactionsForDay) {}
}
