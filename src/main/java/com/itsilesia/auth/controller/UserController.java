package com.itsilesia.auth.controller;


import com.itsilesia.auth.dto.UserDto;
import com.itsilesia.auth.model.User;
import com.itsilesia.auth.service.AuthenticationFacadeService;
import com.itsilesia.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationFacadeService authenticationFacadeService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> list() {
        return userService.findAll();
    }


    @Secured({ROLE_ADMIN})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody UserDto user) {
        return userService.save(user);
    }

    @Secured({ROLE_ADMIN, ROLE_USER})
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable long id) {
        return userService.findOne(id);
    }

    @Secured({ROLE_ADMIN})
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) {
        userService.delete(id);
    }
}


