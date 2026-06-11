package com.kishore.taskproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kishore.taskproject.entity.Users;

@Repository
public interface UserRepositary  extends JpaRepository<Users,Long>{

	
	Users save(Users user);

	 Optional<Users> findByEmail(String email);


}
