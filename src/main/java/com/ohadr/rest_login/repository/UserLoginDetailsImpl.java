package com.ohadr.rest_login.repository;

import java.util.Date;

public class UserLoginDetailsImpl implements UserLoginDetails
{
	private String username;
	private String accessToken;
	private Date expirationDate;
	private String refreshToken;

	public UserLoginDetailsImpl(String username, String accessToken, Date expirationDate, String refreshToken)
	{
		this.username = username;
		this.accessToken = accessToken;
		this.expirationDate = expirationDate;
		this.refreshToken = refreshToken;
	}

}
