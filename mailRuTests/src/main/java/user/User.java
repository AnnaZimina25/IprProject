package user;

/**
 * Базовый класс для хранения информации пользователя
 */
public class User {

    protected String loginName;
    protected String domain;
    protected String password;

    public User(String loginName, String domain, String key) {
        this.loginName = loginName;
        this.domain = domain;
        this.password = System.getenv(key);
    }

    public String getLoginName() {
        return loginName;
    }

    public String getDomain() {
        return domain;
    }

    public String getPassword() {
        return password;
    }

    public String getFullEmailAddress(){
        return loginName + domain;
    }

    @Override
    public String toString() {
        return "User: " + getFullEmailAddress();
    }
}
