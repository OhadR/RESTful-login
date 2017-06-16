package com.ohadr.rest_login.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ohadr.crypto.service.CryptoService;

import java.io.IOException;

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
		
		String encodedString = cryptoService.generateEncodedString("ohad is the man!");
		return Response.ok().entity( encodedString ).build();	
	}
}