/**
 * Product: name + unit price
 */
public class Product {
    private String name;
    private double unitPrice;

    public Product(String name, double unitPrice) {
        this.name = name == null ? "" : name;
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return name + " ($" + String.format("%.2f", unitPrice) + ")";
    }
}
