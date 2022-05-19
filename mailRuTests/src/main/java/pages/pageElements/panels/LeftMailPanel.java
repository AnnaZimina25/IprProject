package pages.pageElements.panels;

import driver.WebDriverWrapper;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.pageElements.BasePageElement;

import java.util.List;

/**
 * Левая панель почтовой страницы ресурса mail.ru
 */
public class LeftMailPanel extends BasePageElement {

    public LeftMailPanel(WebDriverWrapper driver) {
        super(driver, "//div[contains(@class,'sidebar__full')]");
    }

    @Step("Поиск кнопки 'Написать письмо'")
    public WebElement getCreateMessageButton() {
        return driver.findElement(By.ByXPath.xpath(elementRootXpath + "//a[@title='Написать письмо']"));
    }

    @Step("Поиск папки по названию")
    public WebElement getFolderByName(String folderName) {
        String xpath = String.format("%s//a[contains(@class,'nav__item') and contains(@title,'%s')]",
                elementRootXpath,
                folderName);
        return driver.findElement(By.ByXPath.xpath(xpath));
    }

    @Step("Получить список всех пустых/не пустых {isEmpty} папок")
    public List<WebElement> getEmptyFolders(boolean isEmpty) {
        String additionalCondition = isEmpty ? "contains" : "not contains";

        String xpath = String.format("%s//a[contains(@class,'nav__item') and %s(@title,'нет писем')]",
                elementRootXpath, additionalCondition);
        return driver.findElements(By.ByXPath.xpath(xpath));
    }

    @Step("Выяснить является ли папка активной")
    public boolean isFolderActive(WebElement folderRoot) {
        return folderRoot.getAttribute("class").contains("_active");
    }

    @Step("Получить общее количество писем в папке")
    public int getLettersCount(WebElement folderRoot) {
        String title = folderRoot.getAttribute("title");
        return getLettersCountFromTitle(title)[0];
    }

    @Step("Получить общее количество писем в папке {folderName}")
    public int getLettersCount(String folderName) {
        String title = getFolderByName(folderName).getAttribute("title");
        return getLettersCountFromTitle(title)[0];
    }

    @Step("Получить количество непрочитанных писем в папке")
    public int getUnreadLettersCount(WebElement folderRoot) {
        String title = folderRoot.getAttribute("title");
        return getLettersCountFromTitle(title)[1];
    }

    @Step("Получить количество непрочитанных писем в папке {folderName}")
    public int getUnreadLettersCount(String folderName) {
        String title = getFolderByName(folderName).getAttribute("title");
        return getLettersCountFromTitle(title)[1];
    }

    /**
     * Возвращает количество писем из атрибута title папки в виде [общее число писем, кол-во непрочитанных]
     * <p>
     * Пример строки: "Новости, нет писем" результат: [0, 0]
     * Пример строки: "Рассылки, 1 письмо" результат: [1, 0]
     * Пример строки: "Входящие, 8 писем, 3 непрочитанных" результат: [8, 3]
     */
    private int[] getLettersCountFromTitle(String title) {
        int[] result = new int[2];
        String[] allTextParts = title.split(", ");

        result[0] = getNumberFromText(allTextParts[1]);
        result[1] = allTextParts.length > 2 ? getNumberFromText(allTextParts[2]) : 0;
        return result;
    }

    private int getNumberFromText(String text) {
        String countStr = text.split(" ")[0];
        return countStr.equals("нет") ? 0 : Integer.parseInt(countStr);
    }
}
