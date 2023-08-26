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

public class RegistrationTests {
    private UserResponse userResponse;
    private String email;
    private final String password = "q1w2e3r4t5y6";
    private final String name = "Galina";
    private String accessToken;
    @Before
    public void setUp() {
        RestAssured.baseURI = ApiHelper.URL;
        email = UUID.randomUUID() + "_galina@yandex.ru";
        userResponse = ApiHelper.registerUser(email, password, name)
                .body()
                .as(UserResponse.class);
        accessToken = userResponse.getAccessToken();
    }

    @Test
    @DisplayName("Can register new user")
    public void registerNewValidUser() {
        assertThat(userResponse.isSuccess(), equalTo(true));
        assertThat(userResponse.getUser(), notNullValue());
        assertThat(userResponse.getAccessToken(), notNullValue());
        assertThat(userResponse.getRefreshToken(), notNullValue());
    }

    @Test
    @DisplayName("Can not register duplicated user")
    public void canNotRegisterDuplicatedUser() {
        assertThat(userResponse.isSuccess(), equalTo(true));
        assertThat(userResponse.getUser(), notNullValue());
        assertThat(userResponse.getAccessToken(), notNullValue());
        assertThat(userResponse.getRefreshToken(), notNullValue());

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