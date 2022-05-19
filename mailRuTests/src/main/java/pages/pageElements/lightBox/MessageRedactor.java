package pages.pageElements.lightBox;

import driver.WebDriverWrapper;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.By.xpath;

/**
 * Форма составления писльма Mail.ru
 */
public class MessageRedactor extends BaseForm {
    public MessageRedactor(WebDriverWrapper driver) {
        super(driver, "//div[contains(@class,'compose-app_window')]");
    }

    @Step("Поиск кнопки футера редактора '{buttonText}'")
    public WebElement getFooterButton(String buttonText) {
        return driver.findElement(xpath(
                String.format("%s//*[text()='%s']", elementRootXpath, buttonText)));
    }

    @Step("Нажать 'Отправить'")
    public NotificationForm sendEmailClick() {
        getFooterButton("Отправить").click();
        return new NotificationForm(driver);
    }

    @Step("Заполнить поле 'Кому' текстом {text}")
    public MessageRedactor fillContactsInput(String text) {
        driver.findElement(xpath(elementRootXpath + "//div[contains(@class,'contacts')]//input"))
                .sendKeys(text);
        return this;
    }

    @Step("Заполнить поле 'Тема' текстом {text}")
    public MessageRedactor fillSubjectInput(String text) {
        driver.findElement(xpath(elementRootXpath + "//div[contains(@class,'subject')]//input"))
                .sendKeys(text);
        return this;
    }

    @Step("Ввести текст сообщения {text}")
    public MessageRedactor fillMessageText(String text) {
        driver.findElement(xpath(elementRootXpath + "//div[@role='textbox']/div[1]"))
                .sendKeys(text);
        return this;
    }
}
