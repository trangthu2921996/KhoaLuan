package Adventure_Android;


import org.testng.annotations.Test;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Common.Common;
import Common.Database;
import Common.Driver;
import Common.Excel;

public class Adventure_Android_Testing {
	public String pathData = "data/adventure-mobile/data.xls";
	public String timeBuild = "";
	public Driver driver = null;
	public long startTime;
	public long endTime;
	public int event;
	
	Logger logger = Logger.getLogger("run");
	Excel excel;

	String[] array = { "case", "create trips", "check trips view","check validate form create trips",
			"join and interested","check discussion" ,"create diary","check diary view","check validate form create diary",
			"expected result","actual result","result"};

	int resultColumn = Common.getIndex(array, "result");
	int actualResultColumn = Common.getIndex(array,"actual result");
	
	@BeforeTest
	public void open() throws Exception {
		timeBuild = new SimpleDateFormat("yyyy-MM-dd HH.mm").format(Calendar.getInstance().getTime());
		System.setProperty("current.date", timeBuild);
		PropertyConfigurator.configure("log4j.properties");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		Logger logger = Logger.getLogger("open");
		logger.info("Start test at " + dateFormat.format(date));
		logger.info("Setup to connect devices");
		
		startTime = System.currentTimeMillis();
		
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities=DesiredCapabilities.android();		 
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "4653bc57");
		capabilities.setCapability("platformVersion", "4.4.2");
		capabilities.setCapability("appPackage", "studio.crazybt.adventure");	
		capabilities.setCapability("appActivity", "studio.crazybt.adventure.activities.SplashActivity");	
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("fullReset", false);
		capabilities.setCapability("unicodeKeyboard", true);
		
		
		URL url= new URL("http://127.0.0.1:4723/wd/hub");
		 
		driver = new Driver(capabilities, url);
		logger.info("Done setup to connect devices");
		
		logger.info("Connect database");
		//Database.connectDatabase();
		logger.info("Connect database success");
		
		Excel excel = new Excel();
		excel.createFileOutput(pathData, timeBuild);
	}
	

	//---------------------------------------------
	@Test
	/*
	 * Hàm thực hiện auto test
	 */
	public void run () throws Exception {
		excel = new Excel(pathData, timeBuild);
		excel.accessSheet("run");
		

		for(int i=1; i<excel.getSheet().getPhysicalNumberOfRows(); i++) {
			try {
				/*if(excel.getStringData(Common.getIndex(array, "expected result"), i) == null || excel.getStringData(Common.getIndex(array, "expected result"), i).equals(""))
					continue;*/
				if(excel.getStringData(Common.getIndex(array, "case"), i) != null && !excel.getStringData(Common.getIndex(array, "case"), i).equals(""))
				{
					if(excel.getStringData(Common.getIndex(array, "expected result"), i) != null && !excel.getStringData(Common.getIndex(array, "expected result"), i).equals(""))
					{
						logger.info("Start testcase "+excel.getStringData(Common.getIndex(array, "case"), i));
						if(accessAction(excel, array, i,i+1) == true) {
							writeResultToExcel(excel, actualResultColumn, i, "T");
							
						}
						else{
							writeResultToExcel(excel, actualResultColumn, i, "F");
							
						}
						excel.accessSheet("run");
						logger.info("End testcase "+excel.getStringData(Common.getIndex(array, "case"), i));
					}
				}
			} catch (Exception e) {
				excel.accessSheet("Run");
				excel.printStringIntoExcel(actualResultColumn,i,"F");
				excel.printResultToFlow("F", excel.getStringData(Common.getIndex(array, "expected result"), i), excel, i, resultColumn);
				logger.error(e.getMessage());
				excel.accessSheet("Run");
				logger.info("End testcase "+excel.getStringData(Common.getIndex(array, "case"), i));
				
			}
		}
		excel.finish();
	}
	public boolean accessAction(Excel excel, String[] array, int row,int column)throws Exception{
		for(int i=1; i<array.length; i++){
			System.out.println(array[i]);
			System.out.println("get : "+ excel.getStringData(Common.getIndex(array, array[i]), row).equals(""));
			if(!excel.getStringData(Common.getIndex(array, array[i]), row).equals("")){
				switch(array[i]){
					case "create trips":{
						TripScheduleManagement ct= new TripScheduleManagement();
						return ct.createTrips( driver, excel,column);
					}
					case "check trips view":{
						TripScheduleManagement ctv= new TripScheduleManagement();
						return ctv.checkTripsView(driver,excel,column);
					}
					case "check validate form create trips":{
						TripScheduleManagement cv= new TripScheduleManagement();
						return cv.checkValidate(driver,excel,column);
					}
					case "join and interested":{
						TripScheduleManagement rt= new TripScheduleManagement();
						return rt.joinAndInterested(driver,excel,column);
					}
					case "check discussion":{
						TripScheduleManagement cd= new TripScheduleManagement();
						return cd.checkDiscussion(driver,excel,column);
					}
					case "create diary":{
						TripScheduleManagement cdi= new TripScheduleManagement();
						return cdi.createDiary(driver,excel,column);
					}
					case "check diary view":{
						TripScheduleManagement cdv= new TripScheduleManagement();
						return cdv.checkDiaryView(driver,excel,column);
					}
					case "check validate form create diary":{
						TripScheduleManagement cvf= new TripScheduleManagement();
						return cvf.checkValidateDiary(driver,excel,column);
					}
				}

				excel.write();
			}
		}
		return true;

	}

	private void writeResultToExcel(Excel excel, int actualResultColumn, int index, String result) {
//		new Thread() {
//			public void run() {
				try {
						excel.accessSheet("run");
						excel.printStringIntoExcel(actualResultColumn,index,result);
						excel.printResultToFlow(result, excel.getStringData(Common.getIndex(array, "expected result"), index), excel, index, resultColumn);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//				}  
//		}.start();
	}

	//-------------------------------------------------
	//@Test
	/*
	 * Hàm thực hiện auto test module Nhóm cùng du lịch
	 */
	//public void run1 () throws Exception {

	//}

	@AfterTest
	public void end () throws Exception {
		Logger logger = Logger.getLogger("end");
		endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		logger.info("Tong thoi gian chay: "+ totalTime/1000 + "s ~ "+totalTime/1000/60+"p"+totalTime/1000%60+"s");
		logger.info("Tong time nghi: "+ driver.getCountTime()/1000 + "s ~ "+driver.getCountTime()/1000/60+"p"+driver.getCountTime()/1000%60+"s");
	}

}
