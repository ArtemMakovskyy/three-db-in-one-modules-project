# ---------- Stage 1: Build ----------
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

# Копируем вообще всё (все pom.xml и все исходники сразу)
# Это менее эффективно для кэша, но максимально удобно для работы
COPY . .

# Собираем весь проект целиком
RUN mvn clean package -DskipTests

# ---------- Stage 2: Extract ----------
FROM eclipse-temurin:21-jdk-alpine AS app-build
WORKDIR /opt/app

# Используем поиск (find), чтобы найти главный JAR-файл
# и скопировать его, не зная точного пути к модулю
RUN --mount=type=bind,from=build,source=/app,target=/src-data \
    cp $(find /src-data -name "application-*.jar" ! -name "*sources*") app.jar

# Распаковка слоев Spring Boot
RUN java -Djarmode=layertools -jar app.jar extract

# Сборка JRE
RUN "$JAVA_HOME/bin/jlink" \
    --add-modules ALL-MODULE-PATH \
    --strip-debug --no-man-pages --no-header-files \
    --compress=2 --output /opt/jdk

# ---------- Stage 3: Final ----------
FROM alpine:3.18
ENV JAVA_HOME=/opt/jdk
ENV PATH="$JAVA_HOME/bin:$PATH"

RUN apk add --no-cache bash \
 && addgroup -g 1000 spring-app \
 && adduser -D -u 1000 -G spring-app spring-app

WORKDIR /app
COPY --from=app-build /opt/jdk "$JAVA_HOME"

# Копируем слои
COPY --from=app-build /opt/app/dependencies/ ./
COPY --from=app-build /opt/app/spring-boot-loader/ ./
COPY --from=app-build /opt/app/snapshot-dependencies/ ./
COPY --from=app-build /opt/app/application/ ./

USER spring-app
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
