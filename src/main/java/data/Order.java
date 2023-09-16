package data;

public class Order {

    private long number;

    public Order(long number) {
        this.number = number;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Order{" +
                "number=" + number +
                '}';
    }
}
