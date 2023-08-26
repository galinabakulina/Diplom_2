import data.UserResponse;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginTests {
    private String email;
    private final String password = "q1w2e3r4t5y6";
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = ApiHelper.URL;
        email = UUID.randomUUID() + "_galina@yandex.ru";
        String name = "Galina";
        UserResponse userResponse = ApiHelper.registerUser(email, password, name)
                .body()
                .as(UserResponse.class);
        accessToken = userResponse.getAccessToken();
    }

        @Test
        @DisplayName("Valid user can login")
        public void loginWithValidFields() {
            UserResponse loginResponse = ApiHelper.login(email, password)
                    .body()
                    .as(UserResponse.class);

            assertThat(loginResponse.isSuccess(), equalTo(true));
            assertThat(loginResponse.getUser(), notNullValue());
            assertThat(loginResponse.getAccessToken(), notNullValue());
            assertThat(loginResponse.getRefreshToken(), notNullValue());
        }

        @Test
        @DisplayName("Courier with wrong password can not login")
        public void canNotLoginWithWrongPassword() {
            ApiHelper.login(email, "wrong_password")
                    .then()
                    .statusCode(401)
                    .and()
                    .assertThat().body("message", equalTo("email or password are incorrect"));
        }

        @Test
        @DisplayName("Courier with wrong login can not login")
        public void canNotLoginWithWrongLogin() {
            ApiHelper.login("wrong_login", password)
                    .then()
                    .statusCode(401)
                    .and()
                    .assertThat().body("message", equalTo("email or password are incorrect"));
        }

        @After
        public void tearDown() {
            ApiHelper.deleteUser(accessToken);
        }
    }
