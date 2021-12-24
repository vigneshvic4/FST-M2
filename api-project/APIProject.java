package activities;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.equalTo;

public class APIProject {
    // Set base URL
    final static String ROOT_URI = "https://api.github.com/user/keys";
    final static String Base_URI = "https://api.github.com";
    String key="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQCjhRhTdTp2sECnXASnhGCtA71b6WnwfjRu1JQb0ATvbB5ErUtiwvLv3m0I7Hg4OOavWOoC7GTGT073O3H8Su+V28sUZxkBw1PEZPo2cTA8J/4XP1wlO8p3GFe9w/x8vUYpibBL/zto75YEnFA7HekBKuU0wpvkbycmtV+poSa+1tbNwsfitIy7VHq9+mJjBgRw0jzvmyogS8nIn2vbDzz1/RQ2EGa+i/VvnDCC1rskwy6u0+aAXB+2g0ENO4aQH+nti3BIPCpvV8HlOrjl8AGPsI1Zef26+3NvWCxOXKfJ4HtuU+067MFgIIqtW3GXVg3RQc0NJrr71ghEDfmRmDO/vClsBeS5c2hYjybRnCAV8v69XGpbbo/rVJPBbh2fML1e8ieAhozHSx/6/E1yoABQ2VAwpcYUkY1B/OdW9Yl8upAOsuC8P5lGGlZ6PSQLgsIgO8s76E7MM6/MvRxmzdvouGIrXdcuYWy09HuFLBaSWlSiIOob1LFdqBsah4uinV0=";
	String token="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";
	int keyID=0;
	// Declare request specification
	RequestSpecification requestSpec;
	 
	
	@BeforeClass
	public void setUp() {
	    // Create request specification
	    requestSpec = new RequestSpecBuilder()
	    	.setContentType(ContentType.JSON)
	    	.addHeader("Authorization", "Bearer "+token)
	        .setBaseUri(Base_URI)
	        .build();
	    
	   
	}
	@Test(priority=1)
    public void POST() {
    	
		String reqBody="{"+
		"\"title\":\"TestGit\","+
				"\"key\":\"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQCjhRhTdTp2sECnXASnhGCtA71b6WnwfjRu1JQb0ATvbB5ErUtiwvLv3m0I7Hg4OOavWOoC7GTGT073O3H8Su+V28sUZxkBw1PEZPo2cTA8J/4XP1wlO8p3GFe9w/x8vUYpibBL/zto75YEnFA7HekBKuU0wpvkbycmtV+poSa+1tbNwsfitIy7VHq9+mJjBgRw0jzvmyogS8nIn2vbDzz1/RQ2EGa+i/VvnDCC1rskwy6u0+aAXB+2g0ENO4aQH+nti3BIPCpvV8HlOrjl8AGPsI1Zef26+3NvWCxOXKfJ4HtuU+067MFgIIqtW3GXVg3RQc0NJrr71ghEDfmRmDO/vClsBeS5c2hYjybRnCAV8v69XGpbbo/rVJPBbh2fML1e8ieAhozHSx/6/E1yoABQ2VAwpcYUkY1B/OdW9Yl8upAOsuC8P5lGGlZ6PSQLgsIgO8s76E7MM6/MvRxmzdvouGIrXdcuYWy09HuFLBaSWlSiIOob1LFdqBsah4uinV0=\"}";
		 
		 System.out.println(reqBody);
		        Response response = 
		            given().contentType(ContentType.JSON) // Set headers
		            .header("Authorization", "Bearer "+token)
		            .body(reqBody) // Add request body
		            .when().post(ROOT_URI);
		        System.out.println(response.asPrettyString());
		        keyID=response.jsonPath().getInt("id");
		        System.out.println(keyID);
        // Assertions
        response.then().statusCode(201);
       // 
    }

    @Test (priority=2)
    public void GET() {
     
        Response response = 
            given().contentType(ContentType.JSON) // Set headers
           .header("Authorization", "Bearer "+token)
           .log().all()
            .when().get(ROOT_URI); // Run GET request
        String responseBody = response.getBody().asString();
       System.out.println("Response Body is =>  " + responseBody);
         // (responseBody);
        // Assertions
        response.then().statusCode(200);
       // response.then().body("[0].status", equalTo("sold"));
    }
    @Test(priority=3)
    public void Delete() throws IOException {
    	Response response = 
                given().contentType(ContentType.JSON) // Set headers
                .header("Authorization", "Bearer "+token)
                .log().all()
                .pathParam("keyID", keyID) // Add path parameter
                .when().delete(ROOT_URI + "/{keyID}"); // Send POST request
    	            // Assertion
         response.then().statusCode(204);
    }
}