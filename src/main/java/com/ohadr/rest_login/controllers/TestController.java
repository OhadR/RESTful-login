package com.ohadr.rest_login.controllers;

import java.io.IOException;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ohadr.crypto.service.CryptoService;

@Controller
@Path("/test")
public class TestController 
{
	@Autowired
	private CryptoService	cryptoService;

	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(
			@HeaderParam("Authorization") String authString,
			@FormParam("action") String action) throws IOException
	{
		ImmutablePair<Date,String> encodedString = cryptoService.extractStringAndDate(authString);
		return Response.ok().entity( encodedString.toString() ).build();	
	}}
