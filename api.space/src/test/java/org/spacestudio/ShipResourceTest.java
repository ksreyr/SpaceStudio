package org.spacestudio;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import io.quarkus.test.common.QuarkusTestResource;

@QuarkusTest
public class ShipResourceTest {

    @Test
    public void testList() {
        given()
                .when().get("/ships")
                .then()
                .statusCode(200)
                .body("$.size()", is(3),
                        "name", containsInAnyOrder("Flying Snowstorm", "Pineapple Express", "Pearl"),
                        "description", containsInAnyOrder("Winter ship", "Tropical ship", "Flowery Ship"));
    }

    @Test
    public void testAdd() {
        given()
                .body("{\"name\": \"Pearl\", \"description\": \"Flowery Ship\"}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .post("/ships")
                .then()
                .statusCode(200)
                .body("$.size()", is(3),
                        "name", containsInAnyOrder("Flying Snowstorm", "Pineapple Express", "Pearl"),
                        "description", containsInAnyOrder("Winter ship", "Tropical ship", "Flowery Ship"));

        given()
                .body("{\"name\": \"Pear\", \"description\": \"Winter ship\"}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .delete("/ships")
                .then()
                .statusCode(200)
                .body("$.size()", is(3),
                        "name", containsInAnyOrder("Flying Snowstorm", "Pineapple Express", "Pearl"),
                        "description", containsInAnyOrder("Winter ship", "Tropical ship", "Flowery Ship"));
    }
}