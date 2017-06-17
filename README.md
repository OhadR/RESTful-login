RESTful Login
===========

**The Application**

this application simulates RESTful login. which means completely stateless login. as REST declares, each call should be self contained,
so the login call validates the user and password, and returns a token. the client app should keep this token (privately!) and 
use it for each (secured) call to the server.

Unlike other projects of mine, here I use jersey and not spring's controllers.

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

