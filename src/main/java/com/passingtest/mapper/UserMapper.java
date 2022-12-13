package com.passingtest.mapper;

import com.passingtest.dto.AnswerDTO;
import com.passingtest.dto.UserDTO;
import com.passingtest.model.entity.User;

public class UserMapper {
    public static UserDTO toDto(User user) {
        return new UserDTO(user.getId(), user.getName());
    }
}
