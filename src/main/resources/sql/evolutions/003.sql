--liquibase formatted sql
--changeset jaroslaw.sularz:3

CREATE TABLE job_offers(
    id BIGINT PRIMARY KEY ,
    title VARCHAR(60) NOT NULL ,
    description text NOT NULL ,
    salary VARCHAR(15),
    company VARCHAR(30)
);

INSERT INTO job_offers VALUES ((SELECT nextval('hibernate_sequence')), 'Junior Java Developer',
                               'Lorem Ipsum is simply dummy text of the printing and ' ||
                               'typesetting industry. Lorem Ipsum has been the industry''s ' ||
                               'standard dummy text ever since the 1500s, when an unknown ' ||
                               'printer took a galley of type and scrambled it to make a ' ||
                               'type specimen book. It has survived not only five centuries, ' ||
                               'but also the leap into electronic typesetting, ' ||
                               'remaining essentially unchanged. It was popularised ' ||
                               'in the 1960s with the release of Letraset sheets containing ' ||
                               'Lorem Ipsum passages, and more recently with desktop ' ||
                               'publishing software like Aldus PageMaker including versions ' ||
                               'of Lorem Ipsum.', '3500 - 4500 net','Solsoft Sp. Z.O.O' );

INSERT INTO job_offers VALUES ((SELECT nextval('hibernate_sequence')), 'Java Developer',
                               'Lorem Ipsum is simply dummy text of the printing and ' ||
                               'typesetting industry. Lorem Ipsum has been the industry''s ' ||
                               'standard dummy text ever since the 1500s, when an unknown ' ||
                               'printer took a galley of type and scrambled it to make a ' ||
                               'type specimen book. It has survived not only five centuries, ' ||
                               'but also the leap into electronic typesetting, ' ||
                               'remaining essentially unchanged. It was popularised ' ||
                               'in the 1960s with the release of Letraset sheets containing ' ||
                               'Lorem Ipsum passages, and more recently with desktop ' ||
                               'publishing software like Aldus PageMaker including versions ' ||
                               'of Lorem Ipsum.', '5000 - 7500 net','Solsoft Sp. Z.O.O' );

INSERT INTO job_offers VALUES ((SELECT nextval('hibernate_sequence')), 'Senior Java Developer',
                               'Lorem Ipsum is simply dummy text of the printing and ' ||
                               'typesetting industry. Lorem Ipsum has been the industry''s ' ||
                               'standard dummy text ever since the 1500s, when an unknown ' ||
                               'printer took a galley of type and scrambled it to make a ' ||
                               'type specimen book. It has survived not only five centuries, ' ||
                               'but also the leap into electronic typesetting, ' ||
                               'remaining essentially unchanged. It was popularised ' ||
                               'in the 1960s with the release of Letraset sheets containing ' ||
                               'Lorem Ipsum passages, and more recently with desktop ' ||
                               'publishing software like Aldus PageMaker including versions ' ||
                               'of Lorem Ipsum.', '8000 - up net','Solsoft Sp. Z.O.O' );
--rollback drop table job_offers