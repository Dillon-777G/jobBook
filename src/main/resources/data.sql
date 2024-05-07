INSERT INTO USERS (first_name, last_name, email, username, user_type) VALUES ('John', 'Doe', 'john.doe@example.com', 'johndoe', 'Candidate');
INSERT INTO USERS (first_name, last_name, email, username, user_type) VALUES ('Jane', 'Smith', 'jane.smith@example.com', 'janesmith', 'Employer');
INSERT INTO USERS (first_name, last_name, email, username, user_type) VALUES ('Alice', 'Johnson', 'alice.johnson@example.com', 'alicejohnson', 'Candidate');
INSERT INTO USERS (first_name, last_name, email, username, user_type) VALUES ('Bob', 'Brown', 'bob.brown@example.com', 'bobbrown', 'Employer');
-- Insert into company
insert into companies(id, name, description) values (10, 'SE452', 'Test 452');
insert into companies(id, name, description) values (1,'SE352', 'Test 352');   

--Insert into post representing a company
INSERT INTO POST (id, company_id, caption, likes, shares) VALUES (1, 10, 'SE452 post test',0,0);
INSERT INTO POST (id, company_id, caption, likes, shares) VALUES (2, 1, 'SE352 post test',0,0);