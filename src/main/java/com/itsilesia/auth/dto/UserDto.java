package com.itsilesia.auth.dto;


import lombok.Data;
import java.util.List;

@Data
public class UserDto {

    private long id;
    private String username;
    private String password;
    private String email;
    private List<String> role;
}

