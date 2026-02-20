FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

# Явно принимаем переменную из вне и устанавливаем её внутри контейнера
ENV DATABASE_URL=${DATABASE_URL}

# Запускаем приложение (shell теперь не обязателен, так как переменная установлена выше)
CMD ["java", "-jar", "app.jar"]