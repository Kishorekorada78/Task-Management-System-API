package com.kishore.taskproject.service;

import com.kishore.taskproject.entity.RefreshToken;
import com.kishore.taskproject.entity.Users;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(Users user);
    
    RefreshToken verifyExpiration(RefreshToken token);

    RefreshToken findByToken(String token);
    
    void deleteByToken(String token);
}