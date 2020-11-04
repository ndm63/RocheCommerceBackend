# commerce-backend #

This is a Roche home coding assignment.  It is a RESTful application using Java and Spring Boot.  Only the back-end is provided here.  It defines and implements a RESTful API.  The API is related to products and orders; i.e. it is a kind of commerce application.  The application provides persistence to an RDBMS.  The persistence currently can be provided through the internal H2 or an external MySql / MariaDB instance.

## How this project was set up ##

1) Created from Spring initializr  
2) Added Lombok  
3) Added jib for container image builds  
4) Added manifest endpoint  
5) Added Swagger2

## Building and maintaining the application ##

### Lombok ###
Lombok needs to be installed on the IDE: The [page](https://projectlombok.org/) 
Lombok site explains the steps to perform.

### Building ###

Normal build of the jar with unit tests:

```sh
mvn clean install
```

Build and deploy jar to InfoRisk Nexus customer snapshot repository:

```sh
mvn clean install deploy
```

Build a container (Docker) image and deploy to InfoRisk Nexus customer snapshot repository:

```sh
mvn clean package jib:build -Djib.to.image=nexus.inforisk.es:18545/inforisk/roche-commerce-backend:0.0.1-SNAPSHOT -Djib.to.auth.username=neill -Djib.to.auth.password=<password>
```

Run end-to-end tests, using Testcontainers and a remote image

```sh
mvn clean test -Dtest=*E2E
```


## Running the application ##

### On local Docker ###

With remote debugging and internal H2 database:

```sh
docker login nexus.inforisk.es:18549

docker run -dt --name roche-commerce-backend --rm -e "JAVA_TOOL_OPTIONS=-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=0.0.0.0:8453,server=y,suspend=n" -p 8454:8453 -p 9097:8080 nexus.inforisk.es:18549/inforisk/roche-commerce-backend:0.0.1-SNAPSHOT
```

To test with a Dockerised MySql database:
 
```sh
docker run -dt --name mysql -e MYSQL_ROOT_PASSWORD=secret -e "MYSQL_DATABASE=commerce" -e "MYSQL_USER=admin" -e "MYSQL_PASSWORD=frodo12" -p 3306:3306 nexus.inforisk.es:18549/mysql:latest

docker run -dt --name roche-commerce-backend -e "SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.jdbc.Driver" -e "SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.MySQL5InnoDBDialect" -e "SPRING_DATASOURCE_USERNAME=admin" -e "SPRING_DATASOURCE_PASSWORD=frodo12" -e "SPRING_DATASOURCE_JDBC_URL=jdbc:mysql://192.168.181.50:3306/commerce" -e "SPRING_JPA_GENERATE_DDL=true" -e "SPRING_JPA_HIBERNATE_DDL_AUTO=create" -e "JAVA_TOOL_OPTIONS=-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=0.0.0.0:8453,server=y,suspend=n" -p 8454:8453 -p 9097:8080 nexus.inforisk.es:18549/inforisk/roche-commerce-backend:0.0.1-SNAPSHOT
```


## Swagger ##

The Swagger documentation UI is available at the path with prefix '/swagger-ui/'.  For example, for the above Docker deployment:

```sh
http://192.168.181.50:9097/swagger-ui/
```

## Notes##

WebFlux could have been used instead of non reactive WebMVC.  However, it wasn't to avoid any complications given the limited time.

No security mechanism is implemented

The id is not really relevant to the clients.  The SKU could have been used as the primary key (id).  Currently products are referred to only by SKU and id is never seen by the clients.

Currently SKU can be updated.  This probably doesn't want to be the case.

No concurrency mechanism has yet been implemented.  Specifically the @Version / Etag functionality could be added.

Pagination needs to be added.
