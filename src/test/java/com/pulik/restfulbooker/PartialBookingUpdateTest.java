package com.pulik.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PartialBookingUpdateTest  extends BaseTest {
    @Test
    public void partialUpdateBookingTest(){
        Response responseCreate = createBooking();
        responseCreate.print();


        int bookingId = responseCreate.jsonPath().getInt("bookingid");
        System.out.println(bookingId);

        //update booking
        JSONObject body = new JSONObject();
        body.put("firstname", "Dominika");

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2023-03-25");
        bookingdates.put("checkout", "2023-03-30");

        body.put("bookingdates",bookingdates);

        //Partial update booking
        Response responsePartialUpdate = RestAssured.given(spec).auth().preemptive().basic("admin","password123")
                .contentType(ContentType.JSON).body(body.toString())
                .patch("/booking/"+ bookingId);
        responsePartialUpdate.print();

        //Verifiations
        Assert.assertEquals(responsePartialUpdate.
                getStatusCode(),200,"Status code should be 200 but it isnt");

        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = responsePartialUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName,"Dominika","firstname in response is not expected");

        String actualName = responsePartialUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualName,"Pulik","lastname in response is not expected");

        int price = responsePartialUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price,187,"total price in response is not expected ");

        boolean depositpaid = responsePartialUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositpaid,"depositpaid shpould be true");

        String actualCheckIn = responsePartialUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn,"2023-03-25","check in in response is not expected");

        String actualCheckOut = responsePartialUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut,"2023-03-30","check in in response is not expected");

        String additionalNeeds = responsePartialUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(additionalNeeds,"Breakfast");
        softAssert.assertAll();
    
    }
}