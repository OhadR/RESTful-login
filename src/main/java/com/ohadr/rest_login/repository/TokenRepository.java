package com.ohadr.rest_login.repository;

import java.util.Date;

public interface TokenRepository 
{
	void storeAccessToken(String username, String encodedString, Date expirationDate, String refreshToken);
	
	UserLoginDetails loadUserLoginDetailsByUsername(String username);

	void updateAccessToken(String username, String newAccessToken, Date newExpirationDate);
}
