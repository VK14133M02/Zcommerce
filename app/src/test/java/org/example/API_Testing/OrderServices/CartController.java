package org.example.API_Testing.OrderServices;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.testng.annotations.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;


public class CartController {
    private String email = "vikram@gmail.com"; 
    private String getCartBasePath = "api/v1/cart";    
    private String postCartBasePath = "api/v1/cart";
    private int itemId = 1009;
    private int quantity = 2;

    @BeforeTest
    public void setValue() throws IOException {
        RestAssured.baseURI = "http://order-service-prod.zcommerce.crio.do";        
    }

    
    public int[] getCartId(String basePath,String email){ 
        int[] arr = new int[2];        
        RestAssured.basePath = basePath;
        RequestSpecification http = RestAssured.given();
        http.header("Authorization", "Bearer USER_IMPERSONATE_"+email);
        Response response = http.when().get();
        // System.out.println(response.getBody().asPrettyString());
        JsonPath jsonPath = response.jsonPath();
        int cartId = jsonPath.get("data.id");
        int version = jsonPath.get("data.version");            
        arr[0] = cartId;
        arr[1] = version;
        return arr;        
    }

    @Test(description = "POST request on cart")
    public void addValidItemToCart(){
        int[] arr = getCartId(getCartBasePath,email);
        int cartId = arr[0];
        int version = arr[1];
        RestAssured.basePath = postCartBasePath;
        RequestSpecification http = RestAssured.given();
        http.header("Authorization", "Bearer USER_IMPERSONATE_"+email);

        // create json object to store the item ID and Quanity
        JSONObject itemQuantity = new JSONObject();
        itemQuantity.put("itemId", itemId);
        itemQuantity.put("quantity", quantity);

        // Create json Array as itemQuantityList to store the itemQuantity
        JSONArray itemQuantityList = new JSONArray();
        itemQuantityList.put(itemQuantity);

        // create a josn object to pass the cartID, version, userId and itemQuantityList
        JSONObject payLoad = new JSONObject();
        payLoad.put("id", cartId);
        payLoad.put("version", version);
        payLoad.put("userId", email);
        payLoad.put("itemQuantityList", itemQuantityList);

        http.contentType(ContentType.JSON);
        http.body(payLoad.toString());        

        Response response = http.post();

        // System.out.println(response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(),200);
    }    

    // @Test(description = "get request on cart")
    // public void getCartDetails(){

    // }
}