package com.ohadr.rest_login.controllers.https;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ohadr.rest_login.config.AppProperties;
import com.ohadr.rest_login.core.TokenGenerator;
import com.ohadr.rest_login.repository.TokenRepository;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Controller
@Path("/httpsTest")
public class HttpsTestController
{
	@Autowired
	private AppProperties			properties;
	
	@Autowired
	private TokenRepository			tokenRepo;
	
	private TokenGenerator			tokenGenerator = new TokenGenerator();
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(
			@FormParam("username") String username,
			@FormParam("password") String password) throws IOException
	{
//		try
//		{
//			olmfAuthentication.login(username, password);
//		}
//		catch(OlmfAuthenticationException oae)
//		{
//			return Response.status( Response.Status.UNAUTHORIZED ).entity("moshe moshe").build();		
//		}
		
		//token contains:
		//1. expiry
		//2. authorization

		String token = tokenGenerator.nextSessionId();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, properties.getAccessTokenExpirationMinutes());
	    Date expirationDate = calendar.getTime();

//		String encodedString = cryptoService.createEncodedContent(expirationDate, username);
		String refreshToken = null;
		tokenRepo.storeAccessToken(username, token, expirationDate, refreshToken);
		return Response.ok().entity( token ).build();	
	}
}