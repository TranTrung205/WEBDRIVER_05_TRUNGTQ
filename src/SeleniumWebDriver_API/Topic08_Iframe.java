package SeleniumWebDriver_API;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic08_Iframe {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void Practise_Online() {
		driver.get("https://www.hdfcbank.com/");
		
		List <WebElement> notificationIframe = driver
				.findElements(By.xpath("//iframe[@id='vizury-notification-template']"));
		
		if (notificationIframe.get(3).isDisplayed()) {
			driver.switchTo().frame(notificationIframe.get(3));
			driver.findElement(By.xpath("//*[@id='div-close']")).click();
		}

	}

	@AfterClass
	public void afterClass() {
	}

}
