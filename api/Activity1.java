package activities;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Activity1 {
    // Set base URL
    final static String ROOT_URI = "https://petstore.swagger.io/v2/pet";

    @Test(priority=1)
    public void addNewPet() {
        // Create JSON request
        String reqBody = "{"
            + "\"id\": 55554,"
            + "\"name\": \"Vicky\","
            + " \"status\": \"alive\""
        + "}";

        Response response = 
            given().contentType(ContentType.JSON) // Set headers
            .body(reqBody) // Add request body
            .when().post(ROOT_URI); // Send POST request

        // Assertion
        response.then().body("id", equalTo(55554));
        response.then().body("name", equalTo("Vicky"));
        response.then().body("status", equalTo("alive"));
    }

    @Test(priority=2)
    public void getPetInfo() {
        Response response = 
            given().contentType(ContentType.JSON) // Set headers
            .when().pathParam("petId", "55554") // Set path parameter
            .get(ROOT_URI + "{55554}"); // Send GET request

        // Assertion
        response.then().body("id", equalTo(55554));
        response.then().body("name", equalTo("Vicky"));
        response.then().body("status", equalTo("alive"));
    }
    
    @Test(priority=3)
    public void deletePet() {
        Response response = 
            given().contentType(ContentType.JSON) // Set headers
            .when().pathParam("petId", "55554") // Set path parameter
            .delete(ROOT_URI + "{55554}"); // Send DELETE request

        // Assertion
        response.then().body("code", equalTo(201));
        response.then().body("message", equalTo("{Dead}"));
    }
}