# ![RealWorld Example App](logo.png)

> ### **Java 21 + SpringBoot 3 + MyBatis** codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld) spec and API.

This codebase was created to demonstrate a fully fledged fullstack application built with **Java 21 + SrpingBoot 3 + MyBatis** including CRUD operations, authentication, routing, pagination, and more.

We've gone to great lengths to adhere to the **Java 21 + SrpingBoot 3 + MyBatis** community styleguides & best practices.

For more information on how to this works with other frontends/backends, head over to the [RealWorld](https://github.com/gothinkster/realworld) repo.

# How it works

The application uses **Java 21 + SrpingBoot 3 + MyBatis**.

* Use the idea of Domain Driven Design to separate the business term and infrastructure term.
* Use MyBatis to implement the [Data Mapper](https://martinfowler.com/eaaCatalog/dataMapper.html) pattern for persistence.
* Separate the application to multiple modules and use Maven to organize it.

The code is separate into 4 modules:
1. app-permission modules includes the permission related entity include user, profile and security mechanisms for application.
2. app-common modules includes common implement about exception definition.
3. app-article modules includes the business logic about article post/ edit/ delete etc
4. app-main modules the application entry program to start the application.

The core logic of the application is organized as follows:
1. `application` is the web layer implemented by Spring MVC and the high-level services 
2. `domain` is the business model including entities
3. `infra`  contains all the data access implementation classes using MyBatis

# Security

Authentication and authorization management are implemented using Spring Security, with token-based authentication using JWT. Moreover, various features of Spring Boot are utilized to implement exception handling, testing, and more.

# Database

It uses a MariaDB database to store the application data, you can initial the database by execute the schemal.sql under app-main/src/main/resources.

# Getting started

You just need to have JDK 21 installed.

You just need to have MariaDB installed.

## Prepare database
Execute the app-main/src/main/resources/schema.sql in MariaDB prompt
Edit proper database paramenter in app-permission.properties and app-article.properties

## Build application
Under application root directory, run: mvn install

## Test application
Under application root directory, run: mvn test

## Run application
Under app-main directory, run: mvn spring-boot:run
