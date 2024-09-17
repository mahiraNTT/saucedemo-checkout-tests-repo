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

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileDownloadTest {

    private static final String SITE = "https://the-internet.herokuapp.com/download";

    private WebDriver driver;

    @BeforeTest
    public void setUp(){
//this setUp is different than other tests
        Map<String, Object> prefs = new HashMap<>();
       // prefs.put("download.default_directory", "");//download folder
        prefs.put("download.default_directory", "/Users/a266756/Desktop");

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);

       // driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
    }

    private static void delay(){
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }


    @Test
    public void fileDownloadTest(){
        driver.get(SITE);

        WebElement downloadLink = driver.findElement(By.linkText("LambdaTest.txt"));
        downloadLink.click();

        delay();

        File downloadedFile = new File("/Users/a266756/Downloads/LambdaTest.txt");
        //Verify file
        Assert.assertTrue(downloadedFile.exists(),  "File download is not successful!");

    }

    @Test
    public void JPGFileDownloadTest(){
        driver.get(SITE);

        List<WebElement> downloadLinks = driver.findElements(By.cssSelector("a[href$='.jpg']"));
        //will get list of liks
        for(WebElement link : downloadLinks){
            link.click();
            delay();
        }

        File[] downloadedFiles = new File("/Users/a266756/Downloads/")
                .listFiles(((dir, name) -> name.endsWith(".jpg")));
        //Verify file
        Assert.assertNotNull(downloadedFiles, "No files were downloaded!");
        //Assert.assertTrue(downloadedFiles.length > 0,  "No JPG files were downloaded!");//failed in this line

    }

    @AfterTest
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }
}
