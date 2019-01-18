package com.ceiba.estacionamiento.funcionales;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CrearVehiculoTest {

	private static WebDriver driver = null;

	public CrearVehiculoTest() {
	}

	@BeforeClass
	public static void inicializarDriver() {
		System.setProperty("webdriver.chrome.driver", "D:\\jar\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	@Test
	public void comprobarFlujoCorrectoRegistrarVehiculo() {
		driver.get("http://localhost:4200/vehiculo/");

		WebElement placaVehiculo = driver.findElement(By.id("inputPlaca"));
		placaVehiculo.sendKeys("WQE234");
		Select tipoVehiculo = new Select(driver.findElement(By.id("comboTipoVehiculo")));
		tipoVehiculo.selectByVisibleText("MOTO");
		WebElement cilindraje = driver.findElement(By.id("inputCilindraje"));
		cilindraje.sendKeys("800");

		WebElement cmdRegistrar = driver.findElement(By.name("botonRegistrarVehiculo"));
		cmdRegistrar.click();

		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement mensaje = driver.findElement(By.id("mensaje"));
		wait.until(ExpectedConditions.visibilityOf(mensaje));

		Assert.assertEquals(mensaje.getText(), "Vehiculo creado correctamente");

	}

	@AfterClass
	public static void liquidarDriver() {
		driver.quit();
	}

}
