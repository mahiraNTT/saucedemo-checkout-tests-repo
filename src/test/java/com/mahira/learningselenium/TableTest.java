package com.mahira.learningselenium;

import com.mahira.learningselenium.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class TableTest {

    private static final String SITE = "https://testpages.eviltester.com/styled/index.html";

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

    @Test
    public void testRowCount(){
        driver.get(SITE);

        WebElement tableLinkEL = driver.findElement(By.linkText("Table Test Page"));
        tableLinkEL.click();

        WebElement tableEL = driver.findElement(By.id("mytable"));
        List<WebElement> rowsEL = tableEL.findElements(By.tagName("tr"));

        rowsEL.forEach(System.out::println);
        Assert.assertEquals(rowsEL.size(), 5, "Number of rows in static table doesn't match");

        delay();

    }

    @Test
    public void columnCountTest(){
driver.get(SITE);

WebElement tableLinkEL = driver.findElement(By.linkText("Table Test Page"));
tableLinkEL.click();

WebElement tableEL = driver.findElement(By.id("mytable"));

List<WebElement> rowsEL = tableEL.findElements(By.tagName("tr"));

for (int i=1; i<rowsEL.size(); i++){
    List<WebElement> columnsEL = rowsEL.get(i).findElements(By.tagName("td"));

    Assert.assertEquals(columnsEL.size(), 2, "Number of columns in row "+i+ "doesn't match");
}

delay();

    }

    @Test
    public void headersTest(){
        driver.get(SITE);

        WebElement tableLinkEL = driver.findElement(By.linkText("Table Test Page"));
        tableLinkEL.click();

        WebElement tableEL = driver.findElement(By.id("mytable"));

        WebElement headerRowEL = tableEL.findElement(By.tagName("tr"));

        List<WebElement>  headersEL = headerRowEL.findElements(By.tagName("th"));

        Assert.assertEquals(headersEL.size(), 2, "Number of headers in static table doesn't match");

        Assert.assertEquals(headersEL.get(0).getText(), "Name", "Header name for column i doesn't match");

        Assert.assertEquals(headersEL.get(1).getText(), "Amount", "Header name for column 2 doesn't match");

        System.out.println("Headers: "+ headersEL.get(0).getText() + ", " + headersEL.get(1).getText());

        delay();

    }

    @Test
    public void singleRowTest(){
        driver.get(SITE);

        WebElement tableLinkEL = driver.findElement(By.linkText("Table Test Page"));
        tableLinkEL.click();

        WebElement tableEL = driver.findElement(By.id("mytable"));

        WebElement firstRowEL = tableEL.findElements(By.tagName("tr")).get(1);//

        List<WebElement> cellsEL = firstRowEL.findElements(By.tagName("td"));

        System.out.println("Number of cells in the row: "+ cellsEL.size());

        Assert.assertEquals(cellsEL.size(), 2, "Number of cells in the row doesn't match");

        System.out.println("---Test in cells");

        for (WebElement cellEL : cellsEL){
            System.out.println(cellEL.getText());
        }

        Assert.assertEquals(cellsEL.get(0).getText(), "Alan");
        Assert.assertEquals(cellsEL.get(1).getText(), "12");

        delay();

    }

    @Test
    public void tableDataTest(){
        driver.get(SITE);

        WebElement tableLinkEL = driver.findElement(By.linkText("Table Test Page"));
        tableLinkEL.click();

        WebElement tableEL = driver.findElement(By.id("mytable"));

        String[][] expectedData = {
                {"Alan", "12"},
                {"Bob", "23"},
                {"Aleister", "33.3"},
                {"Douglas", "42"}
        };

        List<WebElement> rows = tableEL.findElements(By.tagName("tr"));
        //Skip the header row

        for (int i=1; i<rows.size(); i++){
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));

            Assert.assertEquals(cells.size(), 2, "Number of columns in row " +i+ " doesn't match");

            System.out.println("Row: " + i + ": ");

            for (int j=0; j < cells.size(); j++){
                System.out.println(cells.get(j).getText() + ", ");

                Assert.assertEquals(cells.get(j).getText(), expectedData[i-1][j]);//i-1 is to get right data to verify
            }
        }

        delay();

    }

    @AfterTest
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }

}
