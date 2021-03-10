FROM openjdk:11
MAINTAINER Rafael Kiyota
COPY target/*.jar app.jar
WORKDIR /
ENTRYPOINT java -jar app.jar
EXPOSE 7070