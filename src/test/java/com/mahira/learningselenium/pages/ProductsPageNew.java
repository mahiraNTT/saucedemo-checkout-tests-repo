package com.mahira.learningselenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductsPageNew {

    private WebDriver driver;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartButton;

    public ProductsPageNew(WebDriver driver){
        this.driver  = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened(){

        return driver.getCurrentUrl().contains("inventory.html");
    }

    public void navigateToProductDetailsPage(String productName){
        WebElement productLink = driver.findElement(By.linkText(productName));
        productLink.click();
    }

    public void navigateToCart(){
        cartButton.click();
    }
//logical OR,
    @FindAll({@FindBy(id = "item_0_title_link"), @FindBy(id = "item_0_title_link"), @FindBy(id = "item_0_title_link"), @FindBy(id = "item_0_title_link"),
            @FindBy(id = "item_0_title_link"), @FindBy(id = "item_0_title_link"), })
    private List<WebElement> allInventoryItemsFindAll;

//logical AND, should all conditions staisfied
    @FindBys({@FindBy(className = "inventory_item"), @FindBy(className = "inventory_item_name")})
    private List<WebElement> allInventoryItemsFindBys;

}
