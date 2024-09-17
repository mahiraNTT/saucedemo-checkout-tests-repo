package com.mahira.learningselenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public void login(String username, String password){

        WebElement usernameFiled = driver.findElement(By.id("user-name"));
        WebElement passwordFiled = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameFiled.sendKeys(username);
        passwordFiled.sendKeys(password);
        loginButton.click();
    }
}
