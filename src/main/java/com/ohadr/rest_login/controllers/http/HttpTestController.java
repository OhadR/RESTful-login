package com.ohadr.rest_login.controllers.http;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ohadr.crypto.service.CryptoService;


/**
 * in http mode, where there is no TLS/SSL, the client and server exchange keys (basically the server 
 * sends the client the secret-key, upon registration). for each call, the client uses this key to 
 * sign the request - ALL the params in the request, including the URL and the http-type (see README 
 * for explanations). The server receives the request, uses its similar key to do the same, and compares
 * the results. If equals, the request is valid. Otherwise there is suspicion that someone in the middle 
 * has changed the request.
 * @author ohadr
 *
 */
@Controller
@Path("/httpTest")
public class HttpTestController 
{
	@Autowired
	private CryptoService	cryptoService;

	/**
	 * we encrypt ALL params, including the URL and the type, and compare.
	 * @param authString
	 * @param param1
	 * @param param2
	 * @return
	 * @throws IOException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response httpRequest(
			@HeaderParam("Signature") String authString,
			@FormParam("param1") String param1,
			@FormParam("param2") String param2) throws IOException
	{
		String whatToEncode = restParamsToEncode("login", HttpMethod.POST, param1, param2);			
		String encodedString = cryptoService.createEncodedContent(whatToEncode);
		if( !StringUtils.equals(encodedString, authString) )
		{
			//given signature is not good!
			return Response.notAcceptable(null).entity( "signature does not match the request" ).build();
		}
		else
		{
			return Response.ok().entity( "encodedString.toString()" ).build();	
		}
	}
	
	private static String restParamsToEncode(String url,
			String httpMethod,
			String ... params)
	{
		String retVal = url + httpMethod;
		for(String param : params)
		{
			retVal += param;
		}
		return retVal;
		
	}
}
