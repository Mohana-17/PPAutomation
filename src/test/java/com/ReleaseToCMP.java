package com;

import java.io.IOException;
import java.time.Duration;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
		 driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();
	  }
	
	@Test(priority=1)
	public void SearchJob() throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {
		excel.setExcelFile("PostCards");
		String OrderNumber = excel.getCellData("OrderNumber", 1);
        String[] arrOfStr = OrderNumber.split("-", 2);
        String Order = null , Job = null ;
        String Conjunction = "_";
        for (int i=0; i < arrOfStr.length; i++) {
         Order = arrOfStr[0];
         Job = arrOfStr[1];
       }
        String id = Order + Conjunction + Job ;
        System.out.println(id);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	    driver.findElement(By.xpath("(//a[@class='dropdown-toggle ng-binding ng-scope'])[1]")).click();
	    driver.findElement(By.xpath("(//a[@class='ng-binding'])[4]")).click();
	    driver.findElement(By.xpath("//option[@value='jobNumber']")).click();
	    driver.findElement(By.xpath("(//input[@class='ng-pristine ng-untouched ng-valid ng-empty'])[1]")).sendKeys(Job);
	    driver.findElement(By.xpath("(//button[@class='btn btn-primary btn-xs'])[1]")).click();
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("(//button[@class='ng-scope'])[1]")).click();
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("(//button[@class='ng-scope'])[2]")).click();
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("(//button[@data-target='#myModal'])[1]")).click();
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("(//input[contains(@value,'check_in_with_production_pool')])")).click();
	    Thread.sleep(2000);
	    WebElement m=driver.findElement(By.xpath("//input[@type='file']"));
	    m.sendKeys("C:/Users/t462204/Downloads/"+id+".pdf");		    
	    Thread.sleep(1500);
		driver.findElement(By.id("btn_upload_fileupload")).click();
	}
}
