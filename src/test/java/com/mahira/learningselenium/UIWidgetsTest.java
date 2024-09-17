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

public class UIWidgetsTest {

    private WebDriver driver;
    private static final String SITE = "https://testpages.eviltester.com/styled/basic-html-form-test.html";

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
    public void radioButtonsTest(){
        driver.get(SITE);

        WebElement radio1EL = driver.findElement(By.cssSelector("input[value='rd1']"));
        WebElement radio2EL = driver.findElement(By.cssSelector("input[value='rd2']"));
        WebElement radio3EL = driver.findElement(By.cssSelector("input[value='rd3']"));

        System.out.println("______Before clicking________");
        System.out.println("Radio 1 selected: "+radio1EL.isSelected());
        System.out.println("Radio 2 selected: "+radio2EL.isSelected());
        System.out.println("Radio 3 selected: "+radio3EL.isSelected());

        delay();
        radio3EL.click();

        System.out.println("______After clicking________");
        System.out.println("Radio 1 selected: "+radio1EL.isSelected());
        System.out.println("Radio 2 selected: "+radio2EL.isSelected());
        System.out.println("Radio 3 selected: "+radio3EL.isSelected());

        Assert.assertTrue(radio3EL.isSelected());

        delay();
    }

    @Test
    public void checkboxesTest(){
        driver.get(SITE);

        WebElement checkbox1EL = driver.findElement(By.cssSelector("input[value='cb1']"));
        WebElement checkbox2EL = driver.findElement(By.cssSelector("input[value='cb2']"));
        WebElement checkbox3EL = driver.findElement(By.cssSelector("input[value='cb3']"));

        System.out.println("______Before clicking________");
        System.out.println("Checkbox 1 selected: "+checkbox1EL.isSelected());
        System.out.println("Checkbox 2 selected: "+checkbox2EL.isSelected());
        System.out.println("Checkbox 3 selected: "+checkbox3EL.isSelected());

        delay();
        checkbox1EL.click();

        System.out.println("______After clicking________");
        System.out.println("Checkbox 1 selected: "+checkbox1EL.isSelected());
        System.out.println("Checkbox 2 selected: "+checkbox2EL.isSelected());
        System.out.println("Checkbox 3 selected: "+checkbox3EL.isSelected());

        Assert.assertTrue(checkbox1EL.isSelected());

        delay();
    }

    @AfterTest
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }
}
