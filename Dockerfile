FROM openjdk:8u191-jdk-alpine3.9
ADD target/client-1.0.0.jar .
EXPOSE 8083
CMD java -jar client-1.0.0.jar