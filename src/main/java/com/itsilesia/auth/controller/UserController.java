package com.itsilesia.auth.controller;

import com.itsilesia.auth.dto.UserSaveDto;
import com.itsilesia.auth.dto.UserUpdateDto;
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
    public List<User> list() {
        return userService.findAll();
    }

    @Secured("ROLE_USER_CREATE")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody UserSaveDto userSaveDto) {
        return userService.save(userSaveDto);
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
    public User update(@RequestBody UserUpdateDto userUpdateDto, @PathVariable Long id) {
        return userService.update(userUpdateDto, id);
    }

    @Secured("ROLE_USER_DELETE")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) {
        userService.delete(id);
    }
}


