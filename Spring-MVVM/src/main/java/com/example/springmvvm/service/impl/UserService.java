package com.example.springmvvm.service.impl;

import com.example.springmvvm.dto.UserDTO;
import com.example.springmvvm.entity.User;
import com.example.springmvvm.exception.CodeException;
import com.example.springmvvm.mapper.UserMapper;
import com.example.springmvvm.repository.UserRepository;
import com.example.springmvvm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO login(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(CodeException.USER_NOT_FOUND::throwException);

        return userMapper.userToUserDTO(user);
    }

    @Override
    public List<UserDTO> findAll(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userMapper.userToUserDTOList(userPage.getContent());
    }
}
