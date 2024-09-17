package com.mahira.learningselenium;

import com.mahira.learningselenium.utils.DriverFactory;
import com.sun.nio.file.ExtendedOpenOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class WaitTest {
    private WebDriver driver;
    private static final String SITE = "https://testpages.eviltester.com/styled/index.html";
    private static final String SITE2 = "https://the-internet.herokuapp.com/";
    private long startTime;
    private double duration;

    //Custom functions Lambda function
    private Function<WebDriver, WebElement> buttonsBecomeClickable(By locator){
        return driver -> {
            WebElement element = driver.findElement(locator);
            String disabled = element.getAttribute("disabled");
            return disabled ==null || disabled.equals("true") ? element : null;
        };
    }

    @BeforeTest
    public void setUp(){
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
        //long endTime = System.currentTimeMillis();
        startTime = System.currentTimeMillis();
        //Implicit wait
        System.out.println("Implicit wait timeout (Before): "+
                driver.manage().timeouts().getImplicitWaitTimeout());

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));//setup implicit wait configuration, work entire tests, global settings

        System.out.println("Implicit wait timeout (After): "+
                driver.manage().timeouts().getImplicitWaitTimeout());

    }

    private static void delay(long milliseconds){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException exception){
            throw new RuntimeException(exception);
        }
    }

    @AfterTest
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }

    @Test
    public void buttonClickTest(){
        driver.get(SITE);

        WebElement dynamicButtonsLinkEL = driver.findElement(By.linkText("Dynamic Buttons Challenge 01"));
        //WebElement dynamicButtonsLinkEL = driver.findElement(By.linkText("Dynamic Buttons Challenge 02"));
        dynamicButtonsLinkEL.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement startButtonEL = driver.findElement(By.id("button00"));
        startButtonEL.click();

//        WebElement oneButtonEL = driver.findElement(By.id("button01"));
//        oneButtonEL.click();
        //explicit wait
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //
//        try {
//            WebElement notExistBtnEL = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("doesNotExistButton")));
//            notExistBtnEL.click();
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        WebElement buttonOneEL = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button01")));
        buttonOneEL.click();

        WebElement twoButtonEL = driver.findElement(By.id("button02"));
        twoButtonEL.click();

        WebElement threeButtonEL = driver.findElement(By.id("button03"));
        threeButtonEL.click();

        WebElement allButtonsClickedMessageEL = driver.findElement(By.id("buttonmessage"));
        String message = allButtonsClickedMessageEL.getText();

        Assert.assertEquals(message, "All Buttons Clicked");

    }

    @Test
    public void checkboxTest(){
        driver.get(SITE2);

        WebElement dynamicControlsLinkEL = driver.findElement(By.linkText("Dynamic Controls"));
        dynamicControlsLinkEL.click();

        WebElement checkboxEL = driver.findElement(By.cssSelector("input[type='checkbox']"));
        Assert.assertTrue(checkboxEL.isDisplayed());

        WebElement removeButtonEL = driver.findElement(By.cssSelector("button[onclick='swapCheckbox()']"));
        Assert.assertTrue(removeButtonEL.isDisplayed());
        removeButtonEL.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOf(checkboxEL));

        List<WebElement> elements = driver.findElements(By.id("checkbox"));
        Assert.assertTrue(elements.isEmpty());

        WebElement addButtonEL = driver.findElement(By.cssSelector("button[onclick='swapCheckbox()']"));
        addButtonEL.click();

        checkboxEL = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("checkbox")));
        Assert.assertTrue(checkboxEL.isDisplayed());
    }

    @Test
    public void enableDisableTest(){
        driver.get(SITE2);

        WebElement dynamicControlsLinkEL = driver.findElement(By.linkText("Dynamic Controls"));
        dynamicControlsLinkEL.click();

        WebElement textboxEL = driver.findElement(By.cssSelector("input[type='text']"));
        Assert.assertTrue(textboxEL.isDisplayed());
        //Assert.assertTrue(textboxEL.isEnabled());

        WebElement enableButtonEL = driver.findElement(By.cssSelector("button[onclick='swapInput()']"));
        enableButtonEL.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(textboxEL));

        Assert.assertTrue(textboxEL.isDisplayed());
        Assert.assertTrue(textboxEL.isEnabled());

        WebElement disableButtonEL = driver.findElement(By.cssSelector("button[onclick='swapInput()']"));
        disableButtonEL.click();

        wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(textboxEL)));
        Assert.assertTrue(textboxEL.isDisplayed());
       // Assert.assertTrue(textboxEL.isEnabled());
    }

    @Test
    public void testprogressBars(){
        driver.get(SITE);

        WebElement dynamicButtonsLinkEL = driver.findElement(By.linkText("Multiple Progress Bars"));
        dynamicButtonsLinkEL.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.attributeToBe(By.id("progressbar0"), "value", "100"));
        wait.until(ExpectedConditions.attributeToBe(By.id("progressbar1"), "value", "100"));
        wait.until(ExpectedConditions.attributeToBe(By.id("progressbar2"), "value", "100"));

        wait.until(ExpectedConditions.textToBe(By.id("status"), "Stopped"));

        WebElement statusEL = driver.findElement(By.id("status"));
        String statusText = statusEL.getText();

        Assert.assertEquals(statusText, "Stopped", "Status message is incorrect.");

    }

    @Test
    public void fluentWaitTest(){
        driver.get(SITE);

        WebElement dynamicButtonsLinkEL = driver.findElement(By.linkText("Dynamic Buttons Challenge 02"));
        dynamicButtonsLinkEL.click();
//Fluent wait
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(100))
                .withMessage("Action times out!")
                .ignoring(NoSuchElementException.class);

        WebElement startButtonEL = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button00")));
        startButtonEL.click();

        WebElement buttonOneEL = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button01")));
        buttonOneEL.click();

        WebElement twoButtonEL = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button02")));
        twoButtonEL.click();

        WebElement threeButtonEL = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button03")));
        threeButtonEL.click();

        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement allButtonsClickedMessageEL = driver.findElement(By.id("buttonmessage"));
        wait2.until(ExpectedConditions.visibilityOf(allButtonsClickedMessageEL));
        String message = allButtonsClickedMessageEL.getText();
       // Assert.assertEquals(message, "All Buttons Clicked");
    }

    @Test
    public void customFunctionTest(){
        driver.get(SITE);

        WebElement dynamicButtonsLinkEL = driver.findElement(By.linkText("Dynamic Buttons Challenge 02"));
        dynamicButtonsLinkEL.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement startButtonEL = wait.until(buttonsBecomeClickable(By.id("button00")));
        startButtonEL.click();

        WebElement buttonOneEL = wait.until(buttonsBecomeClickable(By.id("button01")));
        buttonOneEL.click();

        WebElement twoButtonEL = wait.until(buttonsBecomeClickable(By.id("button02")));
        twoButtonEL.click();

        WebElement threeButtonEL = wait.until(buttonsBecomeClickable(By.id("button03")));
        threeButtonEL.click();

        WebElement allButtonsClickedMessageEL = driver.findElement(By.id("buttonmessage"));
        wait.until(ExpectedConditions.visibilityOf(allButtonsClickedMessageEL));
        String message = allButtonsClickedMessageEL.getText();
       // Assert.assertEquals(message, "All Buttons Clicked");
    }
}
