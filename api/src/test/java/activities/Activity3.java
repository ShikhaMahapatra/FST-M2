package activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity3 {
    // Declare request specification
    RequestSpecification requestSpec;
    // Declare response specification
    ResponseSpecification responseSpec;
    String baseURI="https://petstore.swagger.io/v2/pet";
@BeforeClass
    public void setUp(){
        requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON)
                .setBaseUri(baseURI).build();
        responseSpec=new ResponseSpecBuilder().expectStatusCode(200)
                .expectContentType(ContentType.JSON)
        .expectBody("status", equalTo("alive"))
        .build();
    }

    @DataProvider
    public Object[][] petInfoProvider() {
        Object[][] testData = new Object[][] {
                { 77232, "Riley", "alive" },
                { 77233, "Hansel", "alive" }
        };
        return testData;
    }
    @Test(priority=1)
    public void postAddPets(){
        String reqBody = "{\"id\": 77232, \"name\": \"Riley\", \"status\": \"alive\"}";
        Response response = given().spec(requestSpec) // Use requestSpec
                .body(reqBody).log().body()// Send request body
                .when().post();
        reqBody = "{\"id\": 77233, \"name\": \"Hansel\", \"status\": \"alive\"}";
        response = given().spec(requestSpec) // Use requestSpec
                .body(reqBody) .log().body()// Send request body
                .when().post(); // Send POST request
        response.then().spec(responseSpec).log().all();
    }
    @Test(dataProvider = "petInfoProvider", priority=2)
    public void getPets(int id, String name, String status) {
        Response response = given().spec(requestSpec) // Use requestSpec
                .pathParam("petId", id).log().all()// Add path parameter
                .when().get("/{petId}"); // Send GET request

        // Print response
        System.out.println(response.asPrettyString());
        // Assertions
        response.then()
                .spec(responseSpec) // Use responseSpec
                .body("name", equalTo(name)); // Additional Assertion
    }
    @Test(dataProvider = "petInfoProvider", priority=3)
    public void deletePets(int id, String name, String status) {
        Response response = given().spec(requestSpec) // Use requestSpec
                .pathParam("petId", id) // Add path parameter
                .when().delete("/{petId}"); // Send GET request

        // Assertions
        response.then().body("code", equalTo(200));
    }



}
