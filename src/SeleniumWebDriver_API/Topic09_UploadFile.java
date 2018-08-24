package SeleniumWebDriver_API;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic09_UploadFile {
	WebDriver driver;
	String projectDirectory = System.getProperty("user.dir");
	String fileName = "Trung.jpg";
	String uploadFilePath = projectDirectory + "\\Image\\" + fileName;
	String chromeUpload = projectDirectory + "\\Upload\\chrome.exe";
	String firefoxUpload = projectDirectory + "\\Upload\\firefox.exe";
	String IEUpload = projectDirectory + "\\Upload\\ie.exe";
	String folderName = "autoonline" + randomNumber();
	String emailRandom = "online05" + randomNumber() + "@yahoo.com";
	String firstName = "TRUNGTRAN";

	@BeforeClass
	public void beforeClass() {
		//System.setProperty("webdriver.chrome.driver", ".\\Driver\\chromedriver.exe");
		//driver = new ChromeDriver();
		 driver = new FirefoxDriver();
		// System.setProperty("webdriver.ie.driver", ".\\Driver\\IEDriverServer.exe");
		// driver = new InternetExplorerDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test(enabled = false)
	public void TC01_UploadFileBySendkeys() throws Exception {
		// Step 01 - Truy cập vào trang: http://blueimp.github.com/jQuery-File-Upload/
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");

		// Step 02 - Sử dụng phương thức sendKeys để upload file chạy cho 3 trình duyệt
		// (IE/ Firefox/ Chrome)
		WebElement uploadElement = driver.findElement(By.xpath("//input[@type='file']"));
		uploadElement.sendKeys(uploadFilePath);

		// Step 03 - Kiểm tra file đã được tải lên thành công
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class='name'and text()='" + fileName + "']")).isDisplayed());
		driver.findElement(By.xpath("//table//button[@class='btn btn-primary start']")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class='name']/a[@title='" + fileName + "']")).isDisplayed());
	}

	@Test(enabled = false)
	public void TC02_UploadFileByAutoIT() throws Exception {

		// Step 01 - Truy cập vào trang: http://blueimp.github.com/jQuery-File-Upload/
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		// Step 02 - Sử dụng AutoIT để upload file chạy cho 3 trình duyệt (IE/ Firefox/
		// Chrome)
		// Firefox & Chrome
		WebElement uploadBtn = driver.findElement(By.cssSelector(".fileinput-button"));
		uploadBtn.click();
		// IE vs span
		// WebElement uploadBtn = driver.findElement(By.xpath("//input[@type='file']"));
		// clickToElementByJS(driver, uploadBtn);
		Thread.sleep(5000);
		Runtime.getRuntime().exec(new String[] { firefoxUpload, uploadFilePath });

		// Step 03 - Kiểm tra file đã được tải lên thành công
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class='name'and text()='" + fileName + "']")).isDisplayed());
		driver.findElement(By.xpath("//table//button[@class='btn btn-primary start']")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class='name']/a[@title='" + fileName + "']")).isDisplayed());
	}

	@Test(enabled = false)
	public void TC03_UploadFileByRobotClass() throws Exception {
		// Step 01 - Truy cập vào trang: http://blueimp.github.com/jQuery-File-Upload/
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");

		// Step 02 - Sử dụng Robot để upload file chạy cho 3 trình duyệt (IE/ Firefox/
		// Chrome)
		// define location of FileName
		StringSelection select = new StringSelection(uploadFilePath);

		// Copy location to clipboard
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, null);

		// Click
		WebElement uploadBtn = driver.findElement(By.cssSelector(".fileinput-button"));
		uploadBtn.click();
		Thread.sleep(5000);

		Robot robot = new Robot();
		Thread.sleep(1000);

		// Focus to textbox
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		// Ctrl+V Press
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);

		//// Ctrl+V Release
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		Thread.sleep(1000);

		// Focus to textbox again
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		Thread.sleep(5000);
		// Step 03 - Kiểm tra file đã được tải lên thành công
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class='name'and text()='" + fileName + "']")).isDisplayed());
		driver.findElement(By.xpath("//table//button[@class='btn btn-primary start']")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class='name']/a[@title='" + fileName + "']")).isDisplayed());
	}

	@Test(enabled = true)
	public void TC04_UploadFile() throws Exception {
		// Step 01 - Open URL: 'https://encodable.com/uploaddemo/'
		driver.get("https://encodable.com/uploaddemo/");
		// Step 02 - Choose Files to Upload (Ex: UploadFile.jpg)
		WebElement uploadFile = driver.findElement(By.xpath("//input[@type='file']"));
		uploadFile.sendKeys(uploadFilePath);
		// Step 03 - Select dropdown (Upload to: /uploaddemo/files/)
		WebElement uploadDropDown = driver.findElement(By.xpath("//select[@name='subdir1']"));
		Select select = new Select(uploadDropDown);
		select.selectByVisibleText("/uploaddemo/files/");
		// Step 04 - Input random folder to 'New subfolder? Name:) textbox (Ex:
		// dam1254353)
		WebElement newSubfolderTxt = driver.findElement(By.xpath("//input[@id='newsubdir1']"));
		newSubfolderTxt.sendKeys(folderName);
		// Step 05 - Input email and firstname (dam@gmail.com/ DAM DAO)
		WebElement emailTxt = driver.findElement(By.xpath("//input[@id='formfield-email_address']"));
		emailTxt.sendKeys(emailRandom);
		WebElement firstNameTxt = driver.findElement(By.xpath("//input[@id='formfield-first_name']"));
		firstNameTxt.sendKeys(firstName);
		// Step 06 - Click Begin Upload (Note: Wait for page load successfully)
		Thread.sleep(3000);
		WebElement beginUploadBtn = driver.findElement(By.xpath("//input[@id='uploadbutton']"));
		beginUploadBtn.click();
		// Step 07 - Verify information + Email Address: dam@gmail.com/ First Name: DAM
		// DAO + File name: UploadFile.jpg
		Assert.assertTrue(driver
				.findElement(By.xpath("//dl[@id='fcuploadsummary']//dd[text()='Email Address: " + emailRandom + "']"))
				.isDisplayed());
		Assert.assertTrue(
				driver.findElement(By.xpath("//dl[@id='fcuploadsummary']/dd[text()='First Name: " + firstName + "']"))
						.isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath(
				"//dl[@id='fcuploadsummary']//dt[contains(text(),'File 1 of 1:')]//a[text()='" + fileName + "']"))
				.isDisplayed());
		// Step 08 - Click 'View Uploaded Files' link
		driver.findElement(By.xpath("//a[text()='View Uploaded Files']")).click();

		// Step 09 - Click to random folder (Ex: dam1254353)
		driver.findElement(By.xpath("//a[text()='" + folderName + "']")).click();
		// Step 09 - Verify file name exist in folder (UploadFile.jpg)
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + fileName + "']")).isDisplayed());
	}

	@AfterClass
	public void afterClass() {
		driver.close();
	}

	public Object clickToElementByJS(WebDriver driver, WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].click();", element);

	}

	public int randomNumber() {
		Random random = new Random();
		return random.nextInt(9999);
	}

}
