# Turbodepot as an API

This project contains the turbodepot library available as a REST API so it can be used as a language agnostic service

## Building

Use the `res/ci/Dockerfile` to build a new Docker image. For instance (change the tag as needed):

```shell
docker build -f res/ci/Dockerfile --tag 'edertone/turbodepot-api:0.0.1-snapshot' .
```

## Install via Docker Hub

Use `res/ci/docker-compose.yml` as a template to use the `edertone/turbodepot-api` Docker image.

Please, provide your own configuration in a file named `application.yml` and mount it as a volume to `/app/config/application.yml`.
This file can configure any standard Spring Boot property as well as turbodepot specific properties. 

TurboDepot API need a database to store its data. At this time, only MariaDB or MySQL are supported. Use the Spring Boot properties to configure the database connection.

Database migrations are automatically applied at backend startup using Flyway. You can also use the Spring Boot properties to configure Flyway if needed.

You can use the provided `docker-compose.yml` as a starting point. Just go to the `res/ci` folder and run:

```shell
docker-compose up -d
```

This will start both the database and the turbodepot-api service.

## Swagger UI

Once the service is running, you can access the Swagger UI to explore the available endpoints at:

```
http://localhost:8080/turbodepot-api/swagger-ui/index.html
```
