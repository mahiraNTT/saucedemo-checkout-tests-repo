package com.mahira.learningselenium;

import com.mahira.learningselenium.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class RelativeLocatorsTest {

    private WebDriver driver;

    private static final String SITE = "https://testpages.eviltester.com/styled/index.html";

    @BeforeTest
    public void setUp(){
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
    }

    private static void delay(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void relativeLocatorsTest(){
        driver.get(SITE);

        WebElement basicFormLinkEL = driver.findElement(By.linkText("HTML Form Example"));
        basicFormLinkEL.click();
        delay();
        //Form
        WebElement usernameInputEL = driver.findElement(By.name("username"));
        usernameInputEL.sendKeys("Mahira Dolan");

        WebElement passwordInputEL = driver.findElement(RelativeLocator.with(By.tagName("input")).below((usernameInputEL)));
        passwordInputEL.sendKeys("password123");

        WebElement commentsInputEL = driver.findElement(RelativeLocator.with(By.tagName("textarea")).below(passwordInputEL));
        commentsInputEL.clear();
        commentsInputEL.sendKeys("Test automation practice");

        delay();

        WebElement submitInputEL = driver.findElement(By.cssSelector("input[value='submit']"));

        //toleftof locator
        WebElement cancelButtonEL = driver.findElement(RelativeLocator.with(By.tagName("input")).toLeftOf(submitInputEL));
        WebElement submitButtonEL = driver.findElement(RelativeLocator.with(By.tagName("input")).toRightOf(cancelButtonEL));
        //cancelButtonEL.click();
        submitButtonEL.submit();
       // submitInputEL.submit();
        //verify form details
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("_valueusername")));

        WebElement usernameEL= driver.findElement(By.id("_valueusername"));
        Assert.assertEquals(usernameEL.getText(), "Mahira Dolan");

        WebElement passwordEL= driver.findElement(RelativeLocator.with(By.tagName("li")).below(usernameEL));
        Assert.assertEquals(passwordEL.getText(), "password123");

        WebElement commentEL= driver.findElement(RelativeLocator.with(By.tagName("li")).below(passwordEL));
        Assert.assertEquals(commentEL.getText(), "Test automation practice");

        delay();

    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
