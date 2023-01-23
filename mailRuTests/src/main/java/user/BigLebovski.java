package user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс для хранения информации пользователя BigLebovski
 */
public class BigLebovski extends User {

    private static Logger log = LogManager.getRootLogger();

    private static BigLebovski instance = null;

    public static BigLebovski getInstance() {
        if (instance == null) {
            instance = new BigLebovski();
            log.debug("Создал экземпляр пользователя BigLebovski почты Mail.ru (синглтон).");
        }
        return instance;
    }

    private BigLebovski() {
        super("gdedengi.lebovski", "@inbox.ru", "bigLebovski");
        log.debug("Инициализирую экземпляр пользователя BigLebovski.");
    }
}
