# Maven Build Stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY src /app/src
COPY pom.xml /app/
RUN mvn -f /app/pom.xml clean package -DskipTests

# Flyway Stage
FROM boxfuse/flyway:latest-alpine AS flyway
COPY --from=build /app/src/main/resources/db/migration /flyway/sql

# Final Application Stage
FROM openjdk:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar application.jar
EXPOSE 8111
ENTRYPOINT ["java", "-jar", "application.jar"]
CMD ["/flyway/flyway", "-url=jdbc:postgresql://postgres:5432/ctf_kgs", "-schemas=public", "-user=postgres", "-password=1234", "migrate"]
