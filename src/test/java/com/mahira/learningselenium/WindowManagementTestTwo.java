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

public class WindowManagementTestTwo {

    private static final String SITE = "https://the-internet.herokuapp.com/windows";

    private WebDriver driver;

    private WebDriver driver1;

    private WebDriver driver2;

    private WebDriver driver3;

    @BeforeTest
    public void setUp(){
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
        driver1 = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
        driver2 = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
        driver3 = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
    }

    private static void delay(){
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testOpenDifferentWindows(){
        driver1.get(SITE);
        Assert.assertEquals(driver1.getWindowHandles().size(), 1, "Main window is not opened.");

        driver2.get(SITE);
        Assert.assertEquals(driver1.getWindowHandles().size(), 1, "Main window is not opened.");

        driver3.get(SITE);
        Assert.assertEquals(driver1.getWindowHandles().size(), 1, "Main window is not opened.");

        delay();

    }

    @Test
    public void testWindowSize(){
        driver.get(SITE);

        driver.manage().window().setPosition(new Point(0, 0));//top left corner of screen
        driver.manage().window().setSize(new Dimension(800, 600));
        String windowOneHandle = driver.getWindowHandle();

        driver.switchTo().newWindow(WindowType.WINDOW);

        String windowTwoHandle = driver.getWindowHandle();

        driver.get(SITE);

        driver.manage().window().setSize(new Dimension(1000, 375));
        driver.manage().window().setPosition(new Point(0, 250));

        Point windowOnePosition = driver.manage().window().getPosition();
        System.out.println("Window one position: "+windowOnePosition);

        driver.switchTo().window(windowOneHandle);

        Dimension windowOneSize = driver.manage().window().getSize();
        Assert.assertEquals(windowOneSize.getWidth(), 800);
        Assert.assertEquals(windowOneSize.getHeight(), 600);

        driver.switchTo().window(windowTwoHandle);

        Dimension windowTwoSize = driver.manage().window().getSize();
        Assert.assertEquals(windowTwoSize.getWidth(), 1000);
        Assert.assertEquals(windowTwoSize.getHeight(), 375);

        Point windowTwoPosition = driver.manage().window().getPosition();
        System.out.println("Window one position: "+windowTwoPosition);

driver.manage().window().minimize();
        driver.manage().window().fullscreen();
        driver.manage().window().maximize();

    }

    @AfterTest
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }

        if (driver1 != null){
            driver1.quit();
        }

        if (driver2 != null){
            driver2.quit();
        }

        if (driver3 != null){
            driver3.quit();
        }
    }
}
