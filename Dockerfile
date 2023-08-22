FROM gradle:7.2.0-jdk17 as build

WORKDIR /app

COPY ./build.gradle ./build.gradle
COPY ./settings.gradle ./settings.gradle
COPY ./gradlew ./gradlew
COPY ./gradle ./gradle
COPY ./src ./src

RUN ./gradlew bootJar

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]