package org.example.test;

import io.restassured.http.ContentType;
import org.example.client.ResortAPIClient;
import org.example.model.Resort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.example.data.Constants.INVALID_URL;
import static org.example.data.Constants.URL;
import static org.example.data.TestData.*;

public class ResortAPITests {

    private static final Logger LOGGER = Logger.getLogger( ResortAPITests.class.getName() );
    ResortAPIClient resortAPIClient = new ResortAPIClient();

    @Test
    public void CRUDTest(){


        Resort resort = new Resort(SNOWY_HILLS, WIARTON);

        //Create the Resort using a POST API Call
        int createdId = resortAPIClient.createResort(resort);

        //Retrieve the Resort using a GET API Call
        Resort createdResort = resortAPIClient.getResort(createdId);
        Assertions.assertEquals(SNOWY_HILLS, createdResort.getName());
        Assertions.assertEquals(WIARTON, createdResort.getTown());

        //Update the Resort using a PUT API Call
        Resort resortToUpdate = new Resort(SNOWY_HILLS, SOUTHAMPTON);
        int updatedId = resortAPIClient.updateResort(resortToUpdate, createdId);

        //Retrieve the updated Resort using a GET API Call
        Resort updatedResort = resortAPIClient.getResort(updatedId);
        Assertions.assertEquals(SNOWY_HILLS, updatedResort.getName());
        Assertions.assertEquals(SOUTHAMPTON, updatedResort.getTown());

        //Delete the Resort using a DELETE API Call
        resortAPIClient.deleteResort(updatedId);
    }

    @Test
    public void invalidHeaders(){
        String createPayload = "{\"name\": \"Snowy Hills\",\"townInvalid\": \"Wiarton\"}";

        given()
                .when()
                .contentType(ContentType.TEXT)
                .body(createPayload)
                .post("http://localhost:8080/api/v2/resorts")
                .then()
                .statusCode(415);
    }

    @Test
    public void invalidPayload(){

        String createPayload = "{\"name\": \"Snowy Hills\",\"townInvalid\": \"Wiarton\"}";

        int createdId = given()
                .when()
                .contentType(ContentType.JSON)
                .body(createPayload)
                .post("http://localhost:8080/api/v2/resorts")
                .then()
                .statusCode(201).extract().path("id");

        //Retrieve the Resort using a GET API Call
        Resort createdResort = resortAPIClient.getResort(createdId);
        Assertions.assertEquals(SNOWY_HILLS, createdResort.getName());
        Assertions.assertEquals(null, createdResort.getTown());

        //Delete the Resort using a DELETE API Call
        resortAPIClient.deleteResort(createdId);
    }

    @Test
    public void invalidMethod(){
        String createPayload = "{\"name\": \"Snowy Hills\",\"town\": \"Wiarton\"}";

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(createPayload)
                .put(URL)
                .then()
                .statusCode(405);
    }

    @Test
    public void invalidURL(){

        given()
                .when()
                .get(INVALID_URL)
                .then()
                .statusCode(404);

    }


}