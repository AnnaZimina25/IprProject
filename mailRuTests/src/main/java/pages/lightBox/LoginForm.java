package pages.lightBox;

import driver.WebDriverWrapper;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import user.User;

import static org.openqa.selenium.By.xpath;

/**
 * Форма логина ресурса Mail.ru
 */
public class LoginForm {
    private WebDriverWrapper driver;

    public LoginForm(WebDriverWrapper driver) {
        this.driver = driver;
        driver.switchToChildFrame(xpath("//iframe[contains(@class,'ag-popup_')]"));
    }

    @Step("Поиск заголовка формы")
    public WebElement getFormHeader() {
        return driver.findElement(xpath("//*[@data-test-id='header-text']"));
    }

    @Step("Поиск поля ввода логина")
    public WebElement getUserNameInput() {
        return driver.findElement(xpath("//input[@name='username']"));
    }

    @Step("Поиск фильтра домена")
    public WebElement getDomainSelector() {
        return driver.findElement(xpath("//div[@data-test-id='domain-select']"));
    }

    @Step("Поиск кнопки ввода пароля")
    public WebElement getPasswordButton() {
        return driver.findElement(xpath("//button[@data-test-id='next-button']"));
    }

    @Step("Заполнить поле логина занчением {text}")
    public LoginForm fillLoginField(String text) {
        getUserNameInput().sendKeys(text);
        return this;
    }

    @Step("Установить домен {domain}")
    public LoginForm selectDomain(String domain) {
        getDomainSelector().click();
        driver.findElement(xpath("//div[@data-test-id='select-menu-wrapper']//*[text()='" + domain + "']"))
                .click();
        return this;
    }

    @Step("Нажать кнопку 'Ввести пароль'")
    public LoginForm fillPasswordButtonClick() {
        getPasswordButton().click();
        return this;
    }

    @Step("Поиск формы ввода пароля")
    public WebElement fillPasswordInput() {
        return driver.findElement(xpath("//input[@name='password']"));
    }

    @Step("Ввести пароль")
    public LoginForm fillPassword(User user) {
        fillPasswordButtonClick();
        fillPasswordInput().sendKeys(user.getPassword());
        return this;
    }

    @Step("Ввести пароль")
    public LoginForm fillPassword(String password) {
        fillPasswordButtonClick();
        fillPasswordInput().sendKeys(password);
        return this;
    }

    @Step("Выбрать/снять ({toSelect}) выбор с чекбокса 'запомнить'")
    public LoginForm toSelectRememberMe(boolean toSelect) {
        WebElement checkBox = driver.findElement(xpath("//label[@data-test-id='saveauth']//div"));
        boolean isSelected = checkBox.getAttribute("class").contains("activeBox");

        if (isSelected != toSelect) {
            checkBox.click();
        }
        return this;
    }

    @Step("Подтвердить пароль (из формы ввода пароля)")
    public LoginForm submitPassword() {
        driver.findElement(xpath("//button[@data-test-id='submit-button']")).click();
        return this;
    }

    @Step("Поиск пердупреждения 'Такой аккаунт не зарегистрирован'")
    public WebElement getAccountNotRegisteredWarning() {
        return driver.findElement(xpath("//*[@data-test-id='accountNotRegistered']"));
    }

    @Step("Поиск пердупреждения 'Поле «Имя аккаунта» должно быть заполнено'")
    public WebElement getEmptyAccountWarning() {
        return driver.findElement(xpath("//*[@data-test-id='required']"));
    }

   @Step("Поиск пердупреждения 'Неверный пароль, попробуйте ещё раз'")
   public WebElement getWrongPasswordWarning() {
       return driver.findElement(xpath("//*[@data-test-id='password-input-error']"));
   }
}
