import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SignUpTest {
    /*
    0. Открыть браузер
    1. Переходим на страницу https://www.sharelane.com/cgi-bin/register.py
    2. Вводим в поле Zip code 11111
    3. Нажимаем на Continue [value=Continue]
    4. Убеждаемся, что перед нами форма регистрации
    5. Закрыть браузер
     */

    @Test
    public void zipCode5Digits() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.findElement(By.name("zip_code")).sendKeys("11111");
        driver.findElement(By.cssSelector("[value=Continue]")).click();
        boolean isSignUpPageOpened = driver.findElement(By.cssSelector("[value=Register]")).isDisplayed();
        assertTrue(isSignUpPageOpened, "Страница регистрации не открылась");
        driver.quit();
    }

    @Test
    public void zipCode4Digits() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.findElement(By.name("zip_code")).sendKeys("1111");
        driver.findElement(By.cssSelector("[value=Continue]")).click();
        String actualError = driver.findElement(By.cssSelector("[class=error_message]")).getText();
        assertEquals(actualError,"Oops, error on page. ZIP code should have 5 digits",
                "Wrong error message is shown");
        driver.quit();
    }

    @Test
    public void zipCode6Digits() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.findElement(By.name("zip_code")).sendKeys("111111");
        driver.findElement(By.cssSelector("[value=Continue]")).click();
        String actualError = driver.findElement(By.cssSelector("[class=error_message]")).getText();
        assertEquals(actualError,"Oops, error on page. ZIP code should have 5 digits",
                "Wrong error message is shown");
        driver.quit();
    }

    @Test
    public void signUpWithAllFields() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://sharelane.com/cgi-bin/register.py?page=1&zip_code=11111");
        driver.findElement(By.name("first_name")).sendKeys("Vika");
        driver.findElement(By.name("last_name")).sendKeys("Kazak");
        driver.findElement(By.name("email")).sendKeys("olololo@gmaa.com");
        driver.findElement(By.name("password1")).sendKeys("1234");
        driver.findElement(By.name("password2")).sendKeys("1234");
        driver.findElement(By.cssSelector("[value=Register]")).click();
        String actualError = driver.findElement(By.cssSelector("[class=confirmation_message]")).getText();
        assertEquals(actualError,"Account is created!",
                "Wrong error message is shown after signing up");
         driver.quit();
    }

    @Test
    public void signUpWithoutFirstName() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://sharelane.com/cgi-bin/register.py?page=1&zip_code=11111");
        driver.findElement(By.name("last_name")).sendKeys("Last");
        driver.findElement(By.name("email")).sendKeys("olololo@gmaa.com");
        driver.findElement(By.name("password1")).sendKeys("1234");
        driver.findElement(By.name("password2")).sendKeys("1234");
        driver.findElement(By.cssSelector("[value=Register]")).click();
        String actualError = driver.findElement(By.cssSelector("[class=error_message]")).getText();
        assertEquals(actualError,"Oops, error on page. Some of your fields have invalid data or email was previously used",
                "User can login without first name");
        driver.quit();
    }

    @Test
    public void typeOfPasswordField() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://sharelane.com/cgi-bin/register.py?page=1&zip_code=11111");
        String actualResult = driver.findElement((By.name("password1"))).getAttribute("type");
        assertEquals(actualResult,"password",
                "Field's type is not password");
        driver.quit();
    }

    @Test
    public void differentPasswords() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://sharelane.com/cgi-bin/register.py?page=1&zip_code=11111");
        driver.findElement(By.name("first_name")).sendKeys("Vika");
        driver.findElement(By.name("last_name")).sendKeys("Kazak");
        driver.findElement(By.name("email")).sendKeys("olololo@gmaa.com");
        driver.findElement(By.name("password1")).sendKeys("1234");
        driver.findElement(By.name("password2")).sendKeys("5678");
        driver.findElement(By.cssSelector("[value=Register]")).click();
        String actualError = driver.findElement(By.cssSelector("[class=error_message]")).getText();
        assertEquals(actualError,"Oops, error on page. Some of your fields have invalid data or email was previously used",
                "Password and Confirm password fields may not match");
        driver.quit();
    }

}