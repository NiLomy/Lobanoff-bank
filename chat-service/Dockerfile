FROM openjdk:17-jdk-alpine
LABEL authors="Nikita Lobanov"
ARG JAR_FILE=build/libs/chat-service.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
