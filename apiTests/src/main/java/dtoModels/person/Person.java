package dtoModels.person;

import dtoModels.Model;

import java.math.BigDecimal;

import static dtoModels.person.Sex.*;

/**
 * Класс хранения данных модели User
 */
public class Person extends Model {

    private Integer id;
    private String firstName;
    private String secondName;
    private Integer age;
    private String sex;
    private BigDecimal money;
    private Integer house_id;

    public Person() {
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

    public Person setMoney(BigDecimal money) {
        this.money = money;
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
}
