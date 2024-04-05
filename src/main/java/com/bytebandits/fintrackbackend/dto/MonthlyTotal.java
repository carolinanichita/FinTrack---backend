package com.bytebandits.fintrackbackend.dto;

import java.util.List;

public record MonthlyTotal(String monthName, String totalIncome, String totalExpense, List<WeeklyTotal> weeklyData) {}
