package Adventure_Android;

import org.apache.log4j.Logger;

import Common.Driver;
import Common.Excel;

public class Adventure_Common {
	
	public void logIn(Driver driver, Excel excel, int column,int row)throws Exception {
		Logger logger = Logger.getLogger("Login");
		logger.info("Start Login");
		
		excel.accessSheet("Trip Schedule");
		//excel.accessSheet("Login");
		
		logger.info("Login account");
		logger.info("click button login email");
		driver.click("id=studio.crazybt.adventure:id/btnLoginViaEmail",0);
		logger.info("send username");
		driver.sendKey("id=studio.crazybt.adventure:id/etPhoneEmailLogin", excel.getStringData(column, row), 0);
		logger.info("send pass ");
		driver.sendKey("id=studio.crazybt.adventure:id/etPasswordLogin",excel.getStringData(column+1, row), 10);
		driver.getDriver().pressKeyCode(66);
		logger.info("click button login");
		driver.click("id=studio.crazybt.adventure:id/btnLogin",10);
	}
	
	public void logOut(Driver driver)throws Exception {
		Logger logger = Logger.getLogger("Logout");
		logger.info("touch menu");
		driver.getElenment("id=studio.crazybt.adventure:id/tbHomePage").findElementsByClassName("android.widget.ImageButton").get(0).click();
		logger.info("touch Dang xuat");
		driver.getElenment("id=studio.crazybt.adventure:id/navView").findElementsByClassName("android.support.v7.widget.LinearLayoutCompat").get(6).click();
	}
}
