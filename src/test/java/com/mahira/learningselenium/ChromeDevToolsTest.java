package com.mahira.learningselenium;

import com.google.common.collect.ImmutableList;
import com.mahira.learningselenium.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.devtools.v85.network.model.Request;
import org.openqa.selenium.devtools.v85.network.model.Response;
import org.openqa.selenium.devtools.v85.security.Security;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChromeDevToolsTest {
private static final String SITE = "https://www.saucedemo.com/";

private WebDriver driver;

private DevTools devTools;

@BeforeTest
    public void setUp(){
    driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);

    devTools = ((ChromeDriver) driver).getDevTools();
    devTools.createSession();
}

@Test
    public void captureRequestSentTest(){

    List<Request> capturedRequests = new CopyOnWriteArrayList<>();

    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

    devTools.addListener(Network.requestWillBeSent(), request -> {
        capturedRequests.add(request.getRequest());
    });

    driver.get(SITE);

    Assert.assertFalse(capturedRequests.isEmpty(), "No request were captured");

    for (Request request : capturedRequests){
        System.out.println("Request URl: "+request.getUrl());
        System.out.println("Request method: "+request.getMethod());
        System.out.println("Request headers: "+request.getHeaders());

    }
}

    @Test
    public void captureResponseReceivedTest(){

        List<Response> capturedResponses = new CopyOnWriteArrayList<>();

        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.addListener(Network.responseReceived(), responseReceived -> {
            capturedResponses.add(responseReceived.getResponse());
        });

        driver.get(SITE);

        Assert.assertFalse(capturedResponses.isEmpty(), "No request were captured");

        for (Response response : capturedResponses){
            System.out.println("Request URl: "+response.getUrl());
            System.out.println("Request method: "+response.getStatus());
            System.out.println("Request headers: "+response.getHeaders());
        }
    }

    @Test
    public void captureConsoleLogsTest() throws InterruptedException {
    driver.get(SITE);

        WebElement usernameFiled = driver.findElement(By.id("user-name"));
        WebElement passwordFiled = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameFiled.sendKeys("standard-user");
        passwordFiled.sendKeys("wrong-password");
        loginButton.click();

        Thread.sleep(10000);

        LogEntries entries = driver.manage().logs().get(LogType.BROWSER);
        List<LogEntry> logEntries = entries.getAll();

        for (LogEntry entry : logEntries){
            System.out.println("Level: "+ entry.getLevel());
            System.out.println("Message: " + entry.getMessage());
            System.out.println("Timestamp: "+ entry.getTimestamp());
        }
    }

    @Test
    public void blockImageLoadingTest(){
    driver.get(SITE);

        WebElement usernameFiled = driver.findElement(By.id("user-name"));
        WebElement passwordFiled = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameFiled.clear();
        usernameFiled.sendKeys("standard-user");

        passwordFiled.clear();
        passwordFiled.sendKeys("secret_sauce");
        loginButton.click();

        devTools.send(Network.enable(Optional.empty(),
                Optional.empty(),
                Optional.empty()));

        devTools.send(Network.setBlockedURLs(ImmutableList.of("*.jpg", "*.jpeg", "*.png")));
        /////////////////
//        devTools.send(Network.enable(Optional.of(1000000),
//                Optional.empty(),
//                Optional.empty()));
//
//        devTools.send(Network.setBlockedURLs(ImmutableList.of("*.css")));

        driver.navigate().refresh();//images will block


    }

    @Test
    public void handlingInsecureWebsitesTest(){
    devTools.send(Security.enable());

    devTools.send(Security.setIgnoreCertificateErrors(true));

    driver.get("https://expired.badssl.com/");

    Assert.assertTrue(driver.getTitle().equals("expired.badssl.com"), "Title not correct");

    }

@AfterTest
    public void tearDown(){
    if (driver != null){
        driver.quit();
    }
}

}
