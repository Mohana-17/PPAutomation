package Objects;

import java.time.Duration;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class BaseTest {
	
	    WebDriver driver;
	    public BaseTest(WebDriver driver) {
	    	this.driver=driver;
	    }
	    
	    By PopUp = By.xpath("//button[contains(text(),'No thanks')]");
	    By Login = By.id("login-dropdown");
	    By UserName = By.id("UserName");
	    By PassWord = By.id("Password");
	    By Signin = By.className("sign-in");
	    By SearchProduct = By.xpath("//input[@id='search-field']");
	    By Front = By.xpath("(//a[@data-bind='click: showMyFiles'][normalize-space()='Select from My Files'])[1]");
	    By Back = By.xpath("(//a[@data-bind='click: showMyFiles'][normalize-space()='Select from My Files'])[2]");
	    By SearchDesign = By.className("myartwork-txtsearchmedia");
	    By SelectImage2 = By.xpath("(//input[@value='Select'])[2]");
	    By SelectImage1 = By.xpath("(//input[@value='Select'])[1]");
	    By CropImage = By.xpath("//button[@style='display: block;']");
	    By Cart = By.xpath("//input[@value='Add to Cart']");
	    By Ship = By.xpath("//input[@id='ship-delivery']");
	    By Mail = By.xpath("//input[@id='mail-delivery']");
	    By NextBtn = By.xpath("//input[@value='Next']");
	    By Checkout1 = By.xpath("//div[@class='section-action']//a[@class='main-action']");
	    By Checkout2 = By.xpath("(//a[@class='main-action'])[2]");
	    By Option = By.xpath("//label[normalize-space()='Credit Card']");
	    By Holder = By.xpath("//input[@placeholder='Card holder Name']");
	    By CardType = By.id("PaymentWidget-card-type");
	    By CardNumber = By.id("credit-card-number");
	    By ExpiryMonth = By.id("expiration-month");
	    By ExpiryYear = By.id("expiration-year");
	    By Cvv = By.id("cvv");
	    By PlaceOrder = By.id("//input[@value='Place Order']");
	    
	    
	    public void login(String Username, String Password) {
	    	driver.get("https://www.nonprod-psprint.com/");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	    	driver.findElement(PopUp).click();
	    	driver.findElement(Login).click();
	    	driver.findElement(UserName).sendKeys(Username);
	    	driver.findElement(PassWord).sendKeys(Password);
	    	driver.findElement(Signin).click();
	    }   
	    
	    
	    public void SelectProduct(String Product) {
	    	try {
				Thread.sleep(1500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
	    	driver.findElement(SearchProduct).clear();
	    	driver.findElement(SearchProduct).sendKeys(Product);
	    	try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ENTER).build().perform();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	
	    }  
	    
	    public void Design(String PrintedSide, String DesignFile) {
	    	if(PrintedSide.equalsIgnoreCase("Full Color Both Sides")) {
				 for(int i=0; i<2; i++) {
					 if(i==0) {
						 driver.findElement(Front).click();
						 driver.findElement(SearchDesign).clear();
						 driver.findElement(SearchDesign).sendKeys(DesignFile);
						 Actions add = new Actions(driver);
						 add.sendKeys(Keys.ENTER).build().perform();
						 add.sendKeys(Keys.PAGE_DOWN).build().perform();
						 try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						 driver.findElement(SelectImage2).click();
					 }
					 else {
						 try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						 driver.findElement(Back).click();
						 driver.findElement(SearchDesign).clear();
						 driver.findElement(SearchDesign).sendKeys(DesignFile);
						 Actions add = new Actions(driver);
						 add.sendKeys(Keys.ENTER).build().perform();
						 add.sendKeys(Keys.PAGE_DOWN).build().perform();
						 try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						 driver.findElement(SelectImage1).click();
					 }
					 
				 }
				 
			 }
			 else {
				 	driver.findElement(Front).click();
				 	driver.findElement(SearchDesign).clear();
				 	driver.findElement(SearchDesign).sendKeys(DesignFile);
				 	Actions add = new Actions(driver);
				 	add.sendKeys(Keys.ENTER).build().perform();
				 	add.sendKeys(Keys.PAGE_DOWN).build().perform();
				 	try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				 	driver.findElement(SelectImage2).click();
			}
	    	try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			driver.findElement(CropImage).click();
	    }
	    
	    public void AddToCart(String Qty, String PaperType, String Delivery, String Services) {	    	
			if (Qty.equals("50")|| Qty.equals("100")|| Qty.equals("150")|| Qty.equals("200")|| Qty.equals("250")|| PaperType.equalsIgnoreCase("13 pt. 100% Recucled Matte Cover") || PaperType.equalsIgnoreCase("15 pt. Velvet with Soft-Touch")) {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				driver.findElement(Cart).click();
			}
		
			else {
				 Actions mailOption= new Actions(driver);
				 mailOption.sendKeys(Keys.PAGE_DOWN).build().perform();
				 try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				 if(Delivery.equalsIgnoreCase("Ship")) {
					 driver.findElement(Ship).click();
					 driver.findElement(Cart).click();
				 }
				 else {
					 driver.findElement(Mail).click();
					 driver.findElement(By.id("mail")).sendKeys(Services);
					 driver.findElement(Cart).click();
					 Actions Nxtbtn= new Actions(driver);
					 Nxtbtn.sendKeys(Keys.PAGE_DOWN).build().perform();
					 driver.findElement(NextBtn).click();
				 }	 	
			}
	    }
	    
	    public void CheckoutAndPayment() {
	    	if(driver.findElement(Checkout1).isDisplayed()) {
	    		driver.findElement(Checkout1).click();
			}
			else {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				driver.findElement(Checkout2).click();
			}
	    	try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	driver.findElement(Option).click();
	    	driver.findElement(Holder).sendKeys("test");
	    	WebElement Card = driver.findElement(CardType);
	    	Select CardField = new Select(Card);
			CardField.selectByValue("VISA");
			driver.switchTo().frame("braintree-hosted-field-number");
			driver.findElement(CardNumber).sendKeys("4111111111111111");
			driver.switchTo().defaultContent();
			driver.switchTo().frame("braintree-hosted-field-expirationMonth");
			int Month = Calendar.getInstance().get(Calendar.MONTH);
			WebElement ExpMonth = driver.findElement(ExpiryMonth);
			Select MonthField= new Select(ExpMonth);
			MonthField.selectByValue(String.valueOf(Month));
			driver.switchTo().defaultContent();
			driver.switchTo().frame("braintree-hosted-field-expirationYear");
			int year = Calendar.getInstance().get(Calendar.YEAR);
			int Year = year + 3;
			WebElement ExpYear = driver.findElement(ExpiryYear);
			Select YearField= new Select(ExpYear);
			YearField.selectByValue(String.valueOf(Year));
			driver.switchTo().defaultContent();
			driver.switchTo().frame("braintree-hosted-field-cvv");
			driver.findElement(Cvv).sendKeys("686");
			driver.switchTo().defaultContent();
			driver.findElement(By.xpath("//input[@value='Place Order']")).click();
	    }

}
