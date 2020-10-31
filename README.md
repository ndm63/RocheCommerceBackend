# commerce-backend #

This is a Roche home coding assignment.  It is a RESTful application using Java and Spring Boot.  Only the back-end is provided here.  It defines and implements a RESTful API.  The API is related to products and orders; i.e. it is a kind of commerce application.  The application provides persistence to an RDBMS.  The persistence currently can be provided through the internal H2 or an external MySql / MariaDB instance.

## How this project was set up ##

1) Created from Spring initializr  
2) Added Lombok  
3) Add jib for container image builds  
4) Added manifest endpoint  


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

Run end-to-end tests, giving the running application base URL

```sh
mvn clean test -Dtest=*E2E -Dapp.ws.url.base=http://192.168.181.50:9098
```


## Running the application

### On local Docker ###

With remote debugging:

```sh
docker login nexus.inforisk.es:18549

docker run -dt --name roche-commerce-backend --rm -e "JAVA_TOOL_OPTIONS=-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=0.0.0.0:8453,server=y,suspend=n" -p 8454:8453 -p 9097:8080 nexus.inforisk.es:18549/inforisk/roche-commerce-backend:0.0.1-SNAPSHOT
```


