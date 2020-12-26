package com.pulik.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UdateBookingTest extends BaseTest {
    @Test
    public void updateBookingTest() {

        //cereate booking
        Response responseCreate = createBooking();
        responseCreate.print();


        //get booking bookingId

        int bookingId = responseCreate.jsonPath().getInt("bookingid");
        System.out.println(bookingId);

        //update booking
        JSONObject body = new JSONObject();
        body.put("firstname", "Olga");
        body.put("lastname", "Pulik");
        body.put("totalprice", 150);
        body.put("depositpaid", "false");

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2022-03-25");
        bookingdates.put("checkout", "2022-03-30");

        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", "Launch");

        //get responseCreate
        Response responseUpdate = RestAssured.given(spec).auth().preemptive().basic("admin","password123").contentType(ContentType.JSON).body(body.toString())
                .put("/booking/"+ bookingId);
        responseUpdate.print();

        //username/password

        //Verification
        Assert.assertEquals(responseUpdate.
                getStatusCode(),200,"Status code should be 200 but it isnt");

        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName,"Olga","firstname in response is not expected");

        String actualName = responseUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualName,"Pulik","lastname in response is not expected");

        int price = responseUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price,150,"total price in response is not expected ");

        boolean depositpaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositpaid,"depositpaid shpould be true");

        String actualCheckIn = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn,"2022-03-25","check in in response is not expected");

        String actualCheckOut = responseUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut,"2022-03-30","check in in response is not expected");

        String additionalNeeds = responseUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(additionalNeeds,"Launch");
        softAssert.assertAll();
    }


}
