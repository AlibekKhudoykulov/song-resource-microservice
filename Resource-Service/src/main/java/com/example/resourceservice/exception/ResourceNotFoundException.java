package com.example.resourceservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResourceNotFoundException extends ResponseStatusException {
    public ResourceNotFoundException() {
        super(HttpStatus.NOT_FOUND, "The resource with the specified id does not exist");
    }
}
