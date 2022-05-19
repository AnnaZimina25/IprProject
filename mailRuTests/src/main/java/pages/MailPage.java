package pages;

import data.LetterData;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import pages.pageElements.lightBox.MessageRedactor;
import pages.pageElements.panels.LeftMailPanel;
import pages.pageElements.panels.LetterReadingContainer;
import pages.pageElements.panels.LettersListContainer;

/**
 * Страница почты Mail.ru
 */
public class MailPage extends BasePage {
    public LeftMailPanel leftMailPanel;
    public LettersListContainer lettersListContainer;
    public LetterReadingContainer letterReadingContainer;

    public MailPage() {
        super("Страница почты");
        leftMailPanel = new LeftMailPanel(driver);
        lettersListContainer = new LettersListContainer(driver);
        letterReadingContainer = new LetterReadingContainer(driver);
    }

    @Step("Сделать активной папку '{name}'")
    public MailPage selectFolderByName(String name) {
        WebElement folder = leftMailPanel.getFolderByName(name);
        if (!leftMailPanel.isFolderActive(folder)) {
            folder.click();
            lettersListContainer.folderCheckoutWait();
        }
        return this;
    }

    @Step("Проверка наличия писем в папке")
    public boolean containsLetters() {
        return lettersListContainer.getAllLetters().size() > 0;
    }

    @Step("Проверка наличия непрочитанных писем в папке")
    public boolean containsUnreadLetters() {
        return lettersListContainer.getOnlyUnreadLetters().size() > 0;
    }

    @Step("Узнать время получения, тему и отправителя нераскрытого письма")
    public LetterData getLetterDataFromList(WebElement letterFromList) {
        return new LetterData()
                .setReceiptDate(lettersListContainer.getLetterReceiptDate(letterFromList))
                .setSenderName(lettersListContainer.getLetterSender(letterFromList))
                .setTheme(lettersListContainer.getLetterTheme(letterFromList));
    }

    @Step("Открыть письмо")
    public boolean openLetter(WebElement letterFromList) {
        letterFromList.click();
        return letterReadingContainer.letterOpenWait().isDisplayed();

    }

    @Step("Нажать на кнопку 'Написать сообщение'")
    public MessageRedactor createMessageClick() {
        leftMailPanel.getCreateMessageButton().click();

        return new MessageRedactor(driver);
    }

    @Step("Узнать время получения, тему и отправителя раскрытого письма")
    public LetterData getLetterDataFromReading() {
        return new LetterData()
                .setReceiptDate(letterReadingContainer.getLetterReceiptDate())
                .setSenderName(letterReadingContainer.getSenderName())
                .setTheme(letterReadingContainer.getLetterTheme())
                .setSenderAddress(letterReadingContainer.getSenderEmail())
                .setRecipientAddress(letterReadingContainer.getRecipientEmail());
    }
}
