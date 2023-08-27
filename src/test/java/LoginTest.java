import data.UserResponse;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginTest extends TestBase {

        @Test
        @DisplayName("Valid user can login")
        public void loginWithValidFields() {
            UserResponse loginResponse = ApiHelper.login(getTemporaryEmail(), getTemporaryPassword())
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
            ApiHelper.login(getTemporaryEmail(), "wrong_password")
                    .then()
                    .statusCode(401)
                    .and()
                    .assertThat().body("message", equalTo("email or password are incorrect"));
        }

        @Test
        @DisplayName("Courier with wrong login can not login")
        public void canNotLoginWithWrongLogin() {
            ApiHelper.login("wrong_login", getTemporaryPassword())
                    .then()
                    .statusCode(401)
                    .and()
                    .assertThat().body("message", equalTo("email or password are incorrect"));
        }
}
