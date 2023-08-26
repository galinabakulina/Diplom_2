import data.OrderRequest;
import data.OrderResponse;
import data.UserResponse;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderCreationTests {
    private String accessToken;

    @Before
    public void setUp() {
        UserResponse userResponse;
        String email;
        String password = "q1w2e3r4t5y6";
        String name = "Galina";
        RestAssured.baseURI = ApiHelper.URL;
        email = UUID.randomUUID() + "_galina@yandex.ru";
        userResponse = ApiHelper.registerUser(email, password, name)
                .body()
                .as(UserResponse.class);
        accessToken = userResponse.getAccessToken();
    }

    @Test
    @DisplayName("Authorized user can create an order with valid ingredients")
    public void authorizedUserCanCreateAnOrderWithValidIngredientsTest() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa6e", "61c0c5a71d1f82001bdaaa76");;
        OrderResponse order = ApiHelper.order(accessToken, ingredients)
                .body()
                .as(OrderResponse.class);
        assertThat(order.isSuccess(), equalTo(true));
        assertThat(order.getOrder().getNumber(), notNullValue());
        assertThat(order.getName(), notNullValue());
    }

    @Test
    @DisplayName("Authorized user can not create an order without ingredients")
    public void autorizedUserCanNotCreateAnOrderWithoutIngredientsTest() {
        ApiHelper.order(accessToken, Collections.emptyList())
                .then()
                .statusCode(400)
                .and()
                .assertThat()
                .body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .assertThat()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Authorized user can not create an order with wrong ingredients")
    public void autorizedUserCanNotCreateAnOrderWithWrongIngredientsTest() {
        ApiHelper.order(accessToken, List.of("wrong_ingredient_1", "wrong_ingredient_2"))
                .then()
                .statusCode( 500);
    }


    @Test
    @DisplayName("Authorized user can not create an order with wrong ingredients")
    public void unAutorizedUserCanNotCreateAnOrder() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa6e", "61c0c5a71d1f82001bdaaa76");;
        OrderResponse order = ApiHelper.unauthorizedOrder(ingredients)
                .body()
                .as(OrderResponse.class);
        assertThat(order.isSuccess(), equalTo(true));
        assertThat(order.getOrder().getNumber(), notNullValue());
    }

    @After
    public void tearDown() {
        ApiHelper.deleteUser(accessToken);
    }
}
