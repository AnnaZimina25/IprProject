import api.ResponseWrapper;
import dtoModels.AuthorizationResponse;

import static io.restassured.RestAssured.*;

public class Main {

    public static void main(String[] args) {
        final String baseUrl = "http://77.50.236.203:4880";
        final String username = "user@pflb.ru";
        final String password = "user";
        final String query = "?username=user@pflb.ru&password=user";

        // кваеря через дано
     String token =  new ResponseWrapper(given().queryParam("username", username)
                .queryParam("password", password)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .when()
                .request("GET", baseUrl + "/login"))
             .checkStatusCode(202)
             .getBodyAsObject(AuthorizationResponse.class)
             .getAccess_token();
//                .then()
//                .statusCode(202);
        // кваеря через урлу напрямую
        given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .when()
                .request("GET", baseUrl + "/login" + query)
                .then()
                .statusCode(202);
    }
}
