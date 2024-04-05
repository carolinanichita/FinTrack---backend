package com.bytebandits.fintrackbackend.dto;

import java.util.UUID;

public record LoginResponseDto(String token, UUID userId) {}
