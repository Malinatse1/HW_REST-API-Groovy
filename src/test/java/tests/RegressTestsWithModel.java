package tests;
import models.ResponseDataModel;
import models.RequestDataModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegressTestsWithModel extends TestBase  {

    TestData testData = new TestData();

    @Test
    void createUserTest() {
        RequestDataModel data = new RequestDataModel();
        data.setName(testData.firstName);
        data.setJob(testData.job);
        ResponseDataModel response = given()
                .spec(Specs.request)
                .body(data)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(ResponseDataModel.class);
        assertEquals(response.getName(), testData.firstName);
        assertEquals(response.getJob(), testData.job);
    }


    @Test
    void updateUserTestWithLombok() {
        RequestDataModel data = new RequestDataModel();
        data.setName("Alfred");
        ResponseDataModel response = given()
                .spec(Specs.request)
                .body(data)
                .when()
                .patch("/users/447")
                .then()
                .log().status()
                .log().body()
                .spec(Specs.responseSpec)
                .statusCode(200)
                .extract().as(ResponseDataModel.class);
        assertEquals(response.getName(),("Alfred"));
    }

    @Test
    void deleteUserTest() {
        RequestDataModel data = new RequestDataModel();
        data.setName(testData.firstName);
        data.setJob(testData.job);
        given()
                .spec(Specs.request)
                .when()
                .delete("users/447")
                .then()
                .log().body()
                .spec(Specs.responseSpec)
                .statusCode(204);
    }

    @Test
    void listUsersTest() {
        given()
                .spec(Specs.request)
                .when()
                .get("users?page=3")
                .then()
                .log().body()
                .spec(Specs.responseSpec)
                .statusCode(200)
                .body("total", is(12));

    }

    @Test
    void singleUserTestWithGroovy() {
        given()
                .spec(Specs.request)
                .when()
                .get("/users/3")
                .then().log().body()
                .spec(Specs.responseSpec)
                .statusCode(200)
                .body("data.last_name", is("Wong"))
                .body("data.first_name", is("Emma"))
                .body("data.email", is("emma.wong@reqres.in"));
    }
}
