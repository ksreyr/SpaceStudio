# SpaceStudio
## Client Configuration
### working directori 
/Users/kevinsantiago/Documents/Uni-Bremen/4_Semester/SWP2/spacestudio/Client

<div align="center">
<img src="docs/Architekturbeschreibung/pics/SpaceStudioLogo.png" width="400">
</div>


## Run Server
```bash
$ ./gradlew bootRun
```

## Run Client
```bash
 ./gradlew :Client:desktop:run
```



** Known Issues
Failure in Server initialisation. If the Server does not let you create users after start up. You need to restart the Server.
If you experience memory leaks, while starting runs "pkill java" on unix system to resolve these Issues.
The Bugs are in Spring Boot and in Java
```bash
INFO: {"timestamp":"2020-08-02T12:03:54.671+0000","status":500,"error":"Internal Server Error","message":"could not prepare statement; SQL [select player0_.id as id1_0_, player0_.name as name2_0_, player0_.password as password3_0_, player0_.saved_game as saved_ga4_0_, player0_.state_id as state_id5_0_ from player player0_ where player0_.name=?]; nested exception is org.hibernate.exception.SQLGrammarException: could not prepare statement","path":"/player"}
```