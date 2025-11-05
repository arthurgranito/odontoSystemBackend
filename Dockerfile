FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENV PORT=8080
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/odontosystem
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgres
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update

ENTRYPOINT ["java", "-jar", "app.jar"]