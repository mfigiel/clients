FROM openjdk:8u191-jdk-alpine3.9
ADD target/client-0.0.1-SNAPSHOT.jar .
EXPOSE 8083
CMD java -jar client-0.0.1-SNAPSHOT.jar