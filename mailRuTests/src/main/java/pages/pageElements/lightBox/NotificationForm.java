package pages.pageElements.lightBox;

import driver.WebDriverWrapper;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.By.xpath;

/**
 * Форма уведомления Mail.ru
 */
public class NotificationForm extends BaseForm{
    public NotificationForm(WebDriverWrapper driver) {
        super(driver, "//div[contains(@class,'layer_media')]");
    }

    @Step("Поиск заголовка уведомления")
    public WebElement getNotificationHeader() {
        return driver.findElement(xpath(elementRootXpath + "//a[@class='layer__link']"));
    }

    @Step("Убедиться, что заголовок уведомления содержит текст: '{text}'")
    public boolean headerContains(String text) {
       return getNotificationHeader().getText().contains(text);
    }

    @Step("Ожидание исчезновения фомы")
    public void waitingForDisappear() {
        driver.waitingForDisappearElement(xpath(elementRootXpath), 40);
    }
}
