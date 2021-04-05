insert into tag values (1, 'Topic', 'Angular');
insert into tag values (2, 'Topic', 'Spring');
insert into tag values (3, 'Batch', '1120-August');
insert into tag values (4, 'Batch', '1020-Ben');
insert into tag values (5, 'Topic', 'Typescript');
insert into tag values (6, 'Topic', 'JAVA Inheritance');
insert into tag values (7, 'Topic', 'JAVA Collection');

insert into video values (6,'2021-02-12 10:32:33.179', 'Created a basic Angular application and went over TypeScript OOP, data types, and Basics. Introduced data interpolation and NodeJS.', 'Jim' ,false, 'Angular and TypeScript Overview', 
	  'https://chronicle-p3.s3.amazonaws.com/sample-mp4-file.mp4', 'temp_user');
insert into video values (7,'2021-02-12 10:32:33.179', 'Setup a basic Spring Boot web application with rest-controllers and H2 database. Demonstrated Spring Data capabilities via api calls to controller methods', 'Jim2', false, 'Spring Boot Overview and Setup',  
	  'https://chronicle-p3.s3.amazonaws.com/sample-mp4-file.mp4', 'temp_user');
insert into video values (8,'2021-02-12 10:32:33.179', 'Demonstrating Java Inheritance', 'Jim3', false, 'JAVA Inheritance', 'https://chronicle-p3.s3.amazonaws.com/sample-mp4-file.mp4', 'temp_user');
insert into video values (9,'2021-02-12 10:32:33.179', 'Demonstrating Java Collections', 'Jim4', false, 'JAVA Collection', 'https://chronicle-p3.s3.amazonaws.com/sample-mp4-file.mp4', 'temp_user');

insert into video_tag values (6, 1);
insert into video_tag values (6, 3);
insert into video_tag values (7, 5);
insert into video_tag values (6, 2);
insert into video_tag values (7, 4);
insert into video_tag values (8, 6);
insert into video_tag values (9, 7);
	
insert into note values (8, '2021-02-12 10:32:33.179', 'Created a basic Angular application and went over TypeScript OOP, data types, and Basics. Introduced data interpolation and NodeJS.', 'John', false, 'Angular and TypeScript Overview', 
	   'http://www.africau.edu/images/default/sample.pdf','temp_user');
insert into note values (9, '2021-02-12 10:32:33.179', 'Setup a basic Spring Boot web application with rest-controllers and H2 database. Demonstrated Spring Data capabilities via api calls to controller methods', 'Jim', false, 'Spring Boot Overview and Setup', 
	  'https://www.w3.org/TR/PNG/iso_8859-1.txt','temp_user');
	
insert into note_tag values (8, 1);
insert into note_tag values (8, 3);
insert into note_tag values (8, 5);
insert into note_tag values (9, 2);
insert into note_tag values (9, 4);



--Insert some dummy tickets for testing 
--TICKET_ID  	CLIP_ID  	CLIP_URL  	DATE_ACCEPTED   DATE_ISSUED  	DESCRIPTION  	EDITOR_ID  	END_TIME  	IDENTIFIER  	ISSUER_ID  	REJECT_COMMENT  	START_TIME  	TICKET_STATUS  	TOPIC  	ZOOM_LINK  	ZOOM_PASSCODE

insert into ticket values (1, 0, null, null, '2021-01-04','1 out of 10', null,'00:20:00','CR JAN-25','wO8BNFonKvfks1BPjEEqrTLdkhx1', null,'00:10:00','PENDING','JAVA Primitives', 'https://zoom.us/', 'passcode1');
insert into ticket values (2, 0, null, null, '2021-01-04','2 out of 10', null,'00:30:00','CR JAN-25','wO8BNFonKvfks1BPjEEqrTLdkhx1', null,'00:20:00','PENDING','JAVA Method', 'https://zoom.us/', 'passcode1');

insert into ticket values (3, 0, null,'2021-01-06', '2021-01-04','3 out of 10','x75N8SSZFSTdjEt4H4HI7gfoC603','00:40:00','CR JAN-25','wO8BNFonKvfks1BPjEEqrTLdkhx1', null,'00:30:00','IN_PROGRESS','JAVA Interface', 'https://zoom.us/', 'passcode1');
insert into ticket values (4,14,'https://zoom.us/','2021-01-06', '2020-01-04','4 out of 10','x75N8SSZFSTdjEt4H4HI7gfoC603','00:50:00','CR JAN-25','wO8BNFonKvfks1BPjEEqrTLdkhx1','Missing content','00:40:00','IN_PROGRESS','JAVA Access Modifier', 'https://zoom.us/', 'passcode1');

