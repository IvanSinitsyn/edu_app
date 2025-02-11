FROM openjdk:21-jdk-slim
WORKDIR /app

COPY build/libs/billing.jar billing.jar

EXPOSE 9993

ENTRYPOINT ["java", "-jar", "billing.jar"]
