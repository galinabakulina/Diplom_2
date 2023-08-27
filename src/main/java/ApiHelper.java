import data.*;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiHelper {
    public static final String URL = "https://stellarburgers.nomoreparties.site";

    private static final String REGISTER_USER = "/api/auth/register";
    private static final String AUTH_USER = "/api/auth/user";
    private static final String LOGIN_USER = "/api/auth/login";
    private static final String ORDER = "/api/orders";

    @Step("Send post request to " + REGISTER_USER)
    public static Response registerUser(String email, String password, String name) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(new UserRegistration(email, password, name))
                .when()
                .post(REGISTER_USER);
    }

    @Step("Send delete request to " + AUTH_USER)
    public static void deleteUser(String accessToken) {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", String.valueOf(accessToken))
                .when()
                .delete(AUTH_USER);
    }

    @Step("Send post request to " + LOGIN_USER)
    public static Response login(String login, String password){
        return given()
                .header("Content-type", "application/json")
                .body(new Login(login, password))
                .post(LOGIN_USER);
    }

    @Step("Send patch request to " + AUTH_USER)
    public static Response updateUser(String accessToken, String new_email, String new_name){
        User user = new User(new_email, new_name);
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", String.valueOf(accessToken))
                .body(user)
                .when()
                .patch(AUTH_USER);
    }

    @Step("Send post request to " + ORDER)
    public static Response placeOrder(String accessToken, List<String> ingredients){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", String.valueOf(accessToken))
                .body(new OrderRequest(ingredients))
                .when()
                .post(ORDER);
    }

    @Step("Send get request to " + ORDER)
    public static Response getUsersOrders(String accessToken){
        return given()
                .header("Authorization", String.valueOf(accessToken))
                .when()
                .get(ORDER);
    }
}