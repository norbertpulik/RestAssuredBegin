package com.pulik.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetBookingIdTest extends BaseTest {
    @Test
    public void getBookingIdTestWithoutFlter() {
        //get response with booking ids
        Response response = RestAssured.given(spec).get("/booking");
        response.print();
        //verify status 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but it is not");
        //verify at least 1 booking id in response
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "List of booking id is empty but it shouldn't be");
    }

    @Test
    public void getBookingIdTestWithFilters() {
        //add quary param
        spec.queryParam("firstname","Norbert");
        spec.queryParam("lastname","Pulik");

        //get response with booking ids
        Response response = RestAssured.given(spec).get("/booking");
        response.print();
        //verify status 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but it is not");
        //verify at least 1 booking id in response
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "List of booking id is empty but it shouldn't be");
    }

}
