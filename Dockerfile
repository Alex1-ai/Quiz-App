FROM eclipse-temurin:17-jre-alpine

EXPOSE 8080

COPY ./target/quizapp-*.jar /usr/app/
WORKDIR /usr/app

CMD ["java", "-jar", "quizapp-*.jar"]









## ===============================
## 1️⃣ Build stage
## ===============================
#FROM maven:3.9.10-eclipse-temurin-17 AS build
#
#WORKDIR /app
#
## Copy pom.xml first for dependency caching
#COPY pom.xml .
#RUN mvn dependency:go-offline -B
#
## Copy source code
#COPY src ./src
#
## Build application (tests INCLUDED)
#RUN mvn clean package
#
## ===============================
## 2️⃣ Runtime stage
## ===============================
#FROM eclipse-temurin:17-jre
#
#WORKDIR /app
#
## Copy the built jar from the build stage
#COPY --from=build /app/target/*.jar app.jar
#
## Expose Spring Boot port
#EXPOSE 8080
#
## Run the application
#ENTRYPOINT ["java", "-jar", "app.jar"]
