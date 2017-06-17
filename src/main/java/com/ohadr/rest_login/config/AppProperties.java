package com.ohadr.rest_login.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties
{
	@Value("${com.ohadr.restful-login.access-token-expiration-minutes}")
	private int accessTokenExpirationMinutes;

	
	public int getAccessTokenExpirationMinutes()
	{
		return accessTokenExpirationMinutes;
	}

	public void setAccessTokenExpirationMinutes(int accessTokenExpirationMinutes)
	{
		this.accessTokenExpirationMinutes = accessTokenExpirationMinutes;
	}
	
}
