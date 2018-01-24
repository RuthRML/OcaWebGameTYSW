package edu.uclm.esi.tysweb.laoca.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import edu.uclm.esi.tysweb.laoca.dominio.Manager;
import edu.uclm.esi.tysweb.laoca.dominio.Partida;

public class CreacionPartida {
  private WebDriver driver;
  private WebDriver driver2;
  private String baseUrl;
  private boolean acceptNextAlert = true;
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
    
    Thread.sleep(3000);
    
    driver2.get(baseUrl + "/OcaWebGameTYSW/lobby.html");
    driver2.findElement(By.id("nombre")).clear();
    driver2.findElement(By.id("nombre")).sendKeys(jugadorB);
    driver2.findElement(By.id("btnUnirse")).click();
    
    Thread.sleep(3000);
    
    String idPartidaString = driver.findElement(By.id("idPartida")).getText();
    int idPartida = Integer.parseInt(idPartidaString);
    
    Partida partida = Manager.get().getPartidasEnJuego().get(idPartida);
    
    // Comprobar si el jugador A tiene el turno.
    if(!partida.getJugadorConElTurno().getNombre().equals(jugadorA) ) {
    	partida.pasarTurno(false);
    }else {
    	// Turno Jugador A
    	partida.tirarDado(jugadorA, 4); // Origen: 0, Final: 9 (Oca)
    	partida.tirarDado(jugadorA, 3); // Origen: 9, Final: 5 (Puente)
    	partida.tirarDado(jugadorA, 3); // Origen: 5, Final: 13 (Oca)
    	partida.tirarDado(jugadorA, 1); // Origen: 13, Final: 14
    	
    	// Turno Jugador B
    	partida.tirarDado(jugadorB, 1); // Origen: 0, Final: 1
    	
    	// Turno Jugador A
    	partida.tirarDado(jugadorA, 4); // Origen: 14, Final: 18 (Taberna = 3 turnos sin tirar)
    	
    	// Turno Jugador B
    	partida.tirarDado(jugadorB, 4); // Origen: 1, Final: 11 (Puente)
    	partida.tirarDado(jugadorB, 1); // Origen: 11, Final: 12
    	partida.tirarDado(jugadorB, 5); // Origen: 12, Final: 22 (Oca)
    	partida.tirarDado(jugadorB, 3); // Origen: 22, Final: 52 (Dados)
    	partida.tirarDado(jugadorB, 2); // Origen: 52, Final: 54
    	partida.tirarDado(jugadorB, 1); // Origen: 54, Final: 55
    	
    	// Turno Jugador A
    	partida.tirarDado(jugadorA, 4); // Origen: 18, Final: 26 (Oca)
    	partida.tirarDado(jugadorA, 5); // Origen: 26, Final: 35 (Oca)
    	partida.tirarDado(jugadorA, 5); // Origen: 35, Final: 44 (Oca)
    	partida.tirarDado(jugadorA, 5); // Origen: 44, Final: 53 (Oca)
    	partida.tirarDado(jugadorA, 1); // Origen: 53, Final: 54
    	
    	// Turno Jugador B
    	partida.tirarDado(jugadorB, 6); // Origen: 55, Final: 61
    	
    	// Turno Jugador A
    	partida.tirarDado(jugadorA, 1); // Origen: 54, Final: 55
    	
    	// Turno Jugador B, cambiado el dado para poder llegar a meta y pasarse 1
    	partida.tirarDado(jugadorB, 4); // Origen: 61, Final: 63
    	
    	// Turno Jugador A
    	partida.tirarDado(jugadorA, 4); // Origen: 55, Final: 59
    	
    	// Turno Jugador B
    	partida.tirarDado(jugadorB, 1); // Origen: 63, Final: 64 	
    	
    	try {
    		assertEquals("jugadorB", partida.getGanador().getNombre());
    	}catch(Error e) {
    		verificationErrors.append(e.toString());
    	}
    	
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
