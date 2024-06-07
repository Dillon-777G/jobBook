insert into companies(id, name, description) values (10, 'SE452', 'Test 452');
insert into companies(id, name, description) values (1,'SE352', 'Test 352');  
insert into companies(id, name, description) values (2,'COMP2', 'COMP DESC 2');  
insert into companies(id, name, description) values (3,'COMP3', 'COMP DESC 3');  
insert into companies(id, name, description) values (4,'COMP4', 'COMP DESC 4');  
insert into companies(id, name, description) values (5,'COMP5', 'COMP DESC 5');   

-- Insert into post representing a company
INSERT INTO POST (id, company_id, caption, likes, shares) VALUES (100, 10, 'SE452 post test',0,0);
INSERT INTO POST (id, company_id, caption, likes, shares) VALUES (200, 1, 'SE352 post test',0,0);

-- Insert into JOBS table
INSERT INTO JOBS (title, company_id)
VALUES
    ('Software Engineer', 1),
    ('Data Analyst', 10),
    ('Marketing Manager', 1),
    ('Frontend Developer', 2),
    ('Backend Developer', 3),
    ('DevOps Engineer', 4),
    ('Product Manager', 5),
    ('Business Analyst', 1),
    ('UX Designer', 2),
    ('Sales Manager', 3),
    ('HR Specialist', 4),
    ('Customer Support', 5),
    ('QA Engineer', 10);

-- Insert into JOB_DESCRIPTION table
INSERT INTO JOB_DESCRIPTION (job_id, overview, responsibilities, skills, qualification, benefits)
VALUES
    (1, 'Software Engineer Overview', 'Software Engineer Responsibilities', 'Java, Spring Boot, Hibernate', 'Bachelor''s degree in Computer Science or related field', 'Health insurance, Paid time off'),
    (2, 'Data Analyst Overview', 'Data Analyst Responsibilities', 'SQL, Python, Data Visualization', 'Bachelor''s degree in Statistics, Mathematics, or related field', 'Flexible work hours, Remote work option'),
    (3, 'Marketing Manager Overview', 'Marketing Manager Responsibilities', 'Marketing strategy, Campaign management', 'Bachelor''s degree in Marketing or related field', 'Competitive salary, Performance bonuses'),
    (4, 'Frontend Developer Overview', 'Frontend Developer Responsibilities', 'HTML, CSS, JavaScript, React', 'Bachelor''s degree in Computer Science or related field', 'Health insurance, Stock options'),
    (5, 'Backend Developer Overview', 'Backend Developer Responsibilities', 'Java, Spring Boot, Hibernate', 'Bachelor''s degree in Computer Science or related field', 'Health insurance, Flexible hours'),
    (6, 'DevOps Engineer Overview', 'DevOps Engineer Responsibilities', 'AWS, Docker, Kubernetes', 'Bachelor''s degree in Computer Science or related field', 'Health insurance, Remote work option'),
    (7, 'Product Manager Overview', 'Product Manager Responsibilities', 'Product development, Agile methodology', 'Bachelor''s degree in Business or related field', 'Competitive salary, Performance bonuses'),
    (8, 'Business Analyst Overview', 'Business Analyst Responsibilities', 'Data analysis, Reporting', 'Bachelor''s degree in Business or related field', 'Health insurance, Stock options'),
    (9, 'UX Designer Overview', 'UX Designer Responsibilities', 'User research, Prototyping', 'Bachelor''s degree in Design or related field', 'Health insurance, Paid time off'),
    (10, 'Sales Manager Overview', 'Sales Manager Responsibilities', 'Sales strategy, Team management', 'Bachelor''s degree in Business or related field', 'Competitive salary, Performance bonuses'),
    (11, 'HR Specialist Overview', 'HR Specialist Responsibilities', 'Recruitment, Employee relations', 'Bachelor''s degree in Human Resources or related field', 'Health insurance, Stock options'),
    (12, 'Customer Support Overview', 'Customer Support Responsibilities', 'Customer service, Issue resolution', 'High school diploma or equivalent', 'Flexible hours, Remote work option'),
    (13, 'QA Engineer Overview', 'QA Engineer Responsibilities', 'Testing, Bug tracking', 'Bachelor''s degree in Computer Science or related field', 'Health insurance, Paid time off');


-- Insert into JOB_DETAILS table
INSERT INTO JOB_DETAILS (job_id, start_date, posted_date, expiry_date, location, status)
VALUES
    (1, '2022-01-01', '2022-01-02', '2022-01-31', 'New York', 'ACTIVE'),
    (2, '2022-01-01', '2022-01-03', '2022-01-31', 'San Francisco', 'ACTIVE'),
    (3, '2022-01-01', '2022-01-04', '2022-01-31', 'Chicago', 'ACTIVE'),
    (4, '2022-02-01', '2022-02-02', '2022-02-28', 'Austin', 'ACTIVE'),
    (5, '2022-02-01', '2022-02-03', '2022-02-28', 'Boston', 'ACTIVE'),
    (6, '2022-02-01', '2022-02-04', '2022-02-28', 'Denver', 'ACTIVE'),
    (7, '2022-02-01', '2022-02-05', '2022-02-28', 'Seattle', 'ACTIVE'),
    (8, '2022-02-01', '2022-02-06', '2022-02-28', 'Los Angeles', 'ACTIVE'),
    (9, '2022-02-01', '2022-02-07', '2022-02-28', 'Miami', 'ACTIVE'),
    (10, '2022-02-01', '2022-02-08', '2022-02-28', 'Dallas', 'ACTIVE'),
    (11, '2022-02-01', '2022-02-09', '2022-02-28', 'Atlanta', 'ACTIVE'),
    (12, '2022-02-01', '2022-02-10', '2022-02-28', 'Orlando', 'ACTIVE'),
    (13, '2022-02-01', '2022-02-11', '2022-02-28', 'Las Vegas', 'ACTIVE');

-- Insert into JOB_APPLICATION table
INSERT INTO JOB_APPLICATION (job_id, user_id, application_date, status)
VALUES
    (1, 1, '2022-01-02', 'APPLIED'),
    (2, 1, '2022-01-03', 'APPLIED'),
