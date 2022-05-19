package pages.panels;

import driver.WebDriverWrapper;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Панель для взаимодействия с данными аккаунта ресурса mail.ru
 */
public class UserPanel {

    private WebDriverWrapper driver;
    private String rootElement = "//div[contains(@class,'sidebar__frame')]";

    public UserPanel(WebDriverWrapper driver) {
        this.driver = driver;
    }

    /**
     * Выполняет поиск элемента боковой панели по типу элемента и тексту
     *
     * @param elementType - тип элемнта (div, a, button и т.п.)
     * @param text - текст, который содержится в элементе
     */
    public WebElement getElementByTextContains(String elementType, String text) {
        String xpath = String.format("%s//%s[text()='%s']", rootElement,
                elementType, text);
        return driver.findElement(By.ByXPath.xpath(xpath));
    }

    @Step("Выйти из аккаунта")
    public void logout() {
        getElementByTextContains("div","Выйти").click();
    }
}
