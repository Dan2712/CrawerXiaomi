package com.fg.dan.CrawlerXiaomi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Dan
 *
 */
public class CrawlerGO 
{
    public static void main( String[] args )
    {
//    	System.getProperties().setProperty("webdriver.chrome.driver", "E:" + File.separator + "psnl" + File.separator + "chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        String URL = "https://wx.xiaomiquan.com/dweb/#/login";
        webDriver.navigate().to(URL);
        loadCookie(webDriver);
        try {	   
            while (true) {
                Thread.sleep(500L);
                if (!webDriver.getCurrentUrl().startsWith("https://wx.xiaomiquan.com/dweb/#/login")) {
                    break;
                }
            }
            
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
//        storeCookie(webDriver);
        
        List<WebElement> elements = webDriver.findElements(By.className("topic_element"));
        for(int i=0; i<elements.size(); i++) {
        	WebElement user_info = elements.get(i).findElement(By.className("user_name"));
        	System.out.println("Name: " + user_info.getText());
        	try {
        		WebElement topic = elements.get(i).findElement(By.className("topic-pp"));
            	System.out.println("Content: " + topic.getText() + "\n");
        	} catch (NoSuchElementException e) {
        		System.out.println("Content: No Content" + "\n");
        	}
        }
//        WebElement element = webDriver.findElement(By.xpath("/html"));
//        System.out.println(element.getAttribute("outerHTML"));
        webDriver.close();
    }
    
    private static Set<Cookie> storeCookie(WebDriver webDriver) {
    	Set<Cookie> cookies = webDriver.manage().getCookies();
    	File cookieFile = new File("Cookie.data");
    	try {
        	cookieFile.delete();
			cookieFile.createNewFile();
			FileWriter fileWriter = new FileWriter(cookieFile);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			
			for (Cookie cookie : cookies) {
				writer.write(cookie.getName() + ";" + cookie.getValue() + ";" + cookie.getDomain() + ";" 
							+ cookie.getPath() + ";" + cookie.getExpiry() + ";" + cookie.isSecure() + "\n");
			}
			writer.flush();
			writer.close();
			fileWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    
        return cookies;
    }
    
    private static void loadCookie(WebDriver webDriver) {
    	File file = new File("Cookie.data");							
        FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
			BufferedReader buffReader = new BufferedReader(fileReader);
			
			String strLine = "";
			while((strLine = buffReader.readLine()) != null) {
				StringTokenizer tokens = new StringTokenizer(strLine, ";");
				
				while(tokens.hasMoreTokens()) {
					String name = tokens.nextToken();
					String value = tokens.nextToken();
					String domain = tokens.nextToken();
					String path = tokens.nextToken();
					
					Date expiry = null;
					String val;		
			        if(!(val=tokens.nextToken()).equals("null")) {
			        	expiry = new Date(val);					
			        }
			        Boolean isSecure = new Boolean(tokens.nextToken());
			        
			        Cookie ck = new Cookie(name, value, domain, path, expiry, isSecure);
			        webDriver.manage().addCookie(ck);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}							
		
		webDriver.navigate().refresh();
    }
}
