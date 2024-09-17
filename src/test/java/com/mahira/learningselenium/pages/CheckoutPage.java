package com.mahira.learningselenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutPage {

    private WebDriver driver;

    public CheckoutPage(WebDriver driver){
        this.driver =driver;
    }

    public boolean isPageOpened(){
        return driver.getCurrentUrl().contains("checkout-step-one.html");
    }

    public void enterDetails(String firstName, String lastName, String zipCode){
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        WebElement lastNameField = driver.findElement(By.id("last-name"));
        WebElement zipCodeField = driver.findElement(By.id("postal-code"));

        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        zipCodeField.sendKeys(zipCode);

    }

    public String getFirstNameFieldValue(){
        return driver.findElement(By.id("first-name")).getAttribute("Value");
    }

    public String getLastNameFieldValue(){
        return driver.findElement(By.id("last-name")).getAttribute("Value");
    }

    public String getZipcodeFieldValue(){
        return driver.findElement(By.id("postal-code")).getAttribute("Value");
    }

    public void continueCheckout(){
        driver.findElement(By.cssSelector(".cart_button")).click();
    }

}
