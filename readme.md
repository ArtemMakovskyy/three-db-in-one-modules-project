а как правильно написать докерфайл которій использует все єти файлі и запустит проект со всеми модулями

Отличный вопрос 👍
Сразу скажу главное, чтобы не было путаницы:

В Docker тебе нужен ТОЛЬКО JAR модуля application
Он уже содержит все остальные модули внутри.

Ни person-service.jar, ни sales-service.jar, ни ai-service.jar в Docker отдельно не нужны.

1️⃣ Какой JAR мы кладём в Docker

После:

mvn clean package


у тебя будет:

application/target/application-0.0.1-SNAPSHOT.jar


👉 Это единый fat-jar, внутри:

application

person-service

sales-service

ai-service

все зависимости Spring

2️⃣ Правильный Dockerfile (РЕКОМЕНДУЮ)

📄 Dockerfile (в корне проекта или в application/)

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY application/target/application-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

3️⃣ Как правильно собрать образ

⚠️ Сначала Maven, потом Docker

mvn clean package
docker build -t store-app .


или если Dockerfile лежит в application/:

docker build -t store-app application

4️⃣ Как запустить контейнер
docker run -p 8080:8080 store-app


После этого:

application стартует

Spring подтянет все модули

все контроллеры, сервисы, репозитории работают

5️⃣ Частая ошибка ❌ (очень важно)

❌ НЕ ДЕЛАЙ ТАК:

COPY person-service.jar ...
COPY sales-service.jar ...
COPY ai-service.jar ...


Почему:

они уже внутри application.jar

Spring Boot не собирается из нескольких jar вручную

6️⃣ Если нужны профили / env / БД
application.yml
spring:
profiles:
active: docker

Docker run
docker run \
-p 8080:8080 \
-e SPRING_PROFILES_ACTIVE=docker \
store-app

7️⃣ Multi-stage Dockerfile (опционально, красиво)

Если хочешь вообще без Maven на хосте:

FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/application/target/application-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

8️⃣ Итог (очень кратко)
Вопрос	Ответ
Где все модули?	Внутри application.jar
Что кладём в Docker?	Только application.jar
Сколько контейнеров?	Один
Это микросервисы?	❌ Нет
Это модульный монолит?	✅ Да

Если хочешь, следующим шагом могу:

сделать docker-compose + Postgres

показать как разделить на реальные микросервисы

или проверить, что у тебя всё правильно со сканированием пакетов

Скажи, куда идём дальше 🚀