package com.mahira.learningselenium;

import com.mahira.learningselenium.utils.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.devtools.v126.network.model.SubresourceWebBundleInnerResponseError;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Set;

public class CookiesTest {

    private static final String SITE = "https://testpages.eviltester.com/styled/cookies/adminlogin.html";

    private WebDriver driver;

    @BeforeTest
    public void setUp(){
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
    }

    private static void delay(){
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
    //

    @Test
    public void getCookiesTest(){
        driver.get(SITE);

        Set<Cookie> cookies = driver.manage().getCookies();//current site
        System.out.println("Cookies: "+ cookies);

        Assert.assertTrue(cookies.isEmpty(), "Expected empty set of cookies!");

delay();

    }

    @Test
    public void addCookieTest(){
        driver.get(SITE);

        String cookieName = "session_id";
        String cookieValue = "abc123";

        Cookie newCookie = new Cookie(cookieName, cookieValue);
        driver.manage().addCookie(newCookie);

        Set<Cookie> cookies = driver.manage().getCookies();

        Assert.assertFalse(cookies.isEmpty(), "Cookies not found!");
        System.out.println("Cookies: " + cookies);

        Assert.assertTrue(cookies.contains(newCookie), "Added cookie not found!");

    }

    @Test
    public void addMultipleCookiesTest(){
        driver.get(SITE);

        String[] cookieNames = {"session_id", "user_id", "auth_token"};
        String[] cookieValues = {"abc123", "user123", "token123"};

        for (int i=0; i < cookieNames.length; i++){
            Cookie newCookie = new Cookie(cookieNames[i], cookieValues[i]);

            driver.manage().addCookie(newCookie);
        }

        Set<Cookie> cookies = driver.manage().getCookies();

        Assert.assertFalse(cookies.isEmpty(), "Cookies not found!");

        System.out.println("Cookies: " + cookies);

        System.out.println("+++++++++++++++additional details+++++++++++++++");
        for (Cookie cookie : cookies){
            System.out.println("Cookie Domain: "+ cookie.getDomain());
            System.out.println("Cookie Path: " + cookie.getPath());
            System.out.println("Cookie same site: " + cookie.getSameSite());
            System.out.println("Is Cookie Secure: " + cookie.isSecure());
            System.out.println("Is Cookie HttpOnly: " + cookie.isHttpOnly());
            System.out.println("-----------------------------------");

        }
    }
    //
   @Test
   public void getSingleCookieTest(){
        driver.get(SITE);

       String[] cookieNames = {"session_id", "user_id", "auth_token"};
       String[] cookieValues = {"abc123", "user123", "token123"};

       for (int i=0; i < cookieNames.length; i++){
           Cookie newCookie = new Cookie(cookieNames[i], cookieValues[i]);

           driver.manage().addCookie(newCookie);
       }

       Cookie singleCookie = driver.manage().getCookieNamed("auth_token");
       Assert.assertNotNull(singleCookie, "Cookie not found!");

       Assert.assertEquals(singleCookie.getName(),"auth_token");
       Assert.assertEquals(singleCookie.getValue(), "token123");

       System.out.println("=============SINGLE COOLIE DETAILS==============");

       System.out.println("name: "+ singleCookie.getName() + ", Value: " + singleCookie.getValue());

       System.out.println("Cookie Domain: "+ singleCookie.getDomain());
       System.out.println("Cookie Path: "+ singleCookie.getPath());
       System.out.println("Cookie Same Site: "+ singleCookie.getSameSite());
       System.out.println("Is Cookie Secure: "+singleCookie.isSecure());
       System.out.println("Is Cookie HttpOnly: "+singleCookie.isHttpOnly());
   }

   @Test
   public void updateCookieTest(){
        driver.get(SITE);

       String cookieName = "session_id";
       String cookieValue = "abc123";

       Cookie newCooke = new Cookie(cookieName, cookieValue);
       driver.manage().addCookie(newCooke);

       Cookie existingCookie = driver.manage().getCookieNamed(cookieName);

       Cookie updatedCookie = new Cookie.Builder(existingCookie.getName(), "xyz123")
               .domain(existingCookie.getDomain())
               .path(existingCookie.getPath())
               .isHttpOnly(existingCookie.isHttpOnly())
               .isSecure(existingCookie.isSecure())
               .sameSite("Strict")
               .build();
       //Delete and add new cookie
       driver.manage().deleteCookie(existingCookie);
       driver.manage().addCookie(updatedCookie);

       updatedCookie = driver.manage().getCookieNamed(cookieName);
       System.out.println("Cookie value after update: " +updatedCookie.getValue());
       System.out.println("Cookie same site after update: " + updatedCookie.getSameSite());

       Assert.assertEquals(updatedCookie.getValue(), "xyz123");
       Assert.assertEquals(updatedCookie.getSameSite(), "Strict");
   }

    @Test
    public void deleteCookieTest(){
        driver.get(SITE);

        String[] cookieNames = {"session_id", "user_id", "auth_token"};
        String[] cookieValues = {"abc123", "user123", "token123"};

        for (int i=0; i < cookieNames.length; i++){
            Cookie newCookie = new Cookie(cookieNames[i], cookieValues[i]);

            driver.manage().addCookie(newCookie);
        }

        Cookie sessionCookie = driver.manage().getCookieNamed("session_id");
        Assert.assertNotNull(sessionCookie, "Cookie not found!");

        driver.manage().deleteCookieNamed("session_id");//delete cookie by name

        sessionCookie = driver.manage().getCookieNamed("session_id");
        Assert.assertNull(sessionCookie);

        Assert.assertFalse(driver.manage().getCookies().isEmpty(),"Cookie not found");//still have other two cookies

        driver.manage().deleteAllCookies();

        Assert.assertTrue(driver.manage().getCookies().isEmpty(), "Cookie found");
    }

    @Test
    public void loginTest(){
        driver.get(SITE);

        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login"));

        usernameInput.sendKeys("Admin");
        passwordInput.sendKeys("AdminPass");

        delay();

        loginButton.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://testpages.eviltester.com/styled/cookies/adminview.html", "Login failed!");

        Cookie loginCookie = driver.manage().getCookieNamed("loggedin");

        Assert.assertNotNull(loginCookie);
        Assert.assertEquals(loginCookie.getValue(), "Admin");

        System.out.println("Login Cookie: " + loginCookie);

        //more assersion
        Assert.assertNotNull(driver.findElement(By.id("navadminlogin")).getAttribute("href"));
        Assert.assertNotNull(driver.findElement(By.id("navadminview")).getAttribute("href"));
        Assert.assertNotNull(driver.findElement(By.id("navadminlogout")).getAttribute("href"));

        Assert.assertNull(driver.findElement(By.id("navadminsuperview")).getAttribute("href"));
    }

    @Test
    public  void loginLogoutBySettingCookiesTest(){

        driver.get(SITE);

        Assert.assertEquals(driver.getCurrentUrl(), "https://testpages.eviltester.com/styled/cookies/adminlogin.html");

        Cookie newCookie = new Cookie("loggedin", "Admin");
        driver.manage().addCookie(newCookie);
        //
        driver.navigate().refresh();

        Assert.assertEquals(driver.getCurrentUrl(),"https://testpages.eviltester.com/styled/cookies/adminview.html", "Login failed!");

        Cookie loginCookie = driver.manage().getCookieNamed("loggedin");

        Assert.assertNotNull(loginCookie);
        Assert.assertEquals(loginCookie.getValue(), "Admin");

        System.out.println("Login Cookie: "+ loginCookie);

    }


    @AfterTest
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }
}
