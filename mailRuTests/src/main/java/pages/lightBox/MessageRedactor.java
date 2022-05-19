package pages.lightBox;

import driver.WebDriverWrapper;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.By.xpath;

/**
 * Форма составления писльма Mail.ru
 */
public class MessageRedactor {
    private WebDriverWrapper driver;
    private String formRoot = "//div[contains(@class,'compose-app_window')]";

    public MessageRedactor(WebDriverWrapper driver) {
        this.driver = driver;
    }

    @Step("Поиск формы редактора сообщения")
    public WebElement getRedactorForm() {
        return driver.findElement(xpath(formRoot));
    }

    @Step("Поиск кнопки футера редактора '{buttonText}'")
    public WebElement getFooterButton(String buttonText) {
        return driver.findElement(xpath(
                String.format("%s//*[text()='%s']", formRoot, buttonText)));
    }

    @Step("Нажать 'Отправить'")
    public NotificationForm sendEmailClick() {
        getFooterButton("Отправить").click();
        return new NotificationForm(driver);
    }

    @Step("Заполнить поле 'Кому' текстом {text}")
    public MessageRedactor fillContactsInput(String text) {
        driver.findElement(xpath(formRoot + "//div[contains(@class,'contacts')]//input"))
                .sendKeys(text);
        return this;
    }

    @Step("Заполнить поле 'Тема' текстом {text}")
    public MessageRedactor fillSubjectInput(String text) {
        driver.findElement(xpath(formRoot + "//div[contains(@class,'subject')]//input"))
                .sendKeys(text);
        return this;
    }

    @Step("Ввести текст сообщения {text}")
    public MessageRedactor fillMessageText(String text) {
        driver.findElement(xpath(formRoot + "//div[@role='textbox']/div[1]"))
                .sendKeys(text);
        return this;
    }

    @Step("Закрыть редактор сообщения")
    public MessageRedactor closeButtonClick() {
        driver.findElement(xpath(formRoot + "//button[@title='Закрыть']")).click();
        return this;
    }
}
