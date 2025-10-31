import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Small Swing GUI to build an invoice.
 */
public class InvoiceFrame extends JFrame {
    private final Invoice invoice = new Invoice();

    // Form fields
    private final JTextField productNameField = new JTextField(20);
    private final JTextField unitPriceField = new JTextField(8);
    private final JTextField quantityField = new JTextField(4);

    private final JTextArea receiptArea = new JTextArea(15, 50);
    private final JList<String> itemsList = new JList<>(new DefaultListModel<>());

    public InvoiceFrame() {
        super("Invoice Builder - Lab07A_Invoice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Top form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new TitledBorder("Add Line Item"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Product name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(productNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Unit price:"), gbc);
        gbc.gridx = 1;
        formPanel.add(unitPriceField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        formPanel.add(quantityField, gbc);

        JButton addButton = new JButton("Add Item");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(addButton, gbc);

        // center: items list and receipt
        DefaultListModel<String> listModel = (DefaultListModel<String>) itemsList.getModel();
        JPanel centerPanel = new JPanel(new GridLayout(1,2,10,10));
        JScrollPane listScroll = new JScrollPane(itemsList);
        listScroll.setBorder(new TitledBorder("Current Items"));
        centerPanel.add(listScroll);

        receiptArea.setEditable(false);
        JScrollPane receiptScroll = new JScrollPane(receiptArea);
        receiptScroll.setBorder(new TitledBorder("Receipt"));
        centerPanel.add(receiptScroll);

        // bottom: control buttons
        JPanel bottomPanel = new JPanel();
        JButton removeButton = new JButton("Remove Selected");
        JButton clearButton = new JButton("Clear Invoice");
        JButton printButton = new JButton("Show Receipt");
        bottomPanel.add(removeButton);
        bottomPanel.add(clearButton);
        bottomPanel.add(printButton);

        // add handlers
        addButton.addActionListener(e -> {
            try {
                String name = productNameField.getText().trim();
                if (name.isEmpty()) throw new IllegalArgumentException("Name required");
                double price = Double.parseDouble(unitPriceField.getText().trim());
                int qty = Integer.parseInt(quantityField.getText().trim());
                if (qty <= 0) throw new IllegalArgumentException("Quantity must be > 0");
                Product p = new Product(name, price);
                LineItem li = new LineItem(p, qty);
                invoice.addItem(li);
                listModel.addElement(li.toString());
                updateReceiptArea();
                // clear fields
                productNameField.setText("");
                unitPriceField.setText("");
                quantityField.setText("1");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter valid numeric price and quantity.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        removeButton.addActionListener(e -> {
            int idx = itemsList.getSelectedIndex();
            if (idx >= 0) {
                invoice.removeItem(idx);
                listModel.remove(idx);
                updateReceiptArea();
            } else {
                JOptionPane.showMessageDialog(this, "Select an item to remove.");
            }
        });

        clearButton.addActionListener(e -> {
            invoice.clear();
            listModel.clear();
            updateReceiptArea();
        });

        printButton.addActionListener(e -> {
            receiptArea.setText(invoice.toReceiptString());
        });

        // layout frame
        setLayout(new BorderLayout(10,10));
        add(formPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void updateReceiptArea() {
        // simple running subtotal view
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-30s %5s  %8s\n","Item","Qty","LineTotal"));
        sb.append("------------------------------------------------------\n");
        for (LineItem li : invoice.getItems()) {
            sb.append(li.toReceiptLine()).append("\n");
        }
        sb.append("------------------------------------------------------\n");
        sb.append(String.format("Sub-total: %28s\n", String.format("$%.2f", invoice.getSubtotal())));
        sb.append(String.format("Tax: %32s\n", String.format("$%.2f", invoice.getTax())));
        sb.append(String.format("Total: %31s\n", String.format("$%.2f", invoice.getTotal())));
        receiptArea.setText(sb.toString());
    }
}
