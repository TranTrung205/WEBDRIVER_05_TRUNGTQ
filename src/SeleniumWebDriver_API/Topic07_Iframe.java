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

public class Topic07_Iframe {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	// Iframe Handle
	@Test(enabled = false)
	public void TestScript01() {
		driver.get("https://www.hdfcbank.com/");
		List<WebElement> popupMessageframe = driver
				.findElements(By.xpath("//iframe[@id='vizury-notification-template']"));
		if (popupMessageframe.size() > 0) {
			driver.switchTo().frame(popupMessageframe.get(0));
			WebElement closePopUpBtn = driver.findElement(By.xpath("//div[@id='div-close']"));
			JavascriptExecutor je = (JavascriptExecutor) driver;
			je.executeScript("arguments[0].click();", closePopUpBtn);
			driver.switchTo().defaultContent();
		}

		WebElement lookingForIframe = driver.findElement(By.xpath("//div[@class='flipBannerWrap']//iframe"));
		driver.switchTo().frame(lookingForIframe);
		String lookingForText = driver.findElement(By.xpath("//span[@id='messageText']")).getText();
		Assert.assertEquals(lookingForText, "What are you looking for?");
		driver.switchTo().defaultContent();

		WebElement slidingBannerIframe = driver.findElement(By.xpath("//div[@class='slidingbanners']//iframe"));
		driver.switchTo().frame(slidingBannerIframe);
		By bannerImagesXpath = By.xpath("//div[@id='productcontainer']//img");
		List<WebElement> bannerImages = driver.findElements(bannerImagesXpath);
		int bannerImageNumber = bannerImages.size();
		Assert.assertEquals(bannerImageNumber, 6);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(bannerImagesXpath));
		driver.switchTo().defaultContent();

		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='flipBanner']")).isDisplayed(), true);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='flipBanner']")).isDisplayed());

		List<WebElement> flipBannerImages = driver
				.findElements(By.xpath("//div[@class='flipBanner']//img[@class='front icon']"));
		int flipBannerImagesNumer = flipBannerImages.size();
		Assert.assertEquals(flipBannerImagesNumer, 8);
		int i = 0;
		for (WebElement image : flipBannerImages) {
			Assert.assertTrue(image.isDisplayed());
			i++;
			System.out.println("Image" + i + "displayed");
		}
		driver.close();
	}

	// Windows Handle
	@Test(enabled = false)
	public void TestScript02() {
		driver.get("http://daominhdam.890m.com/");
		String parentID = driver.getWindowHandle();
		driver.findElement(By.xpath("//a[contains(text(),'Click Here')]")).click();

		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindow : allWindows) {
			// Swwitch vào từng Window
			driver.switchTo().window(runWindow);
			// getTitle đã switch wa
			String currentWindow = driver.getTitle();
			// kiểm tra nếu title bằng với expected title truyền vào
			if (currentWindow.equals("Google")) {
				// thoát khỏi vòng lặp
				break;
			}
		}
		String googleWindow = driver.getTitle();
		Assert.assertEquals(googleWindow, "Google");
		driver.close();
		driver.switchTo().window(parentID);

	}

	@Test
	public void TestScript03() {
		// Step 01 - Truy cập vào trang: http://www.hdfcbank.com/
		driver.get("https://www.hdfcbank.com/");
		String parentWindow = driver.getWindowHandle();

		// Step 02 - Kiểm tra và close quảng cáo nếu có xuất hiện
		overrideGlobalWait(10);
		List<WebElement> popupMessageframe = driver
				.findElements(By.xpath("//iframe[@id='vizury-notification-template']"));
		if (popupMessageframe.size() > 0) {
			driver.switchTo().frame(popupMessageframe.get(0));
			WebElement closePopUpBtn = driver.findElement(By.xpath("//div[@id='div-close']"));
			JavascriptExecutor je = (JavascriptExecutor) driver;
			je.executeScript("arguments[0].click();", closePopUpBtn);
			driver.switchTo().defaultContent();
		}

		// Step 03 - Click Angri link -> Mở ra tab/window mới -> Switch qua tab mới
		overrideGlobalWait(30);
		driver.findElement(By.xpath("//div[@class='sectionnav']//a[contains(text(),'Agri')]")).click();
		switchToWindowByTitle("HDFC Bank Kisan Dhan Vikas e-Kendra");
		
		//Step 04 - Click Account Details link -> Mở ra tab/window mới -> Switch qua tab mới 
		driver.findElement(By.xpath("//p[text()='Account Details']")).click();
		switchToWindowByTitle("Welcome to HDFC Bank NetBanking");
		
		//Step 05- Click Privacy Policy link (nằm trong frame) -> Mở ra tab/window mới -> Switch qua tab mới
		WebElement footerFrame = driver.findElement(By.xpath("//frame[@name='footer']"));
		driver.switchTo().frame(footerFrame);
		driver.findElement(By.xpath("//a[text()='Privacy Policy']")).click();
		switchToWindowByTitle("HDFC Bank - Leading Bank in India, Banking Services, Private Banking, Personal Loan, Car Loan");
		
		//Step 06- Click CSR link on Privacy Policy page
		driver.findElement(By.xpath("//a[text()='CSR']")).click();
		closeAllWithoutParentWindows(parentWindow);

	}

	@AfterClass
	public void afterClass() {
		driver.close();
	}

	public void switchToWindowByTitle(String title) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			driver.switchTo().window(runWindows);
			String currentWin = driver.getTitle();
			if (currentWin.equals(title)) {
				break;
			}
		}
	}

	public boolean closeAllWithoutParentWindows(String parentWindow) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			if (!runWindows.equals(parentWindow)) {
				driver.switchTo().window(runWindows);
				driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}
	
	public void overrideGlobalWait(long timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}
}
