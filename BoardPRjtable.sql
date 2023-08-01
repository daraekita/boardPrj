CREATE DATABASE boardPrj;

Create Table boardPrj.user(
	userId INT NOT NULL auto_increment,
    loginId varchar(250) NOT NULL,
    name varchar(250) NOT NULL,
    nickname varchar(250) NOT NULL,
    email varchar(250) NOT NULL,
    password varchar(250) NOT NULL,
    primary key(userId),
    unique key(email)
)