package com.mahira.learningselenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageNew {
    private WebDriver driver;

    @FindBy(id="user-name")
    private WebElement userNameField;

    @FindBy(id="password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    //page factory
    public LoginPageNew(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password){
        userNameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();

    }
}
