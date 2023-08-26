package data;

public class UpdatedUser {
    boolean success;
    private User user;

    public UpdatedUser(boolean success, User user) {
        this.success = success;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public String toString() {
        return "UserResponse{" +
                "success=" + success +
                ", user=" + user +
                '}';
    }
}
