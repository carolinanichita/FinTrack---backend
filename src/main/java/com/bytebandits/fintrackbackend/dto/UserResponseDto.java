package com.bytebandits.fintrackbackend.dto;

import java.util.UUID;

public record UserResponseDto(UUID id, String email, String password) {}