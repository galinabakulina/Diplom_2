import data.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static java.util.function.Predicate.not;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class UpdateTests {
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = ApiHelper.URL;
        String name = "Galina";
        String password = "q1w2e3r4t5y6";
        String email = UUID.randomUUID() + "_galina@yandex.ru";
        UserResponse userResponse = ApiHelper.registerUser(email, password, name)
                .body()
                .as(UserResponse.class);
        accessToken = userResponse.getAccessToken();
    }

    @Test
    @DisplayName("Authorized user can update name and email")
    public void authorizedUserCanUpdateData() {
        String new_email = "galochka@yandex.ru";
        String new_name = "Galochka";
        UpdatedUser updatedUser = ApiHelper.update(accessToken, new_email, new_name).as(UpdatedUser.class);
        assertThat(updatedUser.isSuccess(), equalTo(true));
        assertThat(updatedUser.getUser().getEmail(), containsString(new_email));
        assertThat(updatedUser.getUser().getName(), containsString(new_name));
    }

    @Test
    @DisplayName("Unauthorized user cannot be updated")
    public void unauthorizedUserCanNotBeUpdated() {
        ApiHelper.unauthorizedUpdate()
                .then()
                .statusCode(401)
                .and()
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @After
    public void tearDown() {
        ApiHelper.deleteUser(accessToken);
    }
}

