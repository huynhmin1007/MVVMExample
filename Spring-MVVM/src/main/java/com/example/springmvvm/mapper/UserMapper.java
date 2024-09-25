package com.example.springmvvm.mapper;

import com.example.springmvvm.dto.UserDTO;
import com.example.springmvvm.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface UserMapper {

    UserDTO userToUserDTO(User user);
    List<UserDTO> userToUserDTOList(List<User> users);
}
