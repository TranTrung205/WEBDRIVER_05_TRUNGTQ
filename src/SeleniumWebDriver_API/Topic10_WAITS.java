package SeleniumWebDriver_API;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic10_WAITS {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		// System.setProperty("webdriver.chrome.driver", ".\\Driver\\chromedriver.exe");
		// driver = new ChromeDriver();
		driver = new FirefoxDriver();
		// System.setProperty("webdriver.ie.driver", ".\\Driver\\IEDriverServer.exe");
		// driver = new InternetExplorerDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void TC01_ImplicitWait() {

		// Step 01 - Truy cập vào
		// trang:http://the-internet.herokuapp.com/dynamic_loading/2
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		// Step 02 - Define an implicit wait (If you set 2 seconds, test will fail)
		// Step 03 - Click the start button
		driver.findElement(By.xpath("//button[contains(text(),'Start')]")).click();
		// Step 04 - Wait result text will appear
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		// Step 05 - Check result text is "Hello World!"
		String text = driver.findElement(By.xpath("//h4[contains(text(),'Hello World!')]")).getText();
		Assert.assertEquals(text, "Hello World!");
	}

	@Test
	public void TC02_ExplicitWait() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		// Step 01 - Truy cập vào trang:
		// http://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx
		driver.get(
				"http://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		// Step 02 - Wait cho "Date Time Picker" được hiển thị (sử dụng: presence or
		// visibility)
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='calendarContainer']")));
		// Step 03 - In ra ngày đã chọn (Before AJAX call) -> hiện tại chưa chọn nên in
		// ra = "No Selected Dates to display."
		String noSelectedDate = driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']"))
				.getText();
		System.out.println(noSelectedDate);
		Assert.assertEquals(noSelectedDate, "No Selected Dates to display.");
		// Step 04 - Chọn ngày hiện tại (VD: 23/09/2017) (hoặc 1 ngày bất kì tương ứng
		// trong tháng/ năm hiện tại)
		driver.findElement(By.xpath("//td[@title='Tuesday, August 28, 2018']")).click();
		// Step 05 - Wait cho đến khi "loader ajax" không còn visible (sử dụng:
		// invisibility) Xpath: //div[@class='raDiv']
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='raDiv']")));
		// Step 06 - Wait cho selected date = 23 được visible ((sử dụng: visibility)
		// Xpath: //*[contains(@class,'rcSelected')]//a[text()='28']
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[contains(@class,'rcSelected')]//a[text()='28']")));
		// Step 07 - Verify ngày đã chọn bằng = Saturday, September 23, 2017
		String selectedDate = driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']")).getText();
		Assert.assertEquals(selectedDate, "Tuesday, August 28, 2018");

	}

	
	public void TC03_FluentWait() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		// Step 01 - Truy cập vào trang:
		// https://stuntcoders.com/snippets/javascript-countdown/
		driver.get("https://stuntcoders.com/snippets/javascript-countdown/");
		// Step 02 - Wait cho đến khi countdown time được visible (visibility)

		WebElement countdown = driver.findElement(By.xpath("//div[@id='javascript_countdown_time']"));
		wait.until(ExpectedConditions.visibilityOf(countdown));

		// Step 03 - Sử dụng Fluent wait để: Mỗi 1s kiểm tra countdount= 00 được xuất
		// hiện trên page hay chưa (giây đếm ngược về 00). Tức là trong vòng 15s, cứ mỗi
		// 1 giây verify xem nó đã đếm ngược về giây 00 hay chưa

		// Khởi tạo Fluent wait
		new FluentWait<WebElement>(countdown)
		           // Tổng time wait là 15s
		           .withTimeout(15, TimeUnit.SECONDS)
		            // Tần số mỗi 1s check 1 lần
		            .pollingEvery(1, TimeUnit.SECONDS)
		           // Nếu gặp exception là find ko thấy element sẽ bỏ  qua
		            .ignoring(NoSuchElementException.class)
		            // Kiểm tra điều kiện
		            .until(new Function<WebElement, Boolean>() {
		                public Boolean apply(WebElement element) {
		                           // Kiểm tra điều kiện countdount = 00
		                           boolean flag =  element.getText().endsWith("00");
		                           System.out.println("Time = " +  element.getText());
		                           // return giá trị cho function apply
		                           return flag;
		                      }
		               });
	}

	@AfterClass
	public void afterClass() {
		driver.close();
	}

	@Test
	public Object clickToElementByJS(WebDriver driver, WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].click();", element);

	}

}
