package checkLogin;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;
import pages.MainPage;
import pages.lightBox.LoginForm;
import user.BigLebovski;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NegativeLoginCheckTest {
    private static MainPage mainPage = new MainPage();
    private static BigLebovski correctUser = BigLebovski.getInstance();

    @BeforeEach
    void openMainPage() {
        mainPage.open();
    }

    @Test
    @Description("Проверка процедуры логина под некорректным пользователем")
    @Tags({@Tag("NegativeLoginCheck"), @Tag("IncorrectUser")})
    void incorrectUserCheck() {
        LoginForm loginForm = mainPage.headerPanel.openLoginLightBox()
                .fillLoginField("%~randomUser3324465()")
                .toSelectRememberMe(false)
                .fillPasswordButtonClick();

        WebElement loginWarning = loginForm.getAccountNotRegisteredWarning();

        step("Проверка видимости предупреждения 'Такой аккаунт не зарегистрирован'",
                () -> assertTrue(loginWarning.isDisplayed(),
                        "Предупреждение не отображается для пользователя")
        );

        step("Проверка соответствия текста предупреждения 'Такой аккаунт не зарегистрирован'",
                () -> assertEquals("Такой аккаунт не зарегистрирован",
                        loginWarning.getText(),
                        "Текст предупреждения не соответсвует ожидаемому")
        );

        step("Проверка, что при неверном логине, нас не переблосило на форму ввода пароля",
                () -> assertEquals("Войти в аккаунт", loginForm.getFormHeader().getText(),
                        "Заголовок формы не соответсвует ожидаемому")
        );
    }

    @Test
    @Description("Проверка процедуры логина под пустым пользователем")
    @Tags({@Tag("NegativeLoginCheck"), @Tag("EmptyUser")})
    void emptyUserCheck() {
        LoginForm loginForm = mainPage.headerPanel.openLoginLightBox()
                //.fillLoginField("randomUser3324465()")
                .toSelectRememberMe(false)
                .fillPasswordButtonClick();

        WebElement loginWarning = loginForm.getEmptyAccountWarning();

        step("Проверка видимости предупреждения 'Такой аккаунт не зарегистрирован'",
                () -> assertTrue(loginWarning.isDisplayed(),
                        "Предупреждение не отображается для пользователя")
        );

        step("Проверка соответствия текста предупреждения 'Такой аккаунт не зарегистрирован'",
                () -> assertEquals("Поле «Имя аккаунта» должно быть заполнено",
                        loginWarning.getText(),
                        "Текст предупреждения не соответсвует ожидаемому")
        );

        step("Проверка, что при неверном логине, нас не переблосило на форму ввода пароля",
                () -> assertEquals("Войти в аккаунт", loginForm.getFormHeader().getText(),
                        "Заголовок формы не соответсвует ожидаемому")
        );
    }

    @Test
    @Description("Проверка процедуры логина с некорректным паролем")
    @Tags({@Tag("NegativeLoginCheck"), @Tag("IncorrectPassword")})
    void incorrectPasswordCheck() {
        LoginForm loginForm = mainPage.headerPanel.openLoginLightBox()
                .fillLoginField(correctUser.getLoginName())
                .selectDomain(correctUser.getDomain())
                .fillPassword("randomPassword89758439u5cv")
                .toSelectRememberMe(false)
                .submitPassword();

        WebElement passwordWarning = loginForm.getWrongPasswordWarning();
        step("Проверка видимости предупреждения 'Неверный пароль, попробуйте ещё раз'",
                () -> assertTrue(passwordWarning.isDisplayed(),
                        "Предупреждение не отображается для пользователя")
        );

        step("Проверка соответствия текста предупреждения 'Неверный пароль, попробуйте ещё раз'",
                () -> assertEquals("Неверный пароль, попробуйте ещё раз",
                        passwordWarning.getText(),
                        "Текст предупреждения не соответсвует ожидаемому")
        );

        step("Проверка, что при неверном пароле, пользователь остался на форме ввода пароля",
                () -> assertEquals("Введите пароль", loginForm.getFormHeader().getText(),
                        "Заголовок формы не соответсвует ожидаемому")
        );
    }

    @AfterAll
    static void close() {
        mainPage.close();
    }
}
