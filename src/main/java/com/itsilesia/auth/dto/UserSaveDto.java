package com.itsilesia.auth.dto;

import com.itsilesia.auth.dto.validator.UniqueUsername;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class UserSaveDto implements UserDto {

    @NotNull
    @UniqueUsername
    private String username;
    @NotNull
    private String password;
    @NotNull
    @Email
    private String email;

    private List<String> role;
}

