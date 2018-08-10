package SeleniumWebDriver_API;

import org.testng.annotations.Test;



import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic01_CheckEnvironment {
  WebDriver driver;
	@Test
  public void f() {
		String homepageTitle = driver.getTitle();
		Assert.assertEquals(homepageTitle, "Guru99 Bank Home Page");
		
		String homepageUrl = driver.getCurrentUrl();
		Assert.assertEquals(homepageUrl, "http://demo.guru99.com/v4/");
  }
  @BeforeClass
  public void beforeClass() {
	  driver = new FirefoxDriver();
	  driver.get("http://demo.guru99.com/v4/");
	  driver.manage().timeouts().implicitlyWait(30 , TimeUnit.SECONDS);
	  driver.manage().window().maximize();
	  
	  
  }

  @AfterClass
  public void afterClass() {
  }

}
