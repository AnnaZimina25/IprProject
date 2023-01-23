package dtoModels;

/**
 * Класс для парсинга ответа на зопрос авторизации
 */
public class AuthorizationResponse {
   private String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
