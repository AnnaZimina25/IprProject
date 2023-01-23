package apiTests;

import api.PerfApi;
import dataBase.DbGetter;
import dtoModels.Person;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import static dtoModels.Person.Sex.MALE;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositiveTests {

    private static final PerfApi perfApi = new PerfApi();

    @BeforeAll
    static void login(){
        perfApi.login();
    }

    @Test
    @Description("Позитивная проверка метода POST /user")
    @Tags({@Tag("positiveAddUser"), @Tag("Api")})
    void addUserCheck() {
        Person testPerson = new Person()
                .setFirstName("Big")
                .setSecondName("Lebovski")
                .setSex(MALE)
                .setAge(37)
                .setMoney(Double.valueOf("232313.00"));

        Person person = perfApi.addPerson(testPerson).checkStatusCode(201)
                .getBodyAsObject(Person.class);

        userFieldsCheck(testPerson, person);
    }

    @Test
    @Description("Позитивная проверка метода GET /user/{id} ")
    @Tags({@Tag("positiveGetUser"), @Tag("Api")})
    void getUserCheck() {
        Person testPerson = new DbGetter().getLastPerson();

        Person person = perfApi.getPerson(testPerson.getId())
                .checkStatusCode(200)
                .getBodyAsObject(Person.class);

        userFieldsCheck(testPerson, person);
    }

    @Test
    @Description("Позитивная проверка метода DELETE /user/{userId}")
    @Tags({@Tag("positiveDeleteUser"), @Tag("Api")})
    void deleteUserCheck() {
        int id = new DbGetter().getLastPersonWithoutHouseAndCars("Big", "Lebovski").getId();
        perfApi.deletePerson(id).checkStatusCode(204);

        assertTrue(new DbGetter().getPersonsById(id).isEmpty(),
                "Пользователь с id " + id + " не удалился из базы");
    }

    @Step("Проверка соответствия полей объекта 'User', полученного из API запроса, тестовым")
    private void userFieldsCheck(Person testPerson, Person checkedPerson) {
        assertAll("При проверке полей user найдены несоответствия: ", () -> {
                    assertEquals(testPerson.getAge(), checkedPerson.getAge(), "age");
                    assertEquals(testPerson.getFirstName(), checkedPerson.getFirstName(), "firstName");
                    assertEquals(testPerson.getSecondName(), checkedPerson.getSecondName(), "secondName");
                    assertEquals(testPerson.getMoney(), checkedPerson.getMoney(), "money");
                    assertEquals(testPerson.getHouse_id(), checkedPerson.getHouse_id(), "house_id");
                }
        );
    }
}
