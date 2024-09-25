package com.example.springmvvm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalController {

    @GetMapping
    public ResponseEntity<String> connect() {
        return ResponseEntity.ok("Connect success");
    }
}
