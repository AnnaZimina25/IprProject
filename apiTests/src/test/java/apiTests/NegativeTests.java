package apiTests;

import api.PerfApi;
import dataBase.DbGetter;
import dtoModels.car.Car;
import dtoModels.person.Person;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NegativeTests {

    @Test
    @Description("Негативная проверка метода GET /user/{id} (Получение пользователя по несуществующему id)")
    @Tags({@Tag("negativeGetUser"), @Tag("Api")})
    void getUserCheck() {
        PerfApi perfApi = new PerfApi();
        perfApi.login();
        String response = perfApi.getPerson(-1000)
                .checkStatusCode(204)
                .getBodyAsStr();
        assertTrue(response.isEmpty(), "Ответ от сервера не является '{}'");
    }

    @Test
    @Description("Негативная проверка метода DELETE /user/{userId} (Удаление пользователя без авторизации)")
    @Tags({@Tag("negativeDeleteUserWithoutAuthorization"), @Tag("Api")})
    void deleteUserWithoutAuthorizationTest() {
        PerfApi perfApi = new PerfApi();
        int id = new DbGetter().getLastPersonWithoutHouseAndCars("Big", "Lebovski").getId();
        perfApi.deletePerson(id).checkStatusCode(403);

        assertFalse(new DbGetter().getPersonsById(id).isEmpty(),
                "Пользователь с id " + id + " удалился из базы");
    }

    @Test
    @Description("Негативная проверка метода DELETE /user/{userId} (Удаление несуществующего пользователя)")
    @Tags({@Tag("negativeDeleteUser"), @Tag("Api")})
    void deleteUserTest() {
        PerfApi perfApi = new PerfApi();
        perfApi.login();
        perfApi.deletePerson(-1000).checkStatusCode(404);
    }

    @Test
    @Description("Негативная проверка метода POST /user/{userId}/buyCar/{carId} (Покупка машины " +
            "при недостаточном балансе средств на счете)")
    @Tags({@Tag("negativeBuyCarUser"), @Tag("Api")})
    void buyCarUserTest() {
        Car testCar = new DbGetter().getCarWithoutOwnerWithPriceMoreThan(new BigDecimal(10000));
        Person testPerson = new DbGetter().getPersonWithoutHouseAndCarsWithMoney(testCar.getPrice(), false);

        PerfApi perfApi = new PerfApi();
        perfApi.login();

        Person resultPerson = perfApi.buyCar(testPerson.getId(), testCar.getId())
                .checkStatusCode(406).getBodyAsObject(Person.class);

        List<Car> carsAfterPurchase = new DbGetter().getCarsById(testCar.getId());
        step(format("Проверка, что у машина с id = %d не принадлежит человеку с id = %d",
                testCar.getId(), testPerson.getId()), () ->
                assertAll(() -> {
                    assertFalse(carsAfterPurchase.isEmpty(), "Указанная машина не найдена в базе данных");
                    assertNotEquals(carsAfterPurchase.get(0).getPersonId(), testPerson.getId(),
                            "Пользователь является владельцем машины");
                })
        );

        step(format("Проверка, что у человека с id = %d после запроса на покупку машины " +
                "сумма денег на счете не изменилась", testPerson.getId()), () ->
                assertEquals(testPerson.getMoney(), resultPerson.getMoney(),
                        "Сумма на счете пользователя не соответствует ожидаемой")
        );
    }
}
