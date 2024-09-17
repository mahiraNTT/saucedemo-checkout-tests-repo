package com.mahira.learningselenium;

import com.mahira.learningselenium.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class MouseActionsTest {

    private WebDriver driver;
    private static final String SITE = "https://demoqa.com/buttons";
    private static final String SITE2 = "https://demoqa.com/menu";
    private static final String SITE3 = "https://demoqa.com/droppable";

    @BeforeTest
    public void setUp(){
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
    }

    private static void delay(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException exception){
            throw new RuntimeException(exception);
        }
    }

    @Test
    public void clickTest(){
        driver.get(SITE);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement buttonsDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[h1[text()='Buttons']]")));
        //Use javascript executor for mouse actions
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight / 3)");
        WebElement clickButtonEL = buttonsDiv.findElement(By.xpath("//button[text()='Click Me']"));

        new Actions(driver).click(clickButtonEL).perform();
       // new Actions(driver).contextClick(clickButtonEL).perform();//This is use for right click

        WebElement messageEL = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dynamicClickMessage")));
        Assert.assertEquals(messageEL.getText(), "You have done a dynamic click");

        delay();

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight / 3)");
        WebElement rightClickButtonEL = buttonsDiv.findElement(By.id("rightClickBtn"));

        new Actions(driver).contextClick(rightClickButtonEL).perform();//This is use for right click

        WebElement messageEL2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rightClickMessage")));
        Assert.assertEquals(messageEL2.getText(), "You have done a right click");

        delay();

    }

    @Test
    public void hoverTest(){
        driver.get(SITE2);

        WebElement mainItem2EL = driver.findElement(By.xpath("//a[text()='Main Item 2']"));

        Actions actions = new Actions(driver);
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight / 3)");

        actions.moveToElement(mainItem2EL).perform();

        delay();

         WebElement subItem1EL = driver.findElement(By.xpath("//a[text()='Sub Item']"));
        WebElement subItem2EL = driver.findElement(By.xpath("//a[text()='Sub Item']"));
        WebElement subSubItemEL = driver.findElement(By.xpath("//a[normalize-space()='SUB SUB LIST »']"));

        delay();

        //Assert.assertTrue(subItem1EL.isDisplayed());
        //Assert.assertTrue(subItem2EL.isDisplayed());
        //Assert.assertTrue(subSubItemEL.isDisplayed());

        System.out.println("Main menu hovering is done");
        actions.moveToElement(subSubItemEL).perform();
        delay();

        WebElement subSubItem1EL = driver.findElement(By.xpath("//a[text()='Sub Sub Item 1']"));
        WebElement subSubItem2EL = driver.findElement(By.xpath("//a[text()='Sub Sub Item 2']"));

        Assert.assertTrue(subSubItem1EL.isDisplayed());
        Assert.assertTrue(subSubItem2EL.isDisplayed());
        System.out.println("Nested hovering is done");

    }

    @Test
    public void dragDropTest(){
        driver.get(SITE3);

        WebElement draggable = driver.findElement(By.id("draggable"));
        WebElement droppable = driver.findElement(By.id("droppable"));

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight / 3)");

        new Actions(driver).dragAndDrop(draggable, draggable).perform();

        Assert.assertEquals(droppable.getText(), "Drop here");
        delay();

    }

    @AfterTest
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }

}
