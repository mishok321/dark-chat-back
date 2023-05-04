# How to start?
1. Make sure you have Java and MySQL installed
2. Clone the repository from GitHub.
3. Create a MySQL database for the project using the command: `CREATE DATABASE <database_name>;`
4. Open the project in your preferred IDE.
5. In `src/main/resources/application.properties` write the necessary parameters instead of stubs
6. Run the project by running `mvn spring-boot:run` in a terminal in the project folder.

<details> 
  <summary>Hibernate does not create table?</summary>
   You can create a table manually by running the command:
   <pre>
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  login VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL
);</pre>
</details>

# Technologies
- `spring-boot-starter-web`: Starter for building web, including RESTful, applications using Spring MVC.
- `spring-boot-starter-data-jpa`: Starter for using Spring Data JPA with Hibernate.
- `spring-boot-starter-security`: Starter for using Spring Security.
- `mysql-connector-java`: Connector for MySQL database.
- `commons-dbcp2`: Connection pooling implementation.
- `jjwt`: JSON Web Token implementation for Java.
- `hibernate-validator`: Bean validation API for the Hibernate Validator.
- `lombok`: Annotation processing library that helps reduce boilerplate code.
- `spring-boot-starter-test`: Starter for testing Spring Boot applications.
- `netty-socketio`: Socket.IO server implemented on top of Netty.
- `jaxb-api`: API for processing XML.

### There are also two plugins configured for the project:
- `spring-boot-maven-plugin`: Maven plugin for building Spring Boot applications.
- `maven-checkstyle-plugin`: Maven plugin for checking code style against a given configuration.