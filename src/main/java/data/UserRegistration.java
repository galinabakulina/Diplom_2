package data;

public class UserRegistration {
    private String email;
    private String password;
    private String name;

    public UserRegistration(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
