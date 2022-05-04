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
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Objects.BaseTest;
import Util.TestUtil_Products;
import Util.WriteExcel;
import io.github.bonigarcia.wdm.WebDriverManager;

public class PlaceOrderPostcards {
	WebDriver driver;
	WriteExcel obj = new WriteExcel();
	BaseTest objrepo;
	
	@BeforeTest
	public void setUp() {
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
		objrepo = new BaseTest(driver);
		objrepo.login(Username, Password);
	  }
	
	@DataProvider
	public Object[][] Options() throws InvalidFormatException {
		Object data [][] = TestUtil_Products.getTestData("PostCards");
		return data;
	}
				
	@Test(priority=2, dataProvider="Options")
	  public void Customization(String Row, String Product, String PrintedSide, String Size, String Paper, String UV_Coating, String Quantity, String DesignFile, String HardcopyProof, String ProductionTime, String DeliveryMethod, String MailingServices) throws InterruptedException, IOException {
		 int row = Integer.valueOf(Row);
		 objrepo.SelectProduct(Product);
		 WebElement printSide = driver.findElement(By.xpath("//select[@id='colors_postcards']"));
		 if (PrintedSide.equalsIgnoreCase("Color Front and Blank Back")) {
				Select Front = new Select(printSide);
				Front.selectByIndex(1);
		}
		else {
				Select Full = new Select(printSide);
				Full.selectByIndex(0);
		}
		 Thread.sleep(2000);
		 Actions scrolldown= new Actions(driver);
		 scrolldown.sendKeys(Keys.PAGE_DOWN).build().perform();
		 driver.findElement(By.id("size_postcards")).sendKeys(Size);
		 String PaperType = Paper;
		 driver.findElement(By.id("papertype_postcards")).sendKeys(PaperType);
		 driver.findElement(By.id("uvcoating1side")).sendKeys(UV_Coating);
		 String Qty = Quantity;
		 driver.findElement(By.id("quantity_500_150000_2500")).sendKeys(String.valueOf(Qty));
		 objrepo.Design(PrintedSide, DesignFile);		 
		 driver.findElement(By.id("proof")).sendKeys(HardcopyProof);
		 driver.findElement(By.id("turnaround1235_recon")).sendKeys(ProductionTime);
		 String Delivery= DeliveryMethod;
		 String Services= MailingServices;
		 objrepo.AddToCart(Qty, PaperType, Delivery,Services);
		 objrepo.CheckoutAndPayment();	
		 Thread.sleep(2000);
		 Actions order= new Actions(driver);
		 order.sendKeys(Keys.PAGE_DOWN).build().perform();
		 String OrderNumber = driver.findElement(By.xpath("//span[@data-bind='text: jobId']")).getText();
		 System.out.println(OrderNumber);
		 obj.writeExcel("PostCards", OrderNumber, row, 2);			
	}
	
	@AfterTest
	public void setDown() {
		driver.quit();		
	}
}
	
