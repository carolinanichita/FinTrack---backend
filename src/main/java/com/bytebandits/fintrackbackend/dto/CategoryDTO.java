package com.bytebandits.fintrackbackend.dto;

import org.springframework.lang.NonNull;

import java.util.UUID;

public record CategoryDTO(String categoryName, String transactionType, UUID userId){}
