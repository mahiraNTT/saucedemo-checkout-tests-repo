package com.mahira.learningselenium;

import com.mahira.learningselenium.utils.DriverFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class AlertTest {

    private static final String SITE = "https://testpages.eviltester.com/styled/alerts/alert-test.html";

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
    //
    private boolean idAlertPresent(){
        try{
            driver.switchTo().alert();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Test
    public void alertTest(){
        driver.get(SITE);

        driver.findElement(By.id("alertexamples")).click();

        Alert alert = driver.switchTo().alert();;

        String alertText = alert.getText();
        System.out.println("Alert test: "+alertText);

        Assert.assertEquals(alertText, "I am an alert box!");

delay();

alert.accept();

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
