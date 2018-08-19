package SeleniumWebDriver_API;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic08_JavascriptExecutor {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test (enabled = false)
	public void TC01_JSExecutor() {
		// Step 01 - Truy cập vào trang: http://live.guru99.com/
		openAnyUrlByJS("http://live.guru99.com/");

		// Step 02 - Sử dụng JE để get domain của page - > Verify domain =
		// live.guru99.com
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String domainPage = (String) js.executeScript("return document.domain");
		Assert.assertEquals(domainPage, "live.guru99.com");

		// Step 03 - Sử dụng JE để get URL của page Verify URL = http://live.guru99.com/
		String urlPage = (String) js.executeScript("return document.URL");
		Assert.assertEquals(urlPage, "http://live.guru99.com/");

		// Step 04 - Open MOBILE page (Sử dụng JE)
		WebElement mobileTab = driver.findElement(By.xpath("//a[contains(text(),'Mobile')]"));
		clickToElementByJS(driver, mobileTab);

		// Step 05 - Add sản phẩm SAMSUNG GALAXY vào Cart (click vào ADD TO CART button
		// bằng JE)
		WebElement addToCartSSGalaxy = driver.findElement(
				By.xpath("//h2[a[@title='Samsung Galaxy']]/following-sibling::div[@class='actions']/button"));
		clickToElementByJS(driver, addToCartSSGalaxy);

		// Step 06 - Verify message được hiển thị: Samsung Galaxy was added to your
		// shopping cart. (Sử dụng JE - Get innertext of the entire webpage )
		String SSaddMessage = (String) executeForBrowser(driver, "return document.documentElement.innerText");
		Assert.assertTrue(SSaddMessage.contains("Samsung Galaxy was added to your shopping cart."));
		// Step 07 - Open PRIVACY POLICY page (Sử dụng JE)=> Verify title của page =
		// Privacy Policy (Sử dụng JE)
		WebElement privacy = driver.findElement(By.xpath("//a[text()='Privacy Policy']"));
		clickToElementByJS(driver, privacy);

		String privacyPolicyPageTitle = (String) js.executeScript("return document.title");
		Assert.assertEquals(privacyPolicyPageTitle, "Privacy Policy");
		
		// Step 08 - Srcoll xuống cuối page
		scrollToBottomPage(driver);

		// Step 09 - Verify dữ liệu có hiển thị với chỉ 1 xpath:
		WebElement wishlistTableContent = driver.findElement(By.xpath(
				"//th[text()='WISHLIST_CNT']/following-sibling::td[contains(text(),'The number of items in your Wishlist.')]"));
		Assert.assertTrue(wishlistTableContent.isDisplayed());

		// Step 10 - Navigate tới domain: http://demo.guru99.com/v4/ (Sử dụng
		// JE)=>Verify domain sau khi navigate = demo.guru99.com/v4/
		openAnyUrlByJS("http://demo.guru99.com/v4/");
		String navigatedDomain = (String) executeForBrowser(driver, "return document.domain") ;
		Assert.assertEquals(navigatedDomain, "demo.guru99.com");

		driver.close();

	}

	@Test (enabled = true)
	public void TC02_RemoveAttribute() {
		String firstName = "Automation", lastName ="Testing"; 
		// Step 01 - Truy cập vào trang:
		// https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_input_disabled
		driver.get("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_input_disabled");
		
		// Step 02 - Remove thuộc tính disabled của field Last name
		WebElement resultIframe = driver.findElement(By.xpath("//iframe[@id='iframeResult']"));
		driver.switchTo().frame(resultIframe);
		
		driver.findElement(By.xpath("//input[@name='fname']")).sendKeys(firstName);
		WebElement lastNameTxtBox = driver.findElement(By.xpath("//input[@name='lname']"));
		removeAttributeInDOM(lastNameTxtBox, "disabled");
		
		// Step 03 - Sendkey vào field Last name
		driver.findElement(By.xpath("//input[@name='lname']")).sendKeys(lastName);

		// Step 04 - Click Submit button
		WebElement submitBtn = driver.findElement(By.xpath("//input[@value='Submit']"));
		submitBtn.click();
		// Step 05 - Verify dữ liệu sau khi submit chứa đoạn text đã fill trong field
		// Lastname (Automation Testing)
		String messageSuccess = driver.findElement(By.xpath("//div[@class='w3-container w3-large w3-border']")).getText();
		Assert.assertTrue(messageSuccess.contains(firstName) && messageSuccess.contains(lastName));
	}

	@AfterClass
	public void afterClass() {
		driver.close();
	}

	public Object openAnyUrlByJS(String url) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("window.location = ' " + url + "'");
	}

	public Object executeForBrowser(WebDriver driver, String javaSript) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript(javaSript);

	}

	public Object clickToElementByJS(WebDriver driver, WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].click();", element);

	}

	public void removeAttributeInDOM(WebElement element, String attribute) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].removeAttribute('" + attribute + "');", element);

	}

	public Object scrollToBottomPage(WebDriver driver) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

}
