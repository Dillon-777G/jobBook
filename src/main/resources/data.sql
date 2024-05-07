INSERT INTO USERS (first_name, last_name, email, username, user_type) VALUES ('John', 'Doe', 'john.doe@example.com', 'johndoe', 'Candidate');
INSERT INTO USERS (first_name, last_name, email, username, user_type) VALUES ('Jane', 'Smith', 'jane.smith@example.com', 'janesmith', 'Employer');
INSERT INTO USERS (first_name, last_name, email, username, user_type) VALUES ('Alice', 'Johnson', 'alice.johnson@example.com', 'alicejohnson', 'Candidate');
INSERT INTO USERS (first_name, last_name, email, username, user_type) VALUES ('Bob', 'Brown', 'bob.brown@example.com', 'bobbrown', 'Employer');
-- Insert into company
insert into companies(id, name, description) values (10, 'SE452', 'Test 452');
insert into companies(id, name, description) values (1,'SE352', 'Test 352');   

--Insert into post representing a company
INSERT INTO POST (id, company_id, caption) VALUES (RANDOM_UUID(), 10, 'SE452 post test');
INSERT INTO POST (id, company_id, caption) VALUES (RANDOM_UUID(), 1, 'SE352 post test');

-- Inserting data into the JOBS table
INSERT INTO JOBS (title, description, company_id, posted_date, expiry_date, location, status) VALUES
('Software Engineer', 'Develop new features for the company''s web application', 1, '2024-04-25', '2024-05-25', 'New York', 'ACTIVE');
INSERT INTO JOBS (title, description, company_id, posted_date, expiry_date, location, status) VALUES
('Data Analyst', 'Analyze and interpret data to provide actionable insights', 10, '2024-04-25', '2024-05-25', 'San Francisco', 'ACTIVE');
INSERT INTO JOBS (title, description, company_id, posted_date, expiry_date, location, status) VALUES
('Marketing Manager', 'Plan and execute marketing campaigns to promote the company''s products', 1, '2024-04-25', '2024-05-25', 'Chicago', 'ACTIVE');