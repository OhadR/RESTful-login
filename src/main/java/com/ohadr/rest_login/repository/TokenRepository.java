package com.ohadr.rest_login.repository;

import java.util.Date;

public interface TokenRepository 
{

	void storeAccessToken(String username, String encodedString, Date expirationDate, String refreshToken);

}
