package com;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase  {
	WebDriver driver;

	@BeforeTest
		public void setUp() {
			System.out.println("Starting the browser session");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();			
		}
	@Test(priority = 1, dataProvider="wrong credentials")
	  public void VerifyLoginErrorPage(String scenario, String username, String password)    { 
		 driver.get("https://www.nonprod-psprint.com/");
		 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		 String expectedTitle ="PsPrint | Top Quality, Dependable Online Printing Services";
		 String actualTitle = driver.getTitle();
		 Assert.assertEquals(actualTitle, expectedTitle);
		 driver.findElement(By.xpath("//button[contains(text(),'No thanks')]")).click();
	     System.out.println("popup close");
		 driver.findElement(By.id("login-dropdown")).click();
		 driver.findElement(By.id("UserName")).sendKeys(username);
		 driver.findElement(By.id("Password")).sendKeys(password);
		 driver.findElement(By.className("sign-in")).click();
		 WebElement errorMessage= driver.findElement(By.id("form-error"));
		 Assert.assertTrue(errorMessage.isDisplayed());	

	  }
	 
	 @Test(priority = 2, dataProvider="credentials")
	  public void VerifyLoginPage(String scenario, String username, String password) {
		 driver.get("https://www.nonprod-psprint.com/");
		 driver.findElement(By.id("login-dropdown")).click();
		 driver.findElement(By.id("UserName")).sendKeys(username);
		 driver.findElement(By.id("Password")).sendKeys(password);
		 driver.findElement(By.className("sign-in")).click();
		 WebElement account= driver.findElement(By.cssSelector(".myaccount-content.myaccount-content-main"));
		 Assert.assertTrue(account.isDisplayed());	
 
	  }
	 
	 @DataProvider(name="credentials")
	 public Object[][] getData(){
		 return new Object[][] {
			 {"Correct credential","cmp_uat@psptesting.com","Bric@2018"}
		 };	 
	 }
	 
	 @DataProvider(name="wrong credentials")
	 public Object[][] getData2(){
		 return new Object[][] {
			 {"Wrong credential","cmp@psptesting.com","Bric@2018"}
		 };	 
	 }
	 
	 @Test(priority = 3)
	  public void SelectProduct() throws InterruptedException {
		 WebElement Search = driver.findElement(By.id("search-field"));
		 Search.sendKeys("Greeting cards");
		 Thread.sleep(1500);
		 Actions action = new Actions(driver);
		 action.sendKeys(Keys.ENTER).build().perform();
	 } 
	 
	 @Test(priority = 4)
	  public void Customization() throws InterruptedException {
		WebElement PrintedSide = driver.findElement(By.id("colors_inside_outside"));
		Select dropdown1 = new Select(PrintedSide);
		dropdown1.selectByIndex(1);	
		
		Thread.sleep(1000);
		Actions scrldown= new Actions(driver);
		scrldown.sendKeys(Keys.PAGE_DOWN).build().perform();
		
		WebElement Paper = driver.findElement(By.id("papertype_greeting_cards"));
		Select dropdown2 = new Select(Paper);
		dropdown2.selectByIndex(4);
		
		WebElement Size = driver.findElement(By.id("size_greetingcards"));
		Select dropdown3 = new Select(Size);
		dropdown3.selectByIndex(2);
		
		WebElement Envelopes = driver.findElement(By.id("unprinted_envelopes"));
		Select dropdown4 = new Select(Envelopes);
		dropdown4.selectByIndex(0);
		
		WebElement Folding = driver.findElement(By.id("folding"));
		Select dropdown5 = new Select(Folding);
		dropdown5.selectByIndex(1);
		 
		WebElement Quantity = driver.findElement(By.id("quantity_50_10000"));
		Select dropdown6 = new Select(Quantity);
		dropdown6.selectByIndex(5);
	 }

	 @Test(priority = 5)
	 public void DesignAndSpoofing() throws InterruptedException  {
		
	
		 Thread.sleep(1500);
		 driver.findElement(By.xpath("(//a[@data-bind='click: showMyFiles'][normalize-space()='Select from My Files'])[1]")).click();
		 driver.findElement(By.className("myartwork-txtsearchmedia")).clear();
		 driver.findElement(By.className("myartwork-txtsearchmedia")).sendKeys("greeting_card_7x10.pdf");
		 Thread.sleep(1000);
		 Actions action = new Actions(driver);
		 action.sendKeys(Keys.ENTER).build().perform();
		 Thread.sleep(1000);
		 Actions pagedown= new Actions(driver);
		 pagedown.sendKeys(Keys.PAGE_DOWN).build().perform();

		 Thread.sleep(2000);
		 driver.findElement(By.className("myartwork-selectbutton")).click();
		 Thread.sleep(2000);
		 driver.findElement(By.xpath("//button[@style='display: block;']")).click();
		 
		 WebElement Proof = driver.findElement(By.id("proof"));
		 Select dropdown7 = new Select(Proof);
		 dropdown7.selectByIndex(1);
	 }
	 
	 
	 @Test(priority = 6)
	 public void DeliveryOption() throws InterruptedException {
		 WebElement ProductionTime = driver.findElement(By.id("turnaround1235_recon"));
		 Select dropdown8 = new Select(ProductionTime);
		 dropdown8.selectByIndex(1);
		 Thread.sleep(3000);
		 Actions mailOption= new Actions(driver);
		 mailOption.sendKeys(Keys.PAGE_DOWN).build().perform();
		 driver.findElement(By.xpath("//input[@id='mail-delivery']")).click();
		 WebElement MailingService = driver.findElement(By.id("mail"));
		 Select dropdown9 = new Select(MailingService);
		 dropdown9.selectByIndex(1);
	 } 
	 @Test(priority = 6)
	 public void AddToCart() throws InterruptedException {
		 Thread.sleep(3000);
		 driver.findElement(By.xpath("(//input[@value='Add to Cart'])[1]")).click();
		 Thread.sleep(3000);
		 driver.findElement(By.xpath("(//a[@class='main-action'][normalize-space()='Checkout'])[1]")).click();
	 }
	 
	 
	 @AfterTest
		public void setDown() {
		  System.out.println("Successfully implemented");
		  
	  }
	}
