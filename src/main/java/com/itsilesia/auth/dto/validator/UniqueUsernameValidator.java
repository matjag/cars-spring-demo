package com.itsilesia.auth.dto.validator;

import com.itsilesia.auth.dao.UserDao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private UserDao userDao;

    public UniqueUsernameValidator(UserDao userDao){
        this.userDao = userDao;
    }

    public boolean isValid(String username, ConstraintValidatorContext context){
        return userDao.findByUsername(username) == null;
    }
}
