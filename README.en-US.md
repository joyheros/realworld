# ![RealWorld Example App](./logo.png)

**English** | [中文](./README.md)

**Java 21 + SpringBoot 3 + MyBatis** codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld) spec and API.  

This codebase was created to demonstrate a fully fledged fullstack application built with **Java 21 + SrpingBoot 3 + MyBatis** including CRUD operations, authentication, routing, pagination, and more.  

We've gone to great lengths to adhere to the Java, SpringBoot and MyBatis community styleguides & best practices.  

For more information on how to this works with other frontends/backends, head over to the [RealWorld](https://github.com/gothinkster/realworld) repo.  

## Project Features  

* Use Domain Driven Design ideas to separate business logic from infrastructure.  
* Use MyBatis implement [Data Mapper](https://martinfowler.com/eaaCatalog/dataMapper.html) persistence module.  
* Separate the application into modules and organize it using Maven.  


The project code is divided into 4 modules:  
1. The app-permission module includes the permission related entities, including the s users, profiles, and security mechanisms.  
2. The app-common common module includes a common implementation of exception definitions.  
3. app-article module includes article publishing/editing/deleting and other business logic.  
4. The app-main module is the application entry program that starts the application.  


The logical hierarchy of the application is organized as follows:  
1. 'application' is the web layer implemented by Spring MVC and high-level services.
2. 'domain' is the business model layer that contains the domain entities.
3. 'infra' contains all data access classes implemented with MyBatis.


## Security  

Authentication and authorization management is implemented using Spring Security and using JWT for token-based authentication. In addition, with the Spring Boot features to achieve the exception handling, testing and other functions.  

## Database  

This project uses the MariaDB database to store application data, which you can initialize by executing schema.sql in the app-main/src/main/resources directory.  

## How it works  

JDK 21 needs to be installed.  
MariaDB needs to be installed.  

### Database Initialization

In MariaDB command window, execute the schema under app-main/src/main/resources/schema.sql to initialize the database.  
Modify the database parameters according to your database parameters in app-permission.properties and app-article.properties.  

### Build application  
Under application root directory, run: mvn install  

### Test application  
Under application root directory, run: mvn test  

### Run application  
Under app-main directory, run: mvn spring-boot:run  

## Project address  

- [realworld](https://github.com/joyheros/realworld)  

## How to contribute

**Pull Request:**

1. Fork Code!
2. Create your own branch: `git checkout -b feature/xxxx`
3. Submit your changes: `git commit -m 'feature: add xxxxx'`
4. Push your branch: `git push origin feature/xxxx`
5. Submit: `pull request`

## Git Contribution submission specification

- Refer to [Vue](https://github.com/vuejs/vue/blob/dev/.github/COMMIT_CONVENTION.md) specification

  - `feat` : New Features
  - `fix` : Repair defects
  - `docs` : Document change
  - `style` : Code format
  - `refactor` : Code refactoring
  - `perf` : Performance optimization
  - `test` : Add neglected tests or changes to existing tests
  - `build` : Build processes, external dependency changes (such as upgrading npm packages, modifying packaging configurations, etc.)
  - `ci` : Modify CI configuration and scripts
  - `revert` : Roll back the commit
  - `chore` : Changes to the build process or tools and libraries (do not affect source files)
  - `wip` : Under development
  - `types` : Type definition file modification

## Thanks to the following projects for help  

- [gothinkster](https://github.com/gothinkster/realworld)  
- [spring-boot-realworld-example-app](https://github.com/gothinkster/spring-boot-realworld-example-app)  

## maintainers

[@joyheors](https://github.com/joyheros)  

## `Star`

Many thanks to the kind individuals who leave a star. Your support is much appreciated :heart:

