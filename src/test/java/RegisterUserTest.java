import data.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class RegisterUserTest{
    private String accessToken;
    @Before
    public void setUp() {
        RestAssured.baseURI = ApiHelper.URL;
    }

    @Test
    @DisplayName("Can register new user")
    public void registerNewValidUser() {
        String password = "q1w2e3r4t5y6";
        String name = "Galina";
        String email = UUID.randomUUID() + "_galina@yandex.ru";
        UserResponse userResponse = ApiHelper.registerUser(email, password, name)
                .body()
                .as(UserResponse.class);
        accessToken = userResponse.getAccessToken();

        assertThat(userResponse.isSuccess(), equalTo(true));
        assertThat(userResponse.getUser(), notNullValue());
        assertThat(userResponse.getAccessToken(), notNullValue());
        assertThat(userResponse.getRefreshToken(), notNullValue());
    }

    @Test
    @DisplayName("Can not register duplicated user")
    public void canNotRegisterDuplicatedUser() {
        String password = "q1w2e3r4t5y6";
        String name = "Galina";
        String email = UUID.randomUUID() + "_galina@yandex.ru";
        UserResponse userResponse = ApiHelper.registerUser(email, password, name)
                .body()
                .as(UserResponse.class);
        accessToken = userResponse.getAccessToken();

        ApiHelper.registerUser(email, password, name)
                .then()
                .statusCode(403)
                .and()
                .body("message", equalTo("User already exists"));
    }

    @After
    public void tearDown() {
        ApiHelper.deleteUser(accessToken);
    }

}