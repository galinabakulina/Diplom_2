package data;

public class OrderResponse {

    private String name;

    private boolean success;

    private String message;

    private Order order;

    public OrderResponse(String name, boolean success, Order order) {
        this.name = name;
        this.success = success;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "name='" + name + '\'' +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", order=" + order +
                '}';
    }
}
