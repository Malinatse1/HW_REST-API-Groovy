import io.restassured.http.ContentType;
import lombok.RequestData;
import lombok.ResponseData;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class RegressTests {
    TestData testData = new TestData();

    @Test
    void createUserTest() {
        RequestData data = new RequestData();
        data.setName(testData.firstName);
        data.setJob(testData.job);
        ResponseData responseData = Specs.request
                .body(data)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(ResponseData.class);
        assertEquals(responseData.getName(), testData.firstName);
        assertEquals(responseData.getJob(), testData.job);
    }


    @Test
    void updateUserTestWithLombok() {
        RequestData data = new RequestData();
        data.setName("Alfred");
        ResponseData responseData = Specs.request
                .body(data)
                .when()
                .patch("/users/447")
                .then()
                .log().status()
                .log().body()
                .spec(Specs.responseSpec)
                .extract().as(ResponseData.class);
        assertEquals(responseData.getName(),("Alfred"));
    }

    @Test
    void deleteUserTest() {
        RequestData data = new RequestData();
        data.setName(testData.firstName);
        data.setJob(testData.job);
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
    void singleUserTestWithGroovy() {
        given()
                .spec(Specs.request)
                .when()
                .get("/users")
                .then().log().body()
//                .spec(Specs.responseSpec)
//                .body("data.id", is(7));
                .body("data.findAll{it.id == 3}.last_name", hasItem("Wong"))
                .body("data.findAll{it.id == 3}.first_name", hasItem("Emma"))
                .body("data.findAll{it.id == 3}.email", hasItem("emma.wong@reqres.in"));

    }
}
