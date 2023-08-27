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

public class PlaceOrderTest extends TestBase {

    @Test
    @DisplayName("Authorized user can create an order with valid ingredients")
    public void authorizedUserCanCreateAnOrderWithValidIngredientsTest() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa6e", "61c0c5a71d1f82001bdaaa76");;
        OrderResponse order = ApiHelper.placeOrder(getTemporaryAccessToken(), ingredients)
                .body()
                .as(OrderResponse.class);
        assertThat(order.isSuccess(), equalTo(true));
        assertThat(order.getOrder().getNumber(), notNullValue());
        assertThat(order.getName(), notNullValue());
    }

    @Test
    @DisplayName("Authorized user can not create an order without ingredients")
    public void authorizedUserCanNotCreateAnOrderWithoutIngredientsTest() {
        ApiHelper.placeOrder(getTemporaryAccessToken(), Collections.emptyList())
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
    public void authorizedUserCanNotCreateAnOrderWithWrongIngredientsTest() {
        ApiHelper.placeOrder(getTemporaryAccessToken(), List.of("wrong_ingredient_1", "wrong_ingredient_2"))
                .then()
                .statusCode( 500);
    }


    @Test
    @DisplayName("Unauthorized user can create an order with valid ingredients")
    public void unAuthorizedUserCanNotCreateAnOrder() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa6e", "61c0c5a71d1f82001bdaaa76");;
        OrderResponse order = ApiHelper.placeOrder(null, ingredients)
                .body()
                .as(OrderResponse.class);
        assertThat(order.isSuccess(), equalTo(true));
        assertThat(order.getOrder().getNumber(), notNullValue());
    }
}
