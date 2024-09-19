package com.mahira.learningselenium;

import com.mahira.learningselenium.pages.*;
import com.mahira.learningselenium.utils.DriverFactory;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;

import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.apache.logging.log4j.Logger;


public class SaucedemoCheckoutTests {

//    static {
//        ConfigurationBuilder<BuiltConfiguration> builder =
//                ConfigurationBuilderFactory.newConfigurationBuilder();
//
//        builder.setStatusLevel(Level.INFO);
//        builder.setConfigurationName("BuilderTest");
//
//        //Define a Console Appender
//        builder.add(builder.newAppender("stdout", "Console")
//                .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT)
//                .add(builder.newLayout("PatternLayout")
//                        .addAttribute("pattern", "%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n")));
//
//        //AppenderComponentBuilder appenderBuilder = builder.newAppender("stdout", "Console");
//        //Define a file appender
//        builder.add(builder.newAppender("logfile", "File")
//                .addAttribute("fileName", "logs/app.log")
//                .add(builder.newLayout("PatternLayout")
//                        .addAttribute("pattern", "%d{yyyy-MM-dd HH:mm:ss} [%t] %-5level %logger{36} - %msg%n")));
//
//        //Set the root logger level INFO and add the console appender
//        builder.add(builder.newRootLogger(Level.INFO)
//                .add(builder.newAppenderRef("stdout")));//stdout is appender name
//
//        builder.add(builder.newLogger(SaucedemoCheckoutTests.class.getName(), Level.DEBUG)
//                        .addAttribute("additivity", false)
//                .add(builder.newAppenderRef("logfile")));
//
//        //Build the configuration
//        BuiltConfiguration config = builder.build();
//        Configurator.initialize(config);
//    }

    static {
        ConfigurationBuilder<BuiltConfiguration> builder =
                ConfigurationBuilderFactory.newConfigurationBuilder();

        //Define a Console Appender with a pattern layout
        AppenderComponentBuilder appenderBuilder = builder.newAppender(
                "stdout", "Console");

                appenderBuilder.add(builder.newLayout("PatternLayout")
                        .addAttribute("pattern", "%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"));

//                //Add a thresholdFilter to the appender
//        FilterComponentBuilder filterBuilder = builder.newFilter(
//                "ThresholdFilter",
//                Filter.Result.ACCEPT, Filter.Result.DENY);
//        filterBuilder.addAttribute("level", Level.INFO);
//        appenderBuilder.add(filterBuilder);

        //Add a RegexFilter to the appender to exclude message contains 'Starting'
        FilterComponentBuilder regexFilterBuilder = builder.newFilter(
                "RegexFilter", Filter.Result.DENY, Filter.Result.NEUTRAL);
        regexFilterBuilder.addAttribute("regex", ".*Starting.*");//will filter specific message
        appenderBuilder.add(regexFilterBuilder);

        builder.add(appenderBuilder);

        //Set the root logger level DEBUG and add the console appender
        builder.add(builder.newRootLogger(Level.DEBUG)
                .add(builder.newAppenderRef("stdout")));//stdout is appender name

        //Build the configuration
        BuiltConfiguration config = builder.build();
        Configurator.initialize(config);
    }

    //private Logger logger;
    //instantiate logger object
    private static final Logger logger = LogManager.getLogger(SaucedemoCheckoutTests.class);

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

        logger.trace("Setting up WebDriver...");
        //logger = LoggerFactory.getLogger(SaucedemoCheckoutTests.class);

