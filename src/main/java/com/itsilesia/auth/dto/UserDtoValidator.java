package com.itsilesia.auth.dto;

import com.itsilesia.auth.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserDtoValidator implements Validator {

    @Autowired
    private UserDao userDao;

    public boolean supports(Class cls) {
        return UserDto.class.equals(cls);
    }

    @Override
    public void validate(Object object, Errors errors) {

        UserDto userDto = (UserDto) object;
        if (userDao.findByUsername(userDto.getUsername()) != null) {
            errors.rejectValue("username", "username duplication");
        }

        if (userDao.findByEmail(userDto.getEmail()) != null) {
            errors.rejectValue("email", "email duplication");
        }
    }
}
