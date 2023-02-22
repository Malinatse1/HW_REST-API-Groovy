package helpers;

import io.qameta.allure.restassured.AllureRestAssured;

public class CustomApiListener {
    private static final AllureRestAssured FILTER = new AllureRestAssured();

    public static AllureRestAssured withCustomTemplates() {
        FILTER.setRequestTemplate("resources/tpl/request.ftl.url");
        FILTER.setResponseTemplate("resources/tpl/response.ftl.url");
        return FILTER;
    }
}

