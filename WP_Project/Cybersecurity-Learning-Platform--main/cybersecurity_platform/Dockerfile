# 1) Build stage
FROM maven:3.9.1-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy just the pom to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy rest of sources
COPY src ./src
RUN mvn clean package -DskipTests -B

# 2) Run stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# expose your port
EXPOSE 9090

# copy the fat-jar from builder
COPY --from=builder /app/target/*.jar app.jar

# run
ENTRYPOINT ["java","-jar","/app/app.jar"]
