# Chronicle Storage Service
Back end storage service for Chronicle. Includes data access to H2 Database and media storage and retrieval to S3 bucket.

***Documentation is in the Wiki***

## Technologies
- [Project Lombok 1.8.16](https://projectlombok.org/features/all)
- [H2 1.4.200](https://www.h2database.com/html/main.html) Using PostgreSQL9 as the dialect
- [Hibernate 4.3.11.Final](https://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html_single/)
- [Spring Boot 2.4.1](https://docs.spring.io/spring-boot/docs/2.4.1/reference/html/using-spring-boot.html#using-boot-build-systems)
- [Java 1.8](https://javaee.github.io/javaee-spec/javadocs/)
- [JUnit 5.7.0](https://junit.org/junit5/docs/snapshot/release-notes/#release-notes-5.7.0)
* * *

## Getting Started / Contributing
The below can be used with any IDE. IDE Specific instructions will be marked as such. If additional instrucations are necessary or if you find any errors, please reach out to Cassie.

### Pre-requisites
Please ensure you have the following installed prior to cloneing this repository:
- Intellij IDEA Community Edition
- Java EE 1.8 SDK
- Maven
- Project Lombok plugin (see instructions below)
- Git

### Setting up your workspace
Clone the repo with:

`git clone https://github.com/Batch-908-AugustDuet/chronicle-storage-service.git` (please replace this address with the address of your batch's specific repository)

Once cloned, be sure to checkout the base branch your team will be working off of using

`git checkout -b [base-branch]`

And then pull all of the changes for the base-branch.
(If you have just cloned the repo, there is most likely nothing to pull but you should check anyways)

`git pull`

Now to create your branch! While still on the base-branch for your team, do:

`git checkout -b [your branch here] [the branch your are basing your branch off of]`

Then push your new branch

`git push`

You are good to go!


### Project Lombok v.1.18.16
#### For IntelliJ Users
If you encounter an issue with project Lombok not automapping accessor methods, mutator methods, constructors, etc you may need to install the Lombok plugin in IntelliJ. Below are some instructions.

1) Go to *File->Settings->Plugins*
2) Look for the plugin names 'Lombok'
3) Install and Restart IntelliJ
4) Test that the plugin has been successfully installed by viewing one of the models, and then opening the 'Structure' tab in IntelliJ.
If you see accessors/mutators in the structure you are good to go!

5) If you continue to get issues, try going to 
*File->Settings->Build, Execution, Deployment->Compiler->Annotation Processors*
and check 'Enable annoation processing'

8) If all else fails, please reach out.

#### For Eclipse Users
1) Find the Java jar on Maven Central (go to www.mvnrepository.com and search for Lombok. Click on Project Lombok. Choose a popular version or the newest version. On the next page you will find a "Files" field. Click on the "jar" link to download the Lombok jar file. You may need to copy the code also if you are using a different version than what is in the current POM file.)

2) Run the jar and a UI for your installation should appear

3) Under IDEs, make sure that Eclipse is recognized. If not, specify a location.

4) After your IDE is selected, click the 'Install/Update' button.

5) Restart Eclipse if you have it open.

6) Update the project by right-clicking on the project and going to Maven -> Update Project.

### Requirements
Tests first, push to the remote repo, and and Open a Pull Request adding @RevatureGentry or @KennethDavis391.

### How Do I Open a Pull Request?
If you haven't opened a pull request before on Github follow the below instructions:
1) In the secondary navbar click 'Pull Requests'.

2) Click the green 'Open Pull Request' button

3) The Repo should be Batch-908-AugustDuet/... not revature labs!

4) Select your base branch (default is main)

5) Then select your feature branch

6) Add @RevatureGentry and @KennethDavis391 as approvers to your request

7) Select open pull request and viola pull request in progress! Will and Kenneth will comment once they review and merge it in if there are no issues.

###Spring Profiles
There are two profiles in this project, "dev" and "prod".
Go to src/main/resources and open the Application.properties file.
Change "spring.profiles.active=" from "prod" to "dev". If you don't, you won't be able to view the H2 database.
Make sure to change the profile back to "prod" before your final push for the project.

###CORS
Go to the application.properties file.
Change "cors.allow-credentials=" from "true" to "false"
This will allow the front-end to connect to the back-end.
Make sure to change it back to "true" before your final push.

###Postman
For testing endpoints with Postman,there is some additional configuration needed.
1) Install Postman (https://www.postman.com/downloads/)
2) Create a new collection.
3) Create a new request.
4) Change the request to "Post"
5) Paste the following into the address bar (paste the API key from the firebase-clinet-config after "key=") https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyPassword?key=
6) Click on the "Authorization" tab.
7) Change Type to "OAuth2.0"
8) Ensure that "Add authorization data to" is "Request URL"
9) "Access Token" should be "Available Tokens"
10) "Header Prefix" should be "Bearer"
11) "Token Name" - I used "service_account"
12) "Grant Type" should be "Client Credentials"
13) "Access Token URL" should be "https://oauth2.googleapis.com/token"
14) "Client ID" should be found in the Firebase JSON file received from Revature.
15) "Client Authentication" should be "Send as Basic Auth Header"
16) Click on the "Body" tab
17) Here is where you enter a user. We made two users: one for the trainer and one for the editor. Please only paste one of the users below. Do not paste both.

Paste the below for the trainer. (yes, it says "revatrue" not revature.)
{
    "email": "trainer@revatrue.net",
    "password": "111111",
    "returnSecureToken": true
}

Paste the below for the editor. (same password as the trainer)
{
    "email": "editor1@revatrue.net",
    "password": "111111",
    "returnSecureToken": true
}

18) Click on the "Tests" tab.
19) Paste the below in there. This will set the token as a global variable so it can be used by other requests in the same collection.

var jsonData = JSON.parse(responseBody);
pm.globals.set("id_token", jsonData.idToken);

20) Save
21) Click the "Send" button to test. If it was successful, you should get a "200" status code and get an idToken. There is a time limit on the token and you will need to get a new token after the current one expires.
22) Click on the "eye" button in the top right to view the token.
23) For every request you make, you need to go to the "Authorization" tab and select "Bearer Token" as the "Type". The "Token" should be "{{id_token}}".

##H2 Database
The H2 database is a temporary database that only exists when the IDE is running. There is a SQL file in project folder with dummy data in it that you can modify. When you restart or shutdown your IDE, the H2 database will be cleared.
In order to access the H2 database, you need to paste the following into the address bar: http://localhost:8080/myapp/h2-console
The database access will be blocked if you don't change the Spring Profile to "dev"
For the database URL use the following: jdbc:h2:mem:chronicle-db
The password is two double quotes (""), not an empty string.
To access the records in the database use SQL commands (select * from video;)
There is a "data.sql" file in the project (src/main/resources) that you can modify for adding or modifying dummy data.

##Firebase
Firebase is an online authentication API used for the project.
It stores the registered users in its own table; this is why the User table is not being used.
Each user has a name attribute, role attribute, and user Id attribute.
The token token from Firebase will be passed to the front-end upon logging in.
Any HTTP requests to the back-end requires the Firebase token.
You can access the user information from the token in the front-end or back-end.
For an example of getting the user Id it is easier for you to see the getNotifications() method in the TicketController.
For an example of setting the user role you can see the setUser() method in the FirebaseController.

* * *
