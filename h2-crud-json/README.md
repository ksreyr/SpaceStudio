# h2-crud-json

CRUD in h2 with JPA and Hibernate. SQL will be executed on startup server
REST API, create, read, update and delete.

To start just call the Main class H2CrudJsonApplication.java
or make a JAR and run it

### Tested with REST Client POSTMAN

* GET: http://localhost:8080/players
* GET http://localhost:8080/player/{id}
* POST http://localhost:8080/player  -> Body JSON player object
* PUT http://localhost:8080/player   -> Body JSON player object 
* DELETE http://localhost:8080/players   -> deletes all
* DELETE ttp://localhost:8080/player/{id} -> delete by Id

#### Database H2 config see application.properties

## Developer
* Miguel Caceres
