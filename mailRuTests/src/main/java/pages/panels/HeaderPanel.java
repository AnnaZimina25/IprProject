package pages.panels;

import driver.WebDriverWrapper;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import pages.lightBox.LoginForm;

import static org.openqa.selenium.By.xpath;

/**
 * Хидер страниц ресурса mail.ru
 */
public class HeaderPanel {

    private WebDriverWrapper driver;
    private String headerRoot = "//div[@data-testid='whiteline']";

    public HeaderPanel(WebDriverWrapper driver) {
        this.driver = driver;
    }

    @Step("Поиск ссылки хидера по названию '{text}'")
    public WebElement getHeaderLinkByText(String text) {
        return getElementByTextContains("a", text);
    }

    @Step("Поиск кнопки по названию '{text}'")
    public WebElement getButtonByText(String text) {
        return getElementByTextContains("button", text);
    }

    /**
     * Выполняет поиск элемента хидера по типу элемента и содержащемуся тексту
     *
     * @param elementType - тип элемнта (div, a, button и т.п.)
     * @param text - текст, который содержится в элементе
     */
    public WebElement getElementByTextContains(String elementType, String text) {
        String xpath = String.format("%s//%s[contains(.,'%s')]", headerRoot,
                elementType, text);
        return driver.findElement(xpath(xpath));
    }

    @Step("Выяснить является ли вкладка хидера '{tabName}' актиной")
    public boolean isHeaderTabActive(String tabName) {
        return getHeaderLinkByText(tabName)
                .getAttribute("class")
                .contains("ph-project_current");
    }

    @Step("Открыть форму логина")
    public LoginForm openLoginLightBox() {
        String parentHandle = driver.getWindowHandle();
        getButtonByText("Войти").click();
        return new LoginForm(driver);
    }

    @Step("Поиск инфо о пользователе")
    public WebElement getUserInfo() {
        return driver
                .findElement(xpath(String.format("%s//div[@data-testid='whiteline-account']", headerRoot)));
    }

    @Step("Развернуть правую панель пользователя")
    public UserPanel expandUserPanel() {
        getUserInfo().click();
        return new UserPanel(driver);
    }
}
