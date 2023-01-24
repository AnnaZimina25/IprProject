package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.List;

/**
 * Класс для работы с API ответами
 */
public class ResponseWrapper {

    private Response response;

    public ResponseWrapper(Response response) {
        this.response = response;
    }

    @Step("Проверка, что статус код ответа имеет значение {expectedStatusCode}")
    public ResponseWrapper checkStatusCode(int expectedStatusCode) {
        this.response.then().statusCode(expectedStatusCode);
        return this;
    }

    @Step("Проверка, что хидер ответа соответствует {expectedHeader}: {expectedHeaderValue}")
    public ResponseWrapper checkHeader(String expectedHeader, String expectedHeaderValue) {
        this.response.then().header(expectedHeader, expectedHeaderValue);
        return this;
    }

    @Step("Распарсить ответ в список указанных объектов")
    public <T>List<T> getBodyAsObjectsList(Class<T> clazz) {
      return this.response.jsonPath().getList("", clazz);
    }

    @Step("Распарсить ответ в указанный объект")
    public <T> T getBodyAsObject(Class<T> clazz) {
        T model = null;
        try {
            model = new ObjectMapper().createParser(getBodyAsStr()).readValueAs(clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }

    @Step("Получить строку тела ответа")
    public String getBodyAsStr() {
        return this.response.body().asString();
    }
}
