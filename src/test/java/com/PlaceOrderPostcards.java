package com;

import java.io.IOException;
import java.time.Duration;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Util.TestUtil_Products;
import Util.WriteExcel;
import io.github.bonigarcia.wdm.WebDriverManager;

public class PlaceOrderPostcards {
	WebDriver driver;
	WriteExcel obj = new WriteExcel();
	
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
		Object data [][] = TestUtil_Products.getTestData("PostCards");
		return data;
	}
				
	@Test(priority=2, groups= {"Customized"}, dataProvider="Options")
	  public void Customization(String PrintedSide, String Size, String Paper, String UV_Coating, String Quantity, String Design, String HardcopyProof, String ProductionTime, String MailingServices) throws InterruptedException, IOException {
		
		 Thread.sleep(2000);
		 WebElement search = driver.findElement(By.xpath("//input[@id='search-field']"));	
		 search.clear();
		 search.sendKeys("Standard Postcards");
		 Thread.sleep(1500);
		 Actions action = new Actions(driver);
		 action.sendKeys(Keys.ENTER).build().perform();
		 Thread.sleep(2000);
		 WebElement printSide = driver.findElement(By.xpath("//select[@id='colors_postcards']"));
		 if (PrintedSide.equalsIgnoreCase("Color Front and Blank Back")) {
				Select Front = new Select(printSide);
				Front.selectByValue("4/0");
				System.out.println("Selected Color Front and Blank Back");
		}
		else {
				Select Full = new Select(printSide);
				Full.selectByValue("4/4");
				System.out.println("Selected Full Color Both Sides");
		}
		 Thread.sleep(2000);
		 Actions scrolldown= new Actions(driver);
		 scrolldown.sendKeys(Keys.PAGE_DOWN).build().perform();
		 driver.findElement(By.id("size_postcards")).sendKeys(Size);
		 String paper = Paper;
		 driver.findElement(By.id("papertype_postcards")).sendKeys(paper);
		 driver.findElement(By.id("uvcoating1side")).sendKeys(UV_Coating);
		 String Qty = Quantity;
		 driver.findElement(By.id("quantity_500_150000_2500")).sendKeys(String.valueOf(Qty));
		 Thread.sleep(3000);		 
		 //Customization of Design and Proofing
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
				 driver.findElement(By.className("myartwork-txtsearchmedia")).sendKeys(Design);
				 driver.findElement(By.xpath("//a[normalize-space()='3']")).click();
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
			 	driver.findElement(By.className("myartwork-txtsearchmedia")).sendKeys(Design);
			 	Actions add = new Actions(driver);
			 	add.sendKeys(Keys.ENTER).build().perform();
			 	add.sendKeys(Keys.PAGE_DOWN).build().perform();
			 	Thread.sleep(2000);
			 	driver.findElement(By.xpath("//input[@value='Select']")).click();
		}
		 Thread.sleep(3000);
		 driver.findElement(By.xpath("//button[@style='display: block;']")).click();
		 driver.findElement(By.id("proof")).sendKeys(HardcopyProof);
	
		 //Customization of Delivery option
		 
		 driver.findElement(By.id("turnaround1235_recon")).sendKeys(ProductionTime);
		 Thread.sleep(3000);
		 WebElement Cart = driver.findElement(By.xpath("//input[@value='Add to Cart']"));
			if (Qty.equals("50")|| Qty.equals("100")|| Qty.equals("150")|| Qty.equals("200")|| Qty.equals("250")) {
				System.out.println("Mailing is not possible for this quantity");
				Cart.click();
			}
			else if (paper.equalsIgnoreCase("15 pt. Velvet with Soft-Touch")) {
				System.out.println("Mailing is not possible for this Paper");
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
			driver.findElement(By.xpath("(//a[@class='main-action'])[2]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@id='PaymentOption_1']")).click();
			Thread.sleep(1500);
			driver.findElement(By.xpath("//input[@value='Place Order']")).click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.findElement(By.xpath("//img[@id='sa_close']")).click();
			Actions order= new Actions(driver);
			order.sendKeys(Keys.PAGE_DOWN).build().perform();
			String OrderNumber = driver.findElement(By.xpath("//span[@data-bind='text: jobId']")).getText();
			System.out.println(OrderNumber);
			int k=1;
			obj.writeExcel("Postcards", OrderNumber, k, 1);
			k++;
		}
}
	
