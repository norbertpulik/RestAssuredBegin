package com.pulik.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTest extends BaseTest {

    @Test(enabled = false)
    public void getBookingTest() {
        //create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Set path param
        spec.pathParam("bookingid", responseCreate.jsonPath().getInt("bookingid"));

        //get response with booking

        Response response = RestAssured.given(spec).get("/booking/{bookingid}");
        response.print();

        //verify status 200
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Norbert", "firstname in response is not expected");

        String actualName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actualName, "Pulik", "lastname in response is not expected");

        int price = response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 187, "total price in response is not expected ");

        boolean depositpaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositpaid, "depositpaid should be true");

        String actualCheckIn = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn, "2022-03-25", "check in in response is not expected");

        String actualCheckOut = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut, "2022-03-30", "check in in response is not expected");

        softAssert.assertAll();
    }

    @Test
    public void getBookingXMLTest() {
        //create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Set path param
        spec.pathParam("bookingid", responseCreate.jsonPath().getInt("bookingid"));

        //get response with booking
        Header xml = new Header("Accept", "application/xml");
        spec.header(xml);

        Response response = RestAssured.given(spec).get("/booking/{bookingid}");
        response.print();

        //verify status 200
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.xmlPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName, "Norbert", "firstname in response is not expected");

        String actualName = response.xmlPath().getString("booking.lastname");
        softAssert.assertEquals(actualName, "Pulik", "lastname in response is not expected");

        int price = response.xmlPath().getInt("booking.totalprice");
        softAssert.assertEquals(price, 187, "total price in response is not expected ");

        boolean depositpaid = response.xmlPath().getBoolean("booking.depositpaid");
        softAssert.assertTrue(depositpaid, "depositpaid should be true");

        String actualCheckIn = response.xmlPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn, "2022-03-25", "check in in response is not expected");

        String actualCheckOut = response.xmlPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut, "2022-03-30", "check in in response is not expected");

        softAssert.assertAll();
    }
}



