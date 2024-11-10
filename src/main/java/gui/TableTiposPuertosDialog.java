package gui;

import controller.Coordinator;
import models.TipoPuerto;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This class represents a dialog that displays a table with all the port types
 * in the system. It allows the user to add, modify and delete port types.
 */
public class TableTiposPuertosDialog extends javax.swing.JDialog {
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
     * Table displaying the port types.
     */
    private javax.swing.JTable table;


    /**
     * Constructs a new TableTiposPuertosDialog.
     *
     * @param parent      the parent frame of the dialog
     * @param modal       specifies whether the dialog blocks user input to other top-level windows when shown
     * @param coordinator the Coordinator instance to manage the application's business logic
     */
    public TableTiposPuertosDialog(java.awt.Frame parent, boolean modal, Coordinator coordinator) {
        super(parent, modal);
        this.coordinator = coordinator;
        this.rb = coordinator.getResourceBundle();
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
        this.setTitle(rb.getString("TableTiposPuertos_title"));
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Initializes the content of the dialog.
     * <p>
     * This method retrieves the table model, clears any existing rows, and adds each
     * TipoPuerto from the coordinator to the table model. Finally, it sorts the table.
     */
    private void initContent() {
        // Get the table model
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();

        // Clear existing rows
        model.setRowCount(0);

        // Add each TipoPuerto to the table model
        for (Map.Entry<String, TipoPuerto> entry : coordinator.getTiposPuertos().entrySet()) {
            TipoPuerto tipoPuerto = entry.getValue();
            model.addRow(new Object[]{tipoPuerto.getCodigo(), tipoPuerto.getDescripcion(), tipoPuerto.getVelocidad()});
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
        // Button to add a new port type
        javax.swing.JButton agregarBT = new javax.swing.JButton();
        // Button to modify a selected port type
        javax.swing.JButton modificarBT = new javax.swing.JButton();
        // Button to delete a selected port type
        javax.swing.JButton eliminarBT = new javax.swing.JButton();

        // Set the table model with 3 columns: "Codigo", "Descripcion", and "Velocidad"
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{rb.getString("TableDialog_idColumn"), rb.getString("TableDialog_descriptionColumn"), rb.getString("TableDialog_speedColumn")}
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
     * This method creates a form to input a new TipoPuerto, validates the input,
     * and adds the new TipoPuerto to the coordinator and the table if the input is valid.
     */
    private void agregarBTActionPerformed() {
        // Create the form
        javax.swing.JTextField codigoField = new javax.swing.JTextField(10);
        javax.swing.JTextField descripcionField = new javax.swing.JTextField(20);
        javax.swing.JTextField velocidadField = new javax.swing.JTextField(10);
        javax.swing.JPanel panel = createFormPanel(codigoField, descripcionField, velocidadField, true);

        // Show the JOptionPane
        int result = javax.swing.JOptionPane.showConfirmDialog(null, panel, rb.getString("TableTiposPuertos_addTitle"), javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
        if (result == javax.swing.JOptionPane.OK_OPTION) {
            // Get the entered values
            String codigo = codigoField.getText();
            String descripcion = descripcionField.getText();
            int velocidad;
            try {
                velocidad = Integer.parseInt(velocidadField.getText());
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableDialog_error") + ": " + rb.getString("TableDialog_speedColumn") + " debe ser un número", rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verify that all fields are complete
            if (codigo.isEmpty() || descripcion.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableDialog_allFieldsRequired"), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
            } else {
                // Create a new TipoPuerto and add it to the table
                TipoPuerto nuevoTipoPuerto = new TipoPuerto(codigo, descripcion, velocidad);
                try {
                    coordinator.addTipoPuerto(nuevoTipoPuerto);
                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update the table
                javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
                model.addRow(new Object[]{codigo, descripcion, velocidad});

                // Sort the table
                javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter = (javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel>) table.getRowSorter();
                sorter.sort();

                // Show success message
                javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableTiposPuertos_addedSuccess"), rb.getString("TableDialog_success"), javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Handles the action performed when the "Modificar" button is clicked.
     * <p>
     * This method retrieves the selected row from the table, displays a form with the current values,
     * and updates the TipoPuerto in the coordinator and the table if the input is valid.
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

            // Get the current values of the selected row
            String currentCodigo = (String) model.getValueAt(modelRow, 0);
            String currentDescripcion = (String) model.getValueAt(modelRow, 1);
            int currentVelocidad = (int) model.getValueAt(modelRow, 2);

            // Create the form with the current values
            javax.swing.JTextField codigoField = new javax.swing.JTextField(currentCodigo, 10);
            javax.swing.JTextField descripcionField = new javax.swing.JTextField(currentDescripcion, 20);
            javax.swing.JTextField velocidadField = new javax.swing.JTextField(String.valueOf(currentVelocidad), 10);
            javax.swing.JPanel panel = createFormPanel(codigoField, descripcionField, velocidadField, false);

            // Show the JOptionPane
            int result = javax.swing.JOptionPane.showConfirmDialog(null, panel, rb.getString("TableTiposPuertos_modifyTitle"), javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
            if (result == javax.swing.JOptionPane.OK_OPTION) {
                // Get the entered values
                String newDescripcion = descripcionField.getText();
                int newVelocidad;
                try {
                    newVelocidad = Integer.parseInt(velocidadField.getText());
                } catch (NumberFormatException e) {
                    javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableDialog_error") + ": " + rb.getString("TableDialog_speedColumn") + " debe ser un número", rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verify that all fields are complete
                if (newDescripcion.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableDialog_allFieldsRequired"), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                } else {
                    // Update the TipoPuerto in the coordinator
                    TipoPuerto tipoPuerto = coordinator.getTiposPuertos().get(currentCodigo);
                    tipoPuerto.setDescripcion(newDescripcion);
                    tipoPuerto.setVelocidad(newVelocidad);
                    try {
                        coordinator.modifyTipoPuerto(tipoPuerto);
                    } catch (Exception e) {
                        javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Update the row in the table model
                    model.setValueAt(newDescripcion, modelRow, 1);
                    model.setValueAt(newVelocidad, modelRow, 2);

                    // Show success message
                    javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableTiposPuertos_modifiedSuccess"), rb.getString("TableDialog_success"), javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    /**
     * Handles the action performed when the "Eliminar" button is clicked.
     * <p>
     * This method retrieves the selected row from the table, confirms the deletion with the user,
     * and if confirmed, deletes the TipoPuerto from the coordinator and removes the row from the table.
     * If an error occurs during deletion, an error message is displayed.
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

            // Get the code of the selected TipoPuerto
            String codigo = (String) model.getValueAt(modelRow, 0);

            // Show the confirmation message
            int confirm = javax.swing.JOptionPane.showConfirmDialog(null, rb.getString("TableDialog_confirmDelete") + " " + rb.getString("TableTiposPuertos_name") + " " + codigo + "?", rb.getString("TableTiposPuertos_deleteTitle"), javax.swing.JOptionPane.YES_NO_OPTION);
            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                // Delete the TipoPuerto from the coordinator
                try {
                    coordinator.deleteTipoPuerto(coordinator.getTiposPuertos().get(codigo));
                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Remove the row from the table model
                model.removeRow(modelRow);

                javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableTiposPuertos_deletedSuccess"), rb.getString("TableDialog_success"), javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Creates a form panel with the specified text fields and sets the editability of the code field.
     * <p>
     * This method creates a panel with a grid layout and adds labels and text fields for "Codigo",
     * "Descripcion", and "Velocidad". The editability of the "Codigo" field is set based on the
     * provided boolean parameter.
     *
     * @param codigoField      the text field for the code
     * @param descripcionField the text field for the description
     * @param velocidadField   the text field for the speed
     * @param isEditable       specifies whether the code field is editable
     * @return the created form panel
     */
    private javax.swing.JPanel createFormPanel(javax.swing.JTextField codigoField, javax.swing.JTextField descripcionField, javax.swing.JTextField velocidadField, boolean isEditable) {
        // Set the editability of the code field
        codigoField.setEditable(isEditable);

        // Create the panel and set the layout
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(3, 1, 2, 5));

        // Add the fields and labels to the panel
        panel.add(new javax.swing.JLabel(rb.getString("TableDialog_idColumn") + ":"));
        panel.add(codigoField);
        panel.add(new javax.swing.JLabel(rb.getString("TableDialog_descriptionColumn") + ":"));
        panel.add(descripcionField);
        panel.add(new javax.swing.JLabel(rb.getString("TableDialog_speedColumn") + ":"));
        panel.add(velocidadField);

        return panel;
    }

}