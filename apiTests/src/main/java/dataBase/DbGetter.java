package dataBase;

import dtoModels.car.Car;
import dtoModels.person.Person;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

/**
 * Класс для формирования запросов к БД 'pflb_trainingcenter'
 */
public class DbGetter {
    private static Logger log = LogManager.getRootLogger();
    private final String DB_URL = "jdbc:postgresql://77.50.236.203:4832/pflb_trainingcenter";
    private final String USER = "pflb-at-read";
    private final String PASS = System.getenv("dbPass");
    private Connection connection;

    public DbGetter() {
        try {
            log.debug("Загрузка postgresql драйвера'");
            Class.forName("org.postgresql.Driver");
            log.debug("Подключение к БД 'pflb_trainingcenter'");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet executeQuery(String query) throws SQLException {
        log.debug("Выполнение запроса к БД 'pflb_trainingcenter'");
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    @Step("Получить из таблицы 'person' случайную запись")
    public Person getRandomPerson() {
        List<Person> persons = getPersons("select * from public.person " +
                "order by id DESC");

        assumeFalse(persons.isEmpty(), "В таблице 'person' не нашлось ни одной записи");
        Collections.shuffle(persons);
        return persons.get(0);
    }

    @Step("Получить из таблицы 'person' человека с именем '{firstName}', фамилией '{lastName}', " +
            "который не прописан ни в одном доме и не владеет ни одной машиной")
    public Person getLastPersonWithoutHouseAndCars(String firstName, String lastName) {
        String queryStr = format("select * from public.person p " +
                "where p.first_name = '%s' and p.second_name='%s' and p.house_id is null " +
                "and (select count(*) from public.car c where c.id = p.id) = 0 " +
                "order by p.id DESC " +
                "limit 1", firstName, lastName);

        List<Person> persons = getPersons(queryStr);
        assumeFalse(persons.isEmpty(), "В таблице 'person' не нашлось ни одной записи");
        return persons.get(0);
    }

    @Step("Получить из таблицы 'person' человека который не прописан ни в одном доме и не владеет ни одной машиной и" +
            "счет котого соответствует указанным параметрам")
    public Person getPersonWithoutHouseAndCarsWithMoney(BigDecimal money, boolean moreThan) {
        String queryStr = format("select * from public.person p " +
                        "where p.money %s and p.house_id is null " +
                        "and (select count(*) from public.car c where c.id = p.id) = 0 ",
                (moreThan ? " > " : " < ") + money);

        List<Person> persons = getPersons(queryStr);
        Collections.shuffle(persons);
        assumeFalse(persons.isEmpty(), "В таблице 'person' не нашлось ни одной записи");
        return persons.get(0);
    }

    @Step("Получить из таблицы 'car' машину стоимостью более {price} без владельца")
    public Car getCarWithoutOwnerWithPriceMoreThan(BigDecimal price) {
        String queryStr = format("select * from public.car c " +
                "where c.person_id is null and c.price > %s " +
                "order by c.price " +
                "limit 1", price);

        List<Car> cars = getCars(queryStr);
        assumeFalse(cars.isEmpty(), "В таблице 'car' не нашлось ни одной записи");
        return cars.get(0);
    }

    @Step("Получить из таблицы 'car' список машин с id={carId}")
    public List<Car> getCarsById(int carId) {
        String queryStr = format("select * from public.car c " +
                "where c.id = %d " +
                "limit 1", carId);

        return getCars(queryStr);
    }

    @Step("Получить из таблицы 'person' список людей с id = {personId}")
    public List<Person> getPersonsById(int personId) {
        return getPersons(format("select * from public.person " +
                "where id = %d " +
                "limit 1", personId));
    }

    private List<Person> getPersons(String query) {
        List<Person> persons = new ArrayList<>();
        try {
            ResultSet resultSet = executeQuery(query);

            while (resultSet.next()) {
                persons.add(parsePersonTable(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return persons;
    }

    private List<Car> getCars(String query) {
        List<Car> cars = new ArrayList<>();
        try {
            ResultSet resultSet = executeQuery(query);

            while (resultSet.next()) {
                cars.add(parseCarTable(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return cars;
    }

    private Person parsePersonTable(ResultSet resultSet) throws SQLException {
        Person person = new Person();
        person.setAge(resultSet.getInt("age"))
                .setFirstName(resultSet.getString("first_name"))
                .setSecondName(resultSet.getString("second_name"))
                .setId(resultSet.getInt("id"))
                .setMoney(resultSet.getBigDecimal("money"))
                .setDbSex(resultSet.getBoolean("sex"));

        int house_id = resultSet.getInt("house_id");
        if (resultSet.wasNull())
            person.setHouse_id(null);
        else person.setHouse_id(house_id);
        return person;
    }

    private Car parseCarTable(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        car.setDbEngineType(resultSet.getInt("engine_type_id"))
                .setId(resultSet.getInt("id"))
                .setMark(resultSet.getString("mark"))
                .setModel(resultSet.getString("model"))
                .setPrice(resultSet.getBigDecimal("price"));

        int person_id = resultSet.getInt("person_id");
        if (resultSet.wasNull())
            car.setPersonId(null);
        else car.setPersonId(person_id);
        return car;
    }

    public void close() {
        if (connection != null) {
            try {
                log.debug("Отключение от БД 'pflb_trainingcenter'");
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
