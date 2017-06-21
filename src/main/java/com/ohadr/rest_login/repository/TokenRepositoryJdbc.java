package com.ohadr.rest_login.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepositoryJdbc implements TokenRepository, InitializingBean
{
	private static Logger log = Logger.getLogger(TokenRepositoryJdbc.class);

	private static final String TABLE_NAME = "sec_token";

	private static final String TOKEN_FIELDS = "USERNAME, "
			+ "ACCESS_TOKEN, "
			+ "EXPIRATION_DATE, "
			+ "REFRESH_TOKEN";

	private static final String USER_INSERT_STATEMENT = "insert into " + TABLE_NAME + "(" + TOKEN_FIELDS
			+ ") values (?,?,?,?)";

	private static final String USER_SELECT_STATEMENT = "select " + TOKEN_FIELDS
			+ " from " + TABLE_NAME + " where USERNAME = ?";

	private static final String UPDATE_ACCESS_TOKEN_STATEMENT = "update " + TABLE_NAME + 
			" set ACCESS_TOKEN = ?, EXPIRATION_DATE = ? where USERNAME = ?";

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
		log.info("creating new entry for user " + username);
		
//		UserLoginDetails uld = new UserLoginDetailsImpl(username, accessToken, expirationDate, refreshToken);
		int rowsUpdated = jdbcTemplate.update(USER_INSERT_STATEMENT,
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

	@Override
	public UserLoginDetails loadUserLoginDetailsByUsername(String username) 
	{
		UserLoginDetails userFromDB = null;
		try
		{
			userFromDB = jdbcTemplate.queryForObject(USER_SELECT_STATEMENT, 
					new UserLoginDetailsRowMapper(), username);
		}
		catch (EmptyResultDataAccessException e) 
		{
			log.info("no record was found for user=" + username);
		}
	
		return userFromDB;
	}

	
	private static class UserLoginDetailsRowMapper implements RowMapper<UserLoginDetails>
	{
		public UserLoginDetails mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			UserLoginDetails user = new UserLoginDetailsImpl(
					rs.getString(1),		//username / email
					rs.getString(2),		//access-token
					rs.getDate(3),			//exp.date
					rs.getString(4)			//attempts left
					);
			
			return user;
		}
	}

	
	@Override
	public void updateAccessToken(String username, String newAccessToken, Date newExpirationDate) 
	{
		log.info("updating entry for user " + username);
		int count = jdbcTemplate.update(UPDATE_ACCESS_TOKEN_STATEMENT, 
				newAccessToken,
				new java.sql.Timestamp(newExpirationDate.getTime()), 		// https://stackoverflow.com/a/2400992/421642
				username);
		if (count != 1)
		{
			throw new NoSuchElementException("No user with email: " + username);
		}
	}

}
