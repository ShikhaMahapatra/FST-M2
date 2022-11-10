package activities;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity2 {
    //Set the Base URI
    String baseURI="https://petstore.swagger.io/v2/user";
@Test(priority = 1)
    public void PostPetSUser() throws IOException {
        File file=new File("src/test/java/activities/postBody.json");
        FileInputStream inputJSON = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        inputJSON.read(bytes);
        // Read JSON file as String
        String reqBody = new String(bytes, "UTF-8");

        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .body(reqBody).log().all() // Pass request body from file
                        .when().post(baseURI); // Send POST request

        inputJSON.close();

        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("260394"));
    }
    @Test(priority = 2)
    public void getUserInfo() {
        // Import JSON file to write to
        File outputJSON = new File("src/test/java/activities/userGETResponse.json");

        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .pathParam("username", "friendZone2") // Pass request body from file
                        .when().get(baseURI + "/{username}"); // Send POST request

        // Get response body
        String resBody = response.getBody().asPrettyString();

        try {
            // Create JSON file
            outputJSON.createNewFile();
            // Write response body to external file
            FileWriter writer = new FileWriter(outputJSON.getPath());
            writer.write(resBody);
            writer.close();
        } catch (IOException excp) {
            excp.printStackTrace();
        }

        // Assertion
        response.then().body("id", equalTo(260394));
        response.then().body("username", equalTo("friendZone2"));
        response.then().body("firstName", equalTo("friend"));
        response.then().body("lastName", equalTo("Zone"));
        response.then().body("email", equalTo("friendZone2@mail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9876543210"));
    }
@Test(priority = 3)
    public void deleteUser() throws IOException {
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .pathParam("username", "friendZone2") // Add path parameter
                        .when().delete(baseURI + "/{username}"); // Send POST request

        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("friendZone2"));
    }
    }
