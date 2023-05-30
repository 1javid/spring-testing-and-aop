# Spring RESTful and Testing with AOP

This project focuses on building a RESTful application using the Spring framework and incorporating comprehensive testing along with Aspect-Oriented Programming (AOP) for logging.

## Overview

This is a simple RESTful application built using Spring, designed to showcase various features and best practices. The application implements CRUD operations for a single entity, utilizes DTO pattern, performs data validation, automatic mapping using MapStruct, and includes unit and integration tests. Additionally, it incorporates logging using AOP to record method invocations and execution times.

## Features

- CRUD operations: Implements Create, Read, Update, and Delete operations for a single entity, enabling complete data management.
- Data validation: Ensures the integrity of the data by performing validation during entity creation and update, adhering to defined business rules.
- DTO pattern: Utilizes the Data Transfer Object (DTO) pattern to encapsulate entity data, facilitating loose coupling between the backend and frontend layers.
- Automatic mapping: Implements dynamic and automatic mapping between DTOs and entities using the MapStruct library, reducing manual mapping efforts and improving code maintainability.
- Unit testing: Includes comprehensive unit tests for Service class methods to validate their functionality and handle various test scenarios, ensuring robustness and correctness.
- Integration testing: Conducts integration tests for Controller methods to verify the correctness of API endpoints and their interactions with the Service layer.
- Logging with AOP: Implements Aspect-Oriented Programming (AOP) to log method invocations, input parameters, return values, and execution times in the Service class, facilitating performance monitoring and debugging.

## Prerequisites

To run this project, ensure you have the following installed:

- Java Development Kit (JDK) 17 or later
- Apache Maven

## Getting Started

1. Clone the project repository.
2. Build the project using Maven: `cd restful/ mvn clean install`.
3. Run the application using Maven: `mvn spring-boot:run`.
4. The application will be accessible at `http://localhost:8080/users`.

## Testing

This project includes comprehensive unit tests for Service class methods and integration tests for Controller methods. These tests ensure the application's functionality, validate expected behavior, and catch potential issues early in the development process.

## Logging

This project demonstrates the implementation of logging using Aspect-Oriented Programming (AOP). It captures method invocations, records input parameters, return values, and measures execution times. Logging aids in debugging, performance analysis, and monitoring the application's behavior.

## License

This project is licensed under the [MIT License](LICENSE).


# Spring RESTful Application with Testing and AOP

This is a simple RESTful application built using Spring, designed to showcase various features and best practices. The application implements CRUD operations for a single entity, utilizes DTO pattern, performs data validation, automatic mapping using MapStruct, and includes unit and integration tests. Additionally, it incorporates logging using AOP to record method invocations and execution times.

## Features

- CRUD operations for a single entity (GET, POST, DELETE, PUT, PATCH)
- Data validation during entity creation and update
- DTO pattern to encapsulate entity data
- Automatic mapping using MapStruct
- Unit tests for Service class methods
- Integration tests for Controller methods
- Logging method invocations and execution times using AOP

## Prerequisites

To run this application, ensure you have the following installed:

- Java Development Kit (JDK) 8 or later
- Apache Maven

## Setup and Usage

1. Clone the repository or download the source code.
2. Open a terminal and navigate to the project root directory.
3. Build the project using Maven:
   ```shell
   mvn clean install
   ```
4. Run the application using Maven:
   ```shell
   mvn spring-boot:run
   ```
5. The application will start running at `http://localhost:8080`.

## Endpoints

The following endpoints are available:

| Method | Endpoint               | Description                           |
| ------ | ---------------------- | ------------------------------------- |
| GET    | /users            | Retrieve all users                  |
| GET    | /users/{id}       | Retrieve an user by ID               |
| POST   | /users            | Create a new user                    |
| PUT    | /users/{id}       | Update a user by ID                 |
| PATCH  | /users/{id}       | Partially update an user entity by ID       |
| DELETE | /users/{id}       | Delete a user by ID                 |