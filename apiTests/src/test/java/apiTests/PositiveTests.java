package apiTests;

import api.PerfApi;
import dataBase.DbGetter;
import dtoModels.car.Car;
import dtoModels.person.Person;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static dtoModels.person.Sex.MALE;
import static io.qameta.allure.Allure.step;
import static java.lang.Math.random;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositiveTests {

    private static final PerfApi perfApi = new PerfApi();

    @BeforeAll
    static void login() {
        perfApi.login();
    }

    @Test
    @Description("Позитивная проверка метода POST /user")
    @Tags({@Tag("positiveAddUser"), @Tag("Api")})
    void addUserTest() {
        Person testPerson = new Person()
                .setFirstName("Big")
                .setSecondName("Lebovski")
                .setSex(MALE)
                .setAge(generateAge(18, 101))
                .setMoney(generateMoney(0, 300000));

        Person person = perfApi.addPerson(testPerson).checkStatusCode(201)
                .getBodyAsObject(Person.class);

        userFieldsCheck(testPerson, person);
        assertFalse(new DbGetter().getPersonsById(person.getId()).isEmpty(),
                "В базе данных не найден пользователь с id = " + person.getId());
    }

    @Test
    @Description("Позитивная проверка метода GET /user/{id} ")
    @Tags({@Tag("positiveGetUser"), @Tag("Api")})
    void getUserTest() {
        Person testPerson = new DbGetter().getRandomPerson();

        Person person = perfApi.getPerson(testPerson.getId())
                .checkStatusCode(200)
                .getBodyAsObject(Person.class);

        userFieldsCheck(testPerson, person);
    }

    @Test
    @Description("Позитивная проверка метода DELETE /user/{userId}")
    @Tags({@Tag("positiveDeleteUser"), @Tag("Api")})
    void deleteUserTest() {
        int id = new DbGetter().getLastPersonWithoutHouseAndCars("Big", "Lebovski").getId();
        perfApi.deletePerson(id).checkStatusCode(204);

        assertTrue(new DbGetter().getPersonsById(id).isEmpty(),
                "Пользователь с id " + id + " не удалился из базы");
    }

    @Test
    @Description("Позитивная проверка метода POST /user/{userId}/buyCar/{carId}")
    @Tags({@Tag("buyCarUser"), @Tag("Api")})
    void buyCarUserTest() {

        Car testCar = new DbGetter().getCarWithoutOwnerWithPriceMoreThan(new BigDecimal(10000));
        Person testPerson = new DbGetter().getPersonWithoutHouseAndCarsWithMoney(testCar.getPrice(), true);

        Person personAfterPurchase = perfApi.buyCar(testPerson.getId(), testCar.getId())
                .checkStatusCode(200)
                .getBodyAsObject(Person.class);

        List<Car> carsAfterPurchase = new DbGetter().getCarsById(testCar.getId());
        step(format("Проверка, что машина с id = %d принадлежит человеку с id = %d",
                testCar.getId(), testPerson.getId()), () ->
                assertAll(() -> {
                    assertFalse(carsAfterPurchase.isEmpty(), "Указанная машина не найдена в базе данных");
                    assertEquals(carsAfterPurchase.get(0).getPersonId(), testPerson.getId(),
                            "Пользователь не является владельцем машины");
                })
        );

        step(format("Проверка, что у человека с id = %d после покупки машины уменьшилась сумма денег на счете",
                testPerson.getId()), () -> {
            BigDecimal testSumAfter = testPerson.getMoney().subtract(testCar.getPrice());
            BigDecimal factSumAfter = personAfterPurchase.getMoney();
            assertEquals(testSumAfter, factSumAfter, "Сумма на счете пользователя не соответствует ожидаемой");
        });
    }

    @Step("Проверка соответствия полей объекта 'User', полученного из API запроса, тестовым")
    private void userFieldsCheck(Person testPerson, Person checkedPerson) {
        assertAll("При проверке полей user найдены несоответствия: ", () -> {
                    assertEquals(testPerson.getAge(), checkedPerson.getAge(), "age");
                    assertEquals(testPerson.getFirstName(), checkedPerson.getFirstName(), "firstName");
                    assertEquals(testPerson.getSecondName(), checkedPerson.getSecondName(), "secondName");
                    assertEquals(testPerson.getMoney(), checkedPerson.getMoney(), "money");
                    assertEquals(testPerson.getHouse_id(), checkedPerson.getHouse_id(), "house_id");
                    assertEquals(testPerson.getSex(), checkedPerson.getSex(), "sex");
                }
        );
    }

    private int generateAge(int minAge, int maxAge) {
        return (int) (minAge + random() * (maxAge - minAge));
    }

    private BigDecimal generateMoney(double minSum, int maxSum) {
        double value = minSum + random() * (maxSum - minSum);
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
    }
}
