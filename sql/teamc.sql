drop schema if exists public;
CREATE SCHEMA `public` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use public;

drop table if exists public.meishi_info;
CREATE TABLE `public`.`meishi_info` (
  `meishi_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL,
  `yomigana` VARCHAR(50) NULL,
  `tel` VARCHAR(20) NULL,
  `mail` VARCHAR(255) NULL,
  `company` VARCHAR(50) NULL,
  `position` VARCHAR(30) NULL,
  `department` VARCHAR(20) NULL,
  `address` VARCHAR(100) NULL,
  `url` VARCHAR(100) NULL,
  `picture_path` VARCHAR(255) NULL,
  `user_id` INT(11) NOT NULL,
  `memo` VARCHAR(500) NULL,
  `create_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` INT(11) NOT NULL,
  PRIMARY KEY (`meishi_id`),
  UNIQUE INDEX `meishi_id_UNIQUE` (`meishi_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;
  
drop table if exists public.contact;
CREATE TABLE `public`.`contact` (
  `meishi_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `create_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`meishi_id`, `user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

drop table if exists public.user;
CREATE TABLE `public`.`user` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(50) NOT NULL,
  `login_id` VARCHAR(255) NOT NULL,
  `password_hash` VARCHAR(255) NOT NULL,
  `password_salt` VARCHAR(255) NOT NULL,
  `role_id` INT(11) NOT NULL,
  `create_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `USERID_UNIQUE` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `LOGINID_UNIQUE` (`login_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


CREATE TABLE SPRING_SESSION (
	PRIMARY_ID CHAR(36) NOT NULL,
	SESSION_ID CHAR(36) NOT NULL,
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	EXPIRY_TIME BIGINT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
	SESSION_PRIMARY_ID CHAR(36) NOT NULL,
	ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
	ATTRIBUTE_BYTES BLOB NOT NULL,
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

INSERT INTO `public`.`user` (`user_name`, `login_id`, `password_hash`, `password_salt`, `role_id`, `version`) VALUES ('kanri', 'kanri', '$2a$10$n15y3/zuVQVH4jseZveMQuYHhw3eI3U6iWldkLRQihYkvxTONHCN6', 'salt', '1', '1');
INSERT INTO `public`.`user` (`user_name`, `login_id`, `password_hash`, `password_salt`, `role_id`, `version`) VALUES ('ippan', 'ippan', '$2a$10$n15y3/zuVQVH4jseZveMQuYHhw3eI3U6iWldkLRQihYkvxTONHCN6', 'salt', '2', '1');

INSERT INTO `public`.`meishi_info` (`name`, `company`, `user_id`, `version`) VALUES ('test1', 'Test1', '1', '1');
INSERT INTO `public`.`meishi_info` (`name`, `company`, `user_id`, `version`) VALUES ('test2', 'Test2', '2', '1');
INSERT INTO `public`.`meishi_info` (`name`, `company`, `user_id`, `version`) VALUES ('test3', 'Test3', '1', '1');
INSERT INTO `public`.`meishi_info` (`name`, `company`, `user_id`, `version`) VALUES ('test4', 'Test4', '2', '1');

INSERT INTO `public`.`contact` (`meishi_id`, `user_id`) VALUES ('1', '1');
INSERT INTO `public`.`contact` (`meishi_id`, `user_id`) VALUES ('1', '2');
INSERT INTO `public`.`contact` (`meishi_id`, `user_id`) VALUES ('2', '2');
