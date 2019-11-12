--liquibase formatted sql
--changeset jaroslaw.sularz:1

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence;

CREATE TABLE roles(
    id BIGINT PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL ,
    description VARCHAR(150)
);

CREATE TABLE users(
    id BIGINT PRIMARY KEY ,
    email VARCHAR(60) UNIQUE NOT NULL ,
    password VARCHAR(255) NOT NULL ,
    created_on timestamp WITH TIME ZONE NOT NULL ,
    last_login timestamp WITH TIME ZONE,
    status boolean default FALSE
);

CREATE TABLE users_roles(
    user_id BIGINT NOT NULL CONSTRAINT fk_users_id REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL CONSTRAINT fk_roles_id REFERENCES roles(id) ON DELETE CASCADE
);

INSERT INTO roles (id,name,description) VALUES ((SELECT nextval('hibernate_sequence')),'SUPER_USER','This user has ultimate rights for everything');
INSERT INTO roles (id,name,description) VALUES ((SELECT nextval('hibernate_sequence')),'ADMIN_USER','This user has admin rights for administrative work');
INSERT INTO roles (id,name,description) VALUES ((SELECT nextval('hibernate_sequence')),'SITE_USER','This user has access to site, after login - normal user');
--PASSWORD : 12345
INSERT INTO users (id,email,password,created_on,last_login,status) VALUES ((SELECT nextval('hibernate_sequence')),'admin@admin.pl','$2a$10$.dpATOg5GaOSV2LtojMRvOmQDdsXhYR2WBbbQAkAuSJmna.dB2Z6a',NOW(),null,true);
INSERT INTO users (id,email,password,created_on,last_login,status) VALUES ((SELECT nextval('hibernate_sequence')),'jarek.sularz@gmail.com','$2a$10$.dpATOg5GaOSV2LtojMRvOmQDdsXhYR2WBbbQAkAuSJmna.dB2Z6a',NOW(),null,true);
INSERT INTO users_roles (user_id, role_id) VALUES ((SELECT id FROM users WHERE email='admin@admin.pl'),(SELECT id FROM roles WHERE name='ADMIN_USER'));
INSERT INTO users_roles (user_id, role_id) VALUES ((SELECT id FROM users WHERE email='jarek.sularz@gmail.com'),(SELECT id FROM roles WHERE name='SITE_USER'));

--rollback drop table users_role;
--rollback drop table roles cascade;
--rollback drop table users cascade;
