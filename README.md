# Spring-Boot with Micronaut-Data

This is a sample project that uses Spring for dependency injection and controller, and uses Micronaut-Data for the 
persistence

## TLDR; To inject Micronaut beans into a Spring project

See `com.example.springwithmndata.config.MicronautConfig`

```
@Configuration
public class MicronautConfig {
    
    @Bean
    public ApplicationContext micronautApplicationContext() {
        ApplicationContext applicationContext = ApplicationContext.build(emptyMap()).build();
        applicationContext.start();
        return applicationContext;
    }
    
    @Bean
    public BookRepository bookRepository(ApplicationContext applicationContext) {
        return applicationContext.getBean(BookRepository.class);
    }
    
    @Bean
    public SynchronousTransactionManager<Connection> transactionManager(ApplicationContext applicationContext) {
        return (SynchronousTransactionManager<Connection> ) applicationContext.getBean(SynchronousTransactionManager.class);
    }
    
}
```

## Project Requirements

1. Java 11
2. Docker (_to run the tests which uses mysql testcontainer_)

## Project Structure

```
`-- src/main/java
    `-- com.example.springwithmndata
        |-- config
        |   `-- MicronautConfig
        |-- controller
        |   |-- BookController
        |   `-- Paths
        |-- entity
        |   `-- Book
        |-- repository
        |   `-- BookRepository
        |-- service
        |   `-- BookService
        `-- Application
```

 * `Application` is the starting class of this application. This starts the Spring Boot application.
 * `BookController` is the entry point for the `GET:/book/{id}` and `POST:/book/` endpoints. This delegates to 
   `BookService`
 * `BookService` is a simple service. This is just to test if we can inject the spring-managed bean `BookService` into 
   `BookController`. This uses the micronaut-data class `BookRepository`
 * `BookRepository` is micronaut-data `JdbcRepository`.
 * `Book` is the micronaut-data-based entity used by `BookRepository`
 * `MicronautConfig` is what exposes the micronaut managed beans into Spring

## Commands

| Description              | Command                  |
|--------------------------|:-------------------------|
| Application : Build      | `./mvnw clean install`   |
| Application : Run        | `./mvnw -Prun`           |
| MySQL : Create Database  | `./mvnw -Pmysql-create`  |
| MySQL : Start Database   | `./mvnw -Pmysql-start`   |
| MySQL : Stop Database    | `./mvnw -Pmysql-stop`    |
| MySQL : Destroy Database | `./mvnw -Pmysql-destroy` |
| MySQL : Follow Tail      | `./mvnw -Pmysql-logs`    |

## Manual Testing

### Creating a Book

```
curl --location --request POST 'http://localhost:8080/book/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title" : "My First Book"
}'
```

### Retrieving Saved Book

```
curl --location --request GET 'http://localhost:8080/book/1'
```

### Postman

I have also added a postman collection under `./postman/` directory. This collection contains the saving and retrieving 
of book. You can import this into your postman and run the test to see how it exactly works.