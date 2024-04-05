package com.bytebandits.fintrackbackend.dto;

import java.time.LocalDate;
import java.util.UUID;

public record TransactionDTO(int amount, String description, LocalDate date,  UUID userId, UUID categoryId, UUID accountId) {}
