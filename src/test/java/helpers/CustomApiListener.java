package helpers;

import io.qameta.allure.restassured.AllureRestAssured;

public class CustomApiListener {
    private static final AllureRestAssured FILTER = new AllureRestAssured();

    public static AllureRestAssured withCustomTemplates() {
        FILTER.setRequestTemplate("tlp/request.ftl");
        FILTER.setResponseTemplate("tlp/response.ftl");
        return FILTER;
    }
}

