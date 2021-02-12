insert into tag values (1, 'Topic', 'Angular');
insert into tag values (2, 'Topic', 'Spring');
insert into tag values (3, 'Batch', '1120-August');
insert into tag values (4, 'Batch', '1020-Ben');
insert into tag values (5, 'Topic', 'Typescript');

insert into video values (6,'2021-02-12 10:32:33.179', 'Created a basic Angular application and went over TypeScript OOP, data types, and Basics. Introduced data interpolation and NodeJS.', 'Jim' ,false, 'Angular and TypeScript Overview', 
	  'https://chronicle-p3.s3.amazonaws.com/sample-mp4-file.mp4', 'temp_user');
insert into video values (7,'2021-02-12 10:32:33.179', 'Setup a basic Spring Boot web application with rest-controllers and H2 database. Demonstrated Spring Data capabilities via api calls to controller methods', 'Jim2', false, 'Spring Boot Overview and Setup',  
	  'https://chronicle-p3.s3.amazonaws.com/sample-mp4-file.mp4', 'temp_user');

insert into video_tag values (6, 1);
insert into video_tag values (6, 3);
insert into video_tag values (7, 5);
insert into video_tag values (6, 2);
insert into video_tag values (7, 4);
	
insert into note values (8, '2021-02-12 10:32:33.179', 'Created a basic Angular application and went over TypeScript OOP, data types, and Basics. Introduced data interpolation and NodeJS.', 'John', false, 'Angular and TypeScript Overview', 
	   'http://www.africau.edu/images/default/sample.pdf','temp_user');
insert into note values (9, '2021-02-12 10:32:33.179', 'Setup a basic Spring Boot web application with rest-controllers and H2 database. Demonstrated Spring Data capabilities via api calls to controller methods', 'Jim', false, 'Spring Boot Overview and Setup', 
	  'https://www.w3.org/TR/PNG/iso_8859-1.txt','temp_user');
	
insert into note_tag values (8, 1);
insert into note_tag values (8, 3);
insert into note_tag values (8, 5);
insert into note_tag values (9, 2);
insert into note_tag values (9, 4);


