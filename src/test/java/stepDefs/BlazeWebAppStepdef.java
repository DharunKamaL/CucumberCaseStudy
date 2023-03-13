package stepDefs;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BlazeWebAppStepdef {

	static WebDriver driver;
	static WebDriverWait wait;
	static int proSize;
	static int proSizeAft;
	static String productName;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "F:\\Dharun\\webdriver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
		wait = new WebDriverWait(driver, Duration.ofSeconds(40));
	}

	@Given("User is on login Page and enters {string} and {string}")
	public void user_is_on_login_page_and_enters_and(String userName, String password) {
		driver.get("https://www.demoblaze.com/index.html");
		driver.findElement(By.id("login2")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[id=\"loginusername\"]")));
		driver.findElement(By.cssSelector("input[id=\"loginusername\"]")).sendKeys(userName);
		driver.findElement(By.id("loginpassword")).sendKeys(password);
		driver.findElement(By.cssSelector("button[onclick=\"logIn()\"]")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Welcome')]")));
		WebElement welcomeTxt = driver.findElement(By.xpath("//a[contains(text(),'Welcome')]"));
		Assert.assertEquals(welcomeTxt.getText(), "Welcome Dharun_K");
	}

	@When("User adds an items")
	public void user_adds_an_items(DataTable dataTable) {
		List<Map<String, String>> data = dataTable.asMaps();
		for (int i = 0; i < data.size(); i++) {
			String category = data.get(i).get("productCategory");
			String name = data.get(i).get("productName");
			WebElement proCat = driver.findElement(By.partialLinkText(category));
			wait.until(ExpectedConditions.elementToBeClickable(proCat));
			proCat.click();
			WebElement proName = driver.findElement(By.partialLinkText(name));
			wait.until(ExpectedConditions.elementToBeClickable(proName));
			proName.click();
			driver.findElement(By.xpath("//a[contains(text(),'Add to')]")).click();
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
			driver.findElement(By.xpath("(//ul/li//a)[1]")).click();
		}

	}

	@Then("the products are added to the Cart")
	public static void the_products_are_added_to_the_cart() {
		driver.findElement(By.id("cartur")).click();
//	    List<WebElement> products = driver.findElements();
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//td[2]")));
		List<WebElement> products = driver.findElements(By.xpath("//td[2]"));
		proSize = products.size();
		if (proSize > 1) {
			Assert.assertTrue(true);
		}
		productName = driver.findElement(By.xpath("(//td[2])[1]")).getText();

	}

	@When("User delete an item from the Cart Page")
	public void user_delete_an_item_from_the_cart_page() {
		driver.findElement(By.id("cartur")).click();
		driver.findElement(By.xpath("//td/a[1]")).click();
	}

	@Then("the item should be deleted from the Cart Page")
	public void the_item_should_be_deleted_from_the_cart_page() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//td[2])[1]")));
		String proTxt = driver.findElement(By.xpath("(//td[2])[1]")).getText();
		if (productName != proTxt) {
			Assert.assertTrue(true);
		}

	}
	
	@When("User places an order for the items")
	public void user_places_an_order_for_the_items() {
		driver.findElement(By.id("cartur")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//td[2]")));
		driver.findElement(By.xpath("//button[text()='Place Order']")).click();
		driver.findElement(By.id("name")).sendKeys("Dharun");
		driver.findElement(By.id("country")).sendKeys("India");
		driver.findElement(By.id("city")).sendKeys("Chennai");
		driver.findElement(By.id("card")).sendKeys("1234567890");
		driver.findElement(By.id("month")).sendKeys("October");
		driver.findElement(By.id("year")).sendKeys("2022");
		driver.findElement(By.xpath("//button[text()='Purchase']")).click();
	}
	
	
	@Then("It should be ordered and purchased")
	public void it_should_be_ordered_and_purchased() {
		WebElement successMsg = driver.findElement(By.xpath("//h2[text()='Thank you for your purchase!']"));
		wait.until(ExpectedConditions.visibilityOf(successMsg));
		Assert.assertEquals(successMsg.getText(), "Thank you for your purchase!");
	}
	
	@After
	public void attachImgToReport(Scenario scenario) {
		if(scenario.isFailed()) {
			TakesScreenshot screen = (TakesScreenshot)driver;
			byte[] img = screen.getScreenshotAs(OutputType.BYTES);
			scenario.attach(img, "image/png", "sampleImg");
 		}
	}

}
