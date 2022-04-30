package com;

import java.time.Duration;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Util.TestUtil_Login;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginValidation {
	WebDriver driver;
	
	@BeforeTest
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
		driver.findElement(By.xpath("//button[contains(text(),'No thanks')]")).click();
	    System.out.println("Popup dismissed");
		
	}
	
	@DataProvider
	  public Object[][] InvalidUser() throws InvalidFormatException {
		Object data [][] = TestUtil_Login.getTestData("WrongAuthentication");
		return data;
	}
	
	@Test(priority=2,  dataProvider = "InvalidUser")
	  public void VerifyLoginErrorPage(String Username, String Password) throws InterruptedException    { 
		 driver.findElement(By.id("login-dropdown")).click();
		 driver.findElement(By.id("UserName")).sendKeys(Username);
		 driver.findElement(By.id("Password")).sendKeys(Password);
		 driver.findElement(By.className("sign-in")).click();
		 WebElement errorMessage= driver.findElement(By.id("form-error"));
		 Assert.assertTrue(errorMessage.isDisplayed());	
		 System.out.println("Error message displayed while logging in with invalid credential");
		
	 }
	
	@DataProvider

	  public Object[][] ValidUser() throws InvalidFormatException {
		Object data [][] = TestUtil_Login.getTestData("Login");
		return data;
	}
	 
	@Test(priority=3, dataProvider="ValidUser")
	  public void VerifyLoginPage(String Username, String Password) throws InterruptedException  {
		 driver.get("https://www.nonprod-psprint.com/");
		 driver.findElement(By.id("login-dropdown")).click();
		 driver.findElement(By.id("UserName")).sendKeys(Username);
		 driver.findElement(By.id("Password")).sendKeys(Password);
		 driver.findElement(By.className("sign-in")).click();
		 Thread.sleep(2000);
		 String expectedUser = "Hello, Brady";
		 String Customername = driver.findElement(By.xpath("//div[@class='user-name']")).getText();
		 Assert.assertEquals(Customername, expectedUser);
		 System.out.println("Logged in successfully with valid credential");
	}	 
	
	@AfterTest
	public void setDown() {
		driver.close();
		driver.quit();
		System.out.println("Closing the browser session");
		
	}
	
}
