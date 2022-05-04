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

public class PlaceOrderRollSticker {
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
		Object data [][] = TestUtil_Products.getTestData("RollStickers");
		return data;
	}
	
	@Test( priority = 2,  dataProvider="Options")
	  public void Customization(String Row, String Product, String Shape_1, String Size, String Paper, String Unwind_1, String Quantity, String Design, String Turnaround) throws InterruptedException, IOException {
		 int row = Integer.valueOf(Row);
		 objrepo.SelectProduct(Product);
		 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		 WebElement Rectangle = driver.findElement(By.xpath("//label[@title='Rectangle 1/8\" Round Corners']"));
		 WebElement Square = driver.findElement(By.xpath("//label[@title='Square 1/8\" Round Corners']"));
		 WebElement Oval = driver.findElement(By.xpath("//label[@title='Oval']"));
		 WebElement Circle = driver.findElement(By.xpath("//label[@title='Circle']"));
		 WebElement Scalloped_Edge = driver.findElement(By.xpath("//label[@title='Scalloped Edge']"));
		 WebElement Wavy_Edges = driver.findElement(By.xpath("//label[@title='Wavy Edges']"));
		 WebElement Heart = driver.findElement(By.xpath("//label[@title='Heart']"));
		 WebElement Starburst = driver.findElement(By.xpath("//label[@title='Starburst']"));
		 String Shape = Shape_1;
		 if(Shape.equalsIgnoreCase("Rectangle"))
			 Rectangle.click();
		 else if(Shape.equalsIgnoreCase("Square"))
			 Square.click();
		 else if(Shape.equalsIgnoreCase("Oval"))
			 Oval.click();
		 else if(Shape.equalsIgnoreCase("Circle"))
			 Circle.click();
		 else if(Shape.equalsIgnoreCase("Scalloped_Edge"))
			 Scalloped_Edge.click();
		 else if(Shape.equalsIgnoreCase("Wavy_Edges"))
			 Wavy_Edges.click();
		 else if(Shape.equalsIgnoreCase("Heart"))
			 Heart.click();
		 else if(Shape.equalsIgnoreCase("Circle"))
			 Circle.click();
		 else 
			 Starburst.click();
		 Actions scrolldown= new Actions(driver);
		 scrolldown.sendKeys(Keys.PAGE_DOWN).build().perform();
		 driver.findElement(By.id("size_roll_sticker")).sendKeys(Size);
		 driver.findElement(By.id("paper_roll_sticker")).sendKeys(Paper);
		 WebElement Bottom = driver.findElement(By.xpath("//label[@title='Bottom of Copy']"));
		 WebElement Top = driver.findElement(By.xpath("//label[@title='Top of Copy']"));
		 WebElement Right = driver.findElement(By.xpath("//label[@title='Right of Copy']"));
		 WebElement Left = driver.findElement(By.xpath("//label[@title='Left of Copy']"));
		 String Unwind = Unwind_1;
		 if(Unwind.equalsIgnoreCase("Bottom"))
			 Bottom.click();
		 else if(Unwind.equalsIgnoreCase("Top"))
			 Top.click();
		 else if(Unwind.equalsIgnoreCase("Right"))
			 Right.click();
		 else 
			 Left.click();	 
		 WebElement Qty1 = driver.findElement(By.id("quantity_roll_sticker"));
		 Select Qty= new Select(Qty1);
		 Qty.selectByValue(String.valueOf(Quantity));
		 		 
		 //Customization of Design and Proofing
		 
		 driver.findElement(By.xpath("(//a[@data-bind='click: showMyFiles'][normalize-space()='Select from My Files'])[1]")).click();
		 driver.findElement(By.className("myartwork-txtsearchmedia")).clear();
		 driver.findElement(By.className("myartwork-txtsearchmedia")).sendKeys(Design);
		 Actions add = new Actions(driver);
		 add.sendKeys(Keys.ENTER).build().perform();
		 add.sendKeys(Keys.PAGE_DOWN).build().perform();
		 Thread.sleep(2000);
		 driver.findElement(By.className("myartwork-selectbutton")).click();	 	 	

		 //Customization of Delivery option
		 
		 driver.findElement(By.id("turnaround1235_recon")).sendKeys(Turnaround);
		 Thread.sleep(2000);
		 Actions mailOption= new Actions(driver);
		 mailOption.sendKeys(Keys.PAGE_DOWN).build().perform();	
		 driver.findElement(By.xpath("//input[@value='Add to Cart']")).click();
		 objrepo.CheckoutAndPayment();	
		 Thread.sleep(2000);
		 Actions order= new Actions(driver);
		 order.sendKeys(Keys.PAGE_DOWN).build().perform();
		 String OrderNumber = driver.findElement(By.xpath("//span[@data-bind='text: jobId']")).getText();
		 System.out.println(OrderNumber);
		 obj.writeExcel("RollStickers", OrderNumber, row, 2);
	}
	
	@AfterTest
	public void setDown() {
		driver.quit();		
	}
	
}
	