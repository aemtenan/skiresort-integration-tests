package org.example.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.model.Resort;
import org.junit.jupiter.api.Assertions;

import java.util.logging.Level;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.example.data.Constants.URL;

public class ResortAPIClient {

    private static final Logger LOGGER = Logger.getLogger(ResortAPIClient.class.getName());

    public int createResort(Resort resort) {

        String payload = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            payload = objectMapper.writeValueAsString(resort);
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        Assertions.assertNotNull(payload, "Error encountered while processing JSON.");

        int createdId = given()
                .when()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(URL)
                .then()
                .statusCode(201).extract().path("id");

        return createdId;
    }

    public int updateResort(Resort resort, int resortId) {

        String payload = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            payload = objectMapper.writeValueAsString(resort);
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        Assertions.assertNotNull(payload, "Error encountered while processing JSON.");

        int updatedId = given()
                .when()
                .contentType(ContentType.JSON)
                .body(payload)
                .put(URL + "/" + resortId)
                .then()
                .statusCode(200).extract().path("id");

        return updatedId;
    }

    public Resort getResort(int resortId) {
        Response response = given()
                .when()
                .get(URL + "/" + resortId)
                .then()
                .statusCode(200).extract().response();
        JsonPath jsonPath = response.jsonPath();
        String name = jsonPath.get("name");
        String town = jsonPath.get("town");
        return new Resort(name, town);
    }

    public void deleteResort(int resortId){
        given()
                .when()
                .delete(URL + "/" + resortId)
                .then()
                .statusCode(204);
    }
}
