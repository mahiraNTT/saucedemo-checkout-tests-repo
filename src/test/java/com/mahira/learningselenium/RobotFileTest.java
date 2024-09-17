package com.mahira.learningselenium;

import com.mahira.learningselenium.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class RobotFileTest {
    //use for performing complex mouse and keyboard actions
    private static final String SITE = "https://formy-project.herokuapp.com/fileupload";

    private WebDriver driver;

    private Robot robot;

    @BeforeTest
    public void setUp() throws AWTException{
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
        robot = new Robot();
        //

//        ChromeOptions options =new ChromeOptions();
//        options.addArguments("--headless");
        //Robot calss cannot run test in headless mode, because needs to UI interaction
//
//        driver = new ChromeDriver(options);
//        driver.manage().window().maximize();
    }

    @Test
    public void fileUploadTest(){
        driver.get(SITE);

        WebElement chooseButton = driver.findElement(By.cssSelector(".btn-choose"));
        chooseButton.click();

        robot.delay(2000);

        driver.switchTo().window(driver.getWindowHandle());

        robot.delay(2000);
        //hit Tab twice
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);

        robot.delay(2000);

        //hit the down key 5 times, navigate to specific folder
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);

        robot.delay(2000);

        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);

        robot.delay(2000);

        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        robot.delay(2000);

        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        //select file
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        WebElement fileUploadInput = driver.findElement(By.id("file-upload-field"));
        Assert.assertTrue(fileUploadInput.getAttribute("value").contains("notes.txt"), "File not uploaded!");//file name

    }


    @AfterTest
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }

}
