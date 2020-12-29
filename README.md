# Chronicle Storage Service
Back end storage service for Chronicle. Includes data access to H2 Database and media storage and retrieval to S3 bucket.

***Documentation is WIP***

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

`git clone https://github.com/Batch-908-AugustDuet/chronicle-storage-service.git`

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
1) Find the Java jar on Maven Central
2) Run the jar and a UI for your installation should appear
3) Under IDEs, make sure that Eclipse is recognized. If not, specify a location.
4) After your IDE is selected, click the 'Install/Update' button.
5) Restart Eclipse if you have it open and you should be good to go!

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

* * *
