package edu.uclm.esi.tysweb.laoca.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class CreacionPartida {
  private WebDriver driver;
  private WebDriver driver2;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver(); //A
    driver2 = new FirefoxDriver(); //B
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver2.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    
    
  }

  @Test
  public void testCreacionPartida() throws Exception {
    driver.get(baseUrl + "/OcaWebGameTYSW/lobby.html");
    driver.findElement(By.id("nombre")).clear();
    driver.findElement(By.id("nombre")).sendKeys("Prueba0");
    driver.findElement(By.id("numero")).clear();
    driver.findElement(By.id("numero")).sendKeys("2");
    driver.findElement(By.id("btnCrearPartida")).click();
    
    Thread.sleep(3000);
    
    driver2.get(baseUrl + "/OcaWebGameTYSW/lobby.html");
    driver2.findElement(By.id("nombre")).clear();
    driver2.findElement(By.id("nombre")).sendKeys("Prueba1");
    driver2.findElement(By.id("btnUnirse")).click();
    
    if(driver.findElement(By.id("btnDado")).isEnabled() == false) {
    		
    }
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
