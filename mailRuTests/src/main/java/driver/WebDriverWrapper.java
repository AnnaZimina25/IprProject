package driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Класс-обертка для webDriver
 */
public class WebDriverWrapper {

    private static Logger log = LogManager.getRootLogger();
    private RemoteWebDriver driver = null;
    private WebDriverWait wait = null;
    boolean colorElements = false;

    private static WebDriverWrapper instance = null;

    public static WebDriverWrapper getInstance() {
        if (instance == null) {
            instance = new WebDriverWrapper();
            log.debug("Создал экземпляр обертки WebDriver (синглтон).");
        }
        return instance;
    }

    private WebDriverWrapper() {
        log.debug("Инициализирую экземпляр обертки WebDriver.");

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        System.setProperty("webdriver.chrome.driver", "src/main/resources/bin/chromedriver.exe");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        colorElements = true;
        wait = new WebDriverWait(driver, Duration.ofSeconds(6), Duration.ofSeconds(2));
    }

    public WebDriverWrapper get(String url) {
        log.debug("Перехожу по адресу {}", url);
        driver.navigate().to(url);
        return this;
    }

    public WebDriverWrapper close() {
        String tabTitle = driver.getTitle();
        log.debug("Закрываю текущую вкладку {}", tabTitle);
        driver.close();
        return this;
    }

    public void quit() {
        log.debug("Закрываю все вкладки и окна, завершаю процесс webdriver.");
        driver.quit();
    }

    public String getCurrentUrl() {
        log.debug("Определяю текущий URL страницы.");
        return driver.getCurrentUrl();
    }

    public WebElement findElement(By locator) throws TimeoutException {
        WebElement element = null;
        int maxCount = 3;
        for (int count = 0; count < maxCount; count++) {
            log.debug("Пробую найти элемент {} - попытка #{}", locator.toString(), count);

            try {
                element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                element = wait.until(ExpectedConditions.elementToBeClickable(locator));

                //Подсветка на странице найденного нами элемента
                if (colorElements) {
                    driver.executeScript("arguments[0]['style']['backgroundColor']='yellow';", element);
                }

                log.debug("Элемент найден.");
                return element;
            } catch (StaleElementReferenceException e) {
                log.debug("DOM-дерево изменилось. Искомый объект устарел.");
                //продолжаем попытки поиска
            } catch (WebDriverException e) {
                log.debug("Элемент не был найден.");
            }
        }
        log.error("Элемент не был найден.");
        throw new NoSuchElementException("Элемент не был найден.");
    }

    public List<WebElement> findElements(By locator) {
        log.debug("Пытаюсь найти все элементы, подходящие по локатору {}", locator.toString());
        return driver.findElements(locator);
    }

    public void scroll(int pix) {
        log.debug("Отматываем страницу");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(String.format("window.scrollBy(0,%d)", pix));
    }

    public String getWindowHandle() {
        log.debug("Получить заголовк текущего окна");
        return driver.getWindowHandle();
    }

    public void switchToChildFrame(By locator) throws TimeoutException {
        WebElement frame = findElement(locator);
        log.debug("Переключаюсь на внутреннюю форму");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
    }

    public void waitingForDisappearElement(By locator, int sec) throws TimeoutException {
        log.debug("Ожидаю в ткчение {} секунд пока скроется элемент {}", sec, locator.toString());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(sec), Duration.ofSeconds(2));
        wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfElementLocated(locator)));
    }
}