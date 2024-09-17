package com.mahira.learningselenium;

import com.mahira.learningselenium.pages.*;
import com.mahira.learningselenium.utils.DriverFactory;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Checkout flow on Sauce demo")
public class PageObjectModelTest {

    private static final String SITE = "https://www.saucedemo.com/";

    private WebDriver driver;
    //reference to page object can interact with them
    private LoginPageNew loginPageNew;

    private ProductsPageNew productsPageNew;

    private ProductDetailsPageNew productDetailsPageNew;

    private CartPageNew cartPageNew;

    private CheckoutPageNew checkoutPageNew;

    private FinalCheckoutPageNew finalCheckoutPageNew;

    private OrderCompletionPageNew orderCompletionPageNew;

    @BeforeClass
    public void setUp() {
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);

        loginPageNew = new LoginPageNew(driver);
        productsPageNew = new ProductsPageNew(driver);
        productDetailsPageNew = new ProductDetailsPageNew(driver);
        cartPageNew = new CartPageNew(driver);
        checkoutPageNew = new CheckoutPageNew(driver);
        finalCheckoutPageNew = new FinalCheckoutPageNew(driver);
        orderCompletionPageNew = new OrderCompletionPageNew(driver);

        driver.get(SITE);
    }

    private static void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

@Feature("Login flow")
@Story("Login")
@Description("Test to verify login functionality")
@Link("https://www.saucedemo.com/")
@Severity(SeverityLevel.BLOCKER)
@Tag("login")
@Owner("MD")
@Step("Login and Verify")

    @Test
    public void loginTest(){
        loginPageNew.login("standard_user", "secret_sauce");

        Assert.assertTrue(productsPageNew.isPageOpened(), "Login failed!");
        delay();
    }

    @Test(dependsOnMethods = "loginTest")
    public void addBackpackToCartTest(){
productsPageNew.navigateToProductDetailsPage("Sauce Labs Backpack");

productDetailsPageNew.addToCart();

Allure.step("Add item to cart");

Assert.assertEquals(productDetailsPageNew.getButtonText(), "Remove", "Button text didn't change!");

delay();

Allure.step("navigate back to products page");

driver.navigate().back();
    }

    @Feature("Add product flow")
    @Story("Add to Cart")
    @Description("Test to verify add product to cart")
    @Test(dependsOnMethods = "addBackpackToCartTest")
    @Flaky
    public void addFleeceJacketToCartTest(){
        productsPageNew.navigateToProductDetailsPage("Sauce Labs Fleece Jacket");

        productDetailsPageNew.addToCart();

        Assert.assertEquals(productDetailsPageNew.getButtonText(), "Remove", "Button text didn't change!");

        delay();

        driver.navigate().back();
    }

    @Test(dependsOnMethods = {"addBackpackToCartTest", "addFleeceJacketToCartTest"})
    public void testCart(){
        //use Lambda function for steps
        Allure.step("navigate to Cart and verify", step -> {
        productsPageNew.navigateToCart();

        Assert.assertTrue(cartPageNew.isPageOpened(), "Cart page not loaded");
        Assert.assertEquals(cartPageNew.getCartItemCount(), "2", "Incorrect number of items in the cart");
        Assert.assertEquals(cartPageNew.getContinueButtonText(), "Checkout", "Incorrect button text on the cart page");

        Allure.addAttachment("Cart item count", cartPageNew.getCartItemCount());
        });

Allure.step("Verify products in cart", step -> {
        Assert.assertTrue(cartPageNew.productInCart("Sauce Labs Backpack"));
        Assert.assertTrue(cartPageNew.productInCart("Sauce Labs Fleece Jacket"));
});

        delay();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Flaky

    @Test(dependsOnMethods = "testCart")
    public void testCheckout(){
        cartPageNew.continueCheckout();

        Assert.assertTrue(checkoutPageNew.isPageOpened(), "Checkout page not loaded");
        checkoutPageNew.enterDetails("peter", "Hank", "12345");

        Assert.assertEquals(checkoutPageNew.getFirstNameFieldValue(), "peter");
        Assert.assertEquals(checkoutPageNew.getLastNameFieldValue(), "Hank");
        Assert.assertEquals(checkoutPageNew.getZipcodeFieldValue(), "12345");

        //delay();
    }

    @Test(dependsOnMethods = "testCheckout")
    public void finalCheckoutTest(){
        checkoutPageNew.continueCheckout();

       Assert.assertTrue(finalCheckoutPageNew.isPageOpened());
        Assert.assertEquals(finalCheckoutPageNew.getPaymentInfoValue(), "SauceCard #31337");
        Assert.assertEquals(finalCheckoutPageNew.getShippingInfoValue(), "Free Pony Express Delivery!");
        Assert.assertEquals(finalCheckoutPageNew.getTotalLabel(), "Total: $86.38");

       // delay();
    }

    @Feature("Confirmation flow")
    @Story("Order completion")
    @Description("Test to verify order completion")

    @Test(dependsOnMethods = "finalCheckoutTest")
    public void orderCompletionTest(){
        finalCheckoutPageNew.finishCheckout();

        Assert.assertEquals(orderCompletionPageNew.getHeaderText(), "Thank you for your order!");
        Assert.assertEquals(orderCompletionPageNew.getBodyText(),"Your order has been dispatched, and will arrive just as fast as the pony can get there!");
    }


    @AfterClass
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }
}
