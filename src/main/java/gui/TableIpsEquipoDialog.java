package gui;

import controller.Coordinator;
import exceptions.InvalidDireccionIPException;
import models.Equipo;
import utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * This class represents a dialog that displays a table with all the IPs of a specific equipment.
 * It allows the user to add, modify and delete IPs.
 */
public class TableIpsEquipoDialog extends javax.swing.JDialog {
    /**
     * Width of the dialog.
     */
    private static final int WIDTH_DIALOG = 400;
    /**
     * Height of the dialog.
     */
    private static final int HEIGHT_DIALOG = 300;
    /**
     * Coordinator instance to manage the application's business logic.
     */
    private Coordinator coordinator;
    /**
     * Resource bundle for internationalization.
     */
    private ResourceBundle rb;
    /**
     * Equipment instance to manage the application's business logic.
     */
    private Equipo equipo;
    /**
     * Table displaying the equipment types.
     */
    private javax.swing.JTable table;

    /**
     * Constructs a new TableIpsEquipoDialog.
     * <p>
     * This constructor initializes the dialog with the specified parent frame, modality, and coordinator.
     * It also sets up the components, style, and content of the dialog, and makes it visible.
     *
     * @param parent      the parent dialog of the dialog
     * @param modal       specifies whether the dialog blocks user input to other top-level windows when shown
     * @param coordinator the coordinator instance to manage the application's business logic
     * @param equipo      the equipment instance to manage the application's business logic
     */
    public TableIpsEquipoDialog(java.awt.Dialog parent, boolean modal, Coordinator coordinator, Equipo equipo) {
        super(parent, modal);
        this.coordinator = coordinator;
        this.rb = coordinator.getResourceBundle();
        this.equipo = equipo;
        initComponents();
        initStyle();
        initContent();
        this.setVisible(true);
    }

