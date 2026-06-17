package com.kishore.taskproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.kishore.taskproject.entity.RefreshToken;
import com.kishore.taskproject.entity.Users;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long>{

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Transactional
    void deleteByUser(Users user);

    @Modifying
    @Transactional
    void deleteByToken(String token);
}