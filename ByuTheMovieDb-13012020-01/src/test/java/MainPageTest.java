/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author tmsqu
 */
public class MainPageTest {

    private WebDriver driver;

    public MainPageTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\tmsqu\\eclipse-workspace\\ChomeDriver\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
    }
    
     public void waitForLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
    }

    @Test
    public void testSimple() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
//            driver.get("http://localhost:8888/ByuTheMovieDb-13012020-01/");
//            String textFound = driver.findElement(By.xpath("//*[text()='Robyn McCarthy']")).getText();
//            WebElement queryInput = driver.findElement(By.id("queryText"));
//            queryInput.sendKeys("Jaws");
//            WebElement searchButton = driver.findElement(By.id("searchMoviesBnt"));
//            searchButton.click();
//            waitForLoad(driver);
//            WebElement moviesFound = driver.findElement(By.id("moviesFound"));
//            assertTrue(moviesFound.getText().equals("Total Movies Found: 45"));
//            WebElement pageInfo = driver.findElement(By.id("pageInfo"));
//            assertTrue(pageInfo.getText().equals("Page 1 of 3 Movies on this page: 20"));
        } finally {
            driver.close();
        }
    }

}