        logger.trace("Setting up driver...");

        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);

        logger.trace("Settign up page classes for the Page Object Model...");

        loginPageNew = new LoginPageNew(driver);
        productsPageNew = new ProductsPageNew(driver);
        productDetailsPageNew = new ProductDetailsPageNew(driver);
        cartPageNew = new CartPageNew(driver);
        checkoutPageNew = new CheckoutPageNew(driver);
        finalCheckoutPageNew = new FinalCheckoutPageNew(driver);
        orderCompletionPageNew = new OrderCompletionPageNew(driver);

        driver.get(SITE);

        logger.debug("Navigate to site {}",SITE);
    }

    private static void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void loginTest(){
        logger.debug("*****Starting testLogin******");

        loginPageNew.login("standard_user", "secret_sauce");

        if(! productsPageNew.isPageOpened()){
            logger.error("Login failed! Incorrect username or password.");
        }

        Assert.assertTrue(productsPageNew.isPageOpened(), "Login failed!");

        logger.info("User logged in successfully");
        //logger.info(Map.of("suer", "standard_user", "password", "secret_sauce"));//slf4j cannot support Map

        logger.info("Username: {}, Password: {}", "standard_user", "secret_sauce");
    }

    @Test(dependsOnMethods = "loginTest")
    public void addBackpackToCartTest(){
        logger.debug("*********Starting testAddBackpackToCart*******");

productsPageNew.navigateToProductDetailsPage("Sauce Labs Backpack");

productDetailsPageNew.addToCart();


Assert.assertEquals(productDetailsPageNew.getButtonText(), "Remove", "Button text didn't change!");

if (!productDetailsPageNew.getButtonText().equals("Remove")){
    logger.warn("Failed to item to cart");
}

driver.navigate().back();
    }

    @Test(dependsOnMethods = "addBackpackToCartTest")

    public void addFleeceJacketToCartTest(){
        productsPageNew.navigateToProductDetailsPage("Sauce Labs Fleece Jacket");

        productDetailsPageNew.addToCart();

        Assert.assertEquals(productDetailsPageNew.getButtonText(), "Remove", "Button text didn't change!");

        driver.navigate().back();

        logger.info("Navigate back to products page after adding one product");
    }

    @Test(dependsOnMethods = {"addBackpackToCartTest", "addFleeceJacketToCartTest"})
    public void testCart(){
      logger.debug("********Starting testCart**********");

        productsPageNew.navigateToCart();

        if(! cartPageNew.isPageOpened()){
            logger.fatal("Cart page not loaded!");
        }

        Assert.assertTrue(cartPageNew.isPageOpened(), "Cart page not loaded");
        Assert.assertEquals(cartPageNew.getCartItemCount(), "2", "Incorrect number of items in the cart");
        Assert.assertEquals(cartPageNew.getContinueButtonText(), "Checkout", "Incorrect button text on the cart page");

        if(! cartPageNew.productInCart("Sauce Labs Backpack") || cartPageNew.productInCart("Sauce Labs Fleece Jacket"))
        {
            logger.warn("Either {} or {} not found in cart", "Sauce Labs Backpack", "Sauce Labs Fleece Jacket");
            }

        Assert.assertTrue(cartPageNew.productInCart("Sauce Labs Backpack"));
        Assert.assertTrue(cartPageNew.productInCart("Sauce Labs Fleece Jacket"));

            logger.info("Validate the cart items");

    }

    @Test(dependsOnMethods = "testCart")
    public void testCheckout(){

        logger.debug("*********Starting checkout********");

        cartPageNew.continueCheckout();

        if(! checkoutPageNew.isPageOpened()){
            logger.error("First checkout page not loaded!");
        }

        Assert.assertTrue(checkoutPageNew.isPageOpened(), "Checkout page not loaded");
        checkoutPageNew.enterDetails("peter", "Hank", "12345");

        Assert.assertEquals(checkoutPageNew.getFirstNameFieldValue(), "peter");
        Assert.assertEquals(checkoutPageNew.getLastNameFieldValue(), "Hank");
        Assert.assertEquals(checkoutPageNew.getZipcodeFieldValue(), "12345");

        logger.info("Completed first checkout page");

    }

    @Test(dependsOnMethods = "testCheckout")
    public void finalCheckoutTest(){

        logger.debug("*******Starting testFinalCheckout*********");
        checkoutPageNew.continueCheckout();

        if( ! finalCheckoutPageNew.isPageOpened()){
            logger.error("Second checkout page not loaded!");
        }

       Assert.assertTrue(finalCheckoutPageNew.isPageOpened());
        Assert.assertEquals(finalCheckoutPageNew.getPaymentInfoValue(), "SauceCard #31337");
        Assert.assertEquals(finalCheckoutPageNew.getShippingInfoValue(), "Free Pony Express Delivery!");
        Assert.assertEquals(finalCheckoutPageNew.getTotalLabel(), "Total: $86.38");

        logger.info("Completed second checkout page");

    }

    @Test(dependsOnMethods = "finalCheckoutTest")
    public void orderCompletionTest(){
        logger.debug("*******orderCompletionTest*******");
        finalCheckoutPageNew.finishCheckout();

        Assert.assertEquals(orderCompletionPageNew.getHeaderText(), "Thank you for your order!");
        Assert.assertEquals(orderCompletionPageNew.getBodyText(),"Your order has been dispatched, and will arrive just as fast as the pony can get there!");

        logger.info("Completed order");
    }

    @AfterClass
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
        logger.trace("Quit WebDriver...");
    }
}
