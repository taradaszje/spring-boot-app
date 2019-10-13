INSERT INTO roles (id,name,description) VALUES (1,'SUPER_USER','This user has ultimate rights for everything');
INSERT INTO roles (id,name,description) VALUES (2,'ADMIN_USER','This user has admin rights for administrative work');
INSERT INTO roles (id,name,description) VALUES (3,'SITE_USER','This user has access to site, after login - normal user');
insert into users (id,username,email,password,created_on,last_login,status) values (2,'yolo','tadziu@wp.pl','$2a$10$HtrZ3LAhagVEbnBc2bTxD.AwIbnNdTz5QFuf40jZ3JCxBp805XXi.',NOW(),null,true );
insert into users (id,username,email,password,created_on,last_login,status) values (3,'andrzej','tadziu2@wp.pl','$2a$10$OdScnNLIVFqnId7tXUYF.umgKSuR76bq9upwsNDpvdJ.e3U8L1vb6',NOW(),null,true);
insert into users_roles (user_id, role_id) values ('2','1');
insert into users_roles (user_id, role_id) values ('2','2');
insert into users_roles (user_id, role_id) values ('3','3');