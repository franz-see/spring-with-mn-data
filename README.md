# Spring-Boot with Micronaut-Data

This is a sample project that uses Spring for dependency injection and controller, and uses Micronaut-Data for the 
persistence

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

## To Build

```
./mvnw clean install
```

## To Run

```
./mvnw spring-boot:run
```

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