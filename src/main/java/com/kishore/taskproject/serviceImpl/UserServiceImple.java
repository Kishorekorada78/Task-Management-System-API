package com.kishore.taskproject.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kishore.taskproject.entity.Users;
import com.kishore.taskproject.exception.ApiException;
import com.kishore.taskproject.payload.UserDTO;
import com.kishore.taskproject.repository.UserRepositary;
import com.kishore.taskproject.service.UserService;

@Service
public class UserServiceImple implements UserService{

	@Autowired
	  private UserRepositary userRepo;
	
	@Autowired
	private PasswordEncoder passworsEncoder;
	
	@Override
	public UserDTO createUser(UserDTO userdto) {
		// userDTO is not an entity of Users
	   if(userRepo.findByEmail(userdto.getEmail()).isPresent())
		   throw new ApiException("Email already exists");
		userdto.setPassword(passworsEncoder.encode(userdto.getPassword()));
		Users user=userDtoToEntity(userdto);
		Users savedUser=userRepo.save(user);
		return entityToUserDto(savedUser);
	}
	private Users userDtoToEntity(UserDTO userdto) {
		Users users=new Users();
		users.setName(userdto.getName());
		users.setEmail(userdto.getEmail());;
		users.setPassword(userdto.getPassword());
		users.setRole("ROLE_USER");
		return users;
	}
    private UserDTO entityToUserDto(Users savedUser) {
    	UserDTO userdto=new UserDTO();
    	userdto.setId(savedUser.getId());
    	userdto.setEmail(savedUser.getEmail());
    	userdto.setName(savedUser.getName());
    	return userdto;
    }
}
