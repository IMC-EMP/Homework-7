package api;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class ApiTest {

    static {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void getExistingPost_ShouldReturn200AndValidData() {
        Response response = given()
                .log().all()
                .when()
                .get("/posts/1")
                .then()
                .log().all()
                .extract().response();

        assertEquals(200, response.statusCode());
        assertTrue(response.contentType().startsWith("application/json"));
        assertEquals(1, response.jsonPath().getInt("id"));
        assertNotNull(response.jsonPath().getInt("userId"));
        assertFalse(response.jsonPath().getString("title").isEmpty());
    }

    @Test
    public void getAllPosts_ShouldReturn200AndNonEmptyList() {
        Response response = given()
                .log().all()
                .when()
                .get("/posts")
                .then()
                .log().all()
                .extract().response();

        assertEquals(200, response.statusCode());
        assertTrue(response.contentType().startsWith("application/json"));
        assertTrue(response.jsonPath().getList("$").size() > 0);
    }

    @Test
    public void getNonExistingPost_ShouldReturn404() {
        Response response = given()
                .log().all()
                .when()
                .get("/posts/999999")
                .then()
                .log().all()
                .extract().response();
        assertEquals(404, response.statusCode());
    }

    @Test
    public void getInvalidEndpoint_ShouldReturn404() {
        Response response = given()
                .log().all()
                .when()
                .get("/invalidendpoint")
                .then()
                .log().all()
                .extract().response();
        assertEquals(404, response.statusCode());
    }
}