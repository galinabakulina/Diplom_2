import data.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetOrdersTest extends TestBase {

    @Before
    public void setUp() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa6e", "61c0c5a71d1f82001bdaaa76");;
        ApiHelper.placeOrder(getTemporaryAccessToken(), ingredients);
    }

    @Test
    @DisplayName("Authorized user can get orders list")
    public void authorizedUserCanGetOrdersListTest() {
        OrdersList ordersList = ApiHelper.getUsersOrders(getTemporaryAccessToken())
                .body()
                .as(OrdersList.class);
        assertThat(ordersList.isSuccess(), equalTo(true));
        assertThat(ordersList.getOrders(), notNullValue());
    }

    @Test
    @DisplayName("Unauthorized user can not get orders list")
    public void unAuthorizedUserCanNotGetOrdersListTest() {
        ApiHelper.getUsersOrders(null)
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
}
