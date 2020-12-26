package com.pulik.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HealthCheckTest extends BaseTest {
    @Test
    public void healthCheckTest() {
        given().spec(spec).
                when().get("/ping").
                then().assertThat().statusCode(201);

    }

    @Test
    public void headerAndCookieTest() {
        Header someHeader = new Header("same name", "same value");
        spec.header(someHeader);

        Cookie someCookie = new Cookie.Builder("same cookie name", "some cookie value")
                .build();
        spec.cookie(someCookie);


        Response response = RestAssured.given(spec)
                .cookie("test cookie name", "test cookie value")
                .header("test header name", "test header value").log()
                .all().get("/ping");
        //get headers
        Headers headers = response.getHeaders();
        System.out.println("Headers:" + headers);

        Header serverHeader1 = headers.get("Server");
        System.out.println(serverHeader1.getName() + ":" + serverHeader1.getValue());

        String serverHeader2 = response.getHeader("Server");
        System.out.println("Server:" + serverHeader2);

        //get cookies
        Cookies cookies = response.getDetailedCookies();
        System.out.println("Cookies:" + cookies);


    }
}
