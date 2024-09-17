package com.mahira.learningselenium;

import com.mahira.learningselenium.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class LinksTest {

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
    public void brokenLinksTest(){

        driver.get(SITE);

        List<WebElement> links = driver.findElements(By.tagName("a"));

        for (WebElement link : links)
        {
            String url = link.getAttribute("href");

            if (url != null && !url.isEmpty()){
                try {
                    HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
                    connection.setConnectTimeout(10000);
                    connection.connect();

                    int responseCode = connection.getResponseCode();

                    if (responseCode == 200){
                        System.out.println("Link is working fine: " + url);
                    }else {
                        System.out.println("Broken link found: " + url);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);

                }
            }
        }
    }

    @AfterTest
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }
}
