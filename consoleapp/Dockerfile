FROM openjdk:21-jdk-slim
WORKDIR /app

COPY build/libs/consoleapp.jar consoleapp.jar

EXPOSE 9991

ENTRYPOINT ["java", "-jar", "consoleapp.jar"]
