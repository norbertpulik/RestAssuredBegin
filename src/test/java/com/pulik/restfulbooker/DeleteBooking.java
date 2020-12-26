package com.pulik.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteBooking extends BaseTest {
    @Test
    public void deleteBookingTest() {
        //Create Json body
        Response responseCreate = createBooking();
        responseCreate.print();

        int bookingId = responseCreate.jsonPath().getInt("bookingid");
        System.out.println(bookingId);

        //delete booking
        Response responseDelete = RestAssured.given(spec).auth().preemptive().basic("admin","password123")
                .delete("/booking/" + bookingId);
        responseDelete.print();
        //verify response code
        Assert.assertEquals(responseDelete.getStatusCode(), 201);

        Response responseGet = RestAssured.get("https://restful-booker.herokuapp.com/booking/" + bookingId);
        responseGet.print();
        Assert.assertEquals(responseGet.getBody().asString(),"Not Found", "Body should be not found");


    }
}
