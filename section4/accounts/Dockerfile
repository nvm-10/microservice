# Java runtime base image
FROM openjdk:17-jdk-slim

MAINTAINER ankitsingh

# adding application jar to docker image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# executes out application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]