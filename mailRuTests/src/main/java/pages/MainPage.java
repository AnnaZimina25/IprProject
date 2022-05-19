package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.panels.HeaderPanel;

/**
 * Главная страница Mail.ru
 */
public class MainPage extends BasePage{

    public HeaderPanel headerPanel;
    public MainPage() {
        super("Главная страница", "https://mail.ru/");
        headerPanel = new HeaderPanel(driver);
    }

    public WebElement loginButton(){
        return driver.findElement(By.ByXPath.xpath("//button[@data-testid='enter-mail-primary']"));
    }

    public WebElement createAccount(){
        return driver.findElement(By.ByXPath.xpath("//a[@data-testid='mailbox-create-link']"));
    }
}
