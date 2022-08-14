package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtoModels.Person;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Method;
import io.restassured.specification.RequestSenderOptions;
import io.restassured.specification.RequestSpecification;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Класс для формирования API запросов
 */
public class PerfApi {

    private static String baseUri = "http://77.50.236.203:4880";
    // private static RequestSpecification spec;
    private static ObjectMapper mapper = new ObjectMapper();
    private String token;
    private Map<String, String> headers = new HashMap<String, String>() {{
        put("Content-Type", "application/json");
    }};

//    public void login() {
//        Content - Type:application / json
//        {
//            "username":"authTester",
//                "password":"password"
//        }
//
//    }

    private RequestSpecification setRequestSpec(String basePath, String body) {
        return new RequestSpecBuilder().setBaseUri(baseUri)
                .setBasePath(basePath)
                .addHeader("Content-Type", "application/json")
                //.addHeaders(headers)
                .setBody(body)
                .build();
    }

    private ResponseWrapper sendRequest(String basePath, String body, Method method) {

        return new ResponseWrapper(given().spec(setRequestSpec(basePath, body))
                .when()
                //.get()
                //.post()
                .request(method)
                .thenReturn());
    }

    private ResponseWrapper sendRequest(String basePath) {

        return new ResponseWrapper(given().spec(setRequestSpec(basePath, ""))
                .when()
                .request(Method.GET)
                .thenReturn());
    }

    @Step("Выполнить запрос GET /users")
    public ResponseWrapper getPersons() {
//        return new ResponseWrapper(given().spec(setRequestSpec("/users",""))
//                .when()
//                .get()
//                .thenReturn());
        return sendRequest("/users");
    }

    @Step("Выполнить запрос GET /api/users")
    public ResponseWrapper getApiUsers() {
//        return new ResponseWrapper(given().spec(setRequestSpec("/users",""))
//                .when()
//                .get()
//                .thenReturn());
        return sendRequest("/api/users");
    }

    @Step("Выполнить запрос GET /user/{id}")
    public ResponseWrapper getPerson(int id) {
       return sendRequest("/user/");
//        return new ResponseWrapper(given().spec(setRequestSpec("/user/" + id, ""))
//                .when()
//                .get()
//                .thenReturn());
    }

    // POST http://example.org/addUser
    //Content-Type: application/json
    //
    //{
    //  "firstName": "Vasiliy",
    //  "secondName": "Rubenstein",
    //  "age": 42,
    //  "sex": "MALE",
    //  "money": 1000000
    //}
    //Ответ
    //HTTP/1.1 201
    //Content-Type: application/json
    //
    //{
    //  "id": 2,
    //  "firstName": "Vasiliy",
    //  "secondName": "Rubenstein",
    //  "age": 42,
    //  "sex": "MALE",
    //  "money": 1000000
    //}

    @Step("Выполнить запрос POST /addUser")
    public ResponseWrapper addPerson(Person person) {
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(person);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonString);

        return sendRequest("/addUser", jsonString, Method.POST);
//        return new ResponseWrapper(given().spec(setRequestSpec("/addUser", jsonString))
//                .when()
//                .post()
//                .thenReturn());
    }

    public void login() {
        //Content - Type:application / json
        String jsonString = "{\"username\":\"authTester\"," +
                "  \"password\":\"password\"}";
//        String token = new ResponseWrapper(given().spec(setRequestSpec("/login", jsonString))
//                .when()
//                .post()
//                .thenReturn())
//                .getBodyAsStr();

        String token = sendRequest("/login", jsonString, Method.POST)
                .checkStatusCode(200)
                .getBodyAsStr();
        System.out.println(token);

    }

    // POST http://example.org/user/2/money/70230
    //Ответ
    //HTTP/1.1 200
    //Content-Type: application/json
    //
    //{
    //  "id": 2,
    //  "firstName": "Vasiliy",
    //  "secondName": "Rubenstein",
    //  "age": 42,
    //  "sex": "MALE",
    //  "money": 1000000
    //}

    @Step("Выполнить запрос POST /user/{id}/money/{amount}")
    public ResponseWrapper increaseMoneyAmount(String personId, BigDecimal moneyAmount) {
        String basePath = String.format("/user/%s/money/%s", personId, moneyAmount);
        return new ResponseWrapper(given().spec(setRequestSpec(basePath, ""))
                .when()
                .get()
                .thenReturn());
    }

//    private Map<String, String> addHeader(String headerName, String headerValue) {
//
//    }
}
