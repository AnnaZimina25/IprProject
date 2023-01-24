package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtoModels.AuthorizationResponse;
import dtoModels.Model;
import dtoModels.person.Person;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.Method.*;
import static java.lang.String.format;

/**
 * Класс для формирования API запросов к тестовому полигону
 */
public class PerfApi {

    private static final String BASE_URL = "http://77.50.236.203:4880";
    private static ObjectMapper mapper = new ObjectMapper();
    private Map<String, String> headers = new HashMap<>() {{
        put("Content-Type", "application/json");
    }};

    @Step("Выполнить запрос GET /login")
    public void login() {
        String username = "user@pflb.ru";
        String password = System.getenv("user");

        String token =  new ResponseWrapper(given()
                .queryParam("username", username)
                .queryParam("password", password)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .when()
                .request("GET", BASE_URL + "/login"))
                .checkStatusCode(202)
                .getBodyAsObject(AuthorizationResponse.class)
                .getAccess_token();

        headers.put("Authorization", "Bearer " + token );
    }

    @Step("Выполнить запрос GET /user/{userId}")
    public ResponseWrapper getPerson(int userId) {
        return sendRequest("/user/" + userId,"", GET);
    }

    @Step("Выполнить запрос GET /car/{carId}")
    public ResponseWrapper getCar(int carId) {
        return sendRequest("/car/" + carId,"", GET);
    }

    @Step("Выполнить запрос POST /user")
    public ResponseWrapper addPerson(Person person) {
        return sendRequest("/user", getJsonString(person), POST);
    }

    @Step("Выполнить запрос POST /user/{userId}/buyCar/{carId}")
    public ResponseWrapper buyCar(int userId, int carId) {
        String basePath = format("/user/%s/buyCar/%s", userId, carId);
        return sendRequest(basePath, "", POST);
    }

    @Step("Выполнить запрос DELETE /user/{userId}")
    public ResponseWrapper deletePerson(int userId) {
        return sendRequest("/user/" + userId, "", DELETE);
    }

    private RequestSpecification setRequestSpec(String basePath, String body) {
        return new RequestSpecBuilder().setBaseUri(BASE_URL)
                .setBasePath(basePath)
                .addHeaders(headers)
                .setBody(body)
                .build();
    }

    private ResponseWrapper sendRequest(String basePath, String body, Method method) {
        return new ResponseWrapper(given().spec(setRequestSpec(basePath, body))
                .when()
                .request(method)
                .thenReturn());
    }

    @Step("Сформировать json строку")
    private String getJsonString(Model model){
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
