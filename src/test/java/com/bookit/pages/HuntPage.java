package com.bookit.pages;

import com.bookit.utilities.BrowserUtils;
import com.bookit.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HuntPage extends TopNavigationBar{

    @FindBy(xpath = "(//button)[1]")
    public WebElement calendarButton;

    public String getTomorrow(){
        BrowserUtils.waitForClickablility(calendarButton,5);
        calendarButton.click();
        WebElement today = Driver.get().findElement(By.xpath("//td//div[contains(@class,'today')]"));
        BrowserUtils.waitFor(1);
        today.click();
        String todayStr = Driver.get().findElement(By.tagName("input")).getAttribute("value");
        String[] s = todayStr.split(" ");
        int i = Integer.parseInt(s[1]);
        String dumy= s[0]+" "+i+1+", "+s[2];
        System.out.println("dumy = " + dumy);


        return "";
    }

}
