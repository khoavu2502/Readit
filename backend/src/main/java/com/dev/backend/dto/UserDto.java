package com.dev.backend.dto;

import com.dev.backend.entity.Post;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private String avatar;

    private List<RoleDto> roles;
}
