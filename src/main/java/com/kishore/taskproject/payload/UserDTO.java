package com.kishore.taskproject.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
  private long id;
  private String name;
  private String email;
  private String password;
  
}
