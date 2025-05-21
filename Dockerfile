FROM gradle:8.13-jdk21 as build
WORKDIR /app
COPY --chown=gradle:gradle . /app
RUN gradle build --no-daemon

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
