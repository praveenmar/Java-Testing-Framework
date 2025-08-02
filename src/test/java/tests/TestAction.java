package tests;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import base.BaseTest;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class TestAction extends BaseTest  {
    @Test
    public void testAction()  throws InterruptedException {
        driver.get("https://practice.expandtesting.com/hovers");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
        WebElement element = driver.findElement(By.xpath("//*[@id='core']/div/div/div[1]/img"));
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
        Thread.sleep(6000);
        action.sendKeys(Keys.chord(Keys.CONTROL, "a")).perform();
        Thread.sleep(6000);
         





    }
}
