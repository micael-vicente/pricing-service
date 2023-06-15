# 👩‍💻🧑‍💻 Product Service

## Requirements
- Maven
- Docker
- Java 17

## The contents of the project

### 🐳 docker/
Inside this folder you can find the `Dockerfile`. It is used to dockerize the app.

<details>
  <summary>Content of the file</summary>
  
  ```
  FROM openjdk:17
  COPY target/*.jar product-service.jar
  ENTRYPOINT ["java", "-jar", "/product-service.jar"]
  ```
</details>

## 🏗 How to build the project
At the root of the project, in a terminal, execute:
> mvn clean install

### Building the docker image
To build the docker image run the following command in the root of the project:
> docker build -f docker/Dockerfile --tag=product-app:latest . 

## ▶️ How to run the application

### Using maven

By default, the application is configured to run on port `8080`.
At the root of the project, in a terminal, execute the following commands.

To run `COUNT_BASED`discount policy: 
> mvn spring-boot:run -Dspring-boot.run.profiles=count

To run `PERCENTAGE` discount policy:
> mvn spring-boot:run -Dspring-boot.run.profiles=percentage

In case you want to change the port the application runs on you can do it this way:
> mvn spring-boot:run -Dspring-boot.run.profiles=count -Dspring-boot.run.arguments=--server.port=8081

###  Using docker
If you did not build the docker image yet, please do so (refer to `How to build the project`).

Running with `COUNT_BASED` discount policy:
> docker run -e "SPRING_PROFILES_ACTIVE=count" -p 8080:8080 product-app:latest

Running with `PERCENTAGE` discount policy:
> docker run -e "SPRING_PROFILES_ACTIVE=percentage" -p 8080:8080 product-app:latest

If you need to change the port 
> docker run -e "SPRING_PROFILES_ACTIVE=percentage" -e "SERVER_PORT=8081" -p 8080:8081 product-app:latest

## 📖 How to use
For the sake of speed and convenience, the repositories are mocked and not using real databases.
Given this, beware the prices are randomly generated each time.
`quantity` is not a mandatory parameter, if not provided defaults to 1.

### Get a priced product
```bash
curl -X GET http://localhost:8080/products/08658230-beaa-49a8-b0cf-e86923311ff5?quantity=10
```