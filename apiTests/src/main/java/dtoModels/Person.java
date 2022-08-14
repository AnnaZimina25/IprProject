package dtoModels;

import java.math.BigDecimal;
import java.util.Locale;

public class Person {

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

//    public Person(String firstName, String secondName, Integer age, String sex, BigDecimal money) {
//        this.firstName = firstName;
//        this.secondName = secondName;
//        this.age = age;
//        this.sex = sex;
//        this.money = money;
//    }

    public Person setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Person setSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public Person setAge(int age) {
        this.age = age;
        return this;
    }

    public Person setSex(Sex sex) {
        this.sex = sex.name();
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

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", money=" + money +
                '}';
    }

    public enum Sex {
        MALE(true),
        FEMALE(false);

       private boolean databaseValue;

        public boolean getDatabaseValue() {
            return databaseValue;
        }

        Sex(boolean databaseValue) {
            this.databaseValue = databaseValue;
        }

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
