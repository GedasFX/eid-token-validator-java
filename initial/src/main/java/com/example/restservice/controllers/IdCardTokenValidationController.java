package com.example.restservice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import

@RestController
public class IdCardTokenValidationController {

    @PostMapping("/validate")
    public ValidationResult validate( authToken) {
        var a = new I
        return new ValidationResult();
    }
}

