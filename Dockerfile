FROM java:8-jdk-alpine
COPY ./target/chronicle-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "chronicle-0.0.1-SNAPSHOT.jar"]