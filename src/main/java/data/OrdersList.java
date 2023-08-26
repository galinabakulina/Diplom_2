package data;

import java.util.List;

public class OrdersList {
    private boolean success;
    private List<OrderItem> orders;

    public OrdersList(boolean success, List<OrderItem> orders) {
        this.success = success;
        this.orders = orders;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "OrdersList{" +
                "success=" + success +
                ", orders=" + orders +
                '}';
    }
}
