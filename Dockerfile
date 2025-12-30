# Stage 1: Сборка проекта
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# 1. Копируем родительский pom.xml и pom-файлы всех модулей
COPY pom.xml .
COPY application/pom.xml application/
COPY ai-service/pom.xml ai-service/
COPY person-service/pom.xml person-service/
COPY sales-service/pom.xml sales-service/

# Скачиваем зависимости (кешируемый слой)
RUN mvn dependency:go-offline -B

# 2. Копируем исходный код всех модулей
COPY application/src application/src/
COPY ai-service/src ai-service/src/
COPY person-service/src person-service/src/
COPY sales-service/src sales-service/src/

# Сборка всего проекта.
# Мы используем install, чтобы модули-зависимости попали в локальный репозиторий внутри образа
RUN mvn clean install -DskipTests

# Stage 2: Финальный образ
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Безопасность
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Копируем JAR из модуля application (теперь путь точный)
COPY --from=build /app/application/target/application-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]