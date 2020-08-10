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
import org.openqa.selenium.Keys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class UserStepDefs {
    String token;
    Response response;
    String emailGlobal;
    String passwordGlobal;
    String fullNameUi;
    String roleUi;
    String fullNameApi;
    String roleApi;
    long idApi;
    String fullNameDb;
    String roleDb;
    long idDb;


    @Given("I logged Bookit api using {string} and {string}")
    public void i_logged_Bookit_api_using_and(String email, String password) {
        token = BookItApiUtils.generateToken(email, password);
        emailGlobal=email;
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
                "where email = '"+emailGlobal+"';";
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
    }

    @Then("UI,API and Database user information must be match")
    public void ui_API_and_Database_user_information_must_be_match() {
        String query = "select id, firstname, lastname ,role\n" +
                "from users \n" +
                "where email = '"+emailGlobal+"';";
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

    @Given("user logs in UI BookIt by using {int}. row credentials of Excel from {string}{string}")
    public void user_logs_in_UI_BookIt_by_using_row_credentials_of_Excel_from(Integer rowIndex, String path, String sheet) {
        ExcelUtil excelUtil = new ExcelUtil(path,sheet);
        emailGlobal = excelUtil.getDataList().get(rowIndex).get("Email");
        passwordGlobal = excelUtil.getDataList().get(rowIndex).get("password");
        Driver.get().get(ConfigurationReader.get("url"));
        Driver.get().manage().window().maximize();
        SignInPage signInPage =new SignInPage();
        signInPage.email.sendKeys(emailGlobal);
        signInPage.password.sendKeys(passwordGlobal);
        signInPage.signInButton.click();
        new MapPage().goToSelf();

        SelfPage selfPage = new SelfPage();
        BrowserUtils.waitForVisibility(selfPage.name,10);
        fullNameUi = selfPage.name.getText();
        roleUi = selfPage.role.getText();
    }

    @Given("user logs in API BookIt  by using same credentials")
    public void user_logs_in_API_BookIt_by_using_same_credentials() {
        token = BookItApiUtils.generateToken(emailGlobal, passwordGlobal);

        response = given().header("Authorization", token)
                .when().get(ConfigurationReader.get("qa1api.uri") + "/api/users/me");

        Map<String,Object> apiResponseMap = response.body().as(Map.class);
        String ApiFirstname = (String)apiResponseMap.get("firstName");
        String ApiLastname = (String)apiResponseMap.get("lastName");
        fullNameApi= ApiFirstname+" "+ApiLastname;
        roleApi = (String)apiResponseMap.get("role");
        idApi = (long)((double)apiResponseMap.get("id"));
    }

    @Given("user logs in DB BookIt  by using same credentials")
    public void user_logs_in_DB_BookIt_by_using_same_credentials() {
        String query = "select id, firstname, lastname ,role\n" +
                "from users \n" +
                "where email = '"+emailGlobal+"';";
        Map<String, Object> dbResultMap = DBUtils.getRowMap(query);
        String DbFirstname = (String)dbResultMap.get("firstname");
        String DbLastname = (String)dbResultMap.get("lastname");
        fullNameDb= DbFirstname+" "+DbLastname;
        roleDb = (String)dbResultMap.get("role");
        idDb = (long)dbResultMap.get("id");
    }





    @Then("UI,API and DB user information must be match")
    public void ui_API_and_DB_user_information_must_be_match() {
        //UI to DB
        Assert.assertEquals(fullNameUi,fullNameDb);
        Assert.assertEquals(roleUi,roleDb);

        //UI to API
        Assert.assertEquals(fullNameUi,fullNameApi);
        Assert.assertEquals(roleUi,roleApi);

        //API to DB
        Assert.assertEquals(fullNameDb,fullNameApi);
        Assert.assertEquals(roleDb,roleApi);
        Assert.assertEquals(idDb,idApi);

        //UI to Excel

    }
}
