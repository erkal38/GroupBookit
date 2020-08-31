package com.bookit.pages;

import com.bookit.utilities.BrowserUtils;
import com.bookit.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class HuntPage extends TopNavigationBar{

    @FindBy(xpath = "(//button)[1]")
    public WebElement calendarButton;

    public void selectTomorrow(){

        Date dt = new Date();
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        Random r = new Random();
        int i = r.nextInt(6)+1;
        c.add(Calendar.DATE, i);
        dt = c.getTime();
        System.out.println(i);
        System.out.println(dt);
        System.out.println(today);
        BrowserUtils.waitForClickablility(calendarButton,5);
        calendarButton.click();
        BrowserUtils.waitFor(1);

        if(dt.getDate()<today.getDate()){
            Driver.get().findElement(By.xpath("//button[@aria-label='Next month']")).click();
            Driver.get().findElement(By.xpath("//td[.='"+dt.getDate()+"']")).click();
        }else{
            Driver.get().findElement(By.xpath("//td[.='"+dt.getDate()+"']")).click();
        }

        BrowserUtils.waitFor(2);
    }

}
