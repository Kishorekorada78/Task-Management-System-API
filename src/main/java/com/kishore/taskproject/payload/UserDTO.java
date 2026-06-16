package com.kishore.taskproject.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
  private long id;
  
  @NotBlank(message="Name cannot be empty")
  private String name;
  
  @Email(message="Invalid email format")
  private String email;
  
  @Size(min=6,max=12,message="Password must be at least 6 characters")
  private String password;

  
}
