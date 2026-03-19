# -------- STAGE 1: BUILD --------
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copia apenas o pom primeiro (melhora cache de dependências)
COPY pom.xml .

RUN mvn dependency:go-offline -B

# Agora copia o código
COPY src ./src

RUN mvn clean package -DskipTests

# -------- STAGE 2: RUNTIME --------
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=build /app/target/encurtaUrl-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]