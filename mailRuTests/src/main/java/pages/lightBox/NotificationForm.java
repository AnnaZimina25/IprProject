package pages.lightBox;

import driver.WebDriverWrapper;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.By.xpath;

/**
 * Форма уведомления Mail.ru
 */
public class NotificationForm {

    private WebDriverWrapper driver;
    private String formRoot = "//div[contains(@class,'layer_media')]";

    public NotificationForm(WebDriverWrapper driver) {
        this.driver = driver;
    }

    @Step("Ожидание загрузки фомы")
    public WebElement waitingFormIsVisible() {
        return driver.findElement(xpath(formRoot));
    }

    @Step("Поиск заголовка уведомления")
    public WebElement getNotificationHeader() {
        return driver.findElement(xpath(formRoot + "//a[@class='layer__link']"));
    }

    @Step("Закрыть уведомление")
    public NotificationForm closeButtonClick() {
        driver.findElement(xpath(formRoot + "//span[@title='Закрыть']")).click();
        return this;
    }

    @Step("Убедиться, что заголовок уведомления содержит текст: '{text}'")
    public boolean headerContains(String text) {
       return getNotificationHeader().getText().contains(text);
    }

    @Step("Ожидание исчезновения фомы")
    public void waitingForDisappear() {
        driver.waitingForDisappearElement(xpath(formRoot), 40);
    }
}
