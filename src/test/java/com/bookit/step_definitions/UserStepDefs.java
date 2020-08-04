package com.bookit.step_definitions;

import com.bookit.pages.MapPage;
import com.bookit.pages.SelfPage;
import com.bookit.pages.SignInPage;
import com.bookit.utilities.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class UserStepDefs {
    String token;
    Response response;

    @Given("I logged Bookit api using {string} and {string}")
    public void i_logged_Bookit_api_using_and(String email, String password) {
        token = BookItApiUtils.generateToken(email, password);
    }

    @When("I get the current user information from api")
    public void i_get_the_current_user_information_from_api() {
        response = given().header("Authorization", token)
                .when().get(ConfigurationReader.get("qa1api.uri") + "/api/users/me");
    }

    @Then("status code should be {int}")
    public void status_code_should_be(int int1) {
        Assert.assertEquals(response.getStatusCode(),int1);
    }

    @Then("the information about current user from api and database should be match")
    public void the_information_about_current_user_from_api_and_database_should_be_match() {
        String query = "select id, firstname, lastname ,role\n" +
                "from users \n" +
                "where email = 'sbirdbj@fc2.com';";
        Map<String, Object> dbResultMap = DBUtils.getRowMap(query);

        System.out.println(dbResultMap.get("firstname"));

        Map<String,Object> apiResponseMap = response.body().as(Map.class);

        System.out.println("apiResponseMap.get(\"id\") = " + (long)((double)apiResponseMap.get("id")));
        System.out.println("rowMap.get(\"id\") = " + dbResultMap.get("id"));

        Assert.assertEquals(dbResultMap.get("id"),(long)((double)apiResponseMap.get("id")));
        Assert.assertEquals(dbResultMap.get("firstname"),apiResponseMap.get("firstName"));
        Assert.assertEquals(dbResultMap.get("lastname"),apiResponseMap.get("lastName"));
        Assert.assertEquals(dbResultMap.get("role"),apiResponseMap.get("role"));
    }

    @Given("user logs in using {string} {string}")
    public void user_logs_in_using(String email, String password) {
        Driver.get().get(ConfigurationReader.get("url"));
        Driver.get().manage().window().maximize();
        SignInPage signInPage =new SignInPage();
        signInPage.email.sendKeys(email);
        signInPage.password.sendKeys(password);
        signInPage.signInButton.click();
    }

    @When("user is on the my self page")
    public void user_is_on_the_my_self_page() {
        new MapPage().goToSelf();

        BrowserUtils.waitFor(2);
    }

    @Then("UI,API and Database user information must be match")
    public void ui_API_and_Database_user_information_must_be_match() {
        String query = "select id, firstname, lastname ,role\n" +
                "from users \n" +
                "where email = 'sbirdbj@fc2.com';";
        Map<String, Object> dbResultMap = DBUtils.getRowMap(query);

        System.out.println(dbResultMap.get("firstname"));

        Map<String,Object> apiResponseMap = response.body().as(Map.class);

        System.out.println("apiResponseMap.get(\"id\") = " + (long)((double)apiResponseMap.get("id")));
        System.out.println("rowMap.get(\"id\") = " + dbResultMap.get("id"));

        Assert.assertEquals(dbResultMap.get("id"),(long)((double)apiResponseMap.get("id")));
        Assert.assertEquals(dbResultMap.get("firstname"),apiResponseMap.get("firstName"));
        Assert.assertEquals(dbResultMap.get("lastname"),apiResponseMap.get("lastName"));
        Assert.assertEquals(dbResultMap.get("role"),apiResponseMap.get("role"));

        SelfPage selfPage = new SelfPage();
        String fullNameUi = selfPage.name.getText();
        String roleUi = selfPage.role.getText();

        Assert.assertEquals(dbResultMap.get("firstname")+" "+dbResultMap.get("lastname"),fullNameUi);
        Assert.assertEquals(apiResponseMap.get("role"),roleUi);



    }

}
