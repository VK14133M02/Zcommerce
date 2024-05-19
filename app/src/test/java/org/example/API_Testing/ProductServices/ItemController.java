package org.example.API_Testing.ProductServices;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ItemController {

    private int itemId = 1009;
    private String getItemByIdBasePath = String.format("api/v1/item/%d",itemId);
    private String includeReview = "NOT_INCLUDE";
    private String itemToSearch = "boss";
    private String getItemSearchBasePath = "api/v1/item/search";
    private String sortBy = "PRICE_HIGH_TO_LOW";
    private int page = 0;
    private int limit = 5;
    private String getRecomendedBasePath = "api/v1/item/recommended";
    
    @BeforeTest
    public void setBaseURI(){
        RestAssured.baseURI = "http://product-service-prod.zcommerce.crio.do";
    }

    @Test(description = "GET Request on Item By ID")
    public void getItemById(){
        RestAssured.basePath = getItemByIdBasePath;

        RequestSpecification http = RestAssured.given();

        http.queryParam("includeReview",includeReview);

        Response response = http.when().get();

        Assert.assertEquals(response.getStatusCode(),200);

        // Parse the JSON response
        JSONObject responseObject = new JSONObject(response.getBody().asString());
        JSONObject dataObject = responseObject.getJSONObject("data");
        
        // Extract item-id
        int currentItemId = dataObject.getInt("id");

        Assert.assertEquals(currentItemId, itemId);

        // validate the json schmea
        File fileObj = new File("src/test/resources/itemSchema.json");
        JsonSchemaValidator validator = JsonSchemaValidator.matchesJsonSchema(fileObj);
        response.then().assertThat().body(validator);
    }

    @Test(description = "GET request of item inventory")
    public void getItemInventory(){
        RestAssured.basePath = getItemByIdBasePath;

        RequestSpecification http = RestAssured.given();

        Response response = http.get("inventory");

        // System.out.println(response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(),200);

        // Parse the JSON response
        JSONObject responseObject = new JSONObject(response.getBody().asString());
        JSONObject dataObject = responseObject.getJSONObject("data");
        
        // Extract item-id
        int currentInventory = dataObject.getInt("quantityInStock");
        Assert.assertEquals(currentInventory,255);

        // validate the json schmea
        File fileObj = new File("src/test/resources/itemInventorySchema.json");
        JsonSchemaValidator validator = JsonSchemaValidator.matchesJsonSchema(fileObj);
        response.then().assertThat().body(validator);
    }

    @Test(description = "Search item by keyword")
    public void searchItem(){
        RestAssured.basePath = getItemSearchBasePath;
        RequestSpecification http = RestAssured.given();

        http.queryParam("keyword", itemToSearch);
        http.queryParam("sortBy",sortBy);
        http.queryParam("page", page);
        http.queryParam("size", limit);

        Response response = http.when().get();

        Assert.assertEquals(response.getStatusCode(),200);
        
        // Parse the JSON response
        JSONObject responseObject = new JSONObject(response.getBody().asString());
        JSONObject dataObject = responseObject.getJSONObject("data");

        // Get the content array
        JSONArray contentArray = dataObject.getJSONArray("content");

        if(contentArray.length() > 0){
            // Iterate through the content array and extract titles
            for (int i = 0; i < contentArray.length(); i++) {
                JSONObject item = contentArray.getJSONObject(i);
                String title = item.getString("title");
                boolean status = title.contains(itemToSearch);
                Assert.assertTrue(status,"Title is not matching");
            }
        }
        // validate the json schmea
        File fileObj = new File("src/test/resources/itemSearchSchema.json");
        JsonSchemaValidator validator = JsonSchemaValidator.matchesJsonSchema(fileObj);
        response.then().assertThat().body(validator);
    }

    @Test(description = "GET the request for recomended item")
    public void getRecomended(){
        RestAssured.basePath = getRecomendedBasePath;

        RequestSpecification http = RestAssured.given();
        
        Response response = http.when().get();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
