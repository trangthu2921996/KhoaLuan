package Adventure_Android;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import Common.Driver;
import Common.Excel;
import Common.Database;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.io.File;


public class TripScheduleManagement {

	// Tạo thành công lịch trình 3 chuyến đi với 3 quyền: Công khai, Bạn bè, Chỉ mình tôi
	// Môi trường: tài khoản thuntt2902@gmail.com, mk: 2921996
	public boolean createTrips(Driver driver, Excel excel, int column) throws Exception {
		Logger logger = Logger.getLogger("creatTrips");
		logger.info("Start creat trips");
		excel.accessSheet("Trip schedule");

		logger.info("touch tab Ban tin");
		driver.clickByClassName("id=studio.crazybt.adventure:id/tlHomePage", "android.support.v7.app.ActionBar$Tab", 1, 10);

		logger.info("touch +");
		driver.catchCoordinates(excel, 10, 1);

		logger.info("touch  tao chuyen di");
		driver.click("id=studio.crazybt.adventure:id/fabCreateTrip", 100);

		logger.info("touch quyen rieng tu");
		driver.click("id=studio.crazybt.adventure:id/spiPrivacy", 5);

		if(column==2){
			logger.info("touch Cong khai");			
			driver.clickByClassName("className=android.widget.ListView", "android.widget.LinearLayout", 2, 5);
		}else if(column==3){
			logger.info("touch Ban be");			
			driver.clickByClassName("className=android.widget.ListView", "android.widget.LinearLayout", 1, 5);
		}else{
			logger.info("touch Chi minh toi");			
			driver.clickByClassName("className=android.widget.ListView", "android.widget.LinearLayout", 0, 5);
		}


		logger.info("send Ten chuyen di");		
		driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripName",excel.getStringData(column, 2), 10);

		logger.info("send Mieu ta");		
		driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripDescription", excel.getStringData(column, 3), 10);

		logger.info("touch Ban do Diem bat dau");
		driver.click("id=studio.crazybt.adventure:id/btnPlacesStartPosition", 10);
		logger.info("send Diem bat dau");		
		driver.sendKey("id=com.google.android.gms:id/edit_text", excel.getStringData(column, 4), 5000);
		logger.info("touch diem bat dau");		
		driver.clickByClassName("id=com.google.android.gms:id/list", "android.widget.RelativeLayout", 0, 1000);
		logger.info("send Tom tat diem den");		
		driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripDescriptionSummary", excel.getStringData(column, 5), 5);
		logger.info("touch Ban do Diem den");		
		driver.click("id=studio.crazybt.adventure:id/btnPlacesDestination", 5);
		logger.info("send Diem den");		
		driver.sendKey("id=com.google.android.gms:id/edit_text", excel.getStringData(column, 6), 5000);		
		logger.info("touch diem den");		
		driver.clickByClassName("id=com.google.android.gms:id/list", "android.widget.RelativeLayout", 0, 1000);

		logger.info("send Ngay bat dau");		
		driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripStartTime", excel.getCellDate(column, 7), 10);
		logger.info("send Ngay ket thuc");	
		driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripEndTime", excel.getCellDate(column, 8), 10);

		logger.info("send Kinh phi");	
		driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripMoney", excel.getStringData(column,9), 5);

		logger.info("send So nguoi");		
		driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripMember", excel.getStringData(column,10), 5);

		driver.swipingVertical("Bottom To Top");

		logger.info("send Phuong tien");		
		driver.sendKey("id=studio.crazybt.adventure:id/tetCreateTripVehicle", excel.getStringData(column,11)+"\n", 10);

		logger.info("touch button Them chi tiet");		
		driver.click("id=studio.crazybt.adventure:id/btnAddTripRoute", 5);
		logger.info("Ngay bat dau");		
		driver.sendKey("id=studio.crazybt.adventure:id/etStartTimeRoute", excel.getCellDate(column, 12), 10);
		logger.info("Ngay ket thuc");		
		driver.sendKey("id=studio.crazybt.adventure:id/etEndTimeRoute", excel.getCellDate(column, 13), 10);
		logger.info("send Tieu de");		
		driver.sendKey("id=studio.crazybt.adventure:id/etTitleRoute", excel.getStringData(column,14), 10);
		logger.info("send Noi dung");		
		driver.sendKey("id=studio.crazybt.adventure:id/etContentRoute", excel.getStringData(column,15), 10);

		driver.swipingVertical("Bottom To Top");

		logger.info("send Chuan bi");		
		driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripToolbox", excel.getStringData(column,16), 10);

		logger.info("send Luu y");		
		driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripNote", excel.getStringData(column,17),10);

		logger.info("touch button Dang");	
		driver.click("id=studio.crazybt.adventure:id/itemPost", 300);
		
		logger.info("take screen short and check toast message");	
		String image = driver.takeScreenShot();
		Tesseract tessInst = new Tesseract();
		tessInst.setDatapath("D:\\KhoaLuanTotNghiep\\Tess4J");
		tessInst.setLanguage("vie");
		try {
		String result= tessInst.doOCR(new File(image));		
		String successCreateTripMsg = excel.getStringData(7,2);
            if(result.contains(successCreateTripMsg)) {
            	logger.info("pass");
            	return true;
            }
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
            logger.info("fail");
            return false;
        }


		// logger.info("click HOME");
		// driver.getDriver().pressKeyCode(AndroidKeyCode.BACK);
		// driver.getDriver().pressKeyCode(AndroidKeyCode.HOME);
       return false;
	}

	// Kiểm tra vị trí hiển thị của các chuyến đi đã tạo
	public boolean checkTripsView(Driver driver, Excel excel,int column) throws Exception {
		Logger logger = Logger.getLogger("check trips view");
		logger.info("Start check trips view");
		excel.accessSheet("Trip schedule");
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB db = mongoClient.getDB("adventure");
		DBCollection coll = db.getCollection("trips");
		BasicDBObject andQuery = new BasicDBObject();
	
		if(column == 5) {
			logger.info("check chuyen di voi quyen Cong khai");
			logger.info("touch tab Cong khai");			
			driver.clickByClassName("id=studio.crazybt.adventure:id/tlHomePage", "android.support.v7.app.ActionBar$Tab", 0, 5);
			try {
				List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
				obj.add(new BasicDBObject("permission",3));
				obj.add(new BasicDBObject("name",driver.getElenment("id=studio.crazybt.adventure:id/tvTripName").getText()));
				obj.add(new BasicDBObject("start_position",driver.getElenment("id=studio.crazybt.adventure:id/tvTripStartPosition").getText()));
				obj.add(new BasicDBObject("destination_summary",driver.getElenment("id=studio.crazybt.adventure:id/tvTripDestination").getText()));
				obj.add(new BasicDBObject("expense",driver.getElenment("id=studio.crazybt.adventure:id/tvTripMoney").getText()));
				andQuery.put("$and", obj);
				DBCursor cursor = coll.find(andQuery);
				while (cursor.hasNext()) {
					System.out.println(cursor.next());
					logger.info("View Cong khai successfull");
					return true;
				}
			}catch(Exception e) {
				logger.info("View Cong khai fail");
				// chụp màn hinh lỗi
				driver.takeScreenShotfFail("[Trip] View Cong khai fail");
				return false;
			}
		}else if(column == 6) {
			logger.info("check chuyen di voi quyen Bạn bè");
			logger.info("touch tab Bản tin");			
			driver.clickByClassName("id=studio.crazybt.adventure:id/tlHomePage", "android.support.v7.app.ActionBar$Tab", 1, 5);
			try {
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject("permission",2));
			obj.add(new BasicDBObject("name",driver.getElenment("id=studio.crazybt.adventure:id/tvTripName").getText()));
			obj.add(new BasicDBObject("start_position",driver.getElenment("id=studio.crazybt.adventure:id/tvTripStartPosition").getText()));
			obj.add(new BasicDBObject("destination_summary",driver.getElenment("id=studio.crazybt.adventure:id/tvTripDestination").getText()));
			obj.add(new BasicDBObject("expense",driver.getElenment("id=studio.crazybt.adventure:id/tvTripMoney").getText()));
			andQuery.put("$and", obj);
			DBCursor cursor = coll.find(andQuery);
				while (cursor.hasNext()) {
					System.out.println(cursor.next());
					logger.info("View Ban be successfull");
					return true;
				}
			}catch(Exception e) {
				logger.info("View Ban be fail");
				// chụp màn hinh lỗi
				driver.takeScreenShotfFail("[Trip] View Ban be fail");
				return false;
			}
		}else{
			logger.info("check chuyen di voi quyen Chỉ minh tôi");
			logger.info("touch menu");			
			driver.clickByClassName("id=studio.crazybt.adventure:id/tbHomePage", "android.widget.ImageButton", 0, 10);
			logger.info("touch Trang cá nhân");			
			driver.clickByClassName("id=studio.crazybt.adventure:id/navView", "android.support.v7.widget.LinearLayoutCompat", 0, 1000);
			driver.swipingVertical("Bottom To Top");			
			try {
				List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
				obj.add(new BasicDBObject("permission",1));
				obj.add(new BasicDBObject("name",driver.getElenment("id=studio.crazybt.adventure:id/tvTripName").getText()));
				obj.add(new BasicDBObject("start_position",driver.getElenment("id=studio.crazybt.adventure:id/tvTripStartPosition").getText()));
				obj.add(new BasicDBObject("destination_summary",driver.getElenment("id=studio.crazybt.adventure:id/tvTripDestination").getText()));
				obj.add(new BasicDBObject("expense",driver.getElenment("id=studio.crazybt.adventure:id/tvTripMoney").getText()));
				andQuery.put("$and", obj);
				DBCursor cursor = coll.find(andQuery);
				while (cursor.hasNext()) {
					System.out.println(cursor.next());
					logger.info("View Chi minh toi successfull");
					driver.swipingVertical("Top To Bottom");
					driver.getElenment("id=studio.crazybt.adventure:id/tbProfile").findElementsByClassName("android.widget.ImageButton").get(0).click();
					return true;
				}
			}catch(Exception e) {
				logger.info("View Chỉ mình tôi fail");
				// chụp màn hinh lỗi
				driver.takeScreenShotfFail("[Trip] View Chỉ mình tôi fail");
				return false;
			}
		}

