package dataBase;

import dtoModels.Person;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
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

    @Step("Получить из таблицы 'person' последнюю запись")
    public Person getLastPerson() {
        List<Person> persons = new ArrayList<>();
        try {
            ResultSet resultSet = executeQuery("select * from public.person " +
                    "order by id DESC limit 1");
            while (resultSet.next()) {
                Person person = new Person();
                person.setAge(resultSet.getInt("age"))
                        .setFirstName(resultSet.getString("first_name"))
                        .setSecondName(resultSet.getString("second_name"))
                        .setId(resultSet.getInt("id"))
                        .setMoney(resultSet.getDouble("money"))
                        .setDbSex(resultSet.getBoolean("sex"));

                int house_id = resultSet.getInt("house_id");
                if (resultSet.wasNull())
                    person.setHouse_id(null);
                else person.setHouse_id(house_id);

                persons.add(person);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        assumeFalse(persons.isEmpty(), "В таблице 'person' не нашлось ни одной записи");
        return persons.get(0);
    }

    @Step("Получить из таблицы 'person' человека с именем '{firstName}', фамилией '{lastName}', " +
            "которые не прописаны ни в одном доме и не владеют ни одной машинамой")
    public Person getLastPersonWithoutHouseAndCars(String firstName, String lastName) {
        List<Person> persons = new ArrayList<>();
        String queryStr = format("select * from public.person p " +
                "where p.first_name = '%s' and p.second_name='%s' and p.house_id is null " +
                "and (select count(*) from public.car c where c.id = p.id) = 0 " +
                "order by p.id DESC " +
                "limit 1", firstName, lastName);
        try {
            ResultSet resultSet = executeQuery(queryStr);
            while (resultSet.next()) {
                Person person = new Person();
                person.setAge(resultSet.getInt("age"))
                        .setFirstName(resultSet.getString("first_name"))
                        .setSecondName(resultSet.getString("second_name"))
                        .setId(resultSet.getInt("id"))
                        .setMoney(resultSet.getDouble("money"))
                        .setDbSex(resultSet.getBoolean("sex"));

                persons.add(person);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        assumeFalse(persons.isEmpty(), "В таблице 'person' не нашлось ни одной записи");
        return persons.get(0);
    }

    @Step("Получить из таблицы 'person' список людей с id = {personId}")
    public List<Person> getPersonsById(int personId) {
        List<Person> persons = new ArrayList<>();
        String queryStr = format("select * from public.person " +
                "where id = %d " +
                "limit 1", personId);
        try {
            ResultSet resultSet = executeQuery(queryStr);
            while (resultSet.next()) {
                Person person = new Person();
                person.setAge(resultSet.getInt("age"))
                        .setFirstName(resultSet.getString("first_name"))
                        .setSecondName(resultSet.getString("second_name"))
                        .setId(resultSet.getInt("id"))
                        .setMoney(resultSet.getDouble("money"))
                        .setDbSex(resultSet.getBoolean("sex"));

                persons.add(person);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return persons;
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
