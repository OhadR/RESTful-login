create table `restful-login`.sec_token (
  access_token VARCHAR(256),
  username VARCHAR(256) NOT NULL,
  refresh_token VARCHAR(256),
  expiration_date DATETIME,
  PRIMARY KEY (`username`)
);