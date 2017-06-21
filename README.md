RESTful Login
===========

**The Application**

this application simulates RESTful login. which means completely stateless login. as REST declares, each call should be self contained,
so the login call validates the user and password, and returns a token. the client app should keep this token (privately!) and 
use it for each (secured) call to the server.

Unlike other projects of mine, here I use jersey and not spring's controllers.

**HTTP**

in HTTP mode, where there is no TLS/SSL, the client and server exchange keys (basically the server 
sends the client the secret-key, upon registration). for each call, the client uses this key to 
sign the request - ALL the params in the request, including the URL and the http-type (why?  if you don't include the URI
or HTTP method in your HMAC calculation, it leaves you open to more hard-to-track man-in-the-middle attacks where an attacker
could modify the endpoint you are operating on as well as the HTTP method… for example change an HTTP POST to /issue/create to /user/delete. 
taken from the links in the bottom of this page).
The server receives the request, uses its similar key to do the same, and compares
the results. If equals, the request is valid. Otherwise there is suspicion that someone in the middle 
has changed the request.

**HTTPS**

in HTTPS mode we count on TLS/SSL that does the security thing for us; so all we have to do is to generate a secured token (and possibly in the future 
support also a refresh-token) and send it over the wire to the client. The client will identify himself with this token upon every (REST) call to 
the server. 
the class `TokenGenerator` uses `java.security.SecureRandom` to generate a `BigInteger` token.


** Database **

in this application, unlike all other examples, i chose NOT TO use DB-connection pool. So the data-source is a SimpleDriverDataSource.
(properties such as MARS_DB_PASSWORD are read from environment-variables)

```
	<bean id="dataSource" class="org.springframework.jdbc.datasource.SimpleDriverDataSource">
		<property name="driverClass" value="com.mysql.jdbc.Driver" />
		<property name="url"
			value="jdbc:mysql://${MARS_DB_HOST}:3306/${MARS_DB_NAME}?allowMultiQueries=true&amp;characterEncoding=UTF-8" />
		<property name="username" value="${MARS_DB_USER}" />		<!-- OhadDB -->
		<property name="password" value="${MARS_DB_PASSWORD}" />	<!-- ohad0921 -->
	</bean>
```

useful related links:


------------

"Designing a Secure REST (Web) API without OAuth":
http://acaasia.blogspot.co.il/2013/04/designing-secure-rest-web-api-without.html
http://mydevnote.com/designing-a-secure-rest-web-api-without-oauth/
https://www.ida.liu.se/~TDP024/labs/hmacarticle.pdf

https://stackoverflow.com/questions/14043397/http-basic-authentication-instead-of-tls-client-certification

https://stackoverflow.com/questions/13916620/rest-api-login-pattern


https://stackoverflow.com/questions/26658286/universal-way-to-authenticate-clients-and-secure-a-restful-api

What I gather is:
1.	The application must require SSL requests, so a GET at "http://myapi.com/users/1" should be rejected with a bad request response letting the developer know https is required.
2.	An app key / secret must be supplied by the client to verify who they are.
3.	SSL + certificates is a good idea
4.	Require a nonce value
5.	When a client registers their app, require input of URL and IP that they will send requests from to verify upon receiving a request. My concern with this has been the portability of an external app, i.e. app is moved to new server with different IP and now it doesn't work.
