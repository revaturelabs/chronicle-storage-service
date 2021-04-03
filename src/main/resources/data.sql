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



--Insert some dummy tickets for testing 
--TICKET_ID  	CLIP_ID  	CLIP_URL  	DATE_ACCEPTED  	DATE_ISSUED  	DESCRIPTION  	EDITOR_ID  	END_TIME  	IDENTIFIER  	ISSUER_ID  	REJECT_COMMENT  	START_TIME  	TICKET_STATUS  	TOPIC  	ZOOM_LINK  	ZOOM_PASSCODE

insert into ticket values (1,11,'https://zoom.us/','2021-03-04', '2020-01-04','Description1','editorId','end_time','identifier','issuerId','reject_comment','start_time','PENDING','Angular and TypeScript Overview', '', 'passcode1');
insert into ticket values (2,12,'https://zoom.us/','2021-03-04', '2020-01-04','Description2','editorId','end_time','identifier','issuerId','reject_comment','start_time','PENDING','Spring Boot Overview and Setup', '', 'passcode1');

insert into ticket values (3,13,'https://zoom.us/','2021-03-04', '2020-01-04','Description3','editorId','end_time','identifier','issuerId','reject_comment','start_time','IN_PROGRESS','Angular and TypeScript Overview', '', 'passcode1');
insert into ticket values (4,14,'https://zoom.us/','2021-03-04', '2020-01-04','Description4','editorId','end_time','identifier','issuerId','reject_comment','start_time','IN_PROGRESS','Spring Boot Overview and Setup', '', 'passcode1');

insert into ticket values (5,15,'https://zoom.us/','2021-03-04', '2020-01-04','Description5','editorId','end_time','identifier','issuerId','reject_comment','start_time','UNDER_REVIEW','Angular and TypeScript Overview', 'https://zoom.us/', 'passcode1');
insert into ticket values (6,16,'https://zoom.us/','2021-03-04', '2020-01-04','Description6','editorId','end_time','identifier','issuerId','reject_comment','start_time','UNDER_REVIEW','Spring Boot Overview and Setup', 'https://zoom.us/', 'passcode1');

insert into ticket values (7,17,'https://zoom.us/','2021-03-04', '2020-01-04','Description7','editorId','end_time','identifier','issuerId','reject_comment','start_time','ACKNOWLEDGED','Angular and TypeScript Overview', '', 'passcode1');
insert into ticket values (8,18,'https://zoom.us/','2021-03-04', '2020-01-04','Description8','editorId','end_time','identifier','issuerId','reject_comment','start_time','ACKNOWLEDGED','Spring Boot Overview and Setup', '', 'passcode1');

insert into ticket values (9,19,'https://zoom.us/','2021-03-04', '2020-01-04','Description9','editorId','end_time','identifier','issuerId','reject_comment','start_time','ACKNOWLEDGED','Angular and TypeScript Overview', '', 'passcode1');
insert into ticket values (10,20,'https://zoom.us/','2021-03-04', '2020-01-04','Description10','editorId','end_time','identifier','issuerId','reject_comment','start_time','ACKNOWLEDGED','Spring Boot Overview and Setup', '', 'passcode1');

--Insert some dummy notifications for testing
--NOTIFICATION_ID  	NOTE  	RECEIVER_ID  	SEND_DATE  	SENDER_ID  	TICKET_ID  
insert into notification values (1,'note1','x75N8SSZFSTdjEt4H4HI7gfoC603','2021-03-20','senderId',1);
insert into notification values (2,'note2','x75N8SSZFSTdjEt4H4HI7gfoC603','2021-03-20','3',2);
insert into notification values (3,'note3','x75N8SSZFSTdjEt4H4HI7gfoC603','2021-03-20','3',3);
insert into notification values (4,'note4','wO8BNFonKvfks1BPjEEqrTLdkhx1','2021-03-20','3',4);
insert into notification values (5,'note5','wO8BNFonKvfks1BPjEEqrTLdkhx1','2021-03-20','3',5);
insert into notification values (6,'note6','2','2021-03-20','3',6);
insert into notification values (7,'note7','2','2021-03-20','3',7);
insert into notification values (8,'note8','2','2021-03-20','3',8);
insert into notification values (9,'note9','2','2021-03-20','3',9);
insert into notification values (10,'note10','2','2021-03-20','3',10);

