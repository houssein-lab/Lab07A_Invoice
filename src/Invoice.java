import java.util.ArrayList;
import java.util.List;

/**
 * Invoice holds a list of LineItems and provides subtotal/tax/total computations.
 */
public class Invoice {
    private final List<LineItem> items = new ArrayList<>();
    private double taxRate = 0.07; // 7%

    public void addItem(LineItem li) {
        if (li == null) throw new IllegalArgumentException("LineItem cannot be null");
        items.add(li);
    }

    public void removeItem(int index) {
        if (index < 0 || index >= items.size()) throw new IndexOutOfBoundsException();
        items.remove(index);
    }

    public List<LineItem> getItems() {
        return new ArrayList<>(items);
    }

    public void clear() {
        items.clear();
    }

    public double getSubtotal() {
        double s = 0.0;
        for (LineItem li : items) s += li.getLineTotal();
        return s;
    }

    public double getTax() {
        return getSubtotal() * taxRate;
    }

    public double getTotal() {
        return getSubtotal() + getTax();
    }

    public void setTaxRate(double rate) {
        if (rate < 0) throw new IllegalArgumentException("taxRate >= 0");
        this.taxRate = rate;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public String toReceiptString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append(String.format("%-20s %5s  %8s\n", "Item", "Qty", "LineTotal"));
        sb.append("-----------------------------------------\n");
        for (LineItem li : items) {
            sb.append(li.toReceiptLine()).append("\n");
        }
        sb.append("-----------------------------------------\n");
        sb.append(String.format("Sub-total: %25s\n", String.format("$%.2f", getSubtotal())));
        sb.append(String.format("Tax (%.0f%%): %22s\n", getTaxRate() * 100, String.format("$%.2f", getTax())));
        sb.append("-----------------------------------------\n");
        sb.append(String.format("Total: %27s\n", String.format("$%.2f", getTotal())));
        sb.append("=========================================\n");
        return sb.toString();
    }
}

