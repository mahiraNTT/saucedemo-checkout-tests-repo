package com.mahira.learningselenium;

import com.mahira.learningselenium.utils.DriverFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FileUploadTest {

    private static final String SITE = "https://the-internet.herokuapp.com/upload";

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
    public void fileUploadTest(){
        driver.get(SITE);

        WebElement fileInput = driver.findElement(By.id("file-upload"));

        String filePath ="/Users/a266756/IdeaProjects/learning-selenium/TestFile.png";

        fileInput.sendKeys(filePath);

        WebElement uploadButton = driver.findElement(By.id("file-submit"));

        delay();
        uploadButton.click();

        WebElement uploadedFile = driver.findElement(By.id("uploaded-files"));

        String uploadedFileName = uploadedFile.getText().trim();
        Assert.assertEquals(uploadedFileName, "TestFile.png");

        String actualTitle = driver.findElement(By.tagName("h3")).getText();
        //Verify success message
        Assert.assertEquals(actualTitle, "File Uploaded!", "Upload file is not successful!");

    }


    @AfterTest
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }
}
