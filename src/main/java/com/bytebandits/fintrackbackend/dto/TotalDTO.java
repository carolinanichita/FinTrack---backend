package com.bytebandits.fintrackbackend.dto;

public record TotalDTO(int monthTotalIncome, int monthTotalExpense, int monthTotalAmount) {
    public TotalDTO(int monthTotalIncome, int monthTotalExpense, int monthTotalAmount) {
        this.monthTotalIncome = monthTotalIncome;
        this.monthTotalExpense = monthTotalExpense;
        this.monthTotalAmount = monthTotalAmount;
    }
}
