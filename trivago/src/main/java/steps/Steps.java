package steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Steps {

	public WebDriver driver;
	public WebDriverWait wait;
	public Actions action;
	public Select option;
	
	@Given("Launch the Browser")
	public void lauchBrowser() {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver83.exe");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		options.addArguments("--start-maximized");
		
		driver = new ChromeDriver(options);		
	}
	
	@Given("Load the URL https://www.trivago.com/")
	public void loadURL() {
		driver.get("https://www.trivago.com/");
	}
	
	@Given("Type Agra in Destination and select Agra, Uttar Pradesh")
	public void selectDestination() {
		wait = new WebDriverWait(driver, 30);
		action = new Actions(driver);
		WebElement city = driver.findElement(By.id("querytext"));
		action.click(city).sendKeys("Agra").build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'City - Uttar Pradesh, India')]"))).click();
	}
	
	@Given("Choose june 15 as check in and June 30 as check out")
	public void checkInCheckOut() {
		driver.findElement(By.xpath("//time[@datetime='2020-06-15']")).click();
		driver.findElement(By.xpath("//time[@datetime='2020-06-30']")).click();
	}
	
	@Given("Select Room as Family Room")
	public void selectRoom() {
		driver.findElement(By.xpath("//span[text()='Family rooms']")).click();
	}
	
	@Given("Choose Number of Adults 2, Childern 1 and set Child Age as 4")
	public void selectMembers() {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("select-num-adults-2")));
		WebElement adult = driver.findElement(By.id("select-num-adults-2"));
		option =  new Select(adult);
		option.selectByValue("2");
		
		WebElement child = driver.findElement(By.id("select-num-children-2"));
		option = new Select(child);
		option.selectByValue("1");
		
		WebElement childAge = driver.findElement(By.id("select-ages-children-2-3"));
		option = new Select(childAge);
		option.selectByValue("4");
	}
	
	@Given("Click Confirm button and click Search")
	public void confirmSearch() {
		driver.findElement(By.xpath("//span[text()='Confirm']")).click();
		driver.findElement(By.className("search-button__label")).click();
	}
	
	@Given("Select Accommodation type as Hotels only and choose 4 stars")
	public void hotelStar() throws InterruptedException {
		driver.findElement(By.xpath("//li[@data-qa='stars-filter']")).click();
		driver.findElement(By.xpath("//label[text()='Hotels only']")).click();
		driver.findElement(By.xpath("//button[@title='4-star hotels']")).click();
		driver.findElement(By.id("filter-popover-done-button")).click();
	}
	
	@Given("Select Guest rating as Very good")
	public void guestRating() {
		driver.findElement(By.xpath("//li[@data-qa='rating-filter']")).click();
		driver.findElement(By.xpath("//span[text()='Very good']")).click();
	}
	
	@Given("Set Hotel Location as Agra Fort and click Done")
	public void location() {
		driver.findElement(By.xpath("//li[@data-qa='location-filter']")).click();
		
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pois")));
		
		WebElement location = driver.findElement(By.id("pois"));
		option = new Select(location);
		option.selectByVisibleText("Agra Fort");
		
		driver.findElement(By.id("filter-popover-done-button")).click();
	}
	
	@Given("In more Filters, select Air conditioning, Restaurant and WiFi and click Done")
	public void moreFilters() {
		driver.findElement(By.xpath("//li[@data-qa='more-filter']")).click();
		driver.findElement(By.xpath("//label[text()='Air conditioning']")).click();
		driver.findElement(By.xpath("//label[text()='WiFi']")).click();
		driver.findElement(By.xpath("//label[text()='Restaurant']")).click();
		
		driver.findElement(By.id("filter-popover-done-button")).click();
		
	}
	
	@Given("Sort the result as Rating & Recommended")
	public void ratingRecommended() {
		WebElement sortBy = driver.findElement(By.id("mf-select-sortby"));
		option = new Select(sortBy);
		option.selectByVisibleText("Rating & Recommended");
	}
	
	@Given("Print the Hotel name, Rating, Number of Reviews and Click View Deal")
	public void printDetails() throws InterruptedException {
		Thread.sleep(5000);
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='item__flex-column'])[1]//div/div/h3/span")));
		String hotelName = driver.findElement(By.xpath("(//div[@class='item__flex-column'])[1]//div/div/h3/span")).getText();
		String rating = driver.findElement(By.xpath("(//div[@class='item__flex-column'])[1]//meta[@itemprop='ratingValue']")).getAttribute("content");
		String review = driver.findElement(By.xpath("(//div[@class='item__flex-column'])[1]//meta[@itemprop='ratingCount']")).getAttribute("content");
		
		System.out.println("Hotel Name is :"+hotelName);
		System.out.println("Rating for hotel is :"+rating);
		System.out.println("No of review is :"+review);
		
		driver.findElement(By.xpath("(//div[@class='item__flex-column'])[1]//span[text()='View Deal']")).click();	
		
	}
	
	@Given("Print the URL of the Page")
	public void printURL() {
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> noOfWindows = new ArrayList<String>(windowHandles);
		driver.switchTo().window(noOfWindows.get(1));
		String currentUrl = driver.getCurrentUrl();
		System.out.println("Page title is :"+currentUrl);
	}
	
	@When("Print the Price of the Room and click Choose Your Room")
	public void roomPrice() {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='price']")));
		String roomPrice = driver.findElement(By.xpath("//div[@class='price']")).getText();
		System.out.println("Price for the selected room is :"+roomPrice);
	}
	
	@Then("Click Reserve and I will Reserve")
	public void clickReverse() {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='button-book']/button"))).click();
		driver.close();
		driver.quit();
	}
}
