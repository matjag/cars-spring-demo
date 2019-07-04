INSERT INTO test.roles(description,name) values ('Admin', 'ADMIN');
INSERT INTO test.roles(description,name) values ('User', 'USER');
INSERT INTO test.users (email,password,username) values ('admin@gmail.com','$2a$04$EZzbSqieYfe/nFWfBWt2KeCdyq0UuDEM1ycFF8HzmlVR6sbsOnw7u','admin');
INSERT INTO test.USER_ROLES(USER_ID,ROLE_ID) values (1,1);

INSERT INTO test.cars (brand, power) values ('Mazda', 110);
INSERT INTO test.cars (brand, power) values ('Audi', 140);
INSERT INTO test.cars (brand, power) values ('Ferrari', 600);