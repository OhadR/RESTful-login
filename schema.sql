create table `restful-login`.sec_token (
  username VARCHAR(256) NOT NULL,
  access_token VARCHAR(256),
  refresh_token VARCHAR(256),
  expiration_date DATETIME,
  PRIMARY KEY (`username`)
);