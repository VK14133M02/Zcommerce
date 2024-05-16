package org.example.API_Testing.OrderServices;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class OrderController {
    private int itemId = 7107;
    private int quantity = 1;
    private String email = "vikram@gmail.com";
    private String address = "Crio.do.koramangla";
    private String paymentType = "CASH_ON_DELIVERY";
    private String postOrderBasePath = "api/v1/order";
    private int orderId;

    @BeforeTest
    public void setValue() throws IOException {
        RestAssured.baseURI = "http://order-service-prod.zcommerce.crio.do";        
    }

    @Test(description = "POST request for create order", priority = 1)
    public void createOrder(){
        RestAssured.basePath = postOrderBasePath;
        RequestSpecification http = RestAssured.given().when().log().all();        

        // create object to store the itemId and Quantity
        JSONObject itemsDetail = new JSONObject();
        itemsDetail.put("itemId", itemId);
        itemsDetail.put("quantity", quantity);

        // create json array to store the itemsDetail
        JSONArray orderItemsDetail = new JSONArray();
        orderItemsDetail.put(itemsDetail);

        // create json object to store the orderItemsDetail, userID, and address derails;
        JSONObject payload = new JSONObject();
        payload.put("userId", email);
        payload.put("orderItemsDetail", orderItemsDetail);
        payload.put("paymentType",paymentType);
        payload.put("shippingAddress", address);
        payload.put("billingAddress", address);

        http.contentType(ContentType.JSON);

        http.header("Authorization", "Bearer USER_IMPERSONATE_"+email);
        // Generate a unique idempotency token
        String idempotencyToken = UUID.randomUUID().toString();
        http.header("Idempotency-Token", idempotencyToken);

        http.body(payload.toString());        

        Response response = http.when().post();
        // System.out.println(response.getBody().asPrettyString());
        // System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(),200);

        JsonPath jsonPath = response.jsonPath();
        this.orderId = jsonPath.get("data.id");

        // validate the json schmea
        File fileObj = new File("src/test/resources/orderSchema.json");
        JsonSchemaValidator validator = JsonSchemaValidator.matchesJsonSchema(fileObj);
        response.then().assertThat().body(validator);
    }

    // @Test(description = "GET request for order", priority = 2)
    // public void getOrderDetails(){
    //     System.out.println("Order id is "+orderId);
    // }
}
