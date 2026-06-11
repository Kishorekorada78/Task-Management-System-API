package com.kishore.taskproject.service;

import org.springframework.stereotype.Service;

import com.kishore.taskproject.payload.UserDTO;

@Service
public interface UserService {
     public UserDTO createUser(UserDTO userdto);
}
