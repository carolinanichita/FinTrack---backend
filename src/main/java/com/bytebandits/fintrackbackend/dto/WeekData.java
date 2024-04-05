package com.bytebandits.fintrackbackend.dto;

import java.time.LocalDate;

public record WeekData(LocalDate start, LocalDate end, int order) {
}
