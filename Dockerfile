FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY gradlew* build.gradle settings.gradle ./
COPY gradle ./gradle

RUN ./gradlew build --no-daemon --stacktrace --refresh-dependencies

COPY src ./src

RUN ./gradlew clean build -x test --no-daemon

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
