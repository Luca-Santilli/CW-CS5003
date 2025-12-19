package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SupermarketGUI extends JFrame implements ActionListener {

    private final Stock stockSystem;
    private final JTextArea displayArea;
    private final JTextField idField;
    private final JTextField nameField;
    private final JTextField qtyField;
    
    private JButton addButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton addStockBtn;
    private JButton removeStockBtn;
    private JButton viewActivitiesBtn;
    private JButton sortBtn;

    public SupermarketGUI() {
        super("Supermarket Inventory System");
        stockSystem = new Stock(); 
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setSize(800, 600);

        idField = new JTextField(15);
        nameField = new JTextField(15);
        qtyField = new JTextField(15);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Stock Status"));

        add(createInputPanel(), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Product & Activity Details"));

        panel.add(new JLabel("Product ID (for Search/Mod/Del/History):"));
        panel.add(idField);
        panel.add(new JLabel("Product Name (Creation only):"));
        panel.add(nameField);
        panel.add(new JLabel("Quantity (Add/Remove/Initial):"));
        panel.add(qtyField);

        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addButton = new JButton("Create Product");
        deleteButton = new JButton("Delete Product");
        searchButton = new JButton("Search by ID");
        addStockBtn = new JButton("Add Stock (+)");
        removeStockBtn = new JButton("Remove Stock (-)");
        viewActivitiesBtn = new JButton("Recent Activity");
        sortBtn = new JButton("Sorted Activity");

        JButton[] buttons = {
            addButton,
            deleteButton,
            searchButton,
            addStockBtn,
            removeStockBtn,
            viewActivitiesBtn,
            sortBtn
        };
        for (JButton b : buttons) {
            b.addActionListener(this);
            panel.add(b);
        }

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Object source = e.getSource();
            if (source == addButton) handleAdd();
            else if (source == deleteButton) handleDelete();
            else if (source == searchButton) handleSearch();
            else if (source == addStockBtn) handleStockChange(true);
            else if (source == removeStockBtn) handleStockChange(false);
            else if (source == viewActivitiesBtn) handleHistory(false);
            else if (source == sortBtn) handleHistory(true);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity must be a valid integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAdd() {
        String name = nameField.getText().trim();
        int qty = Integer.parseInt(qtyField.getText().trim());
        stockSystem.addProduct(name, qty); 
        refreshDisplay();
        clearFields();
    }

    private void handleDelete() {
        String id = idField.getText().trim();
        Product product = stockSystem.removeProduct(id);
        if (product != null) {        
            JOptionPane.showMessageDialog(this, "Product removed.");
            refreshDisplay();
        } else {
            JOptionPane.showMessageDialog(this, "Product does not exist.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSearch() {
        String id = idField.getText().trim();
        Product p = stockSystem.getProducts().get(id); 
        if (p != null) {
            String info = String.format("ID: %s\nName: %s\nCurrent Qty: %d\nCreated: %s", 
                                         p.getId(), p.getName(), p.getQuantity(), p.getEntryDate());
            JOptionPane.showMessageDialog(this, info, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            nameField.setText(p.getName());
            qtyField.setText(String.valueOf(p.getQuantity()));
        } else {
            JOptionPane.showMessageDialog(this, "Product not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleStockChange(boolean isAdd) {
        String id = idField.getText().trim();
        int qty = Integer.parseInt(qtyField.getText().trim());
        if (isAdd) {
            stockSystem.addToStock(id, qty); 
        } else {
            stockSystem.removeFromStock(id, qty); 
        }
        refreshDisplay();
    }

    private void handleHistory(boolean sorted) {
        String id = idField.getText().trim();
        Product p = stockSystem.getProducts().get(id); 
        
        if (p != null) {
            ActivityList activities = sorted ? p.getActivities().getSorted() : p.getActivities(); 
            StringBuilder sb = new StringBuilder(sorted ? "--- Sorted Activities (Qty) ---\n" : "--- Recent Activity (Max 4) ---\n");
            
            for (Activity a : activities) { 
                sb.append(String.format("[%s] %s | Change: %d | Time: %s\n", 
                          a.getId(), a.getName(), a.getQuantity(), a.getDate()));
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Activity Log", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Select a valid Product ID first.");
        }
    }

    private void refreshDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s | %-20s | %-10s\n", "ID", "Name", "Qty"));
        sb.append("--------------------------------------------------\n");
        for (Product p : stockSystem.getProducts().values()) { 
            sb.append(String.format("%-10s | %-20s | %-10s\n", p.getId(), p.getName(), p.getQuantity()));
        }
        displayArea.setText(sb.toString());
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        qtyField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SupermarketGUI::new);
    }
}