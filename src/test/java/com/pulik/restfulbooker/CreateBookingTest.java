package com.pulik.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTest extends BaseTest {
    @Test
    private void createBookingTest() {
        //Create json body
        Response response = createBooking();
        //verification

        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName, "Norbert", "firstname in response is not expected");

        String actualName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualName, "Pulik", "lastname in response is not expected");

        int price = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(price, 187, "total price in response is not expected ");

        boolean depositpaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertTrue(depositpaid, "depositpaid shpould be true");

        String actualCheckIn = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn, "2022-03-25", "check in in response is not expected");

        String actualCheckOut = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut, "2022-03-30", "check in in response is not expected");

        String additionalNeeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(additionalNeeds, "Breakfast");
        softAssert.assertAll();

    }

    @Test
    private void createBookingWithPOJOTest() {
        // Create body using POJO
        Bookingdates bookingdates = new Bookingdates("2022-03-25", "2022-03-30");
        Booking booking = new Booking("Norbert", "Pulik", 200,
                true, bookingdates, "Kocik");

        //verification
        Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(booking)
                .post("/booking");
        response.print();

        BookingId bookingId = response.as(BookingId.class);

        System.out.println("Request booking:" + booking.toString());
        System.out.println("Response booking:" + bookingId.getBooking().toString());

        Assert.assertEquals(response.getStatusCode(), 200, "Status should be 200 but is not");
        Assert.assertEquals(bookingId.getBooking().toString(), booking.toString());


    }

    @org.jetbrains.annotations.NotNull
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
        Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(body.toString())
                .post("/booking");
        response.print();
        return response;
    }
}