insert into ticket values (5,15,'https://zoom.us/','2021-01-06', '2020-01-04','5 out of 10','x75N8SSZFSTdjEt4H4HI7gfoC603','01:00:00','CR JAN-25','wO8BNFonKvfks1BPjEEqrTLdkhx1', null,'00:50:00','UNDER_REVIEW','JAVA Inheritance', 'https://zoom.us/', 'passcode1');
insert into ticket values (6,16,'https://zoom.us/','2021-01-06', '2020-01-04','6 out of 10','x75N8SSZFSTdjEt4H4HI7gfoC603','01:50:00','CR JAN-25','wO8BNFonKvfks1BPjEEqrTLdkhx1','Unplayable','01:40:00','UNDER_REVIEW','JAVA Collection', 'https://zoom.us/', 'passcode1');

insert into ticket values (7, 0, null,'2021-01-06', '2021-01-04','7 out of 10','x75N8SSZFSTdjEt4H4HI7gfoC603','01:10:00','CR JAN-25','wO8BNFonKvfks1BPjEEqrTLdkhx1', null,'01:00:00','ACKNOWLEDGED','JAVA Flow Control', 'https://zoom.us/', 'passcode1');
insert into ticket values (8, 0, null,'2021-01-06', '2021-01-04','8 out of 10','x75N8SSZFSTdjEt4H4HI7gfoC603','01:20:00','CR JAN-25','wO8BNFonKvfks1BPjEEqrTLdkhx1', null,'01:10:00','ACKNOWLEDGED','JAVA Constructots', 'https://zoom.us/', 'passcode1');

insert into ticket values (9, 0, null,'2021-01-06', '2021-01-04','9 out of 10','x75N8SSZFSTdjEt4H4HI7gfoC603','01:30:00','CR JAN-25','wO8BNFonKvfks1BPjEEqrTLdkhx1', null,'01:20:00','ACKNOWLEDGED','JAVA String', 'https://zoom.us/', 'passcode1');
insert into ticket values (10, 0, null,'2021-01-06', '2021-01-04','10 out of 10','x75N8SSZFSTdjEt4H4HI7gfoC603','01:40:00','CR JAN-25','wO8BNFonKvfks1BPjEEqrTLdkhx1', null,'01:30:00','ACKNOWLEDGED','JAVA Annotation', 'https://zoom.us/', 'passcode1');


--Insert some dummy notifications for testing
--NOTIFICATION_ID  	NOTE  	RECEIVER_ID  	SEND_DATE  	SENDER_ID  	TICKET_ID  
insert into notification values (1,'Ticket 1 is rejected.','x75N8SSZFSTdjEt4H4HI7gfoC603','2021-03-20','wO8BNFonKvfks1BPjEEqrTLdkhx1',1);
insert into notification values (2,'Ticket 2 is rejected.','x75N8SSZFSTdjEt4H4HI7gfoC603','2021-03-20','wO8BNFonKvfks1BPjEEqrTLdkhx1',2);
insert into notification values (3,'Ticket 3 is rejected.','x75N8SSZFSTdjEt4H4HI7gfoC603','2021-03-20','wO8BNFonKvfks1BPjEEqrTLdkhx1',3);
insert into notification values (4,'Ticket 4 is now ACKNOWLEDGED.','wO8BNFonKvfks1BPjEEqrTLdkhx1','2021-03-20','x75N8SSZFSTdjEt4H4HI7gfoC603',4);
insert into notification values (5,'Ticket 5 is now ACKNOWLEDGED.','wO8BNFonKvfks1BPjEEqrTLdkhx1','2021-03-20','x75N8SSZFSTdjEt4H4HI7gfoC603',5);
insert into notification values (6,'Ticket 6 is now IN PROGRESS.','wO8BNFonKvfks1BPjEEqrTLdkhx1','2021-03-20','x75N8SSZFSTdjEt4H4HI7gfoC603',6);
insert into notification values (7,'Ticket 7 is now IN PROGRESS.','wO8BNFonKvfks1BPjEEqrTLdkhx1','2021-03-20','x75N8SSZFSTdjEt4H4HI7gfoC603',7);
insert into notification values (8,'Ticket 8 is now UNDER REVIEW.','wO8BNFonKvfks1BPjEEqrTLdkhx1','2021-03-20','x75N8SSZFSTdjEt4H4HI7gfoC603',8);
insert into notification values (9,'Ticket 9 is now UNDER REVIEW.','wO8BNFonKvfks1BPjEEqrTLdkhx1','2021-03-20','3x75N8SSZFSTdjEt4H4HI7gfoC603',9);
insert into notification values (10,'Ticket 10 is now UNDER REVIEW.','wO8BNFonKvfks1BPjEEqrTLdkhx1','2021-03-20','x75N8SSZFSTdjEt4H4HI7gfoC603',10);

