package checkMail;

import data.LetterData;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;
import pages.MailPage;
import pages.MainPage;
import pages.lightBox.MessageRedactor;
import pages.lightBox.NotificationForm;
import user.BigLebovski;
import user.YolterYait;

import java.util.Date;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class MailCheckTest {
    static MainPage mainPage = new MainPage();
    static BigLebovski lebovski = BigLebovski.getInstance();
    static MailPage mailPage;

    @BeforeAll
    public static void login() {
        mainPage.open();
        mainPage.login(lebovski);
        mailPage = new MailPage();
    }

    @Test
    @Description("Проверка входящих писем. Проверка режима чтения письма и функциональности кнопоки 'Ответить'")
    @Tags({@Tag("CheckMail"), @Tag("CheckIncomingEmails")})
    void checkIncomingEmails() {
        mailPage.selectFolderByName("Входящие");
        assumeTrue(mailPage.containsLetters(), "В выбранной папке нет писем для теста");

        WebElement letter = mailPage.lettersListContainer.getRandomLetterFromAll();
        LetterData letterDataFromList = mailPage.getLetterDataFromList(letter);

        checkLetterOpens(letter);
        LetterData letterData = mailPage.getLetterDataFromReading();

        step("Проверка функциональности кнопки 'Ответить' футера письма", () -> {
            MessageRedactor messageRedactor = mailPage.letterReadingContainer.footerReplyClick();
            assertTrue(messageRedactor.getRedactorForm().isDisplayed(),
                    "Не открылась форма составления письма");
            messageRedactor.closeButtonClick();
        });

        step("Проверка соответствия отображаемых данных письма из списка и при чтении", () -> assertAll(
                () -> assertEquals(letterData.getReceiptDate(), letterDataFromList.getReceiptDate(),
                        "Дата получения письма отображаемая в списке не соответствует " +
                                "дате получения письма из режима чтения"),
                () -> assertEquals(letterData.getTheme(), letterDataFromList.getTheme(),
                        "Тема письма отображаемая в списке не соответствует " +
                                "теме письма из режима чтения"),
                () -> assertEquals(letterData.getSenderName(), letterDataFromList.getSenderName(),
                        "Имя отправителя письма отображаемая в списке не соответствует " +
                                "имени отправителя письма из режима чтения"))
        );
    }

    @Test
    @Description("Проверка входящих писем. Составление и отправка письма себе же.")
    @Tags({@Tag("CheckMail"), @Tag("SendSelfEmail"), @Tag("CheckIncomingSelfEmails")})
    void sendSelfEmail() {

        int allLettersBefore = mailPage.leftMailPanel.getLettersCount("Письма себе");
        int unreadLettersBefore = mailPage.leftMailPanel.getUnreadLettersCount("Письма себе");

        LetterData letterData = sendAndCheckEmail(lebovski.getFullEmailAddress());

        int allLettersAfter = mailPage.leftMailPanel.getLettersCount("Письма себе");
        int unreadLettersAfter = mailPage.leftMailPanel.getUnreadLettersCount("Письма себе");

        step("Проверка показателей количетсва писем папки 'Письма себе' после отправки письма",
                () -> assertAll(() -> {
                            assertEquals(allLettersAfter, allLettersBefore + 1,
                                    "Общее количество писем в папке отображается не корректно");
                            assertEquals(unreadLettersAfter, unreadLettersBefore + 1,
                                    "Количество непрочитанных писем в папке отображается не корректно");
                        }
                ));

        mailPage.selectFolderByName("Письма себе");
        WebElement letter = mailPage.lettersListContainer.getLetterByTheme(letterData.getTheme());

        step("Проверка, что письмо находится в статусе 'Не прочитано'", () ->
                assertTrue(mailPage.lettersListContainer.isLetterUnread(letter),
                        "Письмо не отображается как непрочитанное")
        );

        checkLetterOpens(letter);
        checkLetterReadingData(letterData);
        int allLettersAfterReading = mailPage.leftMailPanel.getLettersCount("Письма себе");
        int unreadLettersAfterReading = mailPage.leftMailPanel.getUnreadLettersCount("Письма себе");

        step("Проверка показателей количетсва писем папки 'Письма себе' после прочтения письма",
                () -> assertAll(() -> {
                            assertEquals(allLettersAfterReading, allLettersAfter,
                                    "Общее количество писем в папке отображается не корректно");
                            assertEquals(unreadLettersAfterReading, unreadLettersAfter - 1,
                                    "Количество непрочитанных писем в папке отображается не корректно");
                        }
                ));
    }

    @Test
    @Description("Проверка исходящих писем. Составление письма, отправка. ")
    @Tags({@Tag("CheckMail"), @Tag("SendEmail"), @Tag("CheckOutgoingEmails")})
    void sendEmail() {
        YolterYait heisenberg = YolterYait.getInstance();

        int allLettersBefore = mailPage.leftMailPanel.getLettersCount("Отправленные");
        LetterData letterData = sendAndCheckEmail(heisenberg.getFullEmailAddress());
        int allLettersAfter = mailPage.leftMailPanel.getLettersCount("Отправленные");

        step("Проверка показателей количетсва писем папки 'Отправленные' после отправки письма", () ->
                assertEquals(allLettersAfter, allLettersBefore + 1,
                        "Общее количество писем в папке отображается не корректно")

        );

        mailPage.selectFolderByName("Отправленные");
        WebElement letter = mailPage.lettersListContainer.getLetterByTheme(letterData.getTheme());
        checkLetterOpens(letter);
        checkLetterReadingData(letterData);
    }

    @Step("Проверка уведомления об успешности отправки письма")
    private void checkNotification(NotificationForm notificationForm) {
        assertAll(() -> {
                    assertTrue(notificationForm.waitingFormIsVisible().isDisplayed(),
                            "Окно с уведомлением об отпраке письма не отображается");
                    assertTrue(notificationForm.headerContains("Письмо отправлено"),
                            "Заголовок уведомления не соответствует указанному");
                }
        );

        notificationForm.waitingForDisappear();
    }

    @Step("Составление и отправка письма на адрес {emailAddress}")
    private LetterData sendAndCheckEmail(String emailAddress) {
        MessageRedactor messageRedactor = mailPage.createMessageClick();
        messageRedactor.fillContactsInput(emailAddress);

        String theme = "Тема тестового письма №" + new Date().getTime();
        messageRedactor.fillSubjectInput(theme);
        messageRedactor.fillMessageText("Hello world!");

        NotificationForm notificationForm = messageRedactor.sendEmailClick();
        checkNotification(notificationForm);

        return new LetterData().setTheme(theme)
                .setRecipientAddress(emailAddress);
    }

    @Step("Проверка корректности открытия письма")
    private void checkLetterOpens(WebElement letter) {
        assertTrue(mailPage.openLetter(letter), "Неккоректное открытие письма");
    }

    @Step("Проверка правильности темы и адреса получателя открытого письма")
    private void checkLetterReadingData(LetterData letterData) {
      LetterData letterDataForCheck =  mailPage.getLetterDataFromReading();
        assertAll(
                () -> assertEquals(letterData.getTheme(), letterDataForCheck.getTheme(),
                        "Тема письма отображается не корректно"),
                () -> assertEquals(letterData.getRecipientAddress(), letterDataForCheck.getRecipientAddress(),
                        "Адрес получателя письма отображается не корректно"));
    }

    @AfterEach
    public void redirectToMail() {
        mainPage.redirectToServiceByName("Почта");
    }

    @AfterAll
    public static void close() {
        mainPage.close();
    }
}
