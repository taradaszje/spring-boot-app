--liquibase formatted sql
--changeset jaroslaw.sularz:2

CREATE TABLE verification_token(
    id BIGINT PRIMARY KEY ,
    expiry_date timestamp NOT NULL ,
    token VARCHAR(255) NOT NULL ,
    user_id BIGINT NOT NULL UNIQUE CONSTRAINT fk_users_id REFERENCES users(id)
);

--rollback drop table verification_token
