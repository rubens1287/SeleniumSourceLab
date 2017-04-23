package com.sourcelabas.login;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.saucelabs.junit.ConcurrentParameterized;

import junit.framework.Assert;


@RunWith(ConcurrentParameterized.class)
public class CN001_Login_ICCID {
	
	
	protected String browser; 
	protected String os; 
	protected String version; 
	protected String sessionId;
	protected WebDriver driver;
	
	public static final String USERNAME = System.getenv("SAUCE_USERNAME"); 
	public static final String ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY"); 
	public static final String sURL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";
	
	@ConcurrentParameterized.Parameters 
	public static LinkedList<String[]> browsersStrings() {
		LinkedList<String[]> browsers = new LinkedList<String[]>(); 
		// windows 7, IE 9 
		browsers.add(new String[]{"Windows 7", "9", "internet explorer"}); 
		// windows 8, IE 10 
		browsers.add(new String[]{"Windows 8", "10", "internet explorer"});
		// windows 8.1, IE 11 
		browsers.add(new String[]{"Windows 8.1", "11", "internet explorer"});
		return browsers;
	}
		
	public CN001_Login_ICCID(String os, String version, String browser) {
		super(); 
		this.os = os; 
		this.version = version; 
		this.browser = browser; }
	
	@Before
	public void Setup() throws MalformedURLException{
		DesiredCapabilities capabilities = new DesiredCapabilities(); 
		
		capabilities.setCapability(CapabilityType.BROWSER_NAME, browser); 
		capabilities.setCapability(CapabilityType.VERSION, version); 
		capabilities.setCapability(CapabilityType.PLATFORM, os); 
		
		driver = new RemoteWebDriver(new URL(sURL), capabilities); 
		
		this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString(); 
		String message = String.format("SauceOnDemandSessionID=%1$s", this.sessionId); 
		System.out.println(message);
		
		//System.setProperty("webdriver.chrome.driver","./src/test/resources/chromedriver.exe");
		//driver = new ChromeDriver();

	}

	@Test
	public void CT001_Login() throws InterruptedException{
		driver.get("http://104.140.112.66/Gerenciador/login.php");
		
		driver.findElement(By.name("login_usuario")).sendKeys("RL00452643@techmahindra.com");
		driver.findElement(By.name("login_senha")).sendKeys("123456");
		driver.findElement(By.xpath("/html/body/div/form/button")).click();
				
		Thread.sleep(2000);
		
		Assert.assertEquals("Sair", driver.findElement(By.xpath("//*[@id='navbar']/ul/li[4]/a")).getText());
		
	}
	
	@After
	public void CleanUp(){
		driver.quit();
		
	}
	

}
