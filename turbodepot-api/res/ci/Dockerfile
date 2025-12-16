FROM gradle:9.2.1-jdk25 AS builder
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build -x test --no-daemon

FROM amazoncorretto:25-alpine
EXPOSE 8080
RUN mkdir -p /app/config
WORKDIR /app
COPY --from=builder /home/gradle/src/build/libs/turbodepot-api.jar /app/app.jar

ENTRYPOINT TZ="Europe/Madrid" java -jar -Duser.country=ES -Duser.language=es /app/app.jar
