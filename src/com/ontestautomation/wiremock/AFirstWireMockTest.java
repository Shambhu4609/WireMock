package com.ontestautomation.wiremock;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import io.restassured.RestAssured;


import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.util.UUID;

import org.apache.http.entity.ContentType;


import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
public class AFirstWireMockTest {
	
	String bodyText = "You've reached a valid WireMock endpoint";
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8090);
	
	@Test
	public void testStatusCodePositive() {
		setupStub();
		RestAssured.baseURI = "http://localhost:8090";
		 String string = RestAssured.given().when().get("http://localhost:8090/an/endpoint")
				.then().toString();
        
		
	}
	
	@Test
	public void testStatusCodeNegative() {
		
		setupStub();
		
		given().
		when().
			get("http://localhost:8090/another/endpoint").
		then().
			assertThat().statusCode(404);
	}
	
	@Test
	public void testResponseContents() {
		
		setupStub();
		
		String response = get("http://localhost:8090/an/endpoint").asString();
		Assert.assertEquals(bodyText, response);
	}
	
	public void setupStub() {
		
		stubFor(get(urlEqualTo("/an/endpoint"))
	            .willReturn(aResponse()
	                .withHeader("Content-Type", "text/plain")
	                .withStatus(200)
	                .withBody(bodyText)));
	}
}