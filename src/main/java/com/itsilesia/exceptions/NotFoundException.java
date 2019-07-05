package com.itsilesia.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(Long id, Class<?> cls) {
        super("Could not find  " + cls.toString() + " of id " + id);
    }
}
