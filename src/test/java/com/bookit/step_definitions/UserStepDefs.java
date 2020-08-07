package com.bookit.step_definitions;

import com.bookit.utilities.BookItApiUtils;
import com.bookit.utilities.ConfigurationReader;
import com.bookit.utilities.DBUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

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
                .get(ConfigurationReader.get("qa1api.uri") + "/api/users/me");


    }

    @Then("status code should be {int}")
    public void status_code_should_be(int int1) {
        Assert.assertEquals(response.statusCode(), int1);

    }

    @Then("the information about current user from api and database should be match")
    public void the_information_about_current_user_from_api_and_database_should_be_match() {


    }
}
