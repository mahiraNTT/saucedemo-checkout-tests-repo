package com.mahira.learningselenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductsPage {

    private WebDriver driver;

    public ProductsPage(WebDriver driver){
        this.driver  = driver;
    }

    public boolean isPageOpened(){
        return driver.getCurrentUrl().contains("inventory.html");
    }

    public void navigateToProductDetailsPage(String productName){
        WebElement productLink = driver.findElement(By.linkText(productName));
        productLink.click();
    }

    public void navigateToCart(){
        WebElement cartButton = driver.findElement(By.cssSelector(".shopping_cart_link"));
        cartButton.click();
    }

}
