package com.mahira.learningselenium;

import com.mahira.learningselenium.utils.DriverFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.List;

import static java.time.Duration.*;

public class LocatorTest {

    private WebDriver driver;

    private static final String SITE = "https://www.demoblaze.com/";
    private static final String CART = SITE + "cart.html";
    private static final String SITE2= "https://www.saucedemo.com/";

    private static void delay(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    @BeforeTest
    public void setUp(){
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);

            }

            @Test
            public void idLocatorsTest(){
        driver.get(SITE);
        driver.get(CART);

                WebElement logoEL = driver.findElement(By.id("nava"));
                System.out.println("Element Text: "+logoEL.getText());

                Assert.assertTrue(logoEL.isDisplayed());
                Assert.assertTrue(logoEL.isEnabled());
                Assert.assertFalse(logoEL.isSelected());

                logoEL.click();

                Assert.assertEquals(driver.getCurrentUrl(),SITE + "index.html");

            }

    @Test
    public void idLocatorsTest2(){
        driver.get(SITE);

        WebElement footerEL = driver.findElement(By.id("footc"));

        Assert.assertNotNull(footerEL);
        Assert.assertTrue(footerEL.isDisplayed());
        Assert.assertTrue(footerEL.isEnabled());

        WebElement caerEL = driver.findElement(By.id("cartur"));

        caerEL.click();

        Assert.assertEquals(driver.getCurrentUrl(), CART);
    }

    @Test
    public void classLocatorsTest(){
        driver.get(SITE);

        WebElement samsungGalaxyEL = driver.findElement(By.cssSelector(".hrefch[href='prod.html?idp_=1']"));

        Assert.assertTrue(samsungGalaxyEL.isDisplayed());
        Assert.assertTrue(samsungGalaxyEL.isEnabled());

        samsungGalaxyEL.click();

        WebElement addToCartButtonEL = driver.findElement(By.xpath("//a[@class='btn btn-success btn-lg']"));
        addToCartButtonEL.click();
        //expilicit wait
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(5));//wait for up to 10 seconds
        wait.until(ExpectedConditions.alertIsPresent());

        //Alert
        Alert alert = driver.switchTo().alert();;
        alert.accept();

        WebElement cartEL = driver.findElement(By.xpath("//a[@class='btn btn-success btn-lg']"));
        cartEL.click();
//        WebElement placeOrderButtonEL = driver.findElement(By.xpath(".btn.btn-success"));
//        wait.until(ExpectedConditions.visibilityOf(placeOrderButtonEL));
//        Assert.assertTrue(placeOrderButtonEL.isDisplayed());
//        Assert.assertTrue(placeOrderButtonEL.isEnabled());
    }

    @Test
    public void tagNameLocatorTest(){
        driver.get(SITE);

       List< WebElement> imgELs = driver.findElements(By.tagName("img"));

       for (WebElement imgEL : imgELs){
           String scrAttr = imgEL.getAttribute("src");
           Assert.assertNotNull(scrAttr);

           System.out.println("image source: "+ scrAttr);
       }

    }

    @Test
    public void tagNameLocatorTest2(){
        driver.get(SITE);

        List< WebElement> categoriesELs = driver.findElements(By.id("itemc"));

        for (WebElement categoryEL : categoriesELs){
            String categoryText = categoryEL.getText();

            System.out.println("Category text: "+ categoryText);
            categoryEL.click();

            //container products under category
            WebElement containerEL = driver.findElement(By.id("tbodyid"));
            //search subset of the DOM find links within the main page
            List<WebElement> linkELs = containerEL.findElements(By.tagName("a"));

            for (WebElement linkEL : linkELs){
                String hrefAttr = linkEL.getAttribute("href");
                Assert.assertNotNull(hrefAttr);

                System.out.println("URL: "+hrefAttr);
            }
        }

    }

    @Test
    public void nameLocatorsTest(){
        driver.get(SITE);

        WebElement formEL = driver.findElement(By.name("frm"));
        Assert.assertTrue(formEL.isEnabled());
        Assert.assertTrue(formEL.isDisplayed());

        List<WebElement> buttonsELs = formEL.findElements(By.tagName("button"));
        //make sure 2 buttons both displayed
        Assert.assertEquals(buttonsELs.size(), 2);
        Assert.assertTrue(buttonsELs.get(0).isDisplayed());
        Assert.assertTrue(buttonsELs.get(1).isDisplayed());

        System.out.println("Button text " + buttonsELs.get(0).getText());
        System.out.println("Button text " + buttonsELs.get(1).getText());

    }

    @Test
    public void cssSelectorsLocatorsTest(){

        driver.get(SITE2);

        WebElement loginEL = driver.findElement(By.cssSelector("div.login-box"));

        Assert.assertTrue(loginEL.isDisplayed());
        //select by id without tag name
        WebElement usernameEL = driver.findElement(By.cssSelector("#user-name"));
        WebElement passwordELL = driver.findElement(By.cssSelector("input#password"));

        usernameEL.sendKeys("standard_user");
        passwordELL.sendKeys("secret_sauce");

        WebElement submitbuttonEL = driver.findElement(By.cssSelector(".submit-button"));
        submitbuttonEL.click();
        //
        WebDriverWait wait=new WebDriverWait(driver, ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='app_logo']")));
        Assert.assertEquals(driver.getCurrentUrl(), SITE2 + "inventory.html");
        //Add product to cart
        WebElement addToCartBackpackEL = driver.findElement(By.cssSelector("#add-to-cart-sauce-labs-backpack"));
        addToCartBackpackEL.click();

        WebElement addToCartTshirtEL = driver.findElement(By.cssSelector("#add-to-cart-sauce-labs-bolt-t-shirt"));
        addToCartTshirtEL.click();

        WebElement cartEL =driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
        cartEL.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='app_logo']")));

        WebElement continueShoppingEL = driver.findElement(By.xpath("//button[@id='continue-shopping']"));
        continueShoppingEL.click();

        Assert.assertEquals(driver.getCurrentUrl(), SITE2 + "inventory.html");

    }

            @AfterTest
            public void tearDown(){
        if(driver != null){
            driver.quit();
        }
            }
}
