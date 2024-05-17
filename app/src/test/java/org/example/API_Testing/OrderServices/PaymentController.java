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

public class PaymentController {
    private String postPaymentBasePath = "api/v1/payment";
    private int itemId = 1009;
    private int quantity = 2;
    private String email = "vikram@gmail.com";
    private String address = "Crio.do.koramangla";
    private String paymentType = "CASH_ON_DELIVERY";
    private String postOrderBasePath = "api/v1/order";
    private int paymentId;


    @BeforeTest
    public void setValue() throws IOException {
        RestAssured.baseURI = "http://order-service-prod.zcommerce.crio.do";        
    }

    public int createOrderId(){
        RestAssured.basePath = postOrderBasePath;
        RequestSpecification http = RestAssured.given();        

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
        int orderId = jsonPath.get("data.id");
        return orderId;
    }

    @Test(description="POST Request on payment")
    public void initiatePayment(){
        int orderId = createOrderId();
        // System.out.println(orderId);
        RestAssured.basePath = postPaymentBasePath;
        RequestSpecification http = RestAssured.given();                    

        // create json object to store the orderItemsDetail, userID, and address derails;
        JSONObject payload = new JSONObject();
        payload.put("userId", email);
        payload.put("orderId", orderId);
        payload.put("paymentType",paymentType);
        payload.put("paymentGateway", "STRIPE");

        http.contentType(ContentType.JSON);

        http.header("Authorization", "Bearer USER_IMPERSONATE_"+email);             

        http.body(payload.toString());        

        Response response = http.when().post();
        // System.out.println(response.getBody().asPrettyString());
        // System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(),200);

        JsonPath jsonPath = response.jsonPath();
        this.paymentId = jsonPath.get("data.id");

        // validate the json schmea
        File fileObj = new File("src/test/resources/paymentSchema.json");
        JsonSchemaValidator validator = JsonSchemaValidator.matchesJsonSchema(fileObj);
        response.then().assertThat().body(validator);
    }
}
