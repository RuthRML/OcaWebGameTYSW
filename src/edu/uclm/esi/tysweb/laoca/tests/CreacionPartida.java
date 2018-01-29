package edu.uclm.esi.tysweb.laoca.tests;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class CreacionPartida {
  private WebDriver driver;
  private WebDriver driver2;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();
  private String jugadorA = "Prueba0";
  private String jugadorB = "Prueba1";

  @Before
  public void setUp() throws Exception {
	  
	System.setProperty("webdriver.chrome.driver", "chromedriver_win32/chromedriver.exe");
	driver = new ChromeDriver(); // Jugador A
	driver2 = new ChromeDriver(); // Jugador B
    baseUrl = "http://localhost:8080";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver2.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    
  }

  @Test
  public void testCreacionPartida() throws Exception {
    driver.get(baseUrl + "/OcaWebGameTYSW/lobby.html");
    driver.findElement(By.id("nombre")).clear();
    driver.findElement(By.id("nombre")).sendKeys(jugadorA);
    driver.findElement(By.id("numero")).clear();
    driver.findElement(By.id("numero")).sendKeys("2");
    driver.findElement(By.id("btnCrearPartida")).click();
    
    Thread.sleep(2000);
    
    driver2.get(baseUrl + "/OcaWebGameTYSW/lobby.html");
    driver2.findElement(By.id("nombre")).clear();
    driver2.findElement(By.id("nombre")).sendKeys(jugadorB);
    driver2.findElement(By.id("btnUnirse")).click();
    
    Thread.sleep(2000);
	    
	// Turno Jugador A
    driver.findElement(By.id("numeroDado")).clear();
    driver.findElement(By.id("numeroDado")).sendKeys("4"); // Origen: 0, Final: 9 (Oca)
    driver.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
    
    driver.findElement(By.id("numeroDado")).clear();
    driver.findElement(By.id("numeroDado")).sendKeys("3"); // Origen: 9, Final: 5 (Puente)
    driver.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
    
    driver.findElement(By.id("numeroDado")).clear();
    driver.findElement(By.id("numeroDado")).sendKeys("3"); // Origen: 5, Final: 13 (Oca)
    driver.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
    
    driver.findElement(By.id("numeroDado")).clear();
    driver.findElement(By.id("numeroDado")).sendKeys("1"); // Origen: 13, Final: 14
    driver.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
	    	
	// Turno Jugador B
    driver2.findElement(By.id("numeroDado")).clear();
    driver2.findElement(By.id("numeroDado")).sendKeys("1"); // Origen: 0, Final: 1
    driver2.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
	    	
	// Turno Jugador A
    driver.findElement(By.id("numeroDado")).clear();
    driver.findElement(By.id("numeroDado")).sendKeys("4"); // Origen: 14, Final: 18 (Taberna = 3 turnos sin tirar)
    driver.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
	    	
	// Turno Jugador B
    driver2.findElement(By.id("numeroDado")).clear();
    driver2.findElement(By.id("numeroDado")).sendKeys("4"); // Origen: 1, Final: 11 (Puente)
    driver2.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
    
    driver2.findElement(By.id("numeroDado")).clear();
    driver2.findElement(By.id("numeroDado")).sendKeys("1"); // Origen: 11, Final: 12
    driver2.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
    
    driver2.findElement(By.id("numeroDado")).clear();
    driver2.findElement(By.id("numeroDado")).sendKeys("5"); // Origen: 12, Final: 22 (Oca)
    driver2.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
    
    driver2.findElement(By.id("numeroDado")).clear();
    driver2.findElement(By.id("numeroDado")).sendKeys("3"); // Origen: 22, Final: 52 (Dados)
    driver2.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
    
    driver2.findElement(By.id("numeroDado")).clear();
    driver2.findElement(By.id("numeroDado")).sendKeys("2"); // Origen: 52, Final: 54
    driver2.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
    
    driver2.findElement(By.id("numeroDado")).clear();
    driver2.findElement(By.id("numeroDado")).sendKeys("1"); // Origen: 54, Final: 55
    driver2.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
	    	
	// Turno Jugador A
    driver.findElement(By.id("numeroDado")).clear();
    driver.findElement(By.id("numeroDado")).sendKeys("4"); // Origen: 18, Final: 26 (Oca)
    driver.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
    
    driver.findElement(By.id("numeroDado")).clear();
    driver.findElement(By.id("numeroDado")).sendKeys("5"); // Origen: 26, Final: 35 (Oca)
    driver.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
    
    driver.findElement(By.id("numeroDado")).clear();
    driver.findElement(By.id("numeroDado")).sendKeys("5"); // Origen: 35, Final: 44 (Oca)
    driver.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
    
    driver.findElement(By.id("numeroDado")).clear();
    driver.findElement(By.id("numeroDado")).sendKeys("5"); // Origen: 44, Final: 53 (Oca)
    driver.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
    
    driver.findElement(By.id("numeroDado")).clear();
    driver.findElement(By.id("numeroDado")).sendKeys("1"); // Origen: 53, Final: 54
    driver.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
	    	
	// Turno Jugador B
    driver2.findElement(By.id("numeroDado")).clear();
    driver2.findElement(By.id("numeroDado")).sendKeys("6"); // Origen: 55, Final: 61
    driver2.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
	    	
	// Turno Jugador A
    driver.findElement(By.id("numeroDado")).clear();
    driver.findElement(By.id("numeroDado")).sendKeys("1"); // Origen: 54, Final: 55
    driver.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
	    	
	// Turno Jugador B, cambiado el número de dado para poder llegar a meta (que es diferente) y pasarse 1
    driver2.findElement(By.id("numeroDado")).clear();
    driver2.findElement(By.id("numeroDado")).sendKeys("4"); // Origen: 61, Final: 63
    driver2.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
	
	// Turno Jugador A
    driver.findElement(By.id("numeroDado")).clear();
    driver.findElement(By.id("numeroDado")).sendKeys("4"); // Origen: 55, Final: 59
    driver.findElement(By.id("btnDado")).click();
    
    Thread.sleep(2000);
	
	// Turno Jugador B
    driver2.findElement(By.id("numeroDado")).clear();
    driver2.findElement(By.id("numeroDado")).sendKeys("1"); // Origen: 63, Final: 64 (Meta)
    driver2.findElement(By.id("btnDado")).click();
	
	String ganador = driver.findElement(By.id("jugadorGanador")).getText();
	    	
	try {
		assertEquals(jugadorB, ganador);
	}catch(Error e) {
		verificationErrors.append(e.toString());
	} 
    
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    driver2.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}
