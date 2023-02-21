package tests;
import lombok.ResponseDataModel;
import models.RequestDataModel;
import org.junit.jupiter.api.Test;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegressTestsWithModel {
    TestData testData = new TestData();

    @Test
    void createUserTest() {
        RequestDataModel data = new RequestDataModel();
        data.setName(testData.firstName);
        data.setJob(testData.job);
        ResponseDataModel response = Specs.request
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
        ResponseDataModel response = Specs.request
                .body(data)
                .when()
                .patch("/users/447")
                .then()
                .log().status()
                .log().body()
                .spec(Specs.responseSpec)
                .extract().as(ResponseDataModel.class);
        assertEquals(response.getName(),("Alfred"));
    }

    @Test
    void deleteUserTest() {
        RequestDataModel data = new RequestDataModel();
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
                .body("data.findAll{it.id == 3}.last_name", hasItem("Wong"))
                .body("data.findAll{it.id == 3}.first_name", hasItem("Emma"))
                .body("data.findAll{it.id == 3}.email", hasItem("emma.wong@reqres.in"));

    }
}
