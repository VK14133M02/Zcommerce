package org.example.API_Testing.OrderServices;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.testng.annotations.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
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
    private String viewCartBasePath = "api/v1/cart/view";
    private String patchCartBasePath = "api/v1/cart";
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

        String currentDateTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
        // create a josn object to pass the cartID, version, userId and itemQuantityList
        JSONObject payLoad = new JSONObject();
        payLoad.put("id", cartId);
        payLoad.put("createdAt", currentDateTime);
        payLoad.put("version", version);
        payLoad.put("userId", email);
        payLoad.put("itemQuantityList", itemQuantityList);

        http.contentType(ContentType.JSON);
        http.body(payLoad.toString());        

        Response response = http.post();

        // System.out.println(response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(),200);

        File fileObj = new File("src/test/resources/cartSchema.json");
        JsonSchemaValidator validator = JsonSchemaValidator.matchesJsonSchema(fileObj);
        response.then().assertThat().body(validator);
    }    

    @Test(description = "get request on cart")
    public void getCartDetails(){
        int[] arr = getCartId(getCartBasePath,email);
        int cartId = arr[0];

        RestAssured.basePath = viewCartBasePath;

        RequestSpecification http = RestAssured.given();

        http.header("Authorization", "Bearer USER_IMPERSONATE_"+email);
        Response response = http.when().get();

        // Extract cartId itemId and quantity from the response body
        JSONObject responseBody = new JSONObject(response.getBody().asString());
        JSONObject data = responseBody.getJSONObject("data");
        int current_cart_id = data.getInt("id");
        JSONArray availableItem = data.getJSONArray("availableItem");
        if (availableItem.length() > 0) {
            JSONObject firstItem = availableItem.getJSONObject(0);
            JSONObject item = firstItem.getJSONObject("item");
            int currentItemId = item.getInt("id");
            int currentQuantity = firstItem.getInt("quantity");
            
            Assert.assertEquals(cartId,current_cart_id);
            Assert.assertEquals(itemId,currentItemId);
            Assert.assertEquals(quantity,currentQuantity);
        } else {
            System.out.println("No available items found.");
        }        
    }

    @Test(description = "PATCH request on cart to update cart quantity")
    public void updateProductQuantity(){       
        RestAssured.basePath = patchCartBasePath;
        RequestSpecification http = RestAssured.given();
        http.header("Authorization", "Bearer USER_IMPERSONATE_"+email);

        // Create object to pass the itemId and quantity
        JSONObject itemQuantity = new JSONObject();
        itemQuantity.put("itemId", itemId);
        itemQuantity.put("quantity",3);

        http.contentType(ContentType.JSON);
        http.body(itemQuantity.toString());
        Response response = http.patch();

        // System.out.println(response.getBody().asPrettyString());
        // System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(),200);

        // validate the json schmea
        File fileObj = new File("src/test/resources/cartSchema.json");
        JsonSchemaValidator validator = JsonSchemaValidator.matchesJsonSchema(fileObj);
        response.then().assertThat().body(validator);
    }
}