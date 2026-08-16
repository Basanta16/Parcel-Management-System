# =========================
# STAGE 1: BUILD APPLICATION
# =========================

FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom first
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy project source
COPY src ./src

# Build Spring Boot jar
RUN mvn clean package -DskipTests


# =========================
# STAGE 2: RUN APPLICATION
# =========================

FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy jar created in build stage
COPY --from=build /app/target/*.jar app.jar

# Render normally exposes the app through its PORT environment variable
EXPOSE 8080

# Start Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]