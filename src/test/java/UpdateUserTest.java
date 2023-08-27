import data.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class UpdateUserTest extends TestBase {

    @Test
    @DisplayName("Authorized user can update name and email")
    public void authorizedUserCanUpdateData() {
        String new_email = "galochka@yandex.ru";
        String new_name = "Galochka";
        UpdatedUser updatedUser = ApiHelper.updateUser(getTemporaryAccessToken(), new_email, new_name).as(UpdatedUser.class);
        assertThat(updatedUser.isSuccess(), equalTo(true));
        assertThat(updatedUser.getUser().getEmail(), containsString(new_email));
        assertThat(updatedUser.getUser().getName(), containsString(new_name));
    }

    @Test
    @DisplayName("Unauthorized user cannot be updated")
    public void unauthorizedUserCanNotBeUpdated() {
        ApiHelper.updateUser(null, "dummy@yandex.ru", "dummy")
                .then()
                .statusCode(401)
                .and()
                .assertThat().body("message", equalTo("You should be authorised"));
    }
}