    /**
     * Initializes the style of the dialog.
     * <p>
     * This method sets the location of the dialog relative to its parent,
     * sets the title of the dialog, and specifies the default close operation.
     */
    private void initStyle() {
        this.setLocationRelativeTo(null);
        String idEquipo = "";
        if (equipo != null)
            idEquipo = " - " + equipo.getCodigo();
        this.setTitle(rb.getString("TableIpsEquipo_title") + idEquipo);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Initializes the content of the dialog.
     * <p>
     * This method retrieves the table model, clears any existing rows, and adds each
     * IP from the equipo to the table model. Finally, it sorts the table.
     */
    private void initContent() {
        // Get the table model
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();

        // Clear existing rows
        model.setRowCount(0);

        // Add each IP to the table model if the equipo is not null
        if (equipo != null) {
            for (String ip : equipo.getDireccionesIp()) {
                model.addRow(new Object[]{ip});
            }
        }

        // Sort the table
        javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter = (javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel>) table.getRowSorter();
        sorter.sort();
    }

    /**
     * Initializes the components of the dialog.
     * <p>
     * This method sets up the main background panel, the scroll pane containing the table,
     * and the bottom panel with action buttons. It also configures the table model,
     * selection mode, and action listeners for the buttons.
     */
    private void initComponents() {
        // Main background panel of the dialog
        javax.swing.JPanel bg = new javax.swing.JPanel();
        // Scroll pane that contains the table
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        // Panel at the bottom of the dialog containing action buttons
        javax.swing.JPanel bottomPanel = new javax.swing.JPanel();
        // Button to add a new IP
        javax.swing.JButton agregarBT = new javax.swing.JButton();
        // Button to modify a selected IP
        javax.swing.JButton modificarBT = new javax.swing.JButton();
        // Button to delete a selected IP
        javax.swing.JButton eliminarBT = new javax.swing.JButton();

        // Set the table model with 1 column: "IP"
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{rb.getString("TableIpsEquipo_ipColumn")}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };

        // Create a TableRowSorter and set it to the table
        javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter = new javax.swing.table.TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Set the table model
        table.setModel(model);

        // Set the selection mode to select rows
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        // Create a cell renderer that centers the text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Apply the renderer to each column
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        scrollPane.setViewportView(table);

        bottomPanel.setLayout(new java.awt.GridLayout(1, 3, 5, 0));

        agregarBT.setText(rb.getString("TableDialog_addButton"));
        agregarBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        agregarBT.addActionListener(_ -> agregarBTActionPerformed());
        bottomPanel.add(agregarBT);

        modificarBT.setText(rb.getString("TableDialog_modifyButton"));
        modificarBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        modificarBT.addActionListener(_ -> modificarBTActionPerformed());
        bottomPanel.add(modificarBT);

        eliminarBT.setText(rb.getString("TableDialog_deleteButton"));
        eliminarBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        eliminarBT.addActionListener(_ -> eliminarBTActionPerformed());
        bottomPanel.add(eliminarBT);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
                bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(bgLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(bottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                                .addContainerGap())
        );
        bgLayout.setVerticalGroup(
                bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(bgLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        getContentPane().add(bg, java.awt.BorderLayout.CENTER);

        // Set the preferred size of the dialog
        setPreferredSize(new java.awt.Dimension(WIDTH_DIALOG, HEIGHT_DIALOG));

        pack();
    }

    /**
     * Handles the action performed when the "Agregar" button is clicked.
     * <p>
     * This method creates a form to input a new IP, validates the input,
     * and adds the new IP to the equipo and the table if the input is valid.
     */
    private void agregarBTActionPerformed() {
        // Create the form
        javax.swing.JTextField ipField = new javax.swing.JTextField(15);
        javax.swing.JPanel panel = createFormPanel(ipField);

        // Show the JOptionPane
        int result = javax.swing.JOptionPane.showConfirmDialog(null, panel, rb.getString("TableIpsEquipo_addTitle"), javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
        if (result == javax.swing.JOptionPane.OK_OPTION) {
            // Get the entered value
            String ip = ipField.getText();


            // Add the new IP to the equipo if valid or show an error message
            try {
                coordinator.addIPEquipo(equipo, ip);
            } catch (InvalidDireccionIPException e) {
                javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update the table
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
            model.addRow(new Object[]{ip});

            // Sort the table
            javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter = (javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel>) table.getRowSorter();
            sorter.sort();

            // Show success message
            javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableIpsEquipo_addedSuccess"), rb.getString("TableDialog_success"), javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Handles the action performed when the "Modificar" button is clicked.
     * <p>
     * This method retrieves the selected row from the table, displays a form with the current value,
     * and updates the IP in the equipo and the table if the input is valid.
     */
    private void modificarBTActionPerformed() {
        // Get the index of the selected row
        int selectedRow = table.getSelectedRow();

        // Verify if a row is selected
        if (selectedRow != -1) {
            // Convert the view index to the model index
            int modelRow = table.convertRowIndexToModel(selectedRow);

            // Get the table model
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();

            // Get the current value of the selected row
            String currentIp = (String) model.getValueAt(modelRow, 0);

            // Create the form with the current value
            javax.swing.JTextField ipField = new javax.swing.JTextField(currentIp, 15);
            javax.swing.JPanel panel = createFormPanel(ipField);

            // Show the JOptionPane
            int result = javax.swing.JOptionPane.showConfirmDialog(null, panel, rb.getString("TableIpsEquipo_modifyTitle"), javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
            if (result == javax.swing.JOptionPane.OK_OPTION) {
                // Get the entered value
                String newIp = ipField.getText();


                // Update the IP if valid or show an error message
                try {
                    coordinator.modifyIPEquipo(equipo, currentIp, newIp);
                } catch (InvalidDireccionIPException e) {
                    javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }


                // Update the row in the table model
                model.setValueAt(newIp, modelRow, 0);

                // Show success message
                javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableIpsEquipo_modifiedSuccess"), rb.getString("TableDialog_success"), javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Handles the action performed when the "Eliminar" button is clicked.
     * <p>
     * This method retrieves the selected row from the table, confirms the deletion,
     * and removes the IP from the equipo and the table if confirmed.
     */
    private void eliminarBTActionPerformed() {
        // Get the index of the selected row
        int selectedRow = table.getSelectedRow();

        // Verify if a row is selected
        if (selectedRow != -1) {
            // Convert the view index to the model index
            int modelRow = table.convertRowIndexToModel(selectedRow);

            // Get the table model
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();

            // Get the current value of the selected row
            String currentIp = (String) model.getValueAt(modelRow, 0);

            // Show the confirmation message
            int confirm = javax.swing.JOptionPane.showConfirmDialog(null, rb.getString("TableDialog_confirmDelete") + " " + rb.getString("TableIpsEquipo_ipColumn") + " " + currentIp + "?", rb.getString("TableIpsEquipo_deleteTitle"), javax.swing.JOptionPane.YES_NO_OPTION);
            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                // Remove the IP
                try {
                    coordinator.removeIPEquipo(equipo, currentIp);
                } catch (InvalidDireccionIPException e) {
                    javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Remove the row from the table model
                model.removeRow(modelRow);

                javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableIpsEquipo_deletedSuccess"), rb.getString("TableDialog_success"), javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Creates a form panel with the specified text field.
     * <p>
     * This method creates a panel with a grid layout and adds a label and field for "IP".
     *
     * @param ipField the text field for the IP
     * @return the created form panel
     */
    private javax.swing.JPanel createFormPanel(javax.swing.JTextField ipField) {
        // Create the panel and set the layout
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(1, 1, 2, 5));

        // Add the field and label to the panel
        panel.add(new javax.swing.JLabel(rb.getString("TableIpsEquipo_ipColumn")));
        panel.add(ipField);

        return panel;
    }

}