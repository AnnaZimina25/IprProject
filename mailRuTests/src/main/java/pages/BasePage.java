package pages;

import driver.WebDriverWrapper;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.panels.HeaderPanel;
import user.User;

import static io.qameta.allure.Allure.step;

/**
 * Базовый класс для тестируемых страниц
 */
public class BasePage {

    static Logger log = LogManager.getRootLogger();
    protected WebDriverWrapper driver = WebDriverWrapper.getInstance();
    protected String pageName;
    protected String address;
    protected HeaderPanel headerPanel;

    protected BasePage(String pageName, String address) {
        this.pageName = pageName;
        this.address = address;
        this.headerPanel = new HeaderPanel(driver);
    }

    protected BasePage(String pageName) {
        this.pageName = pageName;
        this.address = driver.getCurrentUrl();
        this.headerPanel = new HeaderPanel(driver);
    }

    @Step("Закрыть текущую страницу")
    public void close() {
        log.info("Закрываю текущую страницу.");
        driver.close();
    }

    @Step("Открыть страницу")
    public void open() {
        log.info("Открываю страницу {} по адресу {}",
                this.pageName,
                this.address);
        driver.get(this.address);
    }

    @Step("Перейти к сервису {serviceName}")
    public void redirectToServiceByName(String serviceName) {
        headerPanel.getHeaderLinkByText(serviceName).click();
    }

    public void login(User user) {
        step("Залогиниться под пользователем " + user.getLoginName(), () -> {
            log.info("Выполняю процедуру логина для пользователя {}",
                    user.getLoginName());
            headerPanel.openLoginLightBox()
                    .fillLoginField(user.getLoginName())
                    .selectDomain(user.getDomain())
                    .fillPassword(user.getPassword())
                    .toSelectRememberMe(false)
                    .submitPassword();
        });
    }

    @Step("Выйти из системы")
    public void logout() {
        log.info("Выхожу из системы");
        headerPanel.expandUserPanel().logout();
    }

    public WebDriverWrapper getDriver() {
        return driver;
    }

    public HeaderPanel getHeaderPanel() {
        return headerPanel;
    }
}
