package pages.pageElements;

import driver.WebDriverWrapper;

/**
 * Базовый класс элемента страницы ресурса mail.ru
 */
public class BasePageElement {
    protected WebDriverWrapper driver;
    protected String elementRootXpath;

    public BasePageElement(WebDriverWrapper driver, String elementRootXpath) {
        this.driver = driver;
        this.elementRootXpath = elementRootXpath;
    }
}
