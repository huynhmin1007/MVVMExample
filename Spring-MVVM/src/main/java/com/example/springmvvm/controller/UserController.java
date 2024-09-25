package com.example.springmvvm.controller;

import com.example.springmvvm.dto.ApiResponse;
import com.example.springmvvm.dto.UserDTO;
import com.example.springmvvm.service.impl.UserService;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<UserDTO>> signIn(
            @RequestParam @Email String email,
            @RequestParam String password
    ) {
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Đăng nhập thành công",
                userService.login(email, password)));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<UserDTO>>> findAll(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit,
            @RequestParam(defaultValue = "id", required = false) String orderBy,
            @RequestParam(defaultValue = "asc", required = false) String orderDir
    ) {
        Pageable pageable = PageRequest.of(page, limit,
                Sort.by(new Sort.Order(Sort.Direction.fromString(orderDir), orderBy)));

        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                userService.findAll(pageable)));
    }
}
