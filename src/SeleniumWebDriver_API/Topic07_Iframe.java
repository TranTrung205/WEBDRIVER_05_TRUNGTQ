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
	
	//Iframe Handle
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
	@Test
	public void TestScript02() {
		driver.get("http://daominhdam.890m.com/");
		String parentID = driver.getWindowHandle();
		driver.findElement(By.xpath("//a[contains(text(),'Click Here')]")).click();

		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindow : allWindows) {
			driver.switchTo().window(runWindow);
			String currentWindow = driver.getTitle();
			if(currentWindow.equals("Google")) {
				break;
			}
		}
		String googleWindow = driver.getTitle();
		Assert.assertEquals(googleWindow, "Google");
		driver.close();
		driver.switchTo().window(parentID);

	}

	@AfterClass
	public void afterClass() {
	}

}
