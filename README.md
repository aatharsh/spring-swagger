# Spring Framework

Spring framework is used to implement REST APIs for basic CRUD operations related to managing tasks.


## Prerequisite:
- maven
- java

## Run command:
- mvn spring-boot:run - from spring-swagger folder to start the application in port 8080 (localhost:8080/swagger-ui.html).

(OR)

- mvn clean install -  from spring-swagger folder to install dependencies and create a jar file in the local target folder.
- java -jar target/java -jar target/java -jar target/spring-swagger-api-1.0.0-SNAPSHOT.jar from spring-swagger folder to start the backend server in port 8080 (localhost:8080/swagger-ui.html).

## Note:
- Swagger has been implemented to access the backend REST APIs (localhost:8080/swagger-ui.html)
- Steps:
   - Open the Swagger UI by accessing the link when the application starts.
   - Click on task-controller to access the REST APIs (in total six).
   - /addTask api has a sample schema of expected fields and values. 

## validation rules:
- description cannot exceed 1024 chars
- title cannot exceed 256 chars
- status cannot exceed 10 chars
- creationdate and dueDate should be in format yyyy-MM-dd
- title cannot be empty
- id is auto generated - not required to specify the value
- id is primary key

