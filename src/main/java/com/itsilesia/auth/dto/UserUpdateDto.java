package com.itsilesia.auth.dto;

import com.itsilesia.auth.dto.validator.UniqueUsername;
import lombok.Getter;

import javax.validation.constraints.Email;
import java.util.List;

@Getter
public class UserUpdateDto implements UserDto {

    @UniqueUsername
    private String username;
    private String password;

    @Email
    private String email;
    private List<String> role;
}
