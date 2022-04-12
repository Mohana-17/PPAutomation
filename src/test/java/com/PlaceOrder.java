package com;

import java.time.Duration;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Util.TestUtil;
import io.github.bonigarcia.wdm.WebDriverManager;

public class PlaceOrder {
	WebDriver driver;
	@BeforeClass
	public void setUp() {
		System.out.println("Starting the browser session");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
	@Test(priority=1)
	public void VerifyLaunchApplication() {
	
		driver.get("https://www.nonprod-psprint.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String expectedTitle ="PsPrint | Top Quality, Dependable Online Printing Services";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
		System.out.println("Application launched successfully");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.findElement(By.xpath("//button[contains(text(),'No thanks')]")).click();
	    System.out.println("popup closed");
		
	}
	@DataProvider

	  public Object[][] InvalidUser() throws InvalidFormatException {
		Object data [][] = TestUtil.getTestData("WrongAuthentication");
		return data;
	}
	@Test(priority=2, dataProvider = "InvalidUser")
	  public void VerifyLoginErrorPage(String Username, String Password) throws InterruptedException    { 
		 driver.findElement(By.id("login-dropdown")).click();
		 driver.findElement(By.id("UserName")).sendKeys(Username);
		 driver.findElement(By.id("Password")).sendKeys(Password);
		 driver.findElement(By.className("sign-in")).click();
		 WebElement errorMessage= driver.findElement(By.id("form-error"));
		 Assert.assertTrue(errorMessage.isDisplayed());	
		
	  }
	@DataProvider

	  public Object[][] ValidUser() throws InvalidFormatException {
		Object data [][] = TestUtil.getTestData("Login");
		return data;
	}
	 
	@Test(priority=3, dataProvider="ValidUser")
	  public void VerifyLoginPage(String Username, String Password)  {
		 driver.get("https://www.nonprod-psprint.com/");
		 driver.findElement(By.id("login-dropdown")).click();
		 driver.findElement(By.id("UserName")).sendKeys(Username);
		 driver.findElement(By.id("Password")).sendKeys(Password);
		 driver.findElement(By.className("sign-in")).click();
		 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	  }
	
	@DataProvider
	public Object[][] ProductType() throws InvalidFormatException {
		Object data [][] = TestUtil.getTestData("Product");
		return data;

	}
	@Test(priority=4, dataProvider="ProductType")
	  public void SelectProduct(String ProductType) throws InterruptedException {
		Thread.sleep(10000);
		 WebElement Search = driver.findElement(By.xpath("//input[@id='search-field']"));
		 Search.clear();
		 Search.sendKeys(ProductType);
		 try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 Thread.sleep(3000);
		 Actions action = new Actions(driver);
		 action.sendKeys(Keys.ENTER).build().perform();
		 System.out.println("Product selected successfully");
	} 
	
	@DataProvider
	public Object[][] Options() throws InvalidFormatException {
		Object data [][] = TestUtil.getTestData("Customization");
		return data;

	}
	
	@Test(priority=5, dataProvider="Options")
	  public void Customization(String PrintedSide, String Paper, String Size, String Envelopes, String Folding, String Quantity, String DesignFile, String HardcopyProof, String ProductionTime, String MailingServices) throws InterruptedException {
		 
		//Customization of product option
		 
		 driver.findElement(By.id("colors_inside_outside")).sendKeys(PrintedSide);
		 Thread.sleep(1000);
		 Actions scrolldown= new Actions(driver);
		 scrolldown.sendKeys(Keys.PAGE_DOWN).build().perform();
		 driver.findElement(By.id("papertype_greeting_cards")).sendKeys(Paper);
		 driver.findElement(By.id("size_greetingcards")).sendKeys(Size);
		 driver.findElement(By.id("unprinted_envelopes")).sendKeys(Envelopes);
		 driver.findElement(By.id("folding")).sendKeys(Folding);
		 driver.findElement(By.id("quantity_50_10000")).sendKeys(Quantity);
		 Thread.sleep(3000);
		 
		 //Customization of Design and Proofing
		 
		 driver.findElement(By.xpath("(//a[@data-bind='click: showMyFiles'][normalize-space()='Select from My Files'])[1]")).click();
		 driver.findElement(By.className("myartwork-txtsearchmedia")).clear();
		 driver.findElement(By.className("myartwork-txtsearchmedia")).sendKeys(DesignFile);
		 Thread.sleep(1000);
		 Actions add = new Actions(driver);
		 add.sendKeys(Keys.ENTER).build().perform();
		 Thread.sleep(1000);
		 add.sendKeys(Keys.PAGE_DOWN).build().perform();
		 Thread.sleep(2000);
		 driver.findElement(By.className("myartwork-selectbutton")).click();
		 Thread.sleep(2000);
		 driver.findElement(By.xpath("//button[@style='display: block;']")).click();
		 driver.findElement(By.id("proof")).sendKeys(HardcopyProof);	
		 
		 //Customization of Delivery option
		 
		 driver.findElement(By.id("turnaround1235_recon")).sendKeys(ProductionTime);
		 Thread.sleep(3000);
		 Actions mailOption= new Actions(driver);
		 mailOption.sendKeys(Keys.PAGE_DOWN).build().perform();
		 driver.findElement(By.xpath("//input[@id='mail-delivery']")).click();
		 driver.findElement(By.id("mail")).sendKeys(MailingServices);		 
	 } 
	
	@Test(priority = 6)
	 public void AddToCart() throws InterruptedException {
		 Thread.sleep(3000);
		 driver.findElement(By.xpath("(//input[@value='Add to Cart'])[1]")).click();
		 Thread.sleep(3000);
		 driver.findElement(By.xpath("(//a[@class='main-action'][normalize-space()='Checkout'])[1]")).click();
	 }	
}
	