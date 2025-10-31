public class InvoiceRunner {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            InvoiceFrame f = new InvoiceFrame();
            f.setVisible(true);
        });
    }
}
