package com;

import java.time.Duration;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Util.TestUtil_Login;
import io.github.bonigarcia.wdm.WebDriverManager;


public class LoginValidation {
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
		driver.findElement(By.xpath("//button[contains(text(),'No thanks')]")).click();
	    System.out.println("popup closed");
		
	}
	
	@DataProvider
	  public Object[][] InvalidUser() throws InvalidFormatException {
		Object data [][] = TestUtil_Login.getTestData("WrongAuthentication");
		return data;
	}
	
	@Test(priority=2, groups= {"InvalidLogin"},dataProvider = "InvalidUser")
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
		Object data [][] = TestUtil_Login.getTestData("Login");
		return data;
	}
	 
	@Test(priority=3, groups= {"ValidLogin"}, dataProvider="ValidUser")
	  public void VerifyLoginPage(String Username, String Password)  {
		 driver.get("https://www.nonprod-psprint.com/");
		 driver.findElement(By.id("login-dropdown")).click();
		 driver.findElement(By.id("UserName")).sendKeys(Username);
		 driver.findElement(By.id("Password")).sendKeys(Password);
		 driver.findElement(By.className("sign-in")).click();
	  }
	
	@AfterClass
	public void setDown() {
		driver.close();
		driver.quit();
		System.out.println("Closing the browser session");
		
	}
	
}
