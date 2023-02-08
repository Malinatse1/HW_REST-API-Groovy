import io.restassured.http.ContentType;
import lombok.RequestData;
import lombok.ResponseData;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegressTests {
    TestData testData = new TestData();

    @Test
    void createUserTest() {
        RequestData data = new RequestData();
        data.setName(testData.firstName);
        data.setJob(testData.job);
        Specs.request
                .body(data)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(ResponseData.class);
        assertEquals("Michael", testData.firstName);
        assertEquals("QA", testData.job);
    }


    @Test
    void updateUserTest() {
        RequestData data = new RequestData();
        data.setName("Alfred");
        Specs.request
                .body(data)
                .when()
                .patch("/users/447")
                .then()
                .log().status()
                .log().body()
                .spec(Specs.responseSpec)
                .body("name", is("Alfred"));
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
    void singleUserTest() {
        Specs.request
                .when()
                .get("/users/7")
                .then()
                .log().body()
                .spec(Specs.responseSpec)
                .body("data.id", is(7));
    }
}
