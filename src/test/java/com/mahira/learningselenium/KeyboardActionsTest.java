package com.mahira.learningselenium;

import com.mahira.learningselenium.utils.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class KeyboardActionsTest {

    private WebDriver driver;
    private static final String SITE = "https://testpages.eviltester.com/styled/basic-html-form-test.html";


    @BeforeTest
    public void setUp(){
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
    }

    private static void delay(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException exception){
            throw new RuntimeException(exception);
        }
    }

    @Test
    public void keyboardActionsTest(){
        driver.get(SITE);

        WebElement usernameEL = driver.findElement(By.name("username"));
        WebElement passwordEL = driver.findElement(By.name("password"));
        WebElement commentsEL = driver.findElement(By.name("comments"));

        commentsEL.clear();

        //actions can be chain together
       Actions actions =  new Actions(driver);
//       actions.sendKeys(usernameEL, "Tom Jerry")
//               .sendKeys(passwordEL, "password123")
//               .sendKeys(commentsEL, "Practice ")
//               .perform();

       //another way to write
        actions.keyDown(Keys.SHIFT)
                .sendKeys(commentsEL, "More practice")
                .keyUp(Keys.SHIFT)
                .sendKeys(usernameEL, "Dory Cat")
                .sendKeys(passwordEL, "password456")
                        .perform();

        System.out.println("username: "+ usernameEL.getAttribute("value"));
        System.out.println("Password: "+ passwordEL.getAttribute("value"));
        System.out.println("Comments: "+ commentsEL.getAttribute("value"));

       delay();

       WebElement submitInputEL = driver.findElement(By.cssSelector("input[value='submit']"));
       submitInputEL.submit();

       Assert.assertEquals(driver.getCurrentUrl(), "https://testpages.eviltester.com/styled/the_form_processor.php");

        delay();
    }

    //
    @Test
    public void keyboardActionsTest2(){
        driver.get(SITE);

        WebElement usernameEL = driver.findElement(By.name("username"));
        WebElement passwordEL = driver.findElement(By.name("password"));
        WebElement commentsEL = driver.findElement(By.name("comments"));

        commentsEL.clear();

        Actions actions =  new Actions(driver);
        //another way to write change last name
        actions.sendKeys(usernameEL, "John Smith")
                        .sendKeys(Keys.ARROW_LEFT, Keys.ARROW_LEFT, Keys.ARROW_LEFT, Keys.ARROW_LEFT, Keys.ARROW_LEFT)
                                .keyDown(Keys.SHIFT)
                                        .sendKeys("Oliver")
                                                .pause(Duration.ofSeconds(2))
                                                        .keyUp(Keys.SHIFT)
                                                                .sendKeys(Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT)
                                                                        .sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE)//delete last name
                                                                                .pause(Duration.ofSeconds(5))
                                                                                        .sendKeys(passwordEL, "password123")
                                                                                                .sendKeys(commentsEL, "keyboard action practice")
                                                                                                        .perform();


        System.out.println("username: "+ usernameEL.getAttribute("value"));
        System.out.println("Password: "+ passwordEL.getAttribute("value"));
        System.out.println("Comments: "+ commentsEL.getAttribute("value"));

        delay();

        WebElement submitInputEL = driver.findElement(By.cssSelector("input[value='submit']"));
        submitInputEL.submit();

        Assert.assertEquals(driver.getCurrentUrl(), "https://testpages.eviltester.com/styled/the_form_processor.php");

        delay();
    }
    //
    @Test
    public void keyboardActionsTest3(){
        driver.get(SITE);

        WebElement usernameEL = driver.findElement(By.name("username"));
        WebElement passwordEL = driver.findElement(By.name("password"));
        WebElement commentsEL = driver.findElement(By.name("comments"));

        commentsEL.clear();
        //actions can be chain together
        Actions actions =  new Actions(driver);
//
        Keys cmdCtrl = Platform.getCurrent().is(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;

        actions.sendKeys(usernameEL, "John Smith")
                        .keyDown(Keys.SHIFT)
                                .sendKeys(Keys.ARROW_UP)
                                        .keyUp(Keys.SHIFT)
                                                .keyDown(cmdCtrl)
                                                        .sendKeys("xvv")//cut once and paste twice
                                                                .keyUp(cmdCtrl)
                                                                        .sendKeys(passwordEL, "password123")
                                                                                .sendKeys(commentsEL, "More advanced practice")
                                                                                        .perform();


        System.out.println("username: "+ usernameEL.getAttribute("value"));
        System.out.println("Password: "+ passwordEL.getAttribute("value"));
        System.out.println("Comments: "+ commentsEL.getAttribute("value"));

        delay();

        WebElement submitInputEL = driver.findElement(By.cssSelector("input[value='submit']"));
        submitInputEL.submit();

        Assert.assertEquals(driver.getCurrentUrl(), "https://testpages.eviltester.com/styled/the_form_processor.php");

        delay();
    }

    @AfterTest
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }



}
