INSERT INTO user (user, password, role)  VALUES 
('cchou@csumb.edu', '$2a$10$GUrHKEbaqK3XVJIW3ezr1.OsJ1zB0odAWZF0CKvU8hlBtYC/WqjBi', 'instructor'),
('anariswari@csumb.edu', '$2a$10$GUrHKEbaqK3XVJIW3ezr1.OsJ1zB0odAWZF0CKvU8hlBtYC/WqjBi', 'instructor'),
('kposteher@csumb.edu', '$$2a$10$GUrHKEbaqK3XVJIW3ezr1.OsJ1zB0odAWZF0CKvU8hlBtYC/WqjBi', 'instructor'),
('dwisneski@csumb.edu', '$2a$10$GUrHKEbaqK3XVJIW3ezr1.OsJ1zB0odAWZF0CKvU8hlBtYC/WqjBi', 'instructor'),
('sislam@csumb.edu', '$2a$10$GUrHKEbaqK3XVJIW3ezr1.OsJ1zB0odAWZF0CKvU8hlBtYC/WqjBi', 'instructor'),
('hwieland@csumb.edu', '$2a$10$GUrHKEbaqK3XVJIW3ezr1.OsJ1zB0odAWZF0CKvU8hlBtYC/WqjBi', 'instructor'),
('jgross@csumb.edu', '$2a$10$GUrHKEbaqK3XVJIW3ezr1.OsJ1zB0odAWZF0CKvU8hlBtYC/WqjBi', 'instructor')
;

INSERT INTO course (year, semester, course_id, title, instructor)  VALUES 
(2020,'Fall',30157,'BUS 203 - Financial Accounting','cchou@csumb.edu'),
(2020,'Fall',30163,'BUS 306 - Fundamentals of Marketing','anariswari@csumb.edu'),
(2020,'Fall',30291,'BUS 304 - Business Communication, Pro-seminar & Critical Thinking','kposteher@csumb.edu'),
(2020,'Fall',31045,'CST 363 - Introduction to Database Systems','dwisneski@csumb.edu'),
(2020,'Fall',31249,'CST 237 - Intro to Computer Architecture','sislam@csumb.edu'),
(2020,'Fall',31253,'BUS 307 - Finance','hwieland@csumb.edu'),
(2020,'Fall',31747,'CST 238 - Introduction to Data Structures','jgross@csumb.edu'),
(2020,'Fall',40443,'CST 438 - Software Engineering','dwisneski@csumb.edu')
;

insert into assignment (id, due_date, name, course_id) values 
(1, '2021-09-01', 'db design', 31045),
(2, '2021-09-02', 'requirements', 31045),
(3, '2021-09-03', 'lab 1', 40443),
(4, '2021-09-04', 'lab 2', 40443)
;

insert into enrollment (id, student_email, student_name, course_id)  values
(1, 'test@csumb.edu', 'test', 31045),
(2, 'dwisneski@csumb.edu', 'david', 31045),
(3, 'trebold@csumb.edu', 'tom', 31045),
(4, 'test4@csumb.edu', 'test4', 31045),
(5, 'tomcsumb.edu', 'tom', 40443),
(6, 'test5@csumb.edu', 'test5', 40443)
; 

insert into assignment_grade (score, assignment_id, enrollment_id) values 
(90, 1, 1), 
(91, 1, 2), 
(92, 1, 3), 
(93, 2, 1), 
(94, 2, 2), 
(95, 2, 3),
(95, 3, 5), 
(91, 4, 5),
(94, 3, 6), 
(92, 4, 6)
;
