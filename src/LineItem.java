/**
 * LineItem: holds Product and quantity and can compute line total.
 */
public class LineItem {
    private final Product product;
    private int quantity;

    public LineItem(Product product, int quantity) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        this.product = product;
        setQuantity(quantity);
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Quantity must be >= 0");
        this.quantity = quantity;
    }

    public double getLineTotal() {
        return product.getUnitPrice() * quantity;
    }

    public String toReceiptLine() {
        return String.format("%-20s %5d  $%7.2f", product.getName(), quantity, getLineTotal());
    }

    @Override
    public String toString() {
        return product.getName() + " x" + quantity + " => $" + String.format("%.2f", getLineTotal());
    }
}
