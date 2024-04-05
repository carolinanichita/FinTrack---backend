package com.bytebandits.fintrackbackend.dto;

import java.util.UUID;

public record AllCategoriesResponseDTO(UUID categoryId, String categoryName, String transactionType) {}
