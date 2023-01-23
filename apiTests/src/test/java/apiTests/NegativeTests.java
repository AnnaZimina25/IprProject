package apiTests;

import api.PerfApi;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

public class NegativeTests {

    private static final PerfApi perfApi = new PerfApi();

    @Test
    @Description("Негативная проверка метода GET /user/{id} (Получение пользователя по несуществующему id)")
    @Tags({@Tag("negativeGetUser"), @Tag("Api")})
    void getUserCheck() {
        String response = perfApi.getPerson(-1000)
                .checkStatusCode(204)
                .getBodyAsStr();
        Assertions.assertTrue(response.isEmpty(), "Ответ от сервера не является '{}'");
    }
}
