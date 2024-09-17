package com.mahira.learningselenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderCompletionPageNew {
    private WebDriver driver;

    @FindBy(css = "[data-test='complete-header']")
    private WebElement header;

    @FindBy(css = "[data-test='complete-text']")
    private WebElement text;

    public OrderCompletionPageNew(WebDriver driver){

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getHeaderText(){
        return header.getText();
    }

    public String getBodyText(){

        return text.getText();
    }
}
