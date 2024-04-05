package com.bytebandits.fintrackbackend.dto;

import java.util.UUID;

public record AccountDTO(String accountName, int amount, String description, UUID userId) {}
