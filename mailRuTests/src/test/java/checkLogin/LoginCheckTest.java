package checkLogin;

import user.User;
import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import pages.MailPage;
import pages.MainPage;
import user.BigLebovski;
import user.YolterYait;

import java.util.stream.Stream;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginCheckTest {
    static MainPage mainPage = new MainPage();

    @ParameterizedTest(name = "{index} - {0} login check")
    @ArgumentsSource(UserProvider.class)
    @Description("Проверка процедуры логина с корректными данными")
    @Tags({@Tag("LoginCheck"), @Tag("CorrectDataLogin")})
    void correctDataLoginTest(User user) {
        mainPage.open();
        mainPage.login(user);

        MailPage mailPage = new MailPage();
        step("Проверка, что в правом верхнем углу появилась информация о пользователе",
                () -> assertTrue(mailPage.getHeaderPanel().getUserInfo().isDisplayed(),
                        "Информация авторизованного пользователя не отображается")
        );

        step("Проверка, что информация о пользователе содержит корректный адрес",
                () -> assertEquals(user.getFullEmailAddress(),
                        mailPage.getHeaderPanel().getUserInfo().getAttribute("aria-label"),
                        "Адрес почты не совпадает с указанным")
        );

        step("Проверка, что вкладка 'Почта' стала активной",
                () -> assertTrue(mailPage.getHeaderPanel().isHeaderTabActive("Почта"),
                        "Вкладка 'Почта' не является активной")
        );
    }

    @AfterEach
    void logout() {
        mainPage.logout();
    }

    @AfterAll
    static void close() {
        mainPage.getDriver().quit();
    }

    public static class UserProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(BigLebovski.getInstance()),
                    Arguments.of(YolterYait.getInstance())
            );
        }
    }
}
