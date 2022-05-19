package pages.pageElements.panels;

import driver.WebDriverWrapper;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import pages.pageElements.BasePageElement;
import pages.pageElements.lightBox.MessageRedactor;

import static org.openqa.selenium.By.xpath;

/**
 * Область чтения письма почтового ящика mail.ru
 */
public class LetterReadingContainer extends BasePageElement {

    public LetterReadingContainer(WebDriverWrapper driver) {
        super(driver, "//div[@class='layout__letter-content']");
    }

    @Step("Дождаться открытия письма")
    public WebElement letterOpenWait() {
        return driver.findElement(xpath(elementRootXpath));
    }

    @Step("Поиск данных об отправителе")
    public WebElement getSender() {
        return driver.findElement(xpath(elementRootXpath + "//div[@class='letter__author']//span[@class='letter-contact']"));
    }

    @Step("Получить имя отправителя письма")
    public String getSenderName() {
        return getSender().getText();
    }

    @Step("Получить email отправителя письма")
    public String getSenderEmail() {
        return getSender().getAttribute("title");
    }

    @Step("Получить email получателя письма")
    public String getRecipientEmail() {
        return driver.findElement(xpath(elementRootXpath + "//div[contains(@class,'letter__recipients')]//span[@class='letter-contact']"))
                .getAttribute("title");
    }

    @Step("Получить время получения письма")
    public String getLetterReceiptDate() {
        return driver.findElement(xpath(elementRootXpath + "//div[@class='letter__date']"))
                .getText();
    }

    @Step("Получить тему письма")
    public String getLetterTheme() {
        return driver.findElement(xpath(elementRootXpath + "//h2[@class='thread-subject']"))
                .getText();
    }

    @Step("Получить кнопку футера по названию {footerButtonName}")
    public WebElement getFooterButton(String footerButtonName) {
        return driver.findElement(xpath(String.format("%s//div[@class='letter__footer-button']//span[text()='%s']",
                elementRootXpath, footerButtonName)));
    }

    @Step("Нажать кнопку 'Ответить' футера")
    public MessageRedactor footerReplyClick() {
        getFooterButton("Ответить").click();
        return new MessageRedactor(driver);
    }
}
