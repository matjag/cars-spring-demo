package com.itsilesia.auth.service;

import com.itsilesia.auth.dto.UserDto;
import com.itsilesia.auth.model.User;

import java.util.List;

public interface UserService {

    User save(UserDto user);
    List<UserDto> findAll();
    User findOne(long id);
    void delete(long id);
}