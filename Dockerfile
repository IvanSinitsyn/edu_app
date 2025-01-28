FROM openjdk:21-jdk-slim
WORKDIR /app

COPY build/libs/edu_app.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "app.jar"]
