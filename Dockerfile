FROM gradle:8.13.0-jdk21 AS builder

WORKDIR /app

COPY . .

RUN ./gradlew build -x test

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
