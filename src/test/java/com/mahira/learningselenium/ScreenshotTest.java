package com.mahira.learningselenium;

import com.mahira.learningselenium.utils.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

public class ScreenshotTest {

    private static final String DYNAMIC_SITE = "https://the-internet.herokuapp.com/dynamic_content";

    private static final String SCREENSHOT_DIR = "screenshots";

    private WebDriver driver;

    @BeforeTest
    public void setUp(){
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
    }

    @Test
    public void takeFullScreenshot() throws IOException{
        driver.get(DYNAMIC_SITE);

        takeScreenshot(driver, "screenshot_one.png");

        driver.navigate().refresh();

        takeScreenshot(driver, "screenshot_two.png");

    }

        private static void takeScreenshot(WebDriver driver, String fileName) throws IOException{
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        Path directorypath = Paths.get(SCREENSHOT_DIR);

        if (!Files.exists(directorypath)){
            Files.createDirectories(directorypath);
        }

        Path destinationPath = FileSystems.getDefault().getPath(SCREENSHOT_DIR, fileName);

        Files.copy(screenshotFile.toPath(), destinationPath);

    }

    @Test
    public void takeElementScreenshot() throws IOException{
        driver.get(DYNAMIC_SITE);

//        takeELScreenshot(driver, "screenshot_one.png");
//        driver.navigate().refresh();
//        takeELScreenshot(driver, "screenshot_two.png");

        WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement contentEL = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("content")));

        List<WebElement> rowELs = contentEL.findElements(By.className("roe"));
        System.out.println("Row El: "+rowELs);

        int i=0;
        for (WebElement rowEL : rowELs){
            takeELScreenshot(rowEL, "screenshot_row"+ i++ + ".png");
        }

    }

    private static void  takeELScreenshot(WebElement rowEL, String fileName) throws IOException{
       File screenshotFile = rowEL.getScreenshotAs(OutputType.FILE);


        Path directorypath = Paths.get(SCREENSHOT_DIR);

        if (!Files.exists(directorypath)){
            Files.createDirectories(directorypath);
        }

        Path destinationPath = FileSystems.getDefault().getPath(SCREENSHOT_DIR, fileName);

        Files.copy(screenshotFile.toPath(), destinationPath);

    }

    @AfterTest
    public void tearDown(){
        if(driver != null) {
            driver.quit();
        }
    }
}
