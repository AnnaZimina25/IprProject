package apiTests;

import api.PerfApi;
import dtoModels.Person;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static dtoModels.Person.Sex.FEMALE;
import static dtoModels.Person.Sex.MALE;


public class FirstTest {

    PerfApi perfApi = new PerfApi();

    @Test
    @Tag("apiTest")
    void checkWorks() {

 //      String users = perfApi.getApiUsers().getBodyAsStr();
 //       perfApi.login();

        List<Person> people = perfApi
                .getPersons()
                .checkStatusCode(200)
                .checkHeader("Content-Type", "application/json")
                .getBodyAsObjectsList(Person.class);

        people.forEach(System.out::println);
        System.out.println(people.get(0) != null);
    }

    @Test
    @Tag("apiTest2")
    void checkWorks2() {
        Person person = perfApi
                .getPerson(7)
                .checkStatusCode(200)
                .checkHeader("Content-Type", "application/json")
                .getBodyAsObject(Person.class);

        System.out.println(person);
    }

    @Test
    @Description("Проверка запроса на создание пользователя")
    @Tags({@Tag("AddUser"), @Tag("Api")})
    void addUserCheck() {
        Person testPerson = new Person()
                .setFirstName("Big")
                .setSecondName("Lebovski")
                .setSex(MALE)
                .setAge(37)
                .setMoney(new BigDecimal("232313.00"));
        perfApi.addPerson(testPerson);


//        Person user = perfApi
//                .getUser(7)
//                .checkStatusCode(200)
//                .checkHeader("Content-Type", "application/json")
//                .getBodyAsObject(Person.class);

       // System.out.println(user);
    }
}
