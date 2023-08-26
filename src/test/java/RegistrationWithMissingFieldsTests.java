import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized .class)
public class RegistrationWithMissingFieldsTests {
    private final String email;
    private final String password;
    private final String name;

    public RegistrationWithMissingFieldsTests(String email, String password, String name) {
            this.email = email;
            this.password = password;
            this.name = name;
        }

        @Parameterized.Parameters
        public static Object[][] orderDetails() {

            return new Object[][]{
                    {null, null, null},
                    {null, null, "Galina"},
                    {null, "q1w2e3r4t5y6", null},
                    {null, "q1w2e3r4t5y6", "Galina"},
                    {"galina_21@yandex.ru", null, null},
                    {"galina_21@yandex.ru", null, "Galina"},
                    {"galina_21@yandex.ru", "q1w2e3r4t5y6", null}
            };
        }

        @Before
        public void setUp() {
            RestAssured.baseURI = ApiHelper.URL;
        }

        @Test
        @DisplayName("Can not register user with missing fields")
        public void canNotRegisterUserWithMissingFields() {
            ApiHelper.registerUser(email, password, name)
                    .then()
                    .statusCode(403)
                    .and()
                    .body("message", equalTo("Email, password and name are required fields"));
        }
    }

