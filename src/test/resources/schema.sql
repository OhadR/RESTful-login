create table `restful-login`.sec_token (
  ID BIGINT(20) NOT NULL AUTO_INCREMENT,
  access_token VARCHAR(256),
  username VARCHAR(256),
  refresh_token VARCHAR(256),
  expiration_date DATETIME,
  PRIMARY KEY (`ID`)
);