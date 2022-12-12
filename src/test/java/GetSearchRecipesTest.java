import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;


public class GetSearchRecipesTest extends AbstractTest {
    @Test
    void SearchRecipesOfItalianCuisineTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("titleMatch", "Risotto")
                .queryParam("cuisine", "Italian")
                .queryParam("sort", "cholesterol")
                .response()
                .expect()
                .body("totalResults", Matchers.equalTo("31"))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .header("Connection", "keep-alive")
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

        String results1 = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("titleMatch", "Pasta")
                .queryParam("cuisine", "Italian")
                .queryParam("maxCalories", "200")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", endsWith("3"))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L))
                .extract()
                .jsonPath()
                .get("results")
                .toString();
        System.out.println("\n" + "results" + results1);


        String results2 = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("titleMatch", "Pizza")
                .queryParam("cuisine", "Italian")
                .queryParam("maxCalories", "300")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(8))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L))
                .extract()
                .jsonPath()
                .get("results")
                .toString();
        System.out.println("\n" + "results" + results2);


        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "Italian")
                .queryParam("type", "main course")
                .queryParam("includeIngredients", "Cheese, tomatoes")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(26))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));
    }

    @Test
    void SearchRecipesOfGreekCuisineTest() {

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "Greek")
                .queryParam("titleMatch", "Salad")
                .queryParam("maxReadyTime", "20")
                .queryParam("maxVitaminC", "50")
                .header("Connection", "keep-alive")
                .expect()
                .body("totalResults", equalTo(1))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "Greek")
                .queryParam("titleMatch", "pork")
                .queryParam("type", "main course")
                .header("Connection", "keep-alive")
                .expect()
                .body("totalResults", equalTo(1))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

    }

    @Test
    void SearchRecipesKoreanAndVietnameseCuisinesTest() {

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "Korean")
                .queryParam("cuisine", "Vietnamese")
                .header("Connection", "keep-alive")
                .expect()
                .body("totalResults", equalTo(25))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "Korean")
                .queryParam("titleMatch", "Kim Chi")
                .header("Connection", "keep-alive")
                .expect()
                .body("totalResults", equalTo(7))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

    }

    @Test
    void SearchDifferentDietsRecipesTest() {

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("diet", "Ketogenic")
                .queryParam("type", "salad")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(6))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("diet", "Ketogenic")
                .queryParam("type", "breakfast")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(9))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("diet", "Gluten Free")
                .queryParam("type", "dessert")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(71))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

    }

    @Test
    void SearchRecipesOfSoupTest() {

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("type", "soup")
                .queryParam("excludeCuisine", "Korean")
                .queryParam("excludeCuisine", "Vietnamese")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(317))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("type", "soup")
                .queryParam("titleMatch", "Quinoa")
                .queryParam("equipment", "frying pan")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(4))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));
    }


    @Test
    void SearchFastCookingRecipesTest() {

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("titleMatch", "tomato")
                .queryParam("type", "side dish")
                .queryParam("maxReadyTime", "10")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(3))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("titleMatch", "Egg")
                .queryParam("type", "breakfast")
                .queryParam("maxReadyTime", "20")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(4))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("titleMatch", "Steak")
                .queryParam("type", "main course")
                .queryParam("maxReadyTime", "15")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(2))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "Mediterranean")
                .queryParam("includeIngredients", "Shrimp")
                .queryParam("maxReadyTime", "30")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(3))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));
    }

    @Test
    void SearchRecipesOfDrinksTest() {

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("type", "drink")
                .queryParam("maxAlcohol", "10")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(48))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("type", "drink")
                .queryParam("titleMatch", "Smoothie")
                .queryParam("equipment", "blender")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(10))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));
    }

    @Test
    void SearchRecipesOfDessertsTest() {

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("titleMatch", "Pudding")
                .queryParam("type", "dessert")
                .queryParam("maxCalories", "150")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(1))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("titleMatch", "Cherry")
                .queryParam("type", "dessert")
                .queryParam("maxCalories", "350")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(3))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "Italian")
                .queryParam("type", "dessert")
                .queryParam("maxCalories", "200")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(3))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "Southern")
                .queryParam("type", "dessert")
                .queryParam("includeIngredients", "butter, sugar, flour")
                .queryParam("sort", "calories")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(4))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

    }

    @Test
    void SearchRecipesOfMexicanCuisineTest() {

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("titleMatch", "potato")
                .queryParam("cuisine", "Mexican")
                .queryParam("sort", "calories")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(4))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("titleMatch", "tomato")
                .queryParam("cuisine", "Mexican")
                .queryParam("type", "side dishes")
                .expect()
                .header("Connection", "keep-alive")
                .body("totalResults", equalTo(2))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .contentType(ContentType.JSON)
                .time(lessThan(5000L));
    }

}
