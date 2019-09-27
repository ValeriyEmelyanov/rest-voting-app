package com.example.restvotingapp;

import com.example.restvotingapp.util.AppProperties;
import com.example.restvotingapp.util.EndPoins;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test.
 * First run the rest-voting-app application, and then run this test.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class UsersEndPointsIntegrationTest {

    private static String authorizationHeader;

    private static Integer addedUserId;

    private final String CONTEXT_PATH;

    private AppProperties appProperties;

    @Autowired
    public UsersEndPointsIntegrationTest(AppProperties appProperties) {
        this.appProperties = appProperties;
        CONTEXT_PATH = appProperties.getContextPath();
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    @Order(1)
    void login() {
        RequestSpecification httpRequest = given();
        httpRequest.contentType(ContentType.JSON);

        httpRequest.body("{\"email\":\"admin@gmail.com\",\"password\":\"admin\"}");

        Response response = httpRequest
                .post(CONTEXT_PATH + EndPoins.USERS_LOGIN)
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .extract()
                .response();
        authorizationHeader = response.getHeader("Authorization");
        String userIdHeader = response.getHeader("UserId");

        assertNotNull(authorizationHeader);
        assertNotNull(userIdHeader);
    }

    @Test
    @Order(2)
    void create() {
        Map<String, Object> userDetails = mapUserDetails();

        Response response = given()
                .header("Authorization", authorizationHeader)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userDetails)
                .when()
                .post(CONTEXT_PATH + EndPoins.USERS)
                .then()
                .statusCode(HttpServletResponse.SC_CREATED)
                .contentType(ContentType.JSON)
                .extract()
                .response();

        addedUserId = response.jsonPath().getInt("id");
        assertNotNull(addedUserId);

        String bodyString = response.body().asString();
        try {
            JSONObject bodyJSONObject = new JSONObject(bodyString);
            JSONArray roleJsonArray = bodyJSONObject.getJSONArray("roles");

            assertNotNull(roleJsonArray);
            assertEquals(1, roleJsonArray.length());
        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(3)
    void createRepeated() {
        Map<String, Object> userDetails = mapUserDetails();

        given()
                .header("Authorization", authorizationHeader)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userDetails)
                .when()
                .post(CONTEXT_PATH + EndPoins.USERS)
                .then()
                .statusCode(HttpServletResponse.SC_CONFLICT);
    }

    @Test
    @Order(4)
    void createNotAuthorized() {
        Map<String, Object> userDetails = mapUserDetails();

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userDetails)
                .when()
                .post(CONTEXT_PATH + EndPoins.USERS)
                .then()
                .statusCode(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    @Order(5)
    void get() {
        Integer userId = 101;

        Response response = given()
                .pathParam("id", userId)
                .header("Authorization", authorizationHeader)
                .accept(ContentType.JSON)
                .when()
                .get(CONTEXT_PATH + EndPoins.USERS_ID)
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .contentType(ContentType.JSON)
                .extract()
                .response();

        Integer id = response.jsonPath().getInt("id");
        String email = response.jsonPath().getString("email");
        String name = response.jsonPath().getString("name");
        List<String> roles = response.jsonPath().getList("roles");

        assertNotNull(id);
        assertNotNull(email);
        assertNotNull(name);
        assertEquals("user@yandex.ru", email);
        assertEquals(1, roles.size());
        assertEquals("ROLE_USER", roles.get(0));
    }

    @Test
    @Order(6)
    void getWrongId() {
        Integer userId = 5;

        given()
                .pathParam("id", userId)
                .header("Authorization", authorizationHeader)
                .accept(ContentType.JSON)
                .when()
                .get(CONTEXT_PATH + EndPoins.USERS_ID)
                .then()
                .statusCode(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    @Order(7)
    void update() {
        String nameUpdated = "User updated";

        Set<String> rolesUpdated = new HashSet<>();
        rolesUpdated.add("ROLE_ADMIN");
        rolesUpdated.add("ROLE_USER");

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("name", nameUpdated);
        userDetails.put("enabled", "true");
        userDetails.put("roles", rolesUpdated);

        Response response = given()
                .pathParam("id", addedUserId)
                .header("Authorization", authorizationHeader)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userDetails)
                .when()
                .put(CONTEXT_PATH + EndPoins.USERS_ID)
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .contentType(ContentType.JSON)
                .extract()
                .response();

        String name = response.jsonPath().getString("name");
        boolean enabled = response.jsonPath().getBoolean("enabled");
        List<String> roles = response.jsonPath().getList("roles");

        assertNotNull(name);
        assertNotNull(roles);
        assertEquals(nameUpdated, name);
        assertTrue(enabled);
        assertEquals(2, roles.size());
    }

    @Test
    @Order(8)
    void delete() {
        given()
                .pathParam("id", addedUserId)
                .header("Authorization", authorizationHeader)
                .when()
                .delete(CONTEXT_PATH + EndPoins.USERS_ID)
                .then()
                .statusCode(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    @Order(9)
    void deleteRepeated() {
        given()
                .pathParam("id", addedUserId)
                .header("Authorization", authorizationHeader)
                .when()
                .delete(CONTEXT_PATH + EndPoins.USERS_ID)
                .then()
                .statusCode(HttpServletResponse.SC_BAD_REQUEST);
    }

    private Map<String, Object> mapUserDetails() {
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("name", "New user");
        userDetails.put("email", "NewUser@test.com");
        userDetails.put("password", "654321");
        userDetails.put("roles", roles);

        return userDetails;
    }
}
