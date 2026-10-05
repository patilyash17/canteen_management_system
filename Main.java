
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Main extends JFrame {

    private JComboBox<String> foodBox;
    private JSpinner quantitySpinner;
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel totalLabel;

    private final String[] foods = {
        "Burger", "Pizza", "Sandwich", "French Fries",
        "Coffee", "Juice", "Ice Cream", "Samosa"
    };

    private final double[] prices = {
        80, 100, 60, 50, 40, 45, 55, 20
    };

    private double total = 0;

    public Main() {

        setTitle("Smart Canteen Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // Title
        JLabel title = new JLabel(
            "SMART CANTEEN MANAGEMENT SYSTEM",
            SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 24));

        mainPanel.add(title, BorderLayout.NORTH);

        // Selection panel
        JPanel selectionPanel = new JPanel(new FlowLayout());

        foodBox = new JComboBox<>(foods);

        quantitySpinner =
            new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        JButton addButton = new JButton("Add to Order");

        selectionPanel.add(new JLabel("Food:"));
        selectionPanel.add(foodBox);

        selectionPanel.add(new JLabel("Quantity:"));
        selectionPanel.add(quantitySpinner);

        selectionPanel.add(addButton);

        // Table
        String[] columns = {
            "Food Item",
            "Quantity",
            "Price",
            "Total"
        };

        tableModel = new DefaultTableModel(columns, 0);

        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        // Center
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        centerPanel.add(selectionPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel buttons = new JPanel();

        JButton removeButton =
            new JButton("Remove Selected");

        JButton clearButton =
            new JButton("Clear Order");

        JButton checkoutButton =
            new JButton("Checkout");

        buttons.add(removeButton);
        buttons.add(clearButton);
        buttons.add(checkoutButton);

        totalLabel = new JLabel("Grand Total: ₹0.00");

        totalLabel.setFont(
            new Font("Arial", Font.BOLD, 18)
        );

        bottomPanel.add(buttons, BorderLayout.CENTER);
        bottomPanel.add(totalLabel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Add button
        addButton.addActionListener(e -> addItem());

        // Remove button
        removeButton.addActionListener(e -> removeItem());

        // Clear button
        clearButton.addActionListener(e -> clearOrder());

        // Checkout button
        checkoutButton.addActionListener(e -> checkout());
    }

    private void addItem() {

        int index = foodBox.getSelectedIndex();

        String food = foods[index];

        double price = prices[index];

        int quantity =
            (Integer) quantitySpinner.getValue();

        double itemTotal = price * quantity;

        tableModel.addRow(new Object[] {
            food,
            quantity,
            "₹" + String.format("%.2f", price),
            "₹" + String.format("%.2f", itemTotal)
        });

        total += itemTotal;

        updateTotal();
    }

    private void removeItem() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Please select an item first."
            );

            return;
        }

        String value =
            tableModel.getValueAt(row, 3)
                      .toString()
                      .replace("₹", "");

        double itemTotal =
            Double.parseDouble(value);

        total -= itemTotal;

        tableModel.removeRow(row);

        updateTotal();
    }

    private void clearOrder() {

        tableModel.setRowCount(0);

        total = 0;

        updateTotal();
    }

    private void checkout() {

        if (tableModel.getRowCount() == 0) {

            JOptionPane.showMessageDialog(
                this,
                "Your order is empty."
            );

            return;
        }

        JOptionPane.showMessageDialog(
            this,
            "PAYMENT SUCCESSFUL!\n\n" +
            "Total Amount: ₹" +
            String.format("%.2f", total) +
            "\n\nThank you for your order!"
        );

        tableModel.setRowCount(0);

        total = 0;

        updateTotal();
    }

    private void updateTotal() {

        totalLabel.setText(
            "Grand Total: ₹" +
            String.format("%.2f", total)
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Main app = new Main();

            app.setVisible(true);
        });
    }
}

