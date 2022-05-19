package pages.pageElements.panels;

import driver.WebDriverWrapper;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import pages.pageElements.BasePageElement;

import java.util.Collections;
import java.util.List;

import static org.openqa.selenium.By.xpath;

/**
 * Область c письмами (одной папки) почтового ящика mail.ru
 */
public class LettersListContainer extends BasePageElement {

    private static String letterRoot = "a[contains(@class,'llc_')]";

    public LettersListContainer(WebDriverWrapper driver) {
        super(driver, "//div[@class='letter-list__react']");
    }

    @Step("Дождаться обновления писем в папке")
    public WebElement folderCheckoutWait() {
        return driver.findElement(xpath(elementRootXpath));
    }

    @Step("Поиск сообщения о том, что выбранная папка не содержит писем")
    public WebElement getEmptyPackageDescription() {
        return driver.findElement(xpath(elementRootXpath + "//*[@class='octopus__title']"));
    }

    @Step("Поиск всех отображающихся писем выбранной папки")
    public List<WebElement> getAllLetters() {
        return driver.findElements(xpath(elementRootXpath + "//" +letterRoot));
    }

    @Step("Поиск всех отображающихся непрочитанных писем выбранной папки")
    public List<WebElement> getOnlyUnreadLetters() {
        return driver.findElements(xpath(
                String.format("%s//div[contains(@class,'unread')]/ancestor::%s",
                        elementRootXpath, letterRoot)));
    }

    public WebElement getRandomLetter(List<WebElement> letters) {
        Collections.shuffle(letters);
        return letters.stream().findFirst().orElse(null);
    }

    @Step("Получить случайное письмо выбранной папки")
    public WebElement getRandomLetterFromAll() {
      return getRandomLetter(getAllLetters());
    }

    @Step("Получить случайное непрочитанное письмо выбранной папки")
    public WebElement getRandomLetterFromUnread() {
        return getRandomLetter(getOnlyUnreadLetters());
    }

    @Step("Найти письмо по теме {theme}")
    public WebElement getLetterByTheme(String theme) {
        return driver.findElement(xpath(String.format("%s//span[text()='%s']/ancestor::%s",
                elementRootXpath, theme, letterRoot)));
    }

    @Step("Убедиться, что письмо не прочитано")
    public boolean isLetterUnread(WebElement letterRoot) {
        return letterRoot.findElement(xpath("//div[contains(@class,'unread')]")).isDisplayed();
    }

    @Step("Получить имя отправителя")
    public String getLetterSender(WebElement letterRoot) {
       return letterRoot.findElement(xpath(".//span[@class='ll-crpt']")).getText();
    }

    @Step("Получить тему письма")
    public String getLetterTheme(WebElement letterRoot) {
        return letterRoot.findElement(xpath(".//span[@class='ll-sj__normal']"))
                .getText();
    }

    @Step("Получить время получения письма")
    public String getLetterReceiptDate(WebElement letterRoot) {
        return letterRoot.findElement(xpath(".//div[contains(@class,'llc__item_date')]"))
                .getAttribute("title");
    }
}
