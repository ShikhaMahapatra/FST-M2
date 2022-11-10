package examples;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.Assert.assertEquals;

public class FirstTest {
    //Set the Base URI
    String baseURI="https://petstore.swagger.io/v2/pet";

    @Test
    public void getRequestWithQueryParam(){
        //Generate response and save it
        Response response =
                given().header("Content-Type","application/json")
                        .queryParam("status","sold").
                        when().get(baseURI+"/findByStatus");
        //Print the Response Body
        System.out.println("String"+response.getBody().asString());
        System.out.println("Pretty String"+response.getBody().asPrettyString());
        //Print the response Headers
        //System.out.println("Response Header"+response.getHeaders().asList());
        //Extract the Properties from the Response
        String petStatus=response.then().extract().path("[0].status");
       // System.out.println(petStatus);

        //Assertions
        //assertEquals(petStatus,"sold");//or below snippet
        response.then().statusCode(200).body("[0].status",equalTo("sold"));
        response.then().time(lessThan(4000L));
     }
     @Test
    public void getRequestWithPathParam(){
        given().contentType(ContentType.JSON).pathParam("petId",9).
                when().get(baseURI+"/{petId}").
                then().statusCode(200).body("status",equalTo("sold")).time(lessThan(5000L));
     }
}
