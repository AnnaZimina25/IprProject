package dtoModels;

import io.qameta.allure.Step;

import java.math.BigDecimal;
import java.util.Locale;

import static dtoModels.Person.Sex.FEMALE;
import static dtoModels.Person.Sex.MALE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс хранения данных модели User
 */
public class Person extends Model{

//    "id": 1,
//            "firstName": "Vasiliy",
//            "secondName": "Rubenstein",
//            "age": 42,
//            "sex": "MALE",
//            "money": 1000000.00

    private Integer id;
    private String firstName;
    private String secondName;
    private Integer age;
    private String sex;
    private BigDecimal money;
    private Integer house_id;

    public Person() {
    }

    public Person(String firstName, String secondName, Integer age, String sex, BigDecimal money) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.sex = sex;
        this.money = money;
    }


    public Person setId(Integer id) {
        this.id = id;
        return this;
    }

    public Person setHouse_id(Integer house_id) {
        this.house_id = house_id;
        return this;
    }

    public Person setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Person setSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public Person setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Person setSex(Sex sex) {
        this.sex = sex.name();
        return this;
    }

    public Person setDbSex(Boolean dataBaseValue) {
        this.sex = dataBaseValue ?
                MALE.name() : FEMALE.name();
        return this;
    }

    public Person setMoney(Double money) {
        this.money = money != null? new BigDecimal(money) : null;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public Integer getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public Integer getHouse_id() {
        return house_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", money=" + money +
                ", house_id=" + house_id +
                '}';
    }

    public enum Sex {
        MALE(true),
        FEMALE(false);

       private Boolean databaseValue;

        public Boolean getDatabaseValue() {
            return databaseValue;
        }

        Sex(Boolean databaseValue) {
            this.databaseValue = databaseValue;
        }

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
