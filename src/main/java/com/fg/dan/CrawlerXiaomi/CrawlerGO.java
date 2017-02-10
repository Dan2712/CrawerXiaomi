package com.fg.dan.CrawlerXiaomi;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
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
    	System.getProperties().setProperty("webdriver.chrome.driver", "E:" + File.separator + "psnl" + File.separator + "chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://wx.xiaomiquan.com/dweb/#/login");
        webDriver.switchTo().defaultContent();
        try {	    //不停的检测，一旦当前页面URL不是登录页面URL，就说明浏览器已经进行了跳转
            while (true) {
                Thread.sleep(500L);
                if (!webDriver.getCurrentUrl().startsWith("https://wx.xiaomiquan.com/dweb/#/login")) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }	//获取cookie，上面一跳出循环我认为就登录成功了，当然上面的判断不太严格，可以再进行修改
        Set<Cookie> cookies = webDriver.manage().getCookies();
        String cookieStr = "";
        for (Cookie cookie : cookies) {
            cookieStr += cookie.getName() + "=" + cookie.getValue() + "; ";
        }
        System.out.println(cookieStr);
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(webDriver.getCurrentUrl());
        
        List<WebElement> elements = webDriver.findElements(By.className("topic-pp"));
        for(int i=0; i<elements.size(); i++) {
//        	WebElement element = elements.get(i).findElement(By.tagName("p"));
        	System.out.println(elements.get(i).getText());
        }
//        System.out.println(webElement.getAttribute("outerHTML"));
        webDriver.close();
    }
}