		return false;
	}

	// Kiểm tra validate từng trường trong form Tạo chuyến đi
	public boolean checkValidate(Driver driver, Excel excel,int column) throws Exception {
		Logger logger = Logger.getLogger("check validate form create trips");
		excel.accessSheet("Trip schedule");
		
		if(column == 8) {
		logger.info("Check validate");
		logger.info("touch tab Ban tin");		
		driver.clickByClassName("id=studio.crazybt.adventure:id/tlHomePage", "android.support.v7.app.ActionBar$Tab", 1, 50);

		logger.info("touch +");
		driver.catchCoordinates(excel, 10, 1);

		logger.info("touch  tao chuyen di");		
		driver.click("id=studio.crazybt.adventure:id/fabCreateTrip", 10);
		
		logger.info("touch quyen rieng tu");	
		driver.click("id=studio.crazybt.adventure:id/spiPrivacy", 10);
		logger.info("Check cac gia tri combo box quyen rieng tu");
		if(driver.getTextByClassName("className=android.widget.ListView", "android.widget.TextView", 2).equals(excel.getStringData(2,22))
				&& driver.getTextByClassName("className=android.widget.ListView", "android.widget.TextView", 1).equals(excel.getStringData(2,21))
				&& driver.getTextByClassName("className=android.widget.ListView", "android.widget.TextView", 0).equals(excel.getStringData(2,20)) ){			
			logger.info("Pass: Combo box valid");
			logger.info("touch Cong khai");			
			driver.clickByClassName("className=android.widget.ListView", "android.widget.LinearLayout", 2, 10);
			return true;
			}else {
				logger.info("Combo box Quyen rieng tu invalid");
				driver.takeScreenShotfFail("[Trip]_[Quyen rieng tu] invalid");
				logger.info("touch Cong khai");				
				driver.clickByClassName("className=android.widget.ListView", "android.widget.LinearLayout", 2, 10);
				return false;
			}
		}else if(column == 9) {		
			logger.info("Start check trường Tên chuyến đi");
			logger.info("Check bỏ trống");
			logger.info("touch button Dang");			
			driver.click("id=studio.crazybt.adventure:id/itemPost", 20);
			if(driver.getText("id=studio.crazybt.adventure:id/tvCreateTripNameError").equals(excel.getStringData(7, 1))) {
				logger.info("Pass: Không cho phép bỏ trống");
				return true;
			}else{
				logger.info("Fail: Cho phép bỏ trống");
				driver.takeScreenShotfFail("[Trip]_[Tên chuyến đi] blank");
				return false;
			}
		}else if(column == 10) {	
			logger.info("Check nhập > 100 ký tự");			
			driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripName", excel.getStringData(3,23),20);
			if(driver.getText("id=studio.crazybt.adventure:id/tvCreateTripNameError").equals(excel.getStringData(7, 3))){
				logger.info("Pass: Không được phép nhập > 100 ký tự");
				logger.info("Stop check trường Tên chuyến đi");				
				driver.clear("id=studio.crazybt.adventure:id/etCreateTripName");
				logger.info("Tên chuyến đi hợp lệ");
				//driver.sleep(20);
				driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripName", excel.getStringData(2,23),20);
				return true;
			}else {
				logger.info("Fail: Được phép nhập > 100 ký tự");
				driver.takeScreenShotfFail("[Trip]_[Ten chuyen di] hon 100 ky tu");
				logger.info("Stop check trường Tên chuyến đi");				
				driver.clear("id=studio.crazybt.adventure:id/etCreateTripName");
				logger.info("Tên chuyến đi hợp lệ");	
				//driver.sleep(20);
				driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripName", excel.getStringData(2,23),20);
				return false;
			}
		}else if(column == 11) {
			logger.info("Start check trường Miêu tả");
			logger.info("Check bỏ trống");
			logger.info("touch button Dang");			
			driver.click("id=studio.crazybt.adventure:id/itemPost", 10);
			try {				
				if(driver.getText("id=studio.crazybt.adventure:id/etCreateTripDescriptionError").equals(excel.getStringData(7, 1))) {
					logger.info("Fail: Không cho phép bỏ trống");
					driver.takeScreenShotfFail("[Trip]_[Mieu ta] not blank");
					return false;
				}
			}catch(Exception e) {
				logger.info("Pass: Cho phép bỏ trống");
				return true;
			}
				
		}else if(column == 12) {
			logger.info("Check nhập > 200 ký tự");			
			driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripDescription", excel.getStringData(3,24), 20);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/etCreateTripDescriptionError").equals(excel.getStringData(7, 10))) {
					logger.info("Pass: Không được phép nhập > 200 ký tự");
					logger.info("Stop check trường Miêu tả");				
					driver.clear("id=studio.crazybt.adventure:id/etCreateTripDescription");
					logger.info("Miêu tả hợp lệ");					
					driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripDescription", excel.getStringData(2,24),10);
					return true;
				}
			}catch(Exception e){
				logger.info("Fail: Được phép nhập > 200 ký tự");
				driver.takeScreenShotfFail("[Trip]_[Mieu ta] hon 200 ky tu");
				logger.info("Stop check trường Miêu tả");			
				driver.clear("id=studio.crazybt.adventure:id/etCreateTripDescription");
				logger.info("Miêu tả hợp lệ");				
				driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripDescription", excel.getStringData(2,24),10);
				return false;
			}
		}else if(column == 13) {
			logger.info("Start check trường Điểm bắt đầu");
			logger.info("Check bỏ trống");
			logger.info("touch button Dang");			
			driver.click("id=studio.crazybt.adventure:id/itemPost", 10);
			if(driver.getText("id=studio.crazybt.adventure:id/tvCreateTripStartPositionError").equals(excel.getStringData(7, 1))) {
				logger.info("Pass: Không cho phép bỏ trống");
				return true;
			}else{
				logger.info("Fail: Cho phép bỏ trống");
				driver.takeScreenShotfFail("[Trip]_[Diem bat dau] blank");
				return false;
			}
		}else if(column==14) {
			logger.info("Check nhap 2 dia diem bat dau");
			logger.info("Dia diem thu nhat");
			logger.info("toch button Ban do");
			driver.click("id=studio.crazybt.adventure:id/btnPlacesStartPosition", 100);			
			driver.sendKey("id=com.google.android.gms:id/edit_text", excel.getStringData(2,25), 5000);			
			driver.clickByClassName("id=com.google.android.gms:id/list", "android.widget.RelativeLayout", 0,1000);
			logger.info("Dia diem thu hai");
			logger.info("toch button");
			if(driver.getText("id=studio.crazybt.adventure:id/btnPlacesStartPosition").equals(excel.getStringData(10, 3))) {
				logger.info("Fail: Nhap 2 diem den");
				driver.takeScreenShotfFail("[Trip]_[Diem bat dau] nhap 2 diem den");
				logger.info("Stop check trường Điểm đến");
				return false;
			}else{
				logger.info("Pass: Khong duoc phep nhap 2 diem den");
				logger.info("Stop check trường Điểm đến");
				return true;
			}
		}else if(column ==15) {
			logger.info("Start check trường Tóm tắt điểm đến");
			logger.info("Check bỏ trống");
			logger.info("touch button Dang");			
			driver.click("id=studio.crazybt.adventure:id/itemPost", 5);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/etCreateTripDescriptionSummaryError").equals(excel.getStringData(7, 1))) {
					logger.info("Pass: Không cho phép bỏ trống");
					return true;
				}
			}catch(Exception e) {
				logger.info("Fail: Cho phép bỏ trống");
				driver.takeScreenShotfFail("[Trip]_[Tom tat diem den] duoc bo trong");
				return false;
			}
		}else if(column == 16) {
			logger.info("Check nhập > 100 ký tự");			
			driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripDescriptionSummary", excel.getStringData(3,26), 20);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/etCreateTripDescriptionSummaryError").equals(excel.getStringData(7, 3))) {
					logger.info("Pass: Không được phép nhập > 100 ký tự");
					logger.info("Stop check trường Tóm tắt điểm đến");					
					driver.clear("id=studio.crazybt.adventure:id/etCreateTripDescriptionSummary");
					logger.info("Tom tat diem den hop le");				
					driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripDescriptionSummary", excel.getStringData(2,26), 10);
					return true;
				}
			}catch(Exception e){
				logger.info("Fail: Được phép nhập > 100 ký tự");
				driver.takeScreenShotfFail("[Trip]_[Tom tat diem den] hon 100 ky tu");
				logger.info("Stop check trường Tóm tắt điểm đến");				
				driver.clear("id=studio.crazybt.adventure:id/etCreateTripDescriptionSummary");
				logger.info("Tom tat diem den hop le");				
				driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripDescriptionSummary", excel.getStringData(2,26), 10);
				return false;
			}
		}else if(column == 17) {
			logger.info("Start check trường Điểm đến");
			logger.info("Check bỏ trống");
			logger.info("touch button Dang");			
			driver.click("id=studio.crazybt.adventure:id/itemPost", 5);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/btnPlacesDestinationError").equals(excel.getStringData(7, 1))) {
					logger.info("Fail: Không cho phép bỏ trống");
					driver.takeScreenShotfFail("[Trip]_[Diem den] khong duoc bo trong");
					return false;
				}
			}catch(Exception e) {
				logger.info("Pass: Cho phép bỏ trống");
				return true;
			}
		}else if(column==18) {
			logger.info("Check nhap 2 dia diem");
			logger.info("Dia diem thu nhat");
			logger.info("toch button Ban do");
			driver.click("id=studio.crazybt.adventure:id/btnPlacesDestination", 100);			
			driver.sendKey("id=com.google.android.gms:id/edit_text",excel.getStringData(2,27), 5000);			
			driver.clickByClassName("id=com.google.android.gms:id/list", "android.widget.RelativeLayout", 0, 1000);
			if(driver.getText("id=studio.crazybt.adventure:id/btnPlacesDestination").equals(excel.getStringData(10,3))) {
				logger.info("Dia diem thu hai");
				logger.info("toch button Ban do");
				driver.click("id=studio.crazybt.adventure:id/btnPlacesDestination", 100);				
				driver.sendKey("id=com.google.android.gms:id/edit_text", excel.getStringData(3,27), 5000);				
				driver.clickByClassName("id=com.google.android.gms:id/list", "android.widget.RelativeLayout", 0, 1000);
				logger.info("Pass: Nhap 2 diem den");
				logger.info("Stop check trường Điểm đến");
				return true;
			}else {
				logger.info("Fail: Khong duoc phep nhap 2 diem den");
				driver.takeScreenShotfFail("[Trip]_[Diem den] khong duoc nhap 2 dia diem");
				logger.info("Stop check trường Điểm đến");
				return false;
			}
		}else if(column == 19) {
			logger.info("Start check trường Ngày bắt đầu");
			logger.info("Check bỏ trống");
			logger.info("touch button Dang");			
			driver.click("id=studio.crazybt.adventure:id/itemPost", 5);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/tvCreateTripStartTimeError").equals(excel.getStringData(7, 1))) {
					logger.info("Pass: Khong cho phép bỏ trống");
					return true;
				}
			}catch(Exception e) {
				logger.info("Fail: Cho phép bỏ trống");
				driver.takeScreenShotfFail("[Trip]_[Ngay bat dau] duoc bo trong");
				return false;
			}
		}else if(column == 20) {
			logger.info("Check chọn thời gian < thời gian hiện tại");			
			driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripStartTime", excel.getCellDate(3, 28), 10);			
			if(driver.getText("id=studio.crazybt.adventure:id/tvCreateTripStartTimeError").equals(excel.getStringData(7, 4))) {
				logger.info("Pass: khong the nho hon ngay hien tai");
				logger.info("Stop check trường Ngày bắt đầu");
				driver.clear("id=studio.crazybt.adventure:id/etCreateTripStartTime");
				logger.info("Ngay bat dau hop le");					
				driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripStartTime", excel.getCellDate(2, 28), 10);
				return true;
			}else {			
				logger.info("Fail: Co the nho hon ngay hien tai");
				driver.takeScreenShotfFail("[Trip]_[Ngay bat dau] nho hon hien tai");
				logger.info("Stop check trường Ngày bắt đầu");
				driver.clear("id=studio.crazybt.adventure:id/etCreateTripStartTime");
				logger.info("Ngay bat dau hop le");				
				driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripStartTime", excel.getCellDate(2, 28), 10);
				return false;
			}
		}else if(column == 21) {
			logger.info("Start check trường Ngày kết thúc");
			logger.info("Check bỏ trống");
			logger.info("touch button Dang");			
			driver.click("id=studio.crazybt.adventure:id/itemPost", 5);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/etCreateTripEndTimeError").equals(excel.getStringData(7, 1))) {
					logger.info("Fail: Khong cho phép bỏ trống");
					driver.takeScreenShotfFail("[Trip]_[Ngay ket thuc] khong duoc bo trong");
					return false;
				}
			}catch(Exception e) {
				logger.info("Pass: Cho phép bỏ trống");
				return true;
			}
		}else if(column == 22) {
			logger.info("Check chọn thời gian < thời gian hiện tại");			
			driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripEndTime", excel.getCellDate(3, 29), 10);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/etCreateTripEndTimeError").equals(excel.getStringData(7, 4))) {
					logger.info("Pass: khong the nho hon ngay hien tai");
					return true;
				}
			}catch(Exception e) {
				logger.info("Fail: Co the nho hon ngay hien tai");
				driver.takeScreenShotfFail("[Trip]_[Ngay ket thuc] nho hon hien tai");
				return false;
			}
		}else if(column == 23) {
			logger.info("Check chọn thời gian < thời gian [Bắt đầu]");			
			driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripEndTime", excel.getCellDate(4, 29), 10);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/etCreateTripEndTimeError").equals(excel.getStringData(7, 5))) {
					logger.info("Pass: khong the nho hon ngay [Bat dau]");
					logger.info("Stop check trường Ngày kết thúc");
					driver.clear("id=studio.crazybt.adventure:id/etCreateTripEndTime");
					logger.info("Ngay ket thuc hop le");					
					driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripEndTime", excel.getCellDate(2, 29), 10);
					return true;
				}
			}catch(Exception e) {
				logger.info("Fail: Co the nho hon ngay [Bat dau]");
				driver.takeScreenShotfFail("[Trip]_[Ngay ket thuc] nho hon [Bat dau]");
				logger.info("Stop check trường Ngày kết thúc");
				driver.clear("id=studio.crazybt.adventure:id/etCreateTripEndTime");
				logger.info("Ngay ket thuc hop le");				
				driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripEndTime", excel.getCellDate(2, 29), 10);
				return false;
			}
		}else if(column ==24) {
			logger.info("Start check trường [Kinh phí]");
			logger.info("Check bỏ trống");
			logger.info("touch button Dang");			
			driver.click("id=studio.crazybt.adventure:id/itemPost", 5);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/etCreateTripMoneyError").equals(excel.getStringData(7, 1))) {
					logger.info("Fail: Không cho phép bỏ trống");
					driver.takeScreenShotfFail("[Trip]_[Kinh phi] khong duoc bo trong");
					return false;
				}
			}catch(Exception e) {
				logger.info("Pass: Cho phép bỏ trống");
				return true;
			}
		}else if(column ==25) {
			logger.info("Check nhập số âm");			
			driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripMoney", excel.getStringData(3,30), 5);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/etCreateTripMoneyError").equals(excel.getStringData(7, 11))) {
					logger.info("Pass: Không dược phép nhập số âm");
					logger.info("Stop check trường [Kinh phí]");					
					driver.clear("id=studio.crazybt.adventure:id/etCreateTripMoney");
					logger.info("Kinh phí hợp lệ");					
					driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripMoney", excel.getStringData(2,30), 5);
					return true;
				}
			}catch(Exception e) {
				logger.info("Fail: Cho phép nhập số âm");
				driver.takeScreenShotfFail("[Trip]_[Kinh phi] so am");
				logger.info("Stop check trường [Kinh phí]");				
				driver.clear("id=studio.crazybt.adventure:id/etCreateTripMoney");
				logger.info("Kinh phí hợp lệ");				
				driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripMoney", excel.getStringData(2,30), 5);
				return false;
			}
		}else if(column ==26) {
			logger.info("Start check trường [Số người]");
			logger.info("Check bỏ trống");
			logger.info("touch button Dang");			
			driver.click("id=studio.crazybt.adventure:id/itemPost", 5);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/tvCreateTripMemberError").equals(excel.getStringData(7, 1))) {
					logger.info("Fail: Không cho phép bỏ trống");
					driver.takeScreenShotfFail("[Trip]_[So nguoi] khong duoc bo trong");
					return false;
				}
			}catch(Exception e) {
				logger.info("Pass: Cho phép bỏ trống");
				return true;
			}
		}else if(column ==27) {			
			logger.info("Check nhập số âm");			
			driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripMember", excel.getStringData(3,31), 5);
			if(driver.getText("id=studio.crazybt.adventure:id/tvCreateTripMemberError").equals(excel.getStringData(7, 11))) {
				logger.info("Pass: Không dược phép nhập số âm");
				return true;
			}else {				
				logger.info("Fail: Cho phép nhập số âm");
				driver.takeScreenShotfFail("[Trip]_[So nguoi] so am");
				return false;
			}
		}else if(column ==28) {
			logger.info("Check nhập số người > 1000");			
			driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripMember",excel.getStringData(4,31), 20);		
			if(driver.getText("id=studio.crazybt.adventure:id/tvCreateTripMemberError").equals(excel.getStringData(7, 13))) {
				logger.info("Pass: Không dược phép nhập số người  > 1000");
				logger.info("Stop check trường [Số người]");
				logger.info("Số người hợp lệ");				
				driver.clear("id=studio.crazybt.adventure:id/etCreateTripMember");			
				driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripMember", excel.getStringData(2,31), 5);
				return true;
			}else {			
				logger.info("Fail: Cho phép nhập số người > 1000");
				driver.takeScreenShotfFail("[Trip]_[So nguoi] lon hon 1000");
				logger.info("Stop check trường [Số người]");
				logger.info("Số người hợp lệ");				
				driver.clear("id=studio.crazybt.adventure:id/etCreateTripMember");				
				driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripMember", excel.getStringData(2,31), 5);
				return false;
			}
		}else if(column ==29) {
			driver.swipingVertical("Bottom To Top");
			logger.info("Start check trường [Phương tiện]");
			logger.info("Check bỏ trống");
			logger.info("touch button Dang");			
			driver.click("id=studio.crazybt.adventure:id/itemPost", 5);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/tvCreateTripVehicleError").equals(excel.getStringData(7, 1))) {
					logger.info("Fail: Không cho phép bỏ trống");
					driver.takeScreenShotfFail("[Trip]_[Phuong tien] not khong duoc bo trong");
					return false;
				}
			}catch(Exception e) {
				logger.info("Pass: Cho phép bỏ trống");
				return true;
			}
		}else if(column ==30) {
			logger.info("Check nhập 2 phương tiện giống nhau");			
			driver.sendKey("id=studio.crazybt.adventure:id/tetCreateTripVehicle", excel.getStringData(3,32)+"\n"+ excel.getStringData(3,32)+"\n", 20);			
			if(driver.getText("id=studio.crazybt.adventure:id/tvCreateTripVehicleError").equals(excel.getStringData(7, 9))) {
				logger.info("Pass: Không dược phép nhập phương tiện giống nhau");
				logger.info("Stop check trường [Phương tiện]");
				logger.info("Phương tiện hợp lệ");					
				driver.clear("id=studio.crazybt.adventure:id/tetCreateTripVehicle");
				driver.sendKey("id=studio.crazybt.adventure:id/tetCreateTripVehicle", excel.getStringData(2,32)+"\n", 10);
				//them doan nay demo- neu bo nhung case duoi
				logger.info("touch button Dang");				
				driver.click("id=studio.crazybt.adventure:id/itemPost", 100);
				return true;
			}else {			
				logger.info("Fail: Cho phép nhập phương tiện giống nhau");
				driver.takeScreenShotfFail("[Trip]_[Phuong tien] giong nhau");
				logger.info("Stop check trường [Phương tiện]");
				logger.info("Phương tiện hợp lệ");				
				driver.clear("id=studio.crazybt.adventure:id/tetCreateTripVehicle");
				driver.sendKey("id=studio.crazybt.adventure:id/tetCreateTripVehicle", excel.getStringData(2,32)+"\n", 10);
				//them doan nay demo- neu bo nhung case duoi
				logger.info("touch button Dang");				
				driver.click("id=studio.crazybt.adventure:id/itemPost", 100);
				return false;
			}
		}else if(column ==31) {
			logger.info("Start check trường [Thêm chi tiết]");
			logger.info("Check bỏ trống");
			logger.info("touch [Chuẩn bị]");			
			driver.click("id=studio.crazybt.adventure:id/etCreateTripToolbox", 5);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/btnAddTripRouteError").equals(excel.getStringData(7, 1))) {
					logger.info("Fail: Không cho phép bỏ trống");
					driver.takeScreenShotfFail("[Trip]_[Them chi tiet] khong duoc bo trong");
					logger.info("touch Them chi tiet");					
					driver.click("id=studio.crazybt.adventure:id/btnAddTripRoute", 5);
					return false;
				}
			}catch(Exception e) {
				logger.info("Pass: Cho phép bỏ trống");
				logger.info("touch Them chi tiet");
				driver.click("id=studio.crazybt.adventure:id/btnAddTripRoute", 5);
				return true;
			}
		}else if(column == 32) {
			logger.info("Check thời gian bat dau < thời gian hiện tại");			
			driver.sendKey("id=studio.crazybt.adventure:id/etStartTimeRoute", excel.getCellDate(3, 33), 10);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/etStartTimeRouteError").equals(excel.getStringData(7, 4))) {
					logger.info("Pass: khong the nho hon ngay hien tai");
					return true;
				}
			}catch(Exception e) {
				logger.info("Fail: Co the nho hon ngay hien tai");
				driver.takeScreenShotfFail("[Trip]_[Them chi tiet]_[Thoi gian bat dau] nho hon hien tai");
				return false;
			}
		}else if(column == 33) {
			logger.info("Check thời gian bắt đầu < thời gian [Bắt đầu]");			
			driver.sendKey("id=studio.crazybt.adventure:id/etStartTimeRoute", excel.getCellDate(4, 33), 10);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/etStartTimeRouteError").equals(excel.getStringData(7, 5))) {
					logger.info("Pass: khong the nho hon ngay [Bat dau]");
					driver.clear("id=studio.crazybt.adventure:id/etStartTimeRoute");
					logger.info("Ngay bat dau hop le");					
					driver.sendKey("id=studio.crazybt.adventure:id/etStartTimeRoute", excel.getCellDate(2, 33), 10);
					return true;
				}
			}catch(Exception e) {
				logger.info("Fail: Co the nho hon ngay [Bat dau]");
				driver.takeScreenShotfFail("[Trip]_[Them chi tiet]_[Thoi gian bat dau] nho hon [Bat dau]");
				driver.clear("id=studio.crazybt.adventure:id/etStartTimeRoute");
				logger.info("Ngay bat dau hop le");
				driver.sendKey("id=studio.crazybt.adventure:id/etStartTimeRoute", excel.getCellDate(2, 33), 10);
				return false;
			}
		}else if(column == 34) {
			logger.info("Check thời gian kết thúc < thời gian hiện tại");			
			driver.sendKey("id=studio.crazybt.adventure:id/etEndTimeRoute", excel.getCellDate(3, 34), 10);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/etEndTimeRouteError").equals(excel.getStringData(7, 4))) {
					logger.info("Pass: khong the nho hon ngay hien tai");
					return true;
				}
			}catch(Exception e) {
				logger.info("Fail: Co the nho hon ngay hien tai");
				driver.takeScreenShotfFail("[Trip]_[Them chi tiet]_[Ngay ket thuc] nho hon hien tai");
				return false;
			}
		}else if(column == 35) {
			logger.info("Check thời gian kết thúc > thời gian [Kết thúc]");			
			driver.sendKey("id=studio.crazybt.adventure:id/etEndTimeRoute", excel.getCellDate(4, 34), 10);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/etEndTimeRouteError").equals(excel.getStringData(7, 6))) {
					logger.info("Pass: khong the lon hon ngay [Ket thuc]");
					driver.clear("id=studio.crazybt.adventure:id/etEndTimeRoute");
					logger.info("Ngay ket thuc hop le");					
					driver.sendKey("id=studio.crazybt.adventure:id/etEndTimeRoute", excel.getCellDate(2, 34), 10);
					return true;
				}
			}catch(Exception e) {
				logger.info("Fail: Co the lon hon ngay [Ket thuc]");
				driver.takeScreenShotfFail("[Trip]_[Them chi tiet]_[Ngay ket thuc] lon hon [Ket thuc]");
				driver.clear("id=studio.crazybt.adventure:id/etEndTimeRoute");
				logger.info("Ngay ket thuc hop le");
				driver.sendKey("id=studio.crazybt.adventure:id/etEndTimeRoute", excel.getCellDate(2, 34), 10);
				return false;
			}
		}else if(column == 36) {
			logger.info("Check nhập [Tiêu đề] > 100 ký tự");			
			driver.sendKey("id=studio.crazybt.adventure:id/etTitleRoute", excel.getStringData(3,35), 20);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/etTitleRouteError").equals(excel.getStringData(7, 3))) {
					logger.info("Pass: Không được phép nhập > 100 ký tự");					
					driver.clear("id=studio.crazybt.adventure:id/etTitleRoute");
					logger.info("Tiêu đề hợp lệ");					
					driver.sendKey("id=studio.crazybt.adventure:id/etTitleRoute", excel.getStringData(2,35), 10);
					return true;
				}
			}catch(Exception e){
				logger.info("Fail: Được phép nhập > 100 ký tự");
				driver.takeScreenShotfFail("[Trip]_[Them chi tiet]_[Tieu de] hon 100 ky tu");				
				driver.clear("id=studio.crazybt.adventure:id/etTitleRoute");
				logger.info("Tiêu đề hợp lệ");				
				driver.sendKey("id=studio.crazybt.adventure:id/etTitleRoute", excel.getStringData(2,35), 10);
				return false;
			}
		}else if(column == 37) {
			logger.info("Check nhập [Nội dung] > 100 ký tự");			
			driver.sendKey("id=studio.crazybt.adventure:id/etTitleRoute", excel.getStringData(3,36), 20);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/etContentRouteError").equals(excel.getStringData(7, 3))) {
					logger.info("Pass: Không được phép nhập > 100 ký tự");
					logger.info("Stop check trường [Thêm chi tiết]");					
					driver.clear("id=studio.crazybt.adventure:id/etContentRoute");
					logger.info("Nội dung hợp lệ");					
					driver.sendKey("id=studio.crazybt.adventure:id/etContentRoute", excel.getStringData(2,36), 10);
					return true;
				}
			}catch(Exception e){
				logger.info("Fail: Được phép nhập > 100 ký tự");
				driver.takeScreenShotfFail("[Trip]_[Them chi tiet]_[Noi dung] hon 100 ky tu");
				logger.info("Stop check trường [Thêm chi tiết]");				
				driver.clear("id=studio.crazybt.adventure:id/etContentRoute");
				logger.info("Nội dung hợp lệ");			
				driver.sendKey("id=studio.crazybt.adventure:id/etContentRoute", excel.getStringData(2,36), 10);
				return false;
			}
		}else if(column == 38) {
			driver.swipingVertical("Bottom To Top");
			logger.info("Start check trường [Chuẩn bị]");
			logger.info("Check bỏ trống");
			logger.info("touch [Lưu ý]");			
			driver.click("id=studio.crazybt.adventure:id/etCreateTripNote", 5);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/etCreateTripToolboxError").equals(excel.getStringData(7, 1))) {
					logger.info("Fail: Không cho phép bỏ trống");
					driver.takeScreenShotfFail("[Trip]_[Chuan bi] khong duoc bo trong");
					return false;
				}
			}catch(Exception e) {
				logger.info("Pass: Cho phép bỏ trống");
				return true;
			}
				
		}else if(column == 39) {
			logger.info("Check nhập > 200 ký tự");			
			driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripToolbox", excel.getStringData(3,37), 20);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/etCreateTripToolboxError").equals(excel.getStringData(7, 10))) {
					logger.info("Pass: Không được phép nhập > 200 ký tự");
					logger.info("Stop check trường Miêu tả");					
					driver.clear("id=studio.crazybt.adventure:id/etCreateTripToolbox");
					logger.info("Chuẩn bị hợp lệ");					
					driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripToolbox", excel.getStringData(2,37), 10);
					return true;
				}
			}catch(Exception e){
				logger.info("Fail: Được phép nhập > 200 ký tự");
				driver.takeScreenShotfFail("[Trip]_[Chuan bi] hon 200 ky tu");
				logger.info("Stop check trường Miêu tả");				
				driver.clear("id=studio.crazybt.adventure:id/etCreateTripToolbox");
				logger.info("Chuẩn bị hợp lệ");				
				driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripToolbox", excel.getStringData(2,37), 10);
				return false;
			}
		}else if(column == 40) {
			logger.info("Start check trường [Lưu ý]");
			logger.info("Check bỏ trống");
			logger.info("touch [Chuẩn bị]");			
			driver.click("id=studio.crazybt.adventure:id/etCreateTripToolbox", 5);
			try {
				if(driver.getText("id=studio.crazybt.adventure:id/etCreateTripNoteError").equals(excel.getStringData(7, 1))) {
					logger.info("Fail: Không cho phép bỏ trống");
					driver.takeScreenShotfFail("[Trip]_[Luu y] khong duoc bo trong");
					return false;
				}
			}catch(Exception e) {
				logger.info("Pass: Cho phép bỏ trống");
				return true;
			}
				
		}else if(column == 41) {
			logger.info("Check nhập > 200 ký tự");			
			driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripNote", excel.getStringData(3,38), 20);
			try {
				if(driver.getElenment("id=studio.crazybt.adventure:id/etCreateTripNoteError").getText().equals(excel.getStringData(7, 10))) {
					logger.info("Pass: Không được phép nhập > 200 ký tự");
					logger.info("Stop check trường [Lưu ý]");					
					driver.clear("id=studio.crazybt.adventure:id/etCreateTripNote");
					logger.info("Chuẩn bị hợp lệ");					
					driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripNote", excel.getStringData(2,38), 10);
					//logger.info("touch button Dang");					
					//driver.click("id=studio.crazybt.adventure:id/itemPost", 100);
					return true;
				}
			}catch(Exception e){
				logger.info("Fail: Được phép nhập > 200 ký tự");
				driver.takeScreenShotfFail("[Trip]_[Luu y] hon 200 ky tu");
				logger.info("Stop check trường [Lưu ý]");				
				driver.clear("id=studio.crazybt.adventure:id/etCreateTripNote");
				logger.info("Chuẩn bị hợp lệ");				
				driver.sendKey("id=studio.crazybt.adventure:id/etCreateTripNote", excel.getStringData(2,38), 10);
				//logger.info("touch button Dang");				
				//driver.click("id=studio.crazybt.adventure:id/itemPost", 100);
				return false;
			}
		}
		
		// Luồng này kết thúc sẽ tạo ra 1 chuyến đi để phục vụ cho luồng chạy tiếp theo (người tạo: trang thư)
		return true;
		
	}
	
	public boolean joinAndInterested(Driver driver, Excel excel,int column)throws Exception  {
		Logger logger = Logger.getLogger("joinAndInterested");
		excel.accessSheet("Trip Schedule");
		Adventure_Common ac = new Adventure_Common();
		if(column == 42) {
			try {
				logger.info("check demo 1 yêu cầu tham gia va quan tâm");
				ac.logOut(driver);
				logger.info("login demo1");
				ac.logIn(driver, excel, 1, 43);
				logger.info("touch chuyen di");
				driver.getElenment("id=studio.crazybt.adventure:id/tvTripName").click();
				if(driver.getElenment("id=studio.crazybt.adventure:id/tvScheduleJoin").getText().equals(excel.getStringData(4, 43))) {
					logger.info("touch Tham gia");
					driver.getElenment("id=studio.crazybt.adventure:id/tvScheduleJoin").click();
					logger.info("gioi thieu ban than");
					driver.getElenment("id=studio.crazybt.adventure:id/etMessageRequestMember").sendKeys(excel.getStringData(3, 43));
					logger.info("touch Cap nhat");
					driver.getElenment("id=studio.crazybt.adventure:id/btnConfirmRequest").click();
				}
				logger.info("touch quan tam");
				driver.getElenment("id=studio.crazybt.adventure:id/tvScheduleInterested").click();
				logger.info("quay lại màn hình trước");
				driver.getElenment("id=studio.crazybt.adventure:id/tbTrip").findElementsByClassName("android.widget.ImageButton").get(0).click();
				logger.info("Pass: demo 1 gửi yêu cầu tham gia và quan tâm thành công");
				return true;
			}catch(Exception e){
				logger.info("Fail: demo 1 gửi yêu cầu tham gia và quan tâm không thành công");
				driver.takeScreenShotfFail("[joinAndInterested]_demo1 gui yeu cau loi");
				return false;
			}
		}else if(column == 43) {
			try {
				logger.info("check demo 2 yêu cầu tham gia va quan tâm");
				ac.logOut(driver);
				logger.info("login demo2");
				ac.logIn(driver, excel, 1, 44);
				logger.info("touch chuyen di");
				driver.getElenment("id=studio.crazybt.adventure:id/tvTripName").click();
				if(driver.getElenment("id=studio.crazybt.adventure:id/tvScheduleJoin").getText().equals(excel.getStringData(4, 43))) {
					logger.info("touch Tham gia");
					driver.getElenment("id=studio.crazybt.adventure:id/tvScheduleJoin").click();
					logger.info("gioi thieu ban than");
					driver.getElenment("id=studio.crazybt.adventure:id/etMessageRequestMember").sendKeys(excel.getStringData(3, 44));
					logger.info("touch Cap nhat");
					driver.getElenment("id=studio.crazybt.adventure:id/btnConfirmRequest").click();
				}
				logger.info("touch quan tam");
				driver.getElenment("id=studio.crazybt.adventure:id/tvScheduleInterested").click();
				logger.info("quay lại màn hình trước");
				driver.getElenment("id=studio.crazybt.adventure:id/tbTrip").findElementsByClassName("android.widget.ImageButton").get(0).click();
				logger.info("Pass: demo 2 gửi yêu cầu tham gia và quan tâm thành công");
				return true;
			}catch(Exception e){
				logger.info("Fail: demo 2 gửi yêu cầu tham gia và quan tâm không thành công");
				driver.takeScreenShotfFail("[joinAndInterested]_demo2 gui yeu cau loi");
				return false;
			}
		}else if(column == 44){
			try {
				logger.info("check demo 3 yêu cầu tham gia va quan tâm");
				ac.logOut(driver);
				logger.info("login demo3");
				ac.logIn(driver, excel, 1, 45);
				logger.info("touch chuyen di");
				driver.getElenment("id=studio.crazybt.adventure:id/tvTripName").click();
				if(driver.getElenment("id=studio.crazybt.adventure:id/tvScheduleJoin").getText().equals(excel.getStringData(4, 43))) {
					logger.info("touch Tham gia");
					driver.getElenment("id=studio.crazybt.adventure:id/tvScheduleJoin").click();
					logger.info("gioi thieu ban than");
					driver.getElenment("id=studio.crazybt.adventure:id/etMessageRequestMember").sendKeys(excel.getStringData(3, 45));
					logger.info("touch Cap nhat");
					driver.getElenment("id=studio.crazybt.adventure:id/btnConfirmRequest").click();
				}
				logger.info("touch quan tam");
				driver.getElenment("id=studio.crazybt.adventure:id/tvScheduleInterested").click();
				logger.info("Pass: demo 3 gửi yêu cầu tham gia và quan tâm thành công");
				return true;
			}catch(Exception e){
				logger.info("Fail: demo 3 gửi yêu cầu tham gia và quan tâm không thành công");
				driver.takeScreenShotfFail("[joinAndInterested]_demo3 gui yeu cau loi");
				return false;
			}
		}else if(column==45) {
			logger.info("demo 3 hủy yêu cầu tham gia va quan tâm");
			try {
				//if(driver.getElenment("id=studio.crazybt.adventure:id/tvScheduleJoin").getText().equals(excel.getStringData(4, 44))) {
					logger.info("touch Hủy yêu cầu");
					driver.getElenment("id=studio.crazybt.adventure:id/tvScheduleJoin").click();
					logger.info("touch button Hủy yêu cầu");
					driver.getElenment("id=android:id/button1").click();
				//}
				logger.info("touch Quan tam");
				driver.getElenment("id=studio.crazybt.adventure:id/tvScheduleInterested").click();
				logger.info("touch Hủy quan tam");
				driver.getElenment("id=android:id/button1").click();
				logger.info("touch button quay lại màn hình tab Công khai");
				driver.getElenment("id=studio.crazybt.adventure:id/tbTrip").findElement(By.xpath("//*[@class='android.widget.ImageButton' and @index='0']")).click();
				logger.info("Pass: demo 3 hủy yêu cầu tham gia va quan tâm thành công");
				return true;
			}catch(Exception e){
				logger.info("Fail: demo 3 hủy yêu cầu tham gia va quan tâm không thành công");
				driver.takeScreenShotfFail("[joinAndInterested]_demo3 huy yeu cau loi");
				logger.info("touch button quay lại màn hình tab Công khai");
				driver.getElenment("id=studio.crazybt.adventure:id/tbTrip").findElement(By.xpath("//*[@class='android.widget.ImageButton' and @index='0']")).click();
				return false;
			}
		}else if(column ==46) {
			logger.info("check đồng ý yêu cầu demo1");
			ac.logOut(driver);
			logger.info("login trang thư - người tạo");
			ac.logIn(driver, excel, 1, 42);
			logger.info("touch chuyen di");
			driver.getElenment("id=studio.crazybt.adventure:id/tvTripName").click();
			
			driver.ScrollTabs();
			driver.sleep(5);
			logger.info("touch tab Yêu cầu");
			driver.getElenment("id=studio.crazybt.adventure:id/tlTrip").findElement(By.xpath("//*[@class='android.support.v7.app.ActionBar$Tab' and @index='5']")).click();
			logger.info("touch chap nhan");
			driver.getElenment("id=studio.crazybt.adventure:id/rvRequestMemberTrip").findElement(By.xpath("//*[@class='android.widget.FrameLayout' and @index='1']")).findElementById("studio.crazybt.adventure:id/btnLeftFriendTemplate").click();
			driver.sleep(100);
			logger.info("take screen short and check toast message");	
			String image = driver.takeScreenShot();
			System.out.println(driver.takeScreenShot());
			Tesseract tessInst = new Tesseract();
			tessInst.setDatapath("D:\\KhoaLuanTotNghiep\\Tess4J");
			tessInst.setLanguage("vie");
	        try {
	            String result= tessInst.doOCR(new File(image));
	            if(result.toLowerCase().contains(excel.getStringData(5,43).toLowerCase())) {
	            	logger.info("pass");
	            	return true;
	            }
	        } catch (TesseractException e) {
	            System.err.println(e.getMessage());
	        }
		
		}else if(column ==47) {
			logger.info("check đồng ý yêu cầu demo2");
			logger.info("touch chap nhan");
			driver.getElenment("id=studio.crazybt.adventure:id/rvRequestMemberTrip").findElement(By.xpath("//*[@class='android.widget.FrameLayout' and @index='0']")).findElementById("studio.crazybt.adventure:id/btnLeftFriendTemplate").click();
			driver.sleep(100);
			logger.info("take screen short and check toast message");	
			String image = driver.takeScreenShot();
			System.out.println(driver.takeScreenShot());
			Tesseract tessInst = new Tesseract();
			tessInst.setDatapath("D:\\KhoaLuanTotNghiep\\Tess4J");
			tessInst.setLanguage("vie");
	        try {
	            String result= tessInst.doOCR(new File(image));
	            if(result.toLowerCase().contains(excel.getStringData(5,44).toLowerCase())) {
	            	logger.info("pass");
	            	return true;
	            }
	        } catch (TesseractException e) {
	            System.err.println(e.getMessage());
	        }
		}else if(column ==48) {
			logger.info("check demo2 rời khỏi chuyến đi");
			try {
				logger.info("touch button quay lại màn hình tab Công khai");
				driver.getElenment("id=studio.crazybt.adventure:id/tbTrip").findElement(By.xpath("//*[@class='android.widget.ImageButton' and @index='0']")).click();
				ac.logOut(driver);
				logger.info("login demo2");
				ac.logIn(driver, excel, 1, 44);
				logger.info("touch chuyen di");
				driver.getElenment("id=studio.crazybt.adventure:id/tvTripName").click();
				if(driver.getElenment("id=studio.crazybt.adventure:id/tvScheduleJoin").getText().equals(excel.getStringData(4, 45))) {
					logger.info("touch Rời khỏi chuyến đi");
					driver.getElenment("id=studio.crazybt.adventure:id/tvScheduleJoin").click();
					logger.info("touch button rời khỏi");
					driver.getElenment("id=android:id/button1").click();
				}
				logger.info("quay lại màn hình trước");
				driver.getElenment("id=studio.crazybt.adventure:id/tbTrip").findElementsByClassName("android.widget.ImageButton").get(0).click();
				logger.info("Pass: demo2 rời khỏi chuyến đi thành công");
				return true;
			}catch(Exception e){
				logger.info("Fail: demo 2 khong the roi chuyen di");
				driver.takeScreenShotfFail("[joinAndInterested]_demo2 không thể rời khỏi chuyến đi");
				logger.info("quay lại màn hình trước");
				driver.getElenment("id=studio.crazybt.adventure:id/tbTrip").findElementsByClassName("android.widget.ImageButton").get(0).click();
				return false;
			}
		}
		return true;
	}
	
	public boolean checkDiscussion(Driver driver, Excel excel,int column)throws Exception  {
		Logger logger = Logger.getLogger("checkDiscussion");
		excel.accessSheet("Trip Schedule");
		Adventure_Common ac = new Adventure_Common();
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB db = mongoClient.getDB("adventure");
		DBCollection coll = db.getCollection("status");
		BasicDBObject andQuery = new BasicDBObject();
		if(column==49) {
			logger.info("Check tạo bài thảo luận thành công");
			ac.logOut(driver);
			logger.info("login trang thư - người tạo");
			ac.logIn(driver, excel, 1,42);
			//excel.accessSheet("Trip Schedule");
			logger.info("touch chuyen di");
			driver.getElenment("id=studio.crazybt.adventure:id/tvTripName").click();
			logger.info("touch tab Thảo luận");
			driver.getElenment("id=studio.crazybt.adventure:id/tlTrip").findElementsByClassName("android.support.v7.app.ActionBar$Tab").get(2).click();
			logger.info("touch +");
			driver.getElenment("id=studio.crazybt.adventure:id/fabCreateDiscussTrip").click();
			logger.info("send Nội dung");
			driver.getElenment("id=studio.crazybt.adventure:id/eetContentStatus").sendKeys(excel.getStringData(1, 48));
			logger.info("touch button Đăng");
			driver.getElenment("id=studio.crazybt.adventure:id/itemPost").click();
			driver.sleep(1000);
			
			logger.info("take screen short and check toast message");	
			String image = driver.takeScreenShot();
			//System.out.println(image);
			Tesseract tessInst = new Tesseract();
			tessInst.setDatapath("D:\\KhoaLuanTotNghiep\\Tess4J");
			tessInst.setLanguage("vie");
			String result= tessInst.doOCR(new File(image));
			//System.out.println(result);
			try {
	            if(result.toLowerCase().contains(excel.getStringData(7,14).toLowerCase())) {
	            	logger.info("pass");
	            	return true;
	            }
	        } catch (TesseractException e) {
	            System.err.println(e.getMessage());
	            logger.info("fail");
	    		return false;
	        }
		}else if(column==50) {
			logger.info("check thành viên không trong chuyến đi (demo2) xem bài thảo luận");
			logger.info("quay lại màn hình trước");
			driver.getElenment("id=studio.crazybt.adventure:id/tbTrip").findElementsByClassName("android.widget.ImageButton").get(0).click();
			ac.logOut(driver);
			logger.info("login demo2");
			ac.logIn(driver, excel, 1,44);
			//excel.accessSheet("Trip Schedule");
			logger.info("touch chuyen di");
			driver.getElenment("id=studio.crazybt.adventure:id/tvTripName").click();
			logger.info("touch tab Thảo luận");
			driver.getElenment("id=studio.crazybt.adventure:id/tlTrip").findElementsByClassName("android.support.v7.app.ActionBar$Tab").get(2).click();
			try {
				if(driver.getElenment("id=studio.crazybt.adventure:id/etvContentStatus").getText().equals(excel.getStringData(1, 48))) {
					logger.info("Fail: Duoc xem bai thao luan");
					driver.takeScreenShotfFail("[Discussion]_Khong phai thanh vien duoc xem bai thao luan");
					return false;
				}//else {
			}catch(Exception e){
				logger.info("Pass: Khong duoc xem bai thao luan");
				return true;
			}
		}else if(column==51) {
			logger.info("check thành viên trong chuyến đi (demo1) xem bài thảo luận");
			logger.info("quay lại màn hình trước");
			driver.getElenment("id=studio.crazybt.adventure:id/tbTrip").findElementsByClassName("android.widget.ImageButton").get(0).click();
			ac.logOut(driver);
			logger.info("login demo1");
			ac.logIn(driver, excel, 1,43);
			//excel.accessSheet("Trip Schedule");
			logger.info("touch chuyen di");
			driver.getElenment("id=studio.crazybt.adventure:id/tvTripName").click();
			logger.info("touch tab Thảo luận");
			driver.getElenment("id=studio.crazybt.adventure:id/tlTrip").findElementsByClassName("android.support.v7.app.ActionBar$Tab").get(2).click();
			try {
				if(driver.getElenment("id=studio.crazybt.adventure:id/etvContentStatus").getText().equals(excel.getStringData(1, 48))) {
					logger.info("Pass: Duoc xem bai thao luan");
					return true;
				}//else {
			}catch(Exception e){
				logger.info("Fail: Khong duoc xem bai thao luan");
				driver.takeScreenShotfFail("[Discussion]_Thanh vien khong duoc xem bai thao luan");
				return false;
			}
		}else if(column==52) {
			logger.info("check thành viên trong chuyến đi (demo1) Thích bài thảo luận");
			logger.info("touch Thích");
			driver.getElenment("id=studio.crazybt.adventure:id/llLike").click();
			driver.sleep(20);
			try {
				if(driver.getElenment("id=studio.crazybt.adventure:id/tvCountLike").getText().equals("1")) {
					logger.info("Pass: Thích thành công");
					return true;
				}//else {
			}catch(Exception e){
				logger.info("Fail: Thích không thành công");
				driver.takeScreenShotfFail("[Discussion]_Thanh vien thich bai thao luan khong thanh cong");
				return false;
			}
		}else if(column==53) {
			logger.info("check thành viên trong chuyến đi (demo1) Bình luận bài thảo luận");
			logger.info("touch Bình luận");
			driver.getElenment("id=studio.crazybt.adventure:id/tvComment").click();
			logger.info("send Binh luan");
			driver.getElenment("id=studio.crazybt.adventure:id/eetComment").sendKeys(excel.getStringData(1, 49));
			logger.info("touch send");
			driver.getElenment("id=studio.crazybt.adventure:id/ivSendComment").click();
			driver.swipingHorizontal("Left to Right");
			driver.sleep(20);
			//try {
				if(driver.getElenment("id=studio.crazybt.adventure:id/tvCountComment").getText().equals("1")) {
					logger.info("Pass: Bình luận thành công");
					logger.info("quay lại màn hình trước");
					driver.getElenment("id=studio.crazybt.adventure:id/tbTrip").findElementsByClassName("android.widget.ImageButton").get(0).click();
					return true;
				}else {
			//}catch(Exception e){
				logger.info("Fail: Bình luận không thành công");
				driver.takeScreenShotfFail("[Discussion]_Thanh vien binh luan bai thao luan khong thanh cong");
				logger.info("quay lại màn hình trước");
				driver.getElenment("id=studio.crazybt.adventure:id/tbTrip").findElementsByClassName("android.widget.ImageButton").get(0).click();
				return false;
			}
		}else if(column==54) {
			logger.info("check người tạo bài thảo luận xem thông báo Thích");
			ac.logOut(driver);
			logger.info("login trang thư - người tạo");
			ac.logIn(driver, excel,1 ,42);
			logger.info("touch tab Thông báo");
			driver.getElenment("id=studio.crazybt.adventure:id/tlHomePage").findElementsByClassName("android.support.v7.app.ActionBar$Tab").get(3).click();
			logger.info("touch vào thông báo Thích");
			driver.getElenment("id=studio.crazybt.adventure:id/rvNotifications").findElementsByClassName("android.widget.RelativeLayout").get(1).click();
			try {
				if(driver.getElenment("id=studio.crazybt.adventure:id/tvContentStatus").getText().equals(excel.getStringData(1, 48))) {
					driver.getElenment("id=studio.crazybt.adventure:id/tvCountLike").click();
					if(driver.getElenment("id=studio.crazybt.adventure:id/tvProfileName").getText().equals("nguyen demo1")) {
						logger.info("Pass: Xem thông báo Thích thành công");
						driver.swipingHorizontal("Left to Right");
						return true;
					}
				}
			}catch(Exception e) {
				logger.info("Fail: Xem thông báo Thích không thành công");
				driver.takeScreenShotfFail("[Discussion]_Nguoi tao xem thong bao thich khong thanh cong");
				driver.swipingHorizontal("Left to Right");
				return false;
			}
		}else if(column==55) {
			logger.info("check người tạo bài thảo luận xem thông báo Bình luận");
			logger.info("touch vào thông báo Bình luận");
			driver.getElenment("id=studio.crazybt.adventure:id/rvNotifications").findElementsByClassName("android.widget.RelativeLayout").get(0).click();
			try {
				if(driver.getElenment("id=studio.crazybt.adventure:id/tvContentStatus").getText().equals(excel.getStringData(1, 48))) {
					driver.getElenment("id=studio.crazybt.adventure:id/tvCountComment").click();
					if(driver.getElenment("id=studio.crazybt.adventure:id/etvContentComment").getText().equals(excel.getStringData(1, 49))) {
						logger.info("Pass: Xem thông báo Bình luận thành công");
						driver.swipingHorizontal("Left to Right");
						return true;
					}
				}
			}catch(Exception e) {
				logger.info("Fail: Xem thông báo Bình luận không thành công");
				driver.takeScreenShotfFail("[Discussion]_Nguoi tao xem thong bao Binh luan khong thanh cong");
				driver.swipingHorizontal("Left to Right");
				return false;
			}
		}
		return true;
	}
	
	public boolean createDiary(Driver driver, Excel excel,int column)throws Exception  {
		Logger logger = Logger.getLogger("checkDiary");
		excel.accessSheet("Trip Schedule");
		
		if(column == 56) {
			logger.info("touch tab Công khai");
			driver.getElenment("id=studio.crazybt.adventure:id/tlHomePage").findElementsByClassName("android.support.v7.app.ActionBar$Tab").get(0).click();
			logger.info("touch chuyen di");
			driver.getElenment("id=studio.crazybt.adventure:id/tvTripName").click();
			logger.info("touch tab Nhật ký");
			driver.getElenment("id=studio.crazybt.adventure:id/tlTrip").findElementsByClassName("android.support.v7.app.ActionBar$Tab").get(3).click();
			logger.info("touch +");
			driver.getElenment("id=studio.crazybt.adventure:id/fabCreateDiaryTrip").click();
			logger.info("touch chọn quyền");
			driver.getElenment("id=studio.crazybt.adventure:id/spiPrivacy").click();
			logger.info("touch Công khai");
			driver.getElenment("className=android.widget.ListView").findElementsByClassName("android.widget.LinearLayout").get(2).click();
			logger.info("send Tiêu đề");
			driver.getElenment("id=studio.crazybt.adventure:id/etTitleDiaryTrip").sendKeys(excel.getStringData(2, 53));
			logger.info("send Nội dung");
			driver.getElenment("id=studio.crazybt.adventure:id/etContentDiaryTrip").sendKeys(excel.getStringData(2, 54));
			logger.info("Thêm ảnh");
			logger.info("touch button Thêm ảnh");
			driver.getElenment("id=studio.crazybt.adventure:id/btnAddImageDiaryTrip").click();
			logger.info("chọn album");
			driver.getElenment("id=studio.crazybt.adventure:id/recyclerView").findElementsByClassName("android.widget.FrameLayout").get(0).click();
			logger.info("chọn 3 ảnh - dòng 1");
			driver.getElenment("id=studio.crazybt.adventure:id/recyclerView").findElementsByClassName("android.widget.FrameLayout").get(0).click();
			driver.getElenment("id=studio.crazybt.adventure:id/recyclerView").findElementsByClassName("android.widget.FrameLayout").get(1).click();
			driver.getElenment("id=studio.crazybt.adventure:id/recyclerView").findElementsByClassName("android.widget.FrameLayout").get(2).click();
			logger.info("touch button Done");
			driver.getElenment("id=studio.crazybt.adventure:id/toolbar").findElement(By.xpath("//*[@class='android.support.v7.widget.LinearLayoutCompat' and @index='2']")).findElement(By.xpath("//*[@class='android.widget.TextView' and @index='1']")).click();
			driver.swipingVertical("Bottom To Top");
			logger.info("touch button Thêm chi tiết");
			driver.getElenment("id=studio.crazybt.adventure:id/btnAddDetailDiaryTrip").click();
			driver.swipingVertical("Bottom To Top");
			logger.info("send Ngày - Thêm chi tiết");
			driver.getElenment("id=studio.crazybt.adventure:id/etDateDetailDiaryTrip").sendKeys(excel.getCellDate1(2, 56));
			logger.info("send Tiêu đề - Thêm chi tiết");
			driver.getElenment("id=studio.crazybt.adventure:id/etTitleDetailDiaryTrip").sendKeys(excel.getStringData(2, 57));
			logger.info("send Nội dung - Thêm chi tiết");
			driver.getElenment("id=studio.crazybt.adventure:id/etContentDetailDiaryTrip").sendKeys(excel.getStringData(2, 58));
		}else if(column == 57){
			logger.info("touch +");
			driver.getElenment("id=studio.crazybt.adventure:id/fabCreateDiaryTrip").click();
			logger.info("touch chọn quyền");
			driver.getElenment("id=studio.crazybt.adventure:id/spiPrivacy").click();
			logger.info("touch Thành viên chuyến đi");
			driver.getElenment("className=android.widget.ListView").findElementsByClassName("android.widget.LinearLayout").get(1).click();
			logger.info("send Tiêu đề");
			driver.getElenment("id=studio.crazybt.adventure:id/etTitleDiaryTrip").sendKeys(excel.getStringData(3, 53));
			logger.info("send Nội dung");
			driver.getElenment("id=studio.crazybt.adventure:id/etContentDiaryTrip").sendKeys(excel.getStringData(3, 54));
			logger.info("Thêm ảnh");
			logger.info("touch button Thêm ảnh");
			driver.getElenment("id=studio.crazybt.adventure:id/btnAddImageDiaryTrip").click();
			logger.info("chọn album");
			driver.getElenment("id=studio.crazybt.adventure:id/recyclerView").findElementsByClassName("android.widget.FrameLayout").get(0).click();
			logger.info("chọn 3 ảnh - dòng 2");;
			driver.getElenment("id=studio.crazybt.adventure:id/recyclerView").findElementsByClassName("android.widget.FrameLayout").get(3).click();
			driver.getElenment("id=studio.crazybt.adventure:id/recyclerView").findElementsByClassName("android.widget.FrameLayout").get(4).click();
			driver.getElenment("id=studio.crazybt.adventure:id/recyclerView").findElementsByClassName("android.widget.FrameLayout").get(5).click();
			logger.info("touch button Done");
			driver.getElenment("id=studio.crazybt.adventure:id/toolbar").findElement(By.xpath("//*[@class='android.support.v7.widget.LinearLayoutCompat' and @index='2']")).findElement(By.xpath("//*[@class='android.widget.TextView' and @index='1']")).click();
			driver.swipingVertical("Bottom To Top");
			logger.info("touch button Thêm chi tiết");
			driver.getElenment("id=studio.crazybt.adventure:id/btnAddDetailDiaryTrip").click();
			driver.swipingVertical("Bottom To Top");
			logger.info("send Ngày - Thêm chi tiết");
			driver.getElenment("id=studio.crazybt.adventure:id/etDateDetailDiaryTrip").sendKeys(excel.getCellDate1(3, 56));
			logger.info("send Tiêu đề - Thêm chi tiết");
			driver.getElenment("id=studio.crazybt.adventure:id/etTitleDetailDiaryTrip").sendKeys(excel.getStringData(3, 57));
			logger.info("send Nội dung - Thêm chi tiết");
			driver.getElenment("id=studio.crazybt.adventure:id/etContentDetailDiaryTrip").sendKeys(excel.getStringData(3, 58));
		}else if(column==58){
			logger.info("touch +");
			driver.getElenment("id=studio.crazybt.adventure:id/fabCreateDiaryTrip").click();
			logger.info("touch chọn quyền");
			driver.getElenment("id=studio.crazybt.adventure:id/spiPrivacy").click();
			logger.info("touch Chỉ mình tôi");
			driver.getElenment("className=android.widget.ListView").findElementsByClassName("android.widget.LinearLayout").get(0).click();
			logger.info("send Tiêu đề");
			driver.getElenment("id=studio.crazybt.adventure:id/etTitleDiaryTrip").sendKeys(excel.getStringData(4, 53));
			logger.info("send Nội dung");
			driver.getElenment("id=studio.crazybt.adventure:id/etContentDiaryTrip").sendKeys(excel.getStringData(4, 54));
			logger.info("Thêm ảnh");
			logger.info("touch button Thêm ảnh");
			driver.getElenment("id=studio.crazybt.adventure:id/btnAddImageDiaryTrip").click();
			logger.info("chọn album");
			driver.getElenment("id=studio.crazybt.adventure:id/recyclerView").findElementsByClassName("android.widget.FrameLayout").get(0).click();
			logger.info("chọn 3 ảnh - dòng 3");
			driver.getElenment("id=studio.crazybt.adventure:id/recyclerView").findElementsByClassName("android.widget.FrameLayout").get(6).click();
			driver.getElenment("id=studio.crazybt.adventure:id/recyclerView").findElementsByClassName("android.widget.FrameLayout").get(7).click();
			driver.getElenment("id=studio.crazybt.adventure:id/recyclerView").findElementsByClassName("android.widget.FrameLayout").get(8).click();
			logger.info("touch button Done");
			driver.getElenment("id=studio.crazybt.adventure:id/toolbar").findElement(By.xpath("//*[@class='android.support.v7.widget.LinearLayoutCompat' and @index='2']")).findElement(By.xpath("//*[@class='android.widget.TextView' and @index='1']")).click();
			driver.swipingVertical("Bottom To Top");
			logger.info("touch button Thêm chi tiết");
			driver.getElenment("id=studio.crazybt.adventure:id/btnAddDetailDiaryTrip").click();
			driver.swipingVertical("Bottom To Top");
			logger.info("send Ngày - Thêm chi tiết");
			driver.getElenment("id=studio.crazybt.adventure:id/etDateDetailDiaryTrip").sendKeys(excel.getCellDate1(4, 56));
			logger.info("send Tiêu đề - Thêm chi tiết");
			driver.getElenment("id=studio.crazybt.adventure:id/etTitleDetailDiaryTrip").sendKeys(excel.getStringData(4, 57));
			logger.info("send Nội dung - Thêm chi tiết");
			driver.getElenment("id=studio.crazybt.adventure:id/etContentDetailDiaryTrip").sendKeys(excel.getStringData(4, 58));
		}
		logger.info("touch button Đăng");
		driver.getElenment("id=studio.crazybt.adventure:id/itemPost").click();
		
		logger.info("take screen short and check toast message");	
		String image = driver.takeScreenShot();
		System.out.println(driver.takeScreenShot());
		Tesseract tessInst = new Tesseract();
		tessInst.setDatapath("D:\\KhoaLuanTotNghiep\\Tess4J");
		tessInst.setLanguage("vie");
		try {
		String result= tessInst.doOCR(new File(image));
		System.out.println(result);
            if(result.toLowerCase().contains(excel.getStringData(7,15).toLowerCase())) {
            	logger.info("pass");
            	return true;
            }
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
            logger.info("fail");
        }
		return true;
	}
	
	public boolean checkDiaryView(Driver driver, Excel excel,int column)throws Exception  {
		Logger logger = Logger.getLogger("checkDiaryView");
		excel.accessSheet("Trip Schedule");
		Adventure_Common ac = new Adventure_Common();
		if(column==59) {
			//phần này test thôi.. xong xóa
			//logger.info("touch chuyen di");
			//driver.getElenment("id=studio.crazybt.adventure:id/tvTripName").click();
			//logger.info("touch tab Nhật ký");
			//driver.getElenment("id=studio.crazybt.adventure:id/tlTrip").findElementsByClassName("android.support.v7.app.ActionBar$Tab").get(3).click();
			//
			logger.info("Người tạo nhật ký trong chuyến đi với cả 3 quyền -trang thư- xem nhật ký");
			logger.info(driver.getElenment("id=studio.crazybt.adventure:id/rvDiaryTripShortcut").findElement(By.xpath("//*[@class='android.widget.FrameLayout' and @index='0']")).findElementById("studio.crazybt.adventure:id/ivPermission").getAttribute("name"));
			if(driver.getElenment("id=studio.crazybt.adventure:id/rvDiaryTripShortcut").findElement(By.xpath("//*[@class='android.widget.FrameLayout' and @index='0']")).findElementById("studio.crazybt.adventure:id/ivPermission").getAttribute("name").equals("Chỉ mình tôi")) {
				if(driver.getElenment("id=studio.crazybt.adventure:id/rvDiaryTripShortcut").findElement(By.xpath("//*[@class='android.widget.FrameLayout' and @index='1']")).findElementById("studio.crazybt.adventure:id/ivPermission").getAttribute("name").equals("Thành viên chuyến đi")){
					driver.swipingVertical("Bottom To Top");
					if(driver.getElenment("id=studio.crazybt.adventure:id/rvDiaryTripShortcut").findElement(By.xpath("//*[@class='android.widget.FrameLayout' and @index='2']")).findElementById("studio.crazybt.adventure:id/ivPermission").getAttribute("name").equals("Công khai")) {
						logger.info("Pass: Xem nhật ký thành công");
						return true;
					}else {
						logger.info("Fail: Không thể xem bài với quyền Công khai");
						driver.takeScreenShotfFail("[Diary]_Nguoi tao khong the xem bai quyen Cong khai");
					}
				}else {
					logger.info("Fail: Không thể xem bài với quyền Thành viên chuyến đi");
					driver.takeScreenShotfFail("[Diary]_Nguoi tao khong the xem bai quyen Thanh vien chuyen di");
				}
			}else {
				logger.info("Fail: Không thể xem bài với quyền Chỉ mình tôi");
				driver.takeScreenShotfFail("[Diary]_Nguoi tao khong the xem bai Chi minh toi");
				return false;
			}
		}else if(column==60){
			logger.info("Thành viên trong chuyến đi -demo1- xem nhật ký");
			logger.info("touch button Quay lại màn hình tab công khai");
			driver.getElenment("id=studio.crazybt.adventure:id/tbTrip").findElement(By.xpath("//*[@class='android.widget.ImageButton' and @index='0']")).click();
			ac.logOut(driver);
			logger.info("login demo1");
			ac.logIn(driver, excel, 1, 43);
			logger.info("touch chuyen di");
			driver.getElenment("id=studio.crazybt.adventure:id/tvTripName").click();
			logger.info("touch tab Nhật ký");
			driver.getElenment("id=studio.crazybt.adventure:id/tlTrip").findElementsByClassName("android.support.v7.app.ActionBar$Tab").get(3).click();
			if(driver.getElenment("id=studio.crazybt.adventure:id/rvDiaryTripShortcut").findElement(By.xpath("//*[@class='android.widget.FrameLayout' and @index='0']")).findElementById("studio.crazybt.adventure:id/ivPermission").getAttribute("name").equals("Thành viên chuyến đi")){
				if(driver.getElenment("id=studio.crazybt.adventure:id/rvDiaryTripShortcut").findElement(By.xpath("//*[@class='android.widget.FrameLayout' and @index='1']")).findElementById("studio.crazybt.adventure:id/ivPermission").getAttribute("name").equals("Công khai")) {
					logger.info("Pass: Xem nhật ký thành công");
					return true;
				}else {
					logger.info("Fail: Không thể xem bài với quyền Công khai");
					driver.takeScreenShotfFail("[Diary]_Nguoi tao khong the xem bai quyen Cong khai");
					//return false;
				}
			}else {
				logger.info("Fail: Không thể xem bài với quyền Thành viên chuyến đi");
				driver.takeScreenShotfFail("[Diary]_Nguoi tao khong the xem bai quyen Thanh vien chuyen di");
				return false;
			}
		}else if(column==61) {
			logger.info("Thành viên không trong chuyến đi -demo2- xem nhật ký");
			logger.info("touch button Quay lại màn hình tab công khai");
			driver.getElenment("id=studio.crazybt.adventure:id/tbTrip").findElement(By.xpath("//*[@class='android.widget.ImageButton' and @index='0']")).click();
			ac.logOut(driver);
			logger.info("login demo2");
			ac.logIn(driver, excel, 1, 44);
			logger.info("touch chuyen di");
			driver.getElenment("id=studio.crazybt.adventure:id/tvTripName").click();
			logger.info("touch tab Nhật ký");
			driver.getElenment("id=studio.crazybt.adventure:id/tlTrip").findElementsByClassName("android.support.v7.app.ActionBar$Tab").get(3).click();
			logger.info("Không phải là thành viên trong chuyến đi -demo2- xem nhật ký");
			if(driver.getElenment("id=studio.crazybt.adventure:id/rvDiaryTripShortcut").findElement(By.xpath("//*[@class='android.widget.FrameLayout' and @index='0']")).findElementById("studio.crazybt.adventure:id/ivPermission").getAttribute("name").equals("Công khai")) {
				logger.info("Pass: Xem nhật ký thành công");
				return true;
			}else {
				logger.info("Fail: Không thể xem bài với quyền Công khai");
				driver.takeScreenShotfFail("[Diary]_Nguoi tao khong the xem bai quyen Cong khai");
				return false;
			}
		}
		return true;
	}
	
	public boolean checkValidateDiary(Driver driver, Excel excel,int column)throws Exception  {
		Logger logger = Logger.getLogger("checkValidateDiary");
		excel.accessSheet("Trip Schedule");
		Adventure_Common ac = new Adventure_Common();
		if(column==62) {
			logger.info("touch button Quay lại màn hình tab công khai");
			driver.getElenment("id=studio.crazybt.adventure:id/tbTrip").findElement(By.xpath("//*[@class='android.widget.ImageButton' and @index='0']")).click();
			ac.logOut(driver);
			logger.info("login trang thư - người tạo");
			ac.logIn(driver, excel, 1, 42);
			logger.info("touch chuyen di");
			driver.getElenment("id=studio.crazybt.adventure:id/tvTripName").click();
			logger.info("touch tab Nhật ký");
			driver.getElenment("id=studio.crazybt.adventure:id/tlTrip").findElementsByClassName("android.support.v7.app.ActionBar$Tab").get(3).click();
			logger.info("touch +");
			driver.getElenment("id=studio.crazybt.adventure:id/fabCreateDiaryTrip").click();
			logger.info("touch chọn quyền");
			driver.getElenment("id=studio.crazybt.adventure:id/spiPrivacy").click();
			logger.info("check giá trị combo box Quyền riêng tư");
			if(driver.getElenment("className=android.widget.ListView").findElementsByClassName("android.widget.TextView").get(2).getText().equals(excel.getStringData(2,63))
					&& driver.getElenment("className=android.widget.ListView").findElementsByClassName("android.widget.TextView").get(1).getText().equals(excel.getStringData(2,62))
					&& driver.getElenment("className=android.widget.ListView").findElementsByClassName("android.widget.TextView").get(0).getText().equals(excel.getStringData(2,61)) ){			
				logger.info("Pass: Combo box valid");
				logger.info("touch Cong khai");
				driver.getElenment("className=android.widget.ListView").findElementsByClassName("android.widget.LinearLayout").get(2).click();
				return true;
				}else {
					logger.info("Combo box Quyen rieng tu invalid");
					driver.takeScreenShotfFail("[Diary]_[Quyen rieng tu] - diary invalid");
					logger.info("touch Cong khai");
					driver.getElenment("className=android.widget.ListView").findElementsByClassName("android.widget.LinearLayout").get(2).click();
					return false;
				}
		}else if(column==63) {
			logger.info("check bỏ trống [Tiêu đề]");
			logger.info("touch button Đăng");
			driver.getElenment("id=studio.crazybt.adventure:id/itemPost").click();
			try {
				if(driver.getElenment("id=studio.crazybt.adventure:id/tvTitleDiaryTripError").getText().equals(excel.getStringData(7, 1))) {
					logger.info("Fail: Không được bỏ trống");
					driver.takeScreenShotfFail("[Diary]_[Tieu de] - diary khong duoc bo trong");
					return false;
				}
			}catch(Exception e) {
				logger.info("Pass: Được bỏ trống");
				return true;
			}
		}else if(column==64) {
			logger.info("Check nhập > 100 ký tự [Tiêu đề]");
			driver.getElenment("id=studio.crazybt.adventure:id/etTitleDiaryTrip").sendKeys(excel.getStringData(3, 64));
			try {
				if(driver.getElenment("id=studio.crazybt.adventure:id/tvTitleDiaryTripError").getText().equals(excel.getStringData(7, 3))) {
					logger.info("Pass: Không được nhập >100 ký tự");
					logger.info("[Tiêu đề] hợp lệ");
					driver.getElenment("id=studio.crazybt.adventure:id/etTitleDiaryTrip").sendKeys(excel.getStringData(2, 64));
					return true;
				}
			}catch(Exception e) {
				logger.info("Fail: Được nhập >100 ký tự");
				driver.takeScreenShotfFail("[Diary]_[Tieu de] - diary hon 100 ky tu");
				logger.info("[Tiêu đề] hợp lệ");
				driver.getElenment("id=studio.crazybt.adventure:id/etTitleDiaryTrip").sendKeys(excel.getStringData(2, 64));
				return false;
			}
		}else if(column==65) {
			logger.info("check bỏ trống [Nội dung]");
			logger.info("touch [Nội dung]");
			driver.getElenment("id=studio.crazybt.adventure:id/etContentDiaryTrip").click();
			logger.info("touch [Tiêu đề]");
			driver.getElenment("id=studio.crazybt.adventure:id/etTitleDiaryTrip").click();
			try {
				if(driver.getElenment("id=studio.crazybt.adventure:id/etContentDiaryTripError").getText().equals(excel.getStringData(7, 1))) {
					logger.info("Fail: Không được bỏ trống");
					driver.takeScreenShotfFail("[Diary]_[Noi dung] - diary khong duoc bo trong");
					return false;
				}
			}catch(Exception e) {
				logger.info("Pass: Được bỏ trống");
				return true;
			}
		}else if(column == 66) {
			logger.info("Check nhập > 100 ký tự [Nội dung]");
			driver.getElenment("id=studio.crazybt.adventure:id/etContentDiaryTrip").sendKeys(excel.getStringData(3, 65));
			try {
				if(driver.getElenment("id=studio.crazybt.adventure:id/etContentDiaryTripError").getText().equals(excel.getStringData(7, 3))) {
					logger.info("Pass: Không được nhập >100 ký tự");
					logger.info("[Nội dung] hợp lệ");
					driver.getElenment("id=studio.crazybt.adventure:id/etContentDiaryTrip").sendKeys(excel.getStringData(2, 65));
					return true;
				}
			}catch(Exception e) {
				logger.info("Fail: Được nhập >100 ký tự");
				driver.takeScreenShotfFail("[Diary]_[Noi dung] - diary hon 100 ky tu");
				logger.info("[Nội dung] hợp lệ");
				driver.getElenment("id=studio.crazybt.adventure:id/etContentDiaryTrip").sendKeys(excel.getStringData(2, 65));
				return false;
			}
		}else if(column == 67) {
			logger.info("touch button [Thêm chi tiết]");
			driver.getElenment("id=studio.crazybt.adventure:id/btnAddDetailDiaryTrip").click();
			logger.info("Check [Ngày] < [Ngày bắt đầu] chuyến đi [Thêm chi tiết]");
			driver.getElenment("id=studio.crazybt.adventure:id/etDateDetailDiaryTrip").sendKeys(excel.getCellDate1(3, 67));
			try {
				if(driver.getElenment("id=studio.crazybt.adventure:id/etDateDetailDiaryTripError").getText().equals(excel.getStringData(7, 5))) {
					logger.info("Pass: Không thể nhỏ hơn [Ngày bắt đầu] chuyến đi");
					return true;
				}
			}catch(Exception e) {
				logger.info("Fail: Được nhỏ hơn [Ngày bắt đầu] chuyến đi");
				driver.takeScreenShotfFail("[Diary]_[Them chi tiet]_[Ngay] duoc nho hon [Ngay bat dau] chuyến đi");
				return false;	
			}
		}else if(column == 68) {
			logger.info("Check [Ngày] > [Ngày kết thúc] chuyến đi [Thêm chi tiết]");
			driver.getElenment("id=studio.crazybt.adventure:id/etDateDetailDiaryTrip").sendKeys(excel.getCellDate1(4, 67));
			try {
				if(driver.getElenment("id=studio.crazybt.adventure:id/etDateDetailDiaryTripError").getText().equals(excel.getStringData(7, 6))) {
					logger.info("Pass: Không thể lớn hơn [Ngày kết thúc] chuyến đi");
					logger.info("[Ngày] hợp lệ");
					driver.getElenment("id=studio.crazybt.adventure:id/etDateDetailDiaryTrip").sendKeys(excel.getCellDate1(2, 67));
					return true;
				}
			}catch(Exception e) {
				logger.info("Fail: Được lớn hơn [Ngày kết thúc] chuyến đi");
				driver.takeScreenShotfFail("[Diary]_[Them chi tiet]_[Ngay] duoc lon hon [Ngay ket thuc] chuyến đi");
				logger.info("[Ngày] hợp lệ");
				driver.getElenment("id=studio.crazybt.adventure:id/etDateDetailDiaryTrip").sendKeys(excel.getCellDate1(2, 67));
				return false;	
			}
		}else if(column==69) {
			logger.info("Check nhập > 100 ký tự [Tiêu đề] - [Thêm chi tiết]");
			driver.getElenment("id=studio.crazybt.adventure:id/etTitleDetailDiaryTrip").sendKeys(excel.getStringData(3, 68));
			try {
				if(driver.getElenment("id=studio.crazybt.adventure:id/etTitleDetailDiaryTripError").getText().equals(excel.getStringData(7, 3))) {
					logger.info("Pass: Không được nhập >100 ký tự");
					logger.info("[Tiêu đề] hợp lệ - [Thêm chi tiết]");
					driver.getElenment("id=studio.crazybt.adventure:id/etTitleDetailDiaryTrip").sendKeys(excel.getStringData(2, 68));
					return true;
				}
			}catch(Exception e) {
				logger.info("Fail: Được nhập >100 ký tự");
				driver.takeScreenShotfFail("[Diary]_[Them chi tiet]_[Tieu de] hon 100 ky tu");
				logger.info("[Tiêu đề] hợp lệ - [Thêm chi tiết]");
				driver.getElenment("id=studio.crazybt.adventure:id/etTitleDetailDiaryTrip").sendKeys(excel.getStringData(2, 68));
				return false;
			}
		}else if(column==70) {
			logger.info("Check nhập > 100 ký tự [Nội dung] - [Thêm chi tiết]");
			driver.getElenment("id=studio.crazybt.adventure:id/etContentDetailDiaryTrip").sendKeys(excel.getStringData(3, 69));
			try {
				if(driver.getElenment("id=studio.crazybt.adventure:id/etContentDetailDiaryTripError").getText().equals(excel.getStringData(7, 3))) {
					logger.info("Pass: Không được nhập >100 ký tự");
					logger.info("[Nội dung] hợp lệ- [Thêm chi tiết]");
					driver.getElenment("id=studio.crazybt.adventure:id/etContentDetailDiaryTrip").sendKeys(excel.getStringData(2, 69));
					logger.info("touch button Đăng");
					driver.getElenment("id=studio.crazybt.adventure:id/itemPost").click();
					return true;
				}
			}catch(Exception e) {
				logger.info("Fail: Được nhập >100 ký tự");
				driver.takeScreenShotfFail("[Diary]_[Them chi tiet]_[Noi dung] hon 100 ky tu");
				logger.info("[Nội dung] hợp lệ- [Thêm chi tiết]");
				driver.getElenment("id=studio.crazybt.adventure:id/etContentDetailDiaryTrip").sendKeys(excel.getStringData(2, 69));
				logger.info("touch button Đăng");
				driver.getElenment("id=studio.crazybt.adventure:id/itemPost").click();
				return false;
			}
		}
			
			
		return true;
	}
}
