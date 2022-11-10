package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity1 {
    //Set the Base URI
    String baseURI="https://petstore.swagger.io/v2/pet";
    @Test(priority=1)
    public void POSTRequest(){
        // Create JSON request
        String reqBody = "{"
                + "\"id\": 260395,"
                + "\"name\": \"Emliy\","
                + " \"status\": \"alive\""
                + "}";

        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .body(reqBody) // Add request body
                        .when().post(baseURI); // Send POST request

        // Assertion
        response.then().body("id", equalTo(260395));
        response.then().body("name", equalTo("Emliy"));
        response.then().body("status", equalTo("alive"));
    }
    @Test(priority=2)
    public void GETRequest(){
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .when().pathParam("petId", "260395") // Set path parameter
                        .get(baseURI + "/{petId}"); // Send GET request

        // Assertion
        response.then().body("id", equalTo(260395));
        response.then().body("name", equalTo("Emliy"));
        response.then().body("status", equalTo("alive"));
    }
    @Test(priority=3)
    public void deletePet() {
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .when().pathParam("petId", "260395") // Set path parameter
                        .delete(baseURI + "/{petId}"); // Send DELETE request

        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("260395"));
    }
}
