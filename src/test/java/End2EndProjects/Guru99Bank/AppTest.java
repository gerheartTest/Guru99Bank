package End2EndProjects.Guru99Bank;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AppTest extends BaseTest
{
	
	@Test
	public void loginTest() throws IOException
	{
		
		driver.findElement(By.name("uid")).clear(); // Good practice to clear a field before use
		driver.findElement(By.name("uid")).sendKeys(userId); // Enter username
		
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(password); // Enter Password
		
		// Click Login
		driver.findElement(By.name("btnLogin")).click();
		
		String titleHome = driver.getTitle();
		Assert.assertTrue(titleHome.contains(expectTitle));
		
		String managerID = driver.findElement(By.xpath("//td[@style='color: green']")).getText().split(" : ")[1];
		Assert.assertEquals(managerID, userId);

		//Get Screenshot
		File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src,new File(System.getProperty("user.dir")+"\\src\\test\\java\\homepageScreenshot.png"));
	}
	
	@Test(dataProvider="getErrorLoginCredentials")
	public void loginErrorTest(HashMap<String, String> input)
	{
		
		driver.findElement(By.name("uid")).clear(); // Good practice to clear a field before use
		driver.findElement(By.name("uid")).sendKeys(input.get("userID")); // Enter username
		
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(input.get("password")); // Enter Password
		
		// Click Login
		driver.findElement(By.name("btnLogin")).click();
		
		Alert alt = driver.switchTo().alert();
		String actualError = alt.getText();
		alt.accept();
		Assert.assertEquals(actualError, expectError);
	}
	
	@Test
	public void changePassword() throws IOException
	{
		
		driver.findElement(By.name("uid")).clear(); // Good practice to clear a field before use
		driver.findElement(By.name("uid")).sendKeys(userId); // Enter username
		
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(password); // Enter Password
		
		// Click Login
		driver.findElement(By.name("btnLogin")).click();
		
		driver.findElement(By.linkText("Change Password")).click();
	}
	
	@DataProvider
	public Object[][] getErrorLoginCredentials() throws IOException
	{
		List<HashMap<String, String>> data=  getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\End2EndProjects\\data\\ErrorLoginCredentials.json");
		
		return new Object[][] {{data.get(0)},{data.get(1)},{data.get(2)}};
	}
	
}
