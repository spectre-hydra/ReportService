# Report Service

A Spring-Boot application which retrieves the data from the https://swapi.co/api/ and stores it in it's own in-memory database.
Supports three endpoints:

  - /report (GET)
  - /report (PUT)
  - /report (DELETE)


### Deployment

Report Service requires [Java 8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html) to run a standalone WAR without a server-container.

Clone the repository

```sh
$ git clone REPO_URL
```


Run Tests (currently supports integration tests only)
```sh
$ mvn test -e
```

Build a fat WAR

```sh
$ mvn clean install -e
```


# Usage

Run the application with

```sh
$ java -jar ReportService-1.0.war
```

Now you can query the service on http://localhost:8080/report

| Endpoint | Payload Type | Sample Payload |
| ------ | ------ | ------ |
| /report (PUT) | BODY (application/json) | `{"characterPhrase":"Luke","planetName": "Tatooine"}` |
| /report (GET) | Query Params | `/report?characterPhrase=Luke&planetName=Tatooine` |
| /report (DELETE) | Query Params | `/report?characterPhrase=Luke&planetName=Tatooine` |

# Graphical API

You can use a ready-to-go graphical API which is available at http://localhost:8080/

![Graphical API](https://i.imgur.com/oL8Hndj.png)

### Tech

Libraries used in the project:

* [Spring Boot, JPA, Security]
* [H2 DB] 
* [Commons IO, LANG] 
* [Weddini-Throttling] 
* [Unirest] 
* [JUnit] 
* [Jackson]


License
----

MIT
