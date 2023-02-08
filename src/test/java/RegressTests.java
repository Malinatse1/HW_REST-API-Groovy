import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
public class RegressTests {
    @Test
    void createUserTest() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
        Specs.request
                .body(data)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"));
    }

    @Test
    void updateUserTest() {
        String data = "{ \"name\": \"alfa\"}";
        Specs.request
                .body(data)
                .when()
                .patch("/users/447")
                .then()
                .log().status()
                .log().body()
                .spec(Specs.responseSpec)
                .body("name", is("alfa"));
    }

    @Test
    void deleteUserTest() {
        String data = "{ \"name\": \"alfa\", \"job\": \"leader\" }";
        Specs.request
                .when()
                .delete("users/447")
                .then()
                .log().body()
                .statusCode(204);
    }

    @Test
    void listUsersTest() {
        Specs.request
                .when()
                .get("users?page=3")
                .then()
                .log().body()
                .spec(Specs.responseSpec)
                .body("total", is(12));
    }
    @Test
    void singleUserTest() {
        Specs.request
                .when()
                .get("/users/7")
                .then()
                .log().body()
                .spec(Specs.responseSpec)
                .body("data.first_name", is("Michael"));
    }
}
