package com.mahira.learningselenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductDetailsPage {

    private WebDriver driver;

    public ProductDetailsPage(WebDriver driver){
        this.driver  = driver;
    }

    public void addToCart(){
        WebElement addToCartBtn = driver.findElement(By.cssSelector(".btn_inventory"));
        addToCartBtn.click();
    }

    public String getButtonText(){
        WebElement cartButton = driver.findElement(By.cssSelector(".btn_inventory"));
        return cartButton.getText();
    }

}
