set projectLocation=D:\KhoaLuanTotNghiep\Jenkins\workspace\Adventure_Testing
cd %projectLocation%
set classpath=%projectLocation%\bin;%projectLocation%\lib\*
java org.testng.TestNG %projectLocation%\testng.xml
pause