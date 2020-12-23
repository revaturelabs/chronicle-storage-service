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
The below instructions are intended to be used with [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/#section=windows) If additional instrucations are necessary, please reach out.

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

Then create a new branch for your work

`git checkout -b [your branch here] [the branch your are basing your branch off of]`

Then push your new branch

`git push`

You are good to go!


### Project Lombok v.1.18.16
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

### Requirements
- Tests first, please push and contact William to approve before moving forward

* * *
