INSERT INTO roles (id,name,description) VALUES ((select nextval('hibernate_sequence')),'SUPER_USER','This user has ultimate rights for everything');
INSERT INTO roles (id,name,description) VALUES ((select nextval('hibernate_sequence')),'ADMIN_USER','This user has admin rights for administrative work');
INSERT INTO roles (id,name,description) VALUES ((select nextval('hibernate_sequence')),'SITE_USER','This user has access to site, after login - normal user');
insert into users (id,username,email,password,created_on,last_login,status) values ((select nextval('hibernate_sequence')),'admin','admin@admin.pl','$2a$10$.dpATOg5GaOSV2LtojMRvOmQDdsXhYR2WBbbQAkAuSJmna.dB2Z6a',NOW(),null,true);-- password: 12345
insert into users (id,username,email,password,created_on,last_login,status) values ((select nextval('hibernate_sequence')),'taradaszje','jarek.sularz@gmail.com','$2a$10$.dpATOg5GaOSV2LtojMRvOmQDdsXhYR2WBbbQAkAuSJmna.dB2Z6a',NOW(),null,true);
insert into users_roles (user_id, role_id) values ((select id from users where username='admin'),'2');
insert into users_roles (user_id, role_id) values ((select id from users where username='taradaszje'),'3');
-- todo tworzenie 3 tabel