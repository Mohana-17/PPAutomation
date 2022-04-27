package com;

import java.io.IOException;
import java.time.Duration;
import java.util.Calendar;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Util.TestUtil_Products;
import Util.WriteExcel;
import io.github.bonigarcia.wdm.WebDriverManager;

public class PlaceOrderGreetingCards {
	WebDriver driver;
	WriteExcel obj = new WriteExcel();
//	ExtentReports extentReport;
//	ExtentSparkReporter spark;
//	ExtentTest extentTest;
//	@BeforeTest
//	public void setupExtent() {
//		extentReport = new ExtentReports();
//		spark = new ExtentSparkReporter("target/SparkReport.html");
//		extentReport.attachReporter(spark);
//	}
	
	@BeforeClass
	public void setUp() {
		System.out.println("Starting the browser session");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
	
	@DataProvider
	  public Object[][] ValidUser() throws InvalidFormatException {
		Object data [][] = TestUtil_Products.getTestData("Login");
		return data;
	}
	
	@Test(priority=1, dataProvider="ValidUser")
	  public void VerifyLoginPage(String Username, String Password)  {
		 driver.get("https://www.nonprod-psprint.com/");
		 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		 driver.findElement(By.xpath("//button[contains(text(),'No thanks')]")).click();
		 driver.findElement(By.id("login-dropdown")).click();
		 driver.findElement(By.id("UserName")).sendKeys(Username);
		 driver.findElement(By.id("Password")).sendKeys(Password);
		 driver.findElement(By.className("sign-in")).click();
	  }
		
	@DataProvider
	public Object[][] Options() throws InvalidFormatException {
		Object data [][] = TestUtil_Products.getTestData("GreetingCards");
		return data;
	}
	
	@Test(priority = 2, groups= {"Customized"}, dataProvider="Options")
	  public void Customization(String PrintedSide, String Paper, String Size, String Envelopes, String Folding, String Quantity, String DesignFile, String HardcopyProof, String ProductionTime, String MailingServices, String Holder, String CardType, String CardNumber, String Cvv) throws InterruptedException, IOException {
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("search-field")));
		Thread.sleep(2000);
		WebElement search = driver.findElement(By.xpath("//input[@id='search-field']"));	
		search.clear();
		search.sendKeys("Greeting Cards");
		Thread.sleep(1500);
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ENTER).build().perform();
		System.out.println("Product selected successfully");
		Thread.sleep(1500);
		WebElement printSide = driver.findElement(By.xpath("//select[@id='colors_inside_outside']"));
		if (PrintedSide.equalsIgnoreCase("Full Color Both Sides")) {
			Select Front = new Select(printSide);
			Front.selectByIndex(1);
			System.out.println("Selected Full Color Both Sides");
		}
		else {
			Select Full = new Select(printSide);
			Full.selectByIndex(0);
			System.out.println("Selected Color Front and Blank Back");
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		Actions scrolldown= new Actions(driver);
		scrolldown.sendKeys(Keys.PAGE_DOWN).build().perform();
		String paper = Paper;
		driver.findElement(By.id("papertype_greeting_cards")).sendKeys(paper);
		driver.findElement(By.id("size_greetingcards")).sendKeys(Size);
		driver.findElement(By.id("unprinted_envelopes")).sendKeys(Envelopes);
		driver.findElement(By.id("folding")).sendKeys(Folding);
		String Qty = Quantity;
		driver.findElement(By.id("quantity_50_10000")).sendKeys(Quantity);
		if(PrintedSide.equalsIgnoreCase("Full Color Both Sides")) {
			 for(int i=0; i<2; i++) {
				 if(i==0) {
					 driver.findElement(By.xpath("(//a[@data-bind='click: showMyFiles'][normalize-space()='Select from My Files'])[1]")).click();
				 }
				 else {
					 Thread.sleep(3000);
					 driver.findElement(By.xpath("(//a[@data-bind='click: showMyFiles'][normalize-space()='Select from My Files'])[2]")).click();
				 }
				 driver.findElement(By.className("myartwork-txtsearchmedia")).clear();
				 driver.findElement(By.className("myartwork-txtsearchmedia")).sendKeys(DesignFile);
				 Actions add = new Actions(driver);
				 add.sendKeys(Keys.ENTER).build().perform();
				 add.sendKeys(Keys.PAGE_DOWN).build().perform();
				 Thread.sleep(2000);
				 driver.findElement(By.xpath("//input[@value='Select']")).click();
			 }
			 
		 }
		 else {
			 	driver.findElement(By.xpath("(//a[@data-bind='click: showMyFiles'][normalize-space()='Select from My Files'])[1]")).click();
			 	driver.findElement(By.className("myartwork-txtsearchmedia")).clear();
			 	driver.findElement(By.className("myartwork-txtsearchmedia")).sendKeys(DesignFile);
			 	driver.findElement(By.xpath("//a[normalize-space()='3']")).click();
			 	Actions add = new Actions(driver);
			 	add.sendKeys(Keys.ENTER).build().perform();
			 	add.sendKeys(Keys.PAGE_DOWN).build().perform();
			 	Thread.sleep(2000);
			 	driver.findElement(By.xpath("//input[@value='Select']")).click();
		}
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[@style='display: block;']")).click();
		driver.findElement(By.id("proof")).sendKeys(HardcopyProof);
		driver.findElement(By.id("turnaround1235_recon")).sendKeys(ProductionTime);
		Thread.sleep(3000);
		WebElement Cart = driver.findElement(By.xpath("//input[@value='Add to Cart']"));
			if (Qty.equals("50")|| Qty.equals("100")|| Qty.equals("150")|| Qty.equals("200")|| Qty.equals("250")) {
				Cart.click();
			}
			else if (paper.equalsIgnoreCase("15 pt. Velvet with Soft-Touch")) {
				Cart.click();
			}
			else {
				 Actions mailOption= new Actions(driver);
				 mailOption.sendKeys(Keys.PAGE_DOWN).build().perform();
				 Thread.sleep(2000);
				 driver.findElement(By.xpath("//input[@id='mail-delivery']")).click();
				 driver.findElement(By.id("mail")).sendKeys(MailingServices);
				 Cart.click();
				 Actions Nxtbtn= new Actions(driver);
				 Nxtbtn.sendKeys(Keys.PAGE_DOWN).build().perform();
				 driver.findElement(By.xpath("//input[@value='Next']")).click();
			}
			Thread.sleep(1500);
			driver.findElement(By.xpath("(//a[contains(text(),'Checkout')])[1]")).click();
			Thread.sleep(1500);
			driver.findElement(By.xpath("//label[normalize-space()='Credit Card']")).click();
			driver.findElement(By.xpath("//input[@placeholder='Card holder Name']")).sendKeys(Holder);
			WebElement cardType = driver.findElement(By.id("PaymentWidget-card-type"));
			Select card = new Select(cardType);
			card.selectByValue(CardType);
			driver.switchTo().frame("braintree-hosted-field-number");
			driver.findElement(By.id("credit-card-number")).sendKeys(CardNumber);
			driver.switchTo().defaultContent();
			driver.switchTo().frame("braintree-hosted-field-expirationMonth");
			int ExpiryMonth = Calendar.getInstance().get(Calendar.MONTH);
			WebElement ExpMon= driver.findElement(By.id("expiration-month"));
			Select Month= new Select(ExpMon);
			Month.selectByValue(String.valueOf(ExpiryMonth));
			driver.switchTo().defaultContent();
			driver.switchTo().frame("braintree-hosted-field-expirationYear");
			int year = Calendar.getInstance().get(Calendar.YEAR);
			int ExpiryYear = year + 3;
			WebElement Expyear= driver.findElement(By.id("expiration-year"));
			Select Year= new Select(Expyear);
			Year.selectByValue(String.valueOf(ExpiryYear));
			driver.switchTo().defaultContent();
			driver.switchTo().frame("braintree-hosted-field-cvv");
			driver.findElement(By.id("cvv")).sendKeys(Cvv);
			driver.switchTo().defaultContent();
			driver.findElement(By.xpath("//input[@value='Place Order']")).click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.findElement(By.xpath("//img[@id='sa_close']")).click();
			Actions order= new Actions(driver);
			order.sendKeys(Keys.PAGE_DOWN).build().perform();
			String OrderNumber = driver.findElement(By.xpath("//span[@data-bind='text: jobId']")).getText();
			System.out.println(OrderNumber);
			int k=1;
			obj.writeExcel("Greetingcards", OrderNumber, k, 1);
			k++;
		}
	} 
	

//	@AfterTest
//	public void FinishExtent() {
//		extentReport.flush();
//	}


	