insert into companies(id, name, description) values (10, 'SE452', 'Test 452');
insert into companies(id, name, description) values (1,'SE352', 'Test 352');   

--Insert into post representing a company
-- INSERT INTO POST (id, company_id, caption, likes, shares) VALUES (1, 10, 'SE452 post test',0,0);
-- INSERT INTO POST (id, company_id, caption, likes, shares) VALUES (2, 1, 'SE352 post test',0,0);

-- Insert into JOBS table
INSERT INTO JOBS (title, company_id)
VALUES
    ('Software Engineer', 1),
    ('Data Analyst', 10),
    ('Marketing Manager', 1);

-- Insert into JOB_DESCRIPTION table
INSERT INTO JOB_DESCRIPTION (job_id, overview, responsibilities, skills, qualification, benefits)
VALUES
    (1, 'Software Engineer Overview', 'Software Engineer Responsibilities', 'Java, Spring Boot, Hibernate', 'Bachelor''s degree in Computer Science or related field', 'Health insurance, Paid time off'),
    (2, 'Data Analyst Overview', 'Data Analyst Responsibilities', 'SQL, Python, Data Visualization', 'Bachelor''s degree in Statistics, Mathematics, or related field', 'Flexible work hours, Remote work option'),
    (3, 'Marketing Manager Overview', 'Marketing Manager Responsibilities', 'Marketing strategy, Campaign management', 'Bachelor''s degree in Marketing or related field', 'Competitive salary, Performance bonuses');

-- Insert into JOB_DETAILS table
INSERT INTO JOB_DETAILS (job_id, start_date, posted_date, expiry_date, location, status)
VALUES
    (1, '2022-01-01', '2022-01-02', '2022-01-31', 'New York', 'ACTIVE'),
    (2, '2022-01-01', '2022-01-03', '2022-01-31', 'San Francisco', 'ACTIVE'),
    (3, '2022-01-01', '2022-01-04', '2022-01-31', 'Chicago', 'ACTIVE');