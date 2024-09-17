package com.mahira.learningselenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPageNew {

    private WebDriver driver;

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement zipCodeField;

    @FindBy(css = ".cart_button")
    private WebElement continueCheckoutButton;

    public CheckoutPageNew(WebDriver driver){

        this.driver =driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened(){

        return driver.getCurrentUrl().contains("checkout-step-one.html");
    }

    public void enterDetails(String firstName, String lastName, String zipCode){

        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        zipCodeField.sendKeys(zipCode);

    }

    public String getFirstNameFieldValue(){

        return firstNameField.getAttribute("Value");
    }

    public String getLastNameFieldValue(){

        return lastNameField.getAttribute("Value");
    }

    public String getZipcodeFieldValue(){

        return zipCodeField.getAttribute("Value");
    }

    public void continueCheckout(){
        continueCheckoutButton.click();
    }

}
