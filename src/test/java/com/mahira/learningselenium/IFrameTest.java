package com.mahira.learningselenium;

import com.mahira.learningselenium.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class IFrameTest {

    private static final String SITE = "https://omayo.blogspot.com/";

    private WebDriver driver;

    @BeforeTest
    public void setUp(){
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
    }

    private static void delay(){
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void numberOfIframeTest(){
        driver.get(SITE);

        int numberOfIFrames = driver.findElements(By.tagName("iframe")).size();

        System.out.println("Number of iFrames on the page is: " + numberOfIFrames);

        Assert.assertEquals(numberOfIFrames, 3);

        delay();

    }

    @Test
    public void testInputValue(){
        driver.get(SITE);

        //switch to iframe
        WebElement iframeContent = driver.findElement(By.id("navbar-iframe"));
        driver.switchTo().frame(iframeContent);

        WebElement inputBox = driver.findElement(By.id("b-query"));

        String inputText = "Selenium Testing";
        inputBox.sendKeys(inputText);

        String inputValue = inputBox.getAttribute("value");
        System.out.println("Input value: " + inputValue);

        Assert.assertEquals(inputValue, inputText);

        WebElement searchIcon = driver.findElement(By.id("b-query-icon"));
        searchIcon.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("q=Selenium+Testing"));

        Assert.assertTrue(driver.getCurrentUrl().contains("q=Selenium+Testing"));

        delay();

        //
        driver.switchTo().defaultContent();

        WebElement homePageTitle = driver.findElement(By.linkText("omayo (QAFox.com)"));
        homePageTitle.click();

        wait.until(ExpectedConditions.urlToBe(SITE));

        delay();
    }

    //
    private  int countNestedIframes(WebElement element){
        int count = 0;

        for (WebElement iframe : element.findElements(By.tagName("iframe"))){
            count++;
            driver.switchTo().frame(iframe);

            count += countNestedIframes(driver.findElement(By.tagName("body")));
            driver.switchTo().parentFrame();
        }
        return count;
    }


    @AfterTest
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }
}
