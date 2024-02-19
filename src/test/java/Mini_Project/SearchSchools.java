//SEARCH FOR SCHOOLS
/*
Detailed Description:

Open the browser
Enter the url https://www.eduvidya.com/
Click on "Schools" link in the Menu bar
Click on "Course-Type" dropdown and select â€œCBSEâ€ from the list
Click on "City" dropdown and select â€œPuneâ€ from the list
Click on "Search" button
Verify Search results is displayed or not
Close the browser
 */


package Mini_Project;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.searchForSchools.utils.driverSetup;
import com.searchForSchools.utils.excelUtils;



public class SearchSchools {
	public static WebDriver driver;

	public void createDriver(){
	  driver = driverSetup.getWebDriver();
	  String baseUrl = "https://www.eduvidya.com/";
	  driver.get(baseUrl);
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	  //Opening the link by clicking advanced options
	  try {
	  driver.findElement(By.id("details-button")).click();
	  driver.findElement(By.id("proceed-link")).click();}
	  catch(Exception e) {
	  }
	}

	public void setConds() throws IOException, Exception{
		//Select Schools
		driver.findElement(By.xpath("//div[@id='cssmenu']/ul/li[4]/a")).click();
		
		//Using try Catch block to close the adds 
		//Thread.sleep(5000);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		try {
			WebElement f1 = driver.findElement(By.xpath("//div[@id=\"aswift_3_host\"]"));
		    driver.switchTo().frame(f1.findElement(By.xpath("//iframe[@id='aswift_3']")));
			
		    WebElement f2 = driver.findElement(By.xpath("//div[@id=\"ad_position_box\"]"));
		    driver.switchTo().frame(f2.findElement(By.xpath("//iframe[@id=\"ad_iframe\"]")));
			
		    WebElement cl = driver.findElement(By.xpath("//*[@id=\"dismiss-button\"]"));
			js.executeScript("arguments[0].click();",cl);
			driver.switchTo().defaultContent();
			System.out.println("ad closed");
		}
		catch(Exception e) {
		}
		//Thread.sleep(10000);
		
		String file = System.getProperty("user.dir")+"\\src/test/resources\\data.xlsx";
		//Select CBSE
		Select sc = new Select(driver.findElement(By.xpath("//div[@class=\"course-type\"]//select")));
		sc.selectByVisibleText(excelUtils.getCellData(file,"Sheet1",1,0));
		//Thread.sleep(5000);

		//Select City
		Select sci = new Select(driver.findElement(By.xpath("//div[@class=\"college-location\"]//select")));
		sci.selectByVisibleText(excelUtils.getCellData(file,"Sheet1",1,1));
		//Thread.sleep(5000);

		//click search
		WebElement s = driver.findElement(By.id("btnSearch"));
		js.executeScript("arguments[0].click();",s);
	}
	//Displaying the results
	public void searchResults(){
		List<WebElement> results = driver.findElements(By.xpath("//div[@id='content']/div/div/div[2]/div/div/div/div[2]/ul/li/div[2]/a"));
		System.out.println("---------------------------------");
		if(results.size() > 0){
		System.out.println("Search Results are Displayed");
		}
		else{
		System.out.println("Search Results are not Displayed");
		}
		System.out.println("---------------------------------");
	}
	//closing the browser
	public void closeBrowser(){
		driver.close();
	}
	
	public static void main(String[] args) throws Exception {
		//calling the required functions
		SearchSchools mp = new SearchSchools();
		mp.createDriver();
		mp.setConds();
		mp.searchResults();
		mp.closeBrowser();
		
		
	}
}
