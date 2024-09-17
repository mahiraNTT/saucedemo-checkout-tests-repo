package com.mahira.learningselenium;

import com.mahira.learningselenium.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DropDownTest {

    private static final String SITE = "https://testpages.eviltester.com/styled/index.html";

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
    public void dropDownTest(){
        driver.get(SITE);

        WebElement categoryDropDown = driver.findElement(By.id("categoryDropdown"));

        Assert.assertTrue(categoryDropDown.isDisplayed());

        //Select class
        Select categorySelect = new Select(categoryDropDown);


        Assert.assertEquals(categorySelect.getFirstSelectedOption().getText(), "-- Select a Category --");

        WebElement subCategoryDropdown = driver.findElement(By.id("subcategoryDropdown"));

        Assert.assertFalse(subCategoryDropdown.isEnabled());

        delay();
    }

    @AfterTest
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }
}
