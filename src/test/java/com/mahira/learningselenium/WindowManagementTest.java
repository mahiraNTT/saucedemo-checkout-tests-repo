package com.mahira.learningselenium;

import com.mahira.learningselenium.utils.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

public class WindowManagementTest {

    private static final String SITE = "https://the-internet.herokuapp.com/windows";

    private WebDriver driver;

    @BeforeTest
    public void setUp(){
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
    }

    @Test
    public void testMainWindowHandleNotNull(){
        driver.get(SITE);
        Assert.assertNotNull(driver.getWindowHandle(),"Main window handle is null.");

    }

    @Test(dependsOnMethods = {"testMainWindowHandleNotNull"})
    public void testNewTabOpened(){
        //((JavascriptExecutor) driver).executeScript("window.open();");
        driver.switchTo().newWindow(WindowType.TAB);//instead of using javascript executor

        Assert.assertEquals(driver.getWindowHandles().size(), 2, "Expected to find two tabs");
    }

    @Test
    public void openNewTabTest(){
        driver.get(SITE);
        Assert.assertEquals(driver.getWindowHandles().size(), 1, "Main window handle is null.");

        WebElement linkEL =driver.findElement(By.cssSelector(".example a"));
        linkEL.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        Set<String> allWindowHandles = driver.getWindowHandles();
        System.out.println("All window handles: "+ allWindowHandles);

        Assert.assertEquals(driver.getWindowHandles().size(), 2, "New tab not opened");

    }

    @Test
    public void switchToNewTabAndBack(){
        driver.get(SITE);
        Assert.assertEquals(driver.getWindowHandles().size(), 1, "Main window is not opened..");

        String mainWindowHandle =driver.getWindowHandle();
        System.out.println("Main window Handle: " + mainWindowHandle);

        WebElement linkEL = driver.findElement(By.cssSelector(".example a"));
        linkEL.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        Set<String> allWindowHandles = driver.getWindowHandles();
        System.out.println("All window handles: "+ allWindowHandles);

        //Switch to the newly opened tab abd confirm the title
        for(String windowHandle : allWindowHandles){
            driver.switchTo().window(windowHandle);
            System.out.println("New window handle: "+windowHandle);

            break;

        }
       // Assert.assertTrue(driver.getTitle().contains("New Window"), "New window title is not as expected");

        String secondTabHandle = driver.getWindowHandle();
        System.out.println("Second handle: "+ secondTabHandle);
        driver.switchTo().window(mainWindowHandle);

        Assert.assertTrue(driver.getTitle().contains("The Internet"), "Main window title is not as expected");
        //open third tab
        linkEL = driver.findElement(By.cssSelector(".example a"));
        linkEL.click();

        wait.until(ExpectedConditions.numberOfWindowsToBe(3));
        driver.switchTo().window(secondTabHandle);

       // Assert.assertTrue(driver.getTitle().contains("New Window"), "New window title is not as expected");

    }

    @Test(dependsOnMethods = {"testMainWindowHandleNotNull"})
    public void openDifferentTabsTest(){
       driver.get(SITE);

       Assert.assertEquals(driver.getWindowHandles().size(), 1, "Main windoe is not opened.");

        driver.switchTo().newWindow(WindowType.TAB);//instead of using javascript executor

        Assert.assertEquals(driver.getWindowHandles().size(), 2, "Expected to find two tabs");

        driver.get("https://www.codeacademy.com");
        driver.switchTo().newWindow(WindowType.TAB);

        Assert.assertEquals(driver.getWindowHandles().size(),3, "New tab is not opened!");

        driver.get("https://www.skillsoft.com");

        driver.close();
        driver.switchTo().window(driver.getWindowHandles().iterator().next());
        driver.close();
    }

    @AfterTest
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }

}
