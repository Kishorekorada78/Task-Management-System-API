package com.kishore.taskproject.serviceImpl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kishore.taskproject.entity.RefreshToken;
import com.kishore.taskproject.entity.Users;
import com.kishore.taskproject.repository.RefreshTokenRepository;
import com.kishore.taskproject.service.RefreshTokenService;

@Service
@Transactional
public class RefreshTokenServiceImpl
        implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepo;

    @Override
    public RefreshToken createRefreshToken(Users user) {

        System.out.println("Deleting old refresh token");

        refreshTokenRepo.deleteByUser(user);

        System.out.println("Old token deleted");

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(
                Instant.now().plusSeconds(7 * 24 * 60 * 60));

        return refreshTokenRepo.save(refreshToken);
    }

	@Override
	public RefreshToken verifyExpiration(RefreshToken token) {
		 if(token.getExpiryDate()
		            .compareTo(
		                    Instant.now()) < 0) {

		        refreshTokenRepo.delete(token);

		        throw new RuntimeException(
		                "Refresh Token Expired");
		    }

		    return token;
	}

	@Override
	public RefreshToken findByToken(String token) {
		 return refreshTokenRepo
		            .findByToken(token)
		            .orElseThrow(
		                    () -> new RuntimeException(
		                            "Refresh Token not found"));
	}
	
	@Override
	public void deleteByToken(String token) {

	    refreshTokenRepo.deleteByToken(token);
	}
}