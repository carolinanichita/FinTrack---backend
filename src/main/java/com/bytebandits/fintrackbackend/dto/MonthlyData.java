package com.bytebandits.fintrackbackend.dto;

import java.time.LocalDate;
import java.util.List;


public record MonthlyData(int month, List<WeekData> weeks) {}
