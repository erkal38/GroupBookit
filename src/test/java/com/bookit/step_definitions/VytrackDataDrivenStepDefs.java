package com.bookit.step_definitions;

import com.bookit.utilities.BrowserUtils;
import com.bookit.utilities.Driver;
import com.bookit.utilities.ExcelUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class VytrackDataDrivenStepDefs {
    @Given("I go to the vytrack login page")
    public void i_go_to_the_vytrack_login_page() {
        Driver.get().get("https://qa3.vytrack.com");
    }

    @When("I login with the {int} .  user cridentials")
    public void i_login_with_the_user_cridentials(int number) {
        ExcelUtil qa3short = new ExcelUtil("src/test/resources/Vytracktestdata.xlsx","QA3-short");
        String userName = qa3short.getDataList().get(number-1).get("username");
        String password = qa3short.getDataList().get(number-1).get("password");
        Driver.get().findElement(By.id("prependedInput")).sendKeys(userName);
        Driver.get().findElement(By.id("prependedInput2")).sendKeys(password, Keys.ENTER);
        BrowserUtils.waitFor(3);

    }

    @Then("verify {int} . user logged in successfully")
    public void verify_user_logged_in_successfully(Integer int1) {
        String actualUserName = Driver.get().findElement(By.cssSelector("#user-menu > a")).getText();
        ExcelUtil qa3short = new ExcelUtil("src/test/resources/Vytracktestdata.xlsx","QA3-short");
        String firstname = qa3short.getDataList().get(int1-1).get("firstname");
        String lastname = qa3short.getDataList().get(int1-1).get("lastname");
        String expectedFullName= firstname +" "+lastname;
        System.out.println("actualUserName = " + actualUserName);

        Assert.assertEquals(expectedFullName,actualUserName);
    }

}
