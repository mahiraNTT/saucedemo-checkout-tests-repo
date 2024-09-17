package com.mahira.learningselenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.service.DriverFinder;
import org.testng.annotations.Test;

import java.nio.file.Path;

public class BlogTest {

    @Test
    public void navigateToBlogPage(){
        ChromeOptions options = new ChromeOptions();
        options.setBrowserVersion("stable");
//        options.addArguments("--start-maximized");
//        options.addArguments("--incognito");
        options.addArguments("--start-maximized", "--incognito");//can add multiple arguments

        WebDriver driver = new ChromeDriver(options);
        //find binary path
//        Path chromepath = Path.of(DriverFinder.getPath(ChromeDriverService.createDefaultService(), options).getBrowserPath());
//        System.out.println(("Binary location: "+chromepath));

        driver.get("https://www.youtube.com");
        delay(5000);
        driver.quit();
    }
    //method
    private void delay(long milliseconds){
       try {
           Thread.sleep(milliseconds);
       }
       catch (InterruptedException e)
       {
           throw new RuntimeException(e);
       }
    }
}
