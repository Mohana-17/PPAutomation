package com;

import java.io.IOException;
import java.time.Duration;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Util.ReadExcel;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ReleaseToCMP{
	WebDriver driver;
	ReadExcel excel = new ReadExcel();
	@BeforeClass
	public void setUp() {
		System.out.println("Starting the browser session");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
	
	@Test(priority=0)
	  public void VerifyLoginPage() throws InterruptedException  {
		 driver.get("https://oms.nonprod-psprint.com/#/login");
		 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		 driver.findElement(By.id("username")).sendKeys("cmp_uat_oms");
		 driver.findElement(By.id("password")).sendKeys("Bric_2018");
		 Thread.sleep(1500);
		 driver.findElement(By.className("btn-primary")).click();
	  }
	
	@Test(priority=1)
	public void SearchJob() throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {
		excel.setExcelFile("GreetingCards");
		String OrderNumber = excel.getCellData("OrderNumber", 1);
        String[] arrOfStr = OrderNumber.split("-", 2);
        String Order = null , Job = null ;
        String Conjunction = "_";
        for (int i=0; i < arrOfStr.length; i++) {
         Order = arrOfStr[0];
         Job = arrOfStr[1];
       }
        String id = Order + Conjunction + Job ;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	    driver.findElement(By.className("dropdown-toggle")).click();
	    driver.findElement(By.xpath("//a[normalize-space()='File CheckIn CheckOut']")).click();
	    Thread.sleep(2000);
	    WebElement Search = driver.findElement(By.xpath("//select[@ng-model='selectedSearchField']"));
	    Select dropdown = new Select(Search);
	    dropdown.selectByVisibleText("Job Number");
	    driver.findElement(By.xpath("//input[@ng-model='filters.searchby_field_value']")).sendKeys(Job);
	    Thread.sleep(1500);
	    driver.findElement(By.xpath("//input[@ng-model='filters.include_test_jobs']")).click();
	    driver.findElement(By.xpath("//button[normalize-space()='Submit']")).click();
	    String status = driver.findElement(By.xpath("(//div[@role='gridcell'])[17]")).getText(); 
		if(status.equalsIgnoreCase("CMP Pending")) {
			driver.findElement(By.xpath("//button[normalize-space()='Check Out']")).click();
			Thread.sleep(1500);
			driver.findElement(By.xpath("//button[normalize-space()='Download']")).click();
			Thread.sleep(1500);
			driver.findElement(By.xpath("//button[normalize-space()='CheckIn']")).click();
			Thread.sleep(1500);
			driver.findElement(By.xpath("//input[@value='check_in_with_production_pool']")).click();
		    Thread.sleep(2000);
		    WebElement m=driver.findElement(By.xpath("//input[@type='file']"));
		    m.sendKeys("C:/Users/t462204/Downloads/"+id+".pdf");		    
		    Thread.sleep(1500);
			driver.findElement(By.id("btn_upload_fileupload")).click();
			
		}
		else {
			System.out.println("Current status of the order is "+status);
		}
	}
}
