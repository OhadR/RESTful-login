package com.ohadr.rest_login.repository;

import java.sql.Types;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepositoryJdbc implements TokenRepository, InitializingBean
{
	private static final String TABLE_NAME = "sec_token";

	private static final String TOKEN_FIELDS = "USERNAME, "
			+ "ACCESS_TOKEN, "
			+ "EXPIRATION_DATE, "
			+ "REFRESH_TOKEN";

	private static final String DEFAULT_USER_INSERT_STATEMENT = "insert into " + TABLE_NAME + "(" + TOKEN_FIELDS
			+ ") values (?,?,?,?)";

	@Autowired
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	@Override
	public void afterPropertiesSet() throws Exception 
	{
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void storeAccessToken(String username, String accessToken, Date expirationDate, String refreshToken) 
	{
		int rowsUpdated = jdbcTemplate.update(DEFAULT_USER_INSERT_STATEMENT,
				new Object[] {
					username,
					accessToken,
					new java.sql.Date(expirationDate.getTime()), 
					refreshToken},
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR });

		if(rowsUpdated != 1)
		{
			throw new RuntimeException("could not insert new entry to DB");
		}
		
	}

}
