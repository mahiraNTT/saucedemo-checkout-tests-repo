package com.mahira.learningselenium;

import com.mahira.learningselenium.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class RobotTest {
    //use for performing complex mouse and keyboard actions
    private static final String SITE = "";

    private WebDriver driver;

    private Robot robot;

    @BeforeTest
    public void setUp() throws AWTException{
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
        robot = new Robot();
    }
    //type method
    private void typeString(String text){
        //iterate every character
        for (char c : text.toCharArray()){
            int keyCode  = KeyEvent.getExtendedKeyCodeForChar(c);
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
        }
    }
    //another type method
    private void typeUpperCaseAndSpecialChar(String text, boolean capsLock) {

        if (capsLock) {
            robot.keyPress(KeyEvent.VK_CAPS_LOCK);
        }
        for (char c : text.toCharArray()){
            if (capsLock){
                robot.keyPress(KeyEvent.VK_CAPS_LOCK);
            }
            //type @
            int keyCode;
            if (c == '@'){
                robot.keyPress(KeyEvent.VK_SHIFT);
                keyCode = KeyEvent.VK_2;
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }else
            {
                keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
            }

//             keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
//            robot.keyPress(keyCode);
//            robot.keyRelease(keyCode);
        }
        //key release
        if (capsLock){
            robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
        }

    }

    @Test
    public void typeUppercaseUsingRobot(){
        driver.get(SITE);

        driver.switchTo().window(driver.getWindowHandle());

        WebElement nameInput = driver.findElement(By.id("name"));
        nameInput.click();

        robot.delay(3000);

        typeUpperCaseAndSpecialChar("Mia Raymond", true);

        String actualValue = nameInput.getAttribute("value");
        Assert.assertEquals(actualValue, "MIA RAYMOND");
    }

    @Test
    public void typeEmailUsingRobot(){
        driver.get(SITE);

        driver.switchTo().window(driver.getWindowHandle());

        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.click();

        robot.delay(3000);

        typeUpperCaseAndSpecialChar("miaraymond@gmail.com", false);

        String actualValue = emailInput.getAttribute("value");
        Assert.assertEquals(actualValue, "miaraymond@gmail.com");
    }

    @Test
    public void typeFilenameUsingRobot(){
        driver.get(SITE);

        driver.switchTo().window(driver.getWindowHandle());

        WebElement nameInput = driver.findElement(By.id("name"));
        nameInput.click();

        robot.delay(3000);

        typeString("Mia Raymond");

        String actualValue = nameInput.getAttribute("value");
        Assert.assertEquals(actualValue, "mia raymond");
    }

    @Test
    public void scrollToBottomAndUpTest(){
        driver.get(SITE);

        for (int i=0; i < 6; i++){
            robot.mouseWheel(-3);//scroll downward 6 times
            robot.delay(1000);
        }

        for (int i=0; i < 6; i++){
            robot.mouseWheel(+3);//scroll upward times
            robot.delay(1000);
        }
    }

    @Test
    public void rightClickAndReloadTest(){
        driver.get(SITE);

        WebElement nameInput = driver.findElement(By.id("name"));
        WebElement emailInput = driver.findElement(By.id("email"));

        nameInput.sendKeys("Name");
        emailInput.sendKeys("Email");

        robot.mouseMove(800,300);

        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);

        robot.delay(1000);

        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.delay(500);

        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.delay(500);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        robot.delay(3000);

        nameInput = driver.findElement(By.id("name"));
        emailInput = driver.findElement(By.id("email"));

        Assert.assertTrue(nameInput.getAttribute("value").isEmpty());
        Assert.assertTrue(emailInput.getAttribute("value").isEmpty());
    }

    @AfterTest
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }

}
