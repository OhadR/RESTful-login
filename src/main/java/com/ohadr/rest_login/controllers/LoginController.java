package com.ohadr.rest_login.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ohadr.crypto.service.CryptoService;
import com.ohadr.rest_login.repository.TokenRepository;

import java.io.IOException;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Controller
@Path("/login")
public class LoginController
{
	@Autowired
	private CryptoService	cryptoService;

	@Autowired
	TokenRepository			tokenRepo;
	
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
		
		Object email = "ohad is the man!";
		//token contains:
		//1. expiry
		//2. authorization
		Date expirationDate = new Date();
		String encodedString = cryptoService.createEncodedContent(expirationDate, email);
		String refreshToken = null;
		tokenRepo.storeAccessToken(username, encodedString, expirationDate, refreshToken);
		return Response.ok().entity( encodedString ).build();	
	}
}