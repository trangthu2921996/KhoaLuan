package Common;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;


public class Driver {
	// private RemoteWebDriver driver = null;
	protected AndroidDriver<MobileElement> driver = null;
	private int countTime = 0;
	Dimension size;
	String destDir;
	DateFormat dateFormat;

	public Driver(DesiredCapabilities capabilities, URL url) {
		// driver = new RemoteWebDriver(url, capabilities);
		driver = new AndroidDriver<MobileElement>(url, capabilities);
	}

	public By getBy(String locator) {
		By by = null;
		if (locator.startsWith("id=")) {
			locator = locator.substring(3);
			by = By.id(locator);
		} else if (locator.startsWith("xpath=")) {
			locator = locator.substring(6);
			by = By.xpath(locator);
		} else if (locator.startsWith("className=")) {
			locator = locator.substring(10);
			by = By.className(locator);
		} else if (locator.startsWith("name=")) {
			locator = locator.substring(5);
			by = By.name(locator);
		} else if (locator.startsWith("linkText=")) {
			locator = locator.substring(9);
			by = By.linkText(locator);
		}
		return by;
	}

	public MobileElement getElenment(String locator) {
		return (MobileElement) driver.findElement(getBy(locator));
	}

	public List<MobileElement> getListElenment(String locator) {
		List<MobileElement> ls = null;
		ls = driver.findElements(getBy(locator));
		return ls;
	}

	public MobileElement getElementFormList(String locator, int index) throws Exception {
		return this.getListElenment(locator).get(index);
	}

	public void click(String locator, int timeSleep) throws Exception {
		getElenment(locator).click();
		sleep(timeSleep);
	}
	
	public void clickByClassName(String locator, String locator1,int index,int timeSleep) throws Exception {
		getElenment(locator).findElementsByClassName(locator1).get(index).click();
		sleep(timeSleep);
	}

	public AndroidDriver<MobileElement> getDriver() {
		return this.driver;
	}

	public void sendKey(String locator, String message, int timeSleep) throws Exception {
		getElenment(locator).clear();
		getElenment(locator).sendKeys(message);
		sleep(timeSleep);
	}

	public void selectByVisibleText(String locator, String message, int timeSleep) throws Exception {
		new Select(getElenment(locator)).selectByVisibleText(message);
		sleep(timeSleep);
	}

	public boolean isSelected(String locator) {
		if (getElenment(locator).isSelected())
			return true;
		else
			return false;
	}

	public String getTextFromDropDownList(String locator) {
		Select select = new Select(getElenment(locator));
		WebElement option = select.getFirstSelectedOption();
		return option.getText();
	}

	public void clear(String locator) throws Exception {
		getElenment(locator).clear();
		sleep(100);
	}

	public String getText(String locator) {
		return getElenment(locator).getText();
	}
	
	public String getTextByClassName(String locator,String locator1, int index) {
		return getElenment(locator).findElementsByClassName(locator1).get(index).getText();
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public void close() throws Exception {
		this.driver.close();
	}

	public String closeAlertAndGetItsText(boolean acceptNextAlert, int timeSleep) throws Exception {
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		if (acceptNextAlert) {
			alert.accept();
			sleep(timeSleep * 2);
		} else {
			alert.dismiss();
			sleep(timeSleep * 2);
		}
		return alertText;
	}

	public void get(String url) throws Exception {
		this.driver.get(url);
	}

	public void sleep(long timeSleep) throws Exception {
		countTime += timeSleep;
		Thread.sleep(timeSleep);
		//driver.manage().timeouts().implicitlyWait(timeSleep, TimeUnit.MILLISECONDS);
	}

	public int getCountTime() {
		return countTime;
	}

	// Bat toa do
	public void catchCoordinates(Excel excel, int column,int row) throws Exception{
		//System.out.println(excel.getNumberictData(column, row));
		//System.out.println(excel.getNumberictData(column, row+1));
		TouchAction swipe = new TouchAction(driver);
		swipe.tap(excel.getNumberictData(column, row),excel.getNumberictData(column, row+1)).perform();
	}
	
	// Vuốt ngang màn hình
	public void swipingHorizontal(String swip) throws InterruptedException {
		// Get the size of screen.
		size = driver.manage().window().getSize();
		System.out.println(size);

		// Find swipe start and end point from screen's with and height.
		// Find startx point which is at right side of screen.
		int startx = (int) (size.width * 0.90);//0.70
		// Find endx point which is at left side of screen.
		int endx = (int) (size.width * 0.10);//0.30
		// Find vertical point where you wants to swipe. It is in middle of screen
		// height.
		int starty = size.height / 2;
		System.out.println("startx = " + startx + " ,endx = " + endx + " , starty = " + starty);
		
		if(swip =="Right to Left") {
			// Swipe from Right to Left.
			driver.swipe(startx, starty, endx, starty, 3000);
			Thread.sleep(2000);
		}else if(swip=="Left to Right") {
			// Swipe from Left to Right.
			driver.swipe(endx, starty, startx, starty, 3000);
			Thread.sleep(2000);
		}
	}
	
	// Vuốt dọc màn hình(từ trên xuống, từ dưới lên)
	public void swipingVertical(String swip) throws InterruptedException {
		// Get the size of screen.
		size = driver.manage().window().getSize();
		//System.out.println(size);

		// Find swipe start and end point from screen's with and height.
		// Find starty point which is at bottom side of screen.
		int starty = (int) (size.height * 0.80);
		// Find endy point which is at top side of screen.
		int endy = (int) (size.height * 0.20);
		// Find horizontal point where you wants to swipe. It is in middle of screen
		// width.
		int startx = size.width / 2;
		//System.out.println("starty = " + starty + " ,endy = " + endy + " , startx = " + startx);

		if(swip == "Bottom To Top"){
			// Swipe from Bottom to Top
			driver.swipe(startx, starty, startx, endy, 3000);
			Thread.sleep(2000);
		}
		else if(swip == "Top To Bottom"){
			//Swipe from Top to Bottom.
			driver.swipe(startx, endy, startx, starty, 3000);
			Thread.sleep(2000);
		}

	}
	
	 //To scroll tabs right to left In horizontal direction.
	 public void ScrollTabs() {
	  //Get the size of screen.
	  size = driver.manage().window().getSize();  
	  
	  //Find swipe start and end point from screen's with and height.
	  //Find startx point which is at right side of screen.
	  int startx = (int) (size.width * 0.80);
	  //Find endx point which is at left side of screen.
	  int endx = (int) (size.width * 0.20);
	  //Set Y Coordinates of screen where tabs display.
	  int YCoordinates = 150;  

	  //Swipe tabs from Right to Left.
	  driver.swipe(startx, YCoordinates, endx, YCoordinates, 3000);  
	 }
	
	// Chụp màn hình screenshort
	public void takeScreenShotfFail(String name) {
	  // Set folder name to store screenshots.
	  destDir = "screenshotsFail";
	  // Capture screenshot.
	  File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	  // Set date format to set It as screenshot file name.
	  dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
	  // Create folder under project with name "screenshots" provided to destDir.
	  new File(destDir).mkdirs();
	  // Set file name using current date time.
	  String destFile = "__"+dateFormat.format(new Date()) + ".png";

	  try {
	   // Copy paste file at destination folder location
	   FileUtils.copyFile(scrFile, new File(destDir + "/"+name+ destFile));
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	}
	// Chụp màn hình trả ra đường dẫn để check toast message
	public String takeScreenShot() {
		  // Set folder name to store screenshots.
		  destDir = "screenshots";
		  // Capture screenshot.
		  File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		  // Set date format to set It as screenshot file name.
		  //dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		  dateFormat = new SimpleDateFormat("dd-MM-yyyy__HH_mm");
		  // Create folder under project with name "screenshots" provided to destDir.
		  new File(destDir).mkdirs();
		  // Set file name using current date time.
		  String destFile = dateFormat.format(new Date()) + ".png";
		  String image = "";
		  try {
		   // Copy paste file at destination folder location
		   FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
		   //image="D:\\KhoaLuanTotNghiep\\Demo\\Adventure_Testing\\"+destDir + "\\" + destFile;
		   image="D:/KhoaLuanTotNghiep/Jenkins/workspace/Adventure_Testing/"+destDir + "/" + destFile;
		   
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		  return image;
	}
	

}
