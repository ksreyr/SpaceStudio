# h2-crud-json

CRUD in h2 with JPA and Hibernate. SQL will be executed on startup server
REST API, create, read, update and delete.

To start server from gradle

```bash
$ ./gradlew bootRun
```

### Swagger API documentation
```bash
http://localhost:8080/swagger-ui.html
```
### Tested with REST Client POSTMAN

* GET: http://localhost:8080/players
* GET http://localhost:8080/player/{id}
* POST http://localhost:8080/player  -> Body JSON player object
* PUT http://localhost:8080/player   -> Body JSON player object 
* DELETE http://localhost:8080/players   -> deletes all
* DELETE http://localhost:8080/player/{id} -> delete by Id

#### Database H2 config see application.properties

## Developer
* Miguel Caceres

### Player JSON
```json
[
    {
        "id": 1,
        "name": "Nick",
        "password": "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"
    },
    {
        "id": 2,
        "name": "Judy",
        "password": "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"
    }
]
```
