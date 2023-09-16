import data.UserResponse;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.UUID;

public class TestBase {

    private String temporaryAccessToken;

    private String temporaryEmail;

    private String temporaryPassword;

    private String temporaryName;

    @BeforeClass
    public static void setBaseUrl() {
        RestAssured.baseURI = ApiHelper.URL;
    }

    @Before
    public void registerTemporaryUser() {
        temporaryName = "Galina";
        temporaryPassword = "q1w2e3r4t5y6";
        temporaryEmail = UUID.randomUUID() + "_galina@yandex.ru";
        UserResponse userResponse = ApiHelper.registerUser(temporaryEmail, temporaryPassword, temporaryName)
                .body()
                .as(UserResponse.class);
        temporaryAccessToken = userResponse.getAccessToken();
    }

    @After
    public void deleteTemporaryUser() {
        ApiHelper.deleteUser(temporaryAccessToken);
    }

    protected String getTemporaryAccessToken() {
        return temporaryAccessToken;
    }

    protected String getTemporaryEmail() {
        return temporaryEmail;
    }

    protected String getTemporaryPassword() {
        return temporaryPassword;
    }

    protected String getTemporaryName() {
        return temporaryName;
    }
}
