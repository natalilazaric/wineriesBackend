# 1. Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2. Runtime stage
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/Wineries-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 4040
ENTRYPOINT ["java", "-jar", "app.jar"]