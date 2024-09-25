package com.example.springmvvm.service;

import com.example.springmvvm.dto.UserDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {

    UserDTO login(String email, String password);

    List<UserDTO> findAll(Pageable pageable);
}
