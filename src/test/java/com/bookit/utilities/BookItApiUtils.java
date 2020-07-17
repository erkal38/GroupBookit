package com.bookit.utilities;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookItApiUtils {

    public static String generateToken(String email,String password){

        Response response = given().queryParam("email", email)
                .queryParam("password", password)
                .when().get(ConfigurationReader.get("qa1api.uri") + "/sign");

        //get token from api and save token
        String token = "Bearer "+ response.path("accessToken");
        return token;

    }
}