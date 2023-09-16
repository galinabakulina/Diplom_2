package data;

import java.util.List;

public class OrderRequest {
    List<String> ingredients;

    public OrderRequest(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "ingredients=" + ingredients +
                '}';
    }
}
