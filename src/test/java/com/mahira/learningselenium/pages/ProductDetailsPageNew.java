package com.mahira.learningselenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductDetailsPageNew {

    private WebDriver driver;

    @FindBy(css = ".btn_inventory")
    private WebElement cartButton;

    public ProductDetailsPageNew(WebDriver driver){
        this.driver  = driver;
        PageFactory.initElements(driver, this);
    }

    public void addToCart(){
        cartButton.click();
    }

    public String getButtonText(){
        return cartButton.getText();
    }

}
