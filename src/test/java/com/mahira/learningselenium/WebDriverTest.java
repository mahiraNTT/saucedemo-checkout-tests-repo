package com.mahira.learningselenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.Test;

public class WebDriverTest {

    @Test
    public void openPageUsingChrome()
    {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        driver.quit();
    }

    @Test
    public void openPageUsingFirefox()
    {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless");
        WebDriver driver = new FirefoxDriver(options);
        driver.get("https://www.duckduckgo.com");
        driver.quit();
    }

    @Test
    public void openPageUsingEdge()
    {
        EdgeOptions options = new EdgeOptions();
        options.setBrowserVersion("stable");
        WebDriver driver = new EdgeDriver(options);

        driver.get("https://www.bing.com");
        driver.quit();
    }

    @Test
    public void navigationTest(){
        ChromeOptions options =new ChromeOptions();
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.skillsoft.com/");
        driver.quit();
    }

}