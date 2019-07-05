package com.itsilesia.auth.controller;


import com.itsilesia.auth.dto.UserDto;
import com.itsilesia.auth.model.User;
import com.itsilesia.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Secured("ROLE_USER_LIST")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> list() {
        return userService.findAll();
    }


    @Secured("ROLE_USER_CREATE")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody UserDto user) {
        return userService.save(user);
    }

    @Secured("ROLE_USER_GET")
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable long id) {
        return userService.findOne(id);
    }


    @Secured("ROLE_USER_UPDATE")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User update(@RequestBody UserDto newUser, @PathVariable Long id) {
        return userService.update(newUser, id);
    }


    @Secured("ROLE_USER_DELETE")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) {
        userService.delete(id);
    }
}


