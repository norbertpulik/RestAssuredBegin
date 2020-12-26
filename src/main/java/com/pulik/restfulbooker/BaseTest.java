package com.pulik.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected RequestSpecification spec;

    @BeforeMethod
    public void setup() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com").build();

    }

    protected Response createBooking() {
        JSONObject body = new JSONObject();
        body.put("firstname", "Norbert");
        body.put("lastname", "Pulik");
        body.put("totalprice", 187);
        body.put("depositpaid", "false");

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2022-03-25");
        bookingdates.put("checkout", "2022-03-30");

        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", "Breakfast");

        //get response
        Response response = RestAssured.given().contentType(ContentType.JSON).body(body.toString())
                .post("https://restful-booker.herokuapp.com/booking");
        response.print();
        return response;
    }
}




