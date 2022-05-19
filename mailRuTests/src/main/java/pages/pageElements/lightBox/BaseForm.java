package pages.pageElements.lightBox;

import driver.WebDriverWrapper;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import pages.pageElements.BasePageElement;

import static org.openqa.selenium.By.xpath;

/**
 * Базовый класс формы лайт-бокса ресурса mail.ru
 */
public class BaseForm extends BasePageElement {
    public BaseForm(WebDriverWrapper driver, String elementRootXpath) {
        super(driver, elementRootXpath);
    }

    @Step("Ожидание загрузки фомы")
    public WebElement waitingFormIsVisible() {
        return driver.findElement(xpath(elementRootXpath));
    }

    @Step("Закрыть форму")
    public void closeButtonClick() {
        driver.findElement(xpath(elementRootXpath + "//*[@title='Закрыть']")).click();
    }
}
