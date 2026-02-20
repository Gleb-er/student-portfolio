FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Явно указываем, что приложение будет слушать порт, который задаст Render
EXPOSE 8080

# Добавляем скрипт, который будет ждать базу данных (опционально, но полезно)
RUN apk add --no-cache curl

# Команда запуска с передачей переменных окружения
CMD ["java", "-jar", "app.jar"]