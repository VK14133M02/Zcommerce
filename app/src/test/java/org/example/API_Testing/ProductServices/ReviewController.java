package org.example.API_Testing.ProductServices;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.util.*;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ReviewController {

    private int ReviewId = 397653006;
    private String getReviewBasePath = String.format("api/v1/review/%d", ReviewId);
    private String email = "criodo@crio.com";
    private int itemId = 1009;
    private int page = 1;
    private int size = 3;
    private String sort = "asc";
    private String getAllReviewBasePath = "api/v1/review";

    
    @BeforeTest
    public void setBaseURI(){
        RestAssured.baseURI = "http://product-service-prod.zcommerce.crio.do";
    }

    @Test(description = "GET request on review")
    public void getReview(){
        RestAssured.basePath = getReviewBasePath;
        RequestSpecification http = RestAssured.given();

        http.header("Authorization", "Bearer USER_IMPERSONATE_"+email);

        Response response = http.when().get();

        // System.out.println(response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(),200);

        // Parse the JSON response
        JSONObject responseObject = new JSONObject(response.getBody().asString());
        JSONObject dataObject = responseObject.getJSONObject("data");
        
        // Extract review-id, item-id, and reviewerName
        int currentReviewId = dataObject.getInt("id");
        this.itemId = dataObject.getJSONObject("item").getInt("id");
        // String reviewerName = dataObject.getString("reviewerName");

        Assert.assertEquals(ReviewId, currentReviewId);
        // Assert.assertEquals(reviewerName, email);

        // validate the json schmea
        File fileObj = new File("src/test/resources/reviewSchema.json");
        JsonSchemaValidator validator = JsonSchemaValidator.matchesJsonSchema(fileObj);
        response.then().assertThat().body(validator);
    }


    @Test(description = "Get all the review")
    public void getAllReview(){
        RestAssured.basePath = getAllReviewBasePath;
        RequestSpecification http = RestAssured.given();

        // Add query parameters
        http.queryParam("itemId", itemId);
        http.queryParam("page", page);
        http.queryParam("size", size);
        http.queryParam("sort", sort);

        Response response = http.when().get();

        System.out.println(response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(),200);

        // Parse the JSON response
        JSONObject responseObject = new JSONObject(response.getBody().asPrettyString());
        JSONObject dataObject = responseObject.getJSONObject("data");

        // Get the content array
        JSONArray contentArray = dataObject.getJSONArray("content");

        // compare the size of the content array
        Assert.assertEquals(contentArray.length(), size);
    }
}
