insert into tag values (1, 'Topic', 'Angular');
insert into tag values (2, 'Topic', 'Spring');
insert into tag values (3, 'Batch', '1120-August');
insert into tag values (4, 'Batch', '1020-Ben');
insert into tag values (5, 'Topic', 'Typescript');

insert into video values (6, 'https://chronicle-p3.s3.amazonaws.com/sample-mp4-file.mp4', 'Angular and TypeScript Overview', 'temp_user', 
	'Created a basic Angular application and went over TypeScript OOP, data types, and Basics. Introduced data interpolation and NodeJS.');
insert into video values (7, 'https://chronicle-p3.s3.amazonaws.com/sample-mp4-file.mp4', 'Spring Boot Overview and Setup', 'temp_user', 
	'Setup a basic Spring Boot web application with rest-controllers and H2 database. Demonstrated Spring Data capabilities via api calls to controller methods');

insert into video_tag (6, 1);
insert into video_tag (6, 3);
insert into video_tag (7, 5);
insert into video_tag (6, 2);
insert into video_tag (7, 4);
	
insert into note values (8, 'http://www.africau.edu/images/default/sample.pdf', 'Angular and TypeScript Overview', 'temp_user', 
	'Created a basic Angular application and went over TypeScript OOP, data types, and Basics. Introduced data interpolation and NodeJS.');
insert into note values (9, 'https://www.w3.org/TR/PNG/iso_8859-1.txt', 'Spring Boot Overview and Setup', 'temp_user', 
	'Setup a basic Spring Boot web application with rest-controllers and H2 database. Demonstrated Spring Data capabilities via api calls to controller methods');
	
insert into note_tag (8, 1);
insert into note_tag (8, 3);
insert into note_tag (8, 5);
insert into note_tag (9, 2);
insert into note_tag (9, 4);


