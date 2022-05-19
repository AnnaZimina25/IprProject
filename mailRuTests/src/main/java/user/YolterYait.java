package user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс для хранения информации пользователя YolterYait
 */
public class YolterYait extends User {

    private static Logger log = LogManager.getRootLogger();
    private String loginName;
    private String domain;
    private String password;

    private static YolterYait instance = null;

    public static YolterYait getInstance() {
        if (instance == null) {
            instance = new YolterYait();
            log.debug("Создал экземпляр пользователя YolterYait почты Mail.ru (синглтон).");
        }
        return instance;
    }

    private YolterYait() {
        super("heisenberg.08", "@mail.ru", "heisenberg");
        log.debug("Инициализирую экземпляр пользователя YolterYait.");
    }
}
