import data.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetUsersOrderTests {
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

        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa6e", "61c0c5a71d1f82001bdaaa76");;
        OrderResponse order = ApiHelper.order(accessToken, ingredients)
                .body()
                .as(OrderResponse.class);
    }

    @Test
    @DisplayName("Authorized user can get orders list")
    public void authorizedUserCanGetOrdersListTest() {
        OrdersList ordersList = ApiHelper.getUsersOrders(accessToken)
                .body()
                .as(OrdersList.class);
        assertThat(ordersList.isSuccess(), equalTo(true));
        assertThat(ordersList.getOrders(), notNullValue());
    }

    @Test
    @DisplayName("Unauthorized user cannot get orders list")
    public void unAuthorizedUserCanNotGetOrdersListTest() {
        ApiHelper.getUnauthorizedUsersOrders()
                .then()
                .statusCode(401)
                .and()
                .assertThat()
                .body("success", equalTo(false))
                .and()
                .assertThat()
                .body("message", equalTo("You should be authorised"));
        ;
    }
    @After
    public void tearDown() {
        ApiHelper.deleteUser(accessToken);
    }
}
