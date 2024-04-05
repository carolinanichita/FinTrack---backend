package com.bytebandits.fintrackbackend.util;

import com.bytebandits.fintrackbackend.dto.UserDTO;
import com.bytebandits.fintrackbackend.model.User;

import java.util.List;

public class Converter {
    private Converter() {
    }
    public static UserDTO toUserResponseDto(User user) {
        return new UserDTO(
                user.getEmail(),
                user.getPassword()
        );
    }
    public static List<UserDTO> toUserResponseDtoList(List<User> users) {
        return users.stream().map(Converter::toUserResponseDto).toList();
    }
}
