
-- drop database wreckcontrol;
-- create database wreckcontrol;
--
-- GRANT USAGE ON *.* TO wreckcontrol IDENTIFIED BY '42' REQUIRE NONE;
-- create database wreckcontrol;
-- GRANT Select  ON wreckcontrol.* TO wreckcontrol;
--
-- use wreckcontrol;
create table persistent_logins (username varchar(64) not null, series varchar(64) primary key, token varchar(64) not null, last_used timestamp not null);