# Build stage
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN apk add --no-cache curl

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring

# Create uploads directory AS ROOT
RUN mkdir -p /app/uploads && chown -R spring:spring /app

# Copy jar
COPY --from=builder /app/target/*.jar app.jar

# Switch to non-root user
USER spring:spring

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/api/health || exit 1

ENTRYPOINT ["java", "-jar", "/app/app.jar"]