import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.List;

public class LoginTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void testLoginAndFetchUnreadEmail() {
        driver.get("https://mail.google.com/");
        driver.findElement(By.id("identifierId")).sendKeys("jesserjae@gmail.com"); 
        driver.findElement(By.id("identifierNext")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        driver.findElement(By.name("password")).sendKeys("tEstcase25"); 
        driver.findElement(By.id("passwordNext")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table[role='grid']"))); 
        List<WebElement> unreadEmails = driver.findElements(By.cssSelector("tr.zA.zE")); 
        System.out.println("Total unread emails: " + unreadEmails.size());

        if (!unreadEmails.isEmpty()) {
            WebElement last = unreadEmails.get(unreadEmails.size() - 1);
            String subject = last.findElement(By.cssSelector("span.bog")).getText();
            System.out.println("Last unread email subject: " + subject);
        } else {
            System.out.println("No unread emails found.");
        }
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
