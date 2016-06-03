package AtheneUSA.SeleniumwithMavenProject;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class AppTest {
  private String baseUrl;
  private WebDriver driver;
  private ScreenshotHelper screenshotHelper;
  
  @BeforeTest
  @Parameters("browser")
  public void openBrowser(String browser) {
    baseUrl = "http://www.google.com";
    if (browser.equalsIgnoreCase("chrome")){
    
    	driver = new ChromeDriver();
    }
    else if (browser.equalsIgnoreCase("ie")){
    	driver = new InternetExplorerDriver();
    }
    
    driver.get(baseUrl);
    screenshotHelper = new ScreenshotHelper();
  }
  
  @AfterTest
  @Parameters("browser")
  public void saveScreenshotAndCloseBrowser(String browser) throws IOException {
    screenshotHelper.saveScreenshot("screenshot_" + browser + ".png");
    driver.quit();
  }
  
  @Test
  public void pageTitleAfterSearchShouldBeginWithSelenium() throws IOException {
	  
	Assert.assertEquals("Google", driver.getTitle());
    WebElement searchField = driver.findElement(By.name("q"));
    searchField.sendKeys("Selenium testing!");
    searchField.submit();
    WebElement myDynamicElement = (new WebDriverWait(driver, 10))
    		  .until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
    Assert.assertEquals("Selenium testing! - Google Search", driver.getTitle());
    
    
  }
  
  private class ScreenshotHelper {
  
    public void saveScreenshot(String screenshotFileName) throws IOException {
      File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      FileUtils.copyFile(screenshot, new File(screenshotFileName));
    }
  }
}
