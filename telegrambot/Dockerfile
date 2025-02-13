FROM openjdk:21-jdk-slim
WORKDIR /app

COPY build/libs/telegrambot.jar telegrambot.jar

EXPOSE 9990

ENTRYPOINT ["java", "-jar", "telegrambot.jar"]
