package gui;

import controller.Coordinator;
import exceptions.InvalidPuertoEquipoException;
import models.Equipo;
import models.TipoPuerto;
import models.Puerto;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * This class represents a dialog that displays a table with all the ports of a specific equipment.
 * It allows the user to add, modify and delete ports.
 */
public class TablePuertosEquipoDialog extends javax.swing.JDialog {
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
     * Constructs a new TablePuertosEquipoDialog.
     * <p>
     * This constructor initializes the dialog with the specified parent frame, modality, and coordinator.
     * It also sets up the components, style, and content of the dialog, and makes it visible.
     *
     * @param parent      the parent dialog of the dialog
     * @param modal       specifies whether the dialog blocks user input to other top-level windows when shown
     * @param coordinator the coordinator instance to manage the application's business logic
     * @param equipo      the equipment instance to manage the application's business logic
     */
    public TablePuertosEquipoDialog(java.awt.Dialog parent, boolean modal, Coordinator coordinator, Equipo equipo) {
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
        this.setTitle(rb.getString("TablePuertosEquipo_title") + " - " + equipo.getCodigo());
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Initializes the content of the dialog.
     * <p>
     * This method retrieves the table model, clears any existing rows, and adds each
     * Puerto from the equipo to the table model. Finally, it sorts the table.
     */
    private void initContent() {
        // Get the table model
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();

        // Clear existing rows
        model.setRowCount(0);

        // Add each Puerto to the table model
        for (Puerto puerto : equipo.getPuertos()) {
            TipoPuerto tipoPuerto = puerto.getTipoPuerto();
            Integer cantidad = puerto.getCantidad();
            model.addRow(new Object[]{cantidad, tipoPuerto.getCodigo()});
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
        // Button to add a new port
        javax.swing.JButton agregarBT = new javax.swing.JButton();
        // Button to modify a selected port
        javax.swing.JButton modificarBT = new javax.swing.JButton();
        // Button to delete a selected port
        javax.swing.JButton eliminarBT = new javax.swing.JButton();

        // Set the table model with 2 columns: "Cantidad" and "Tipo Puerto"
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{rb.getString("TablePuertosEquipo_quantityColumn"), rb.getString("TablePuertosEquipo_portTypeColumn")}
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
     * This method creates a form to input a new Puerto, validates the input,
     * and adds the new Puerto to the equipo and the table if the input is valid.
     */
    private void agregarBTActionPerformed() {
        // Create the form
        javax.swing.JTextField cantidadField = new javax.swing.JTextField(10);
        javax.swing.JComboBox<TipoPuerto> tipoPuertoComboBox = new javax.swing.JComboBox<>(coordinator.getTiposPuertos().values().toArray(new TipoPuerto[0]));

        // Set a custom renderer to display only the codigo attribute
        tipoPuertoComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof TipoPuerto) {
                    setText(((TipoPuerto) value).getCodigo());
                }
                return c;
            }
        });

        javax.swing.JPanel panel = createFormPanel(cantidadField, tipoPuertoComboBox);

        // Show the JOptionPane
        int result = javax.swing.JOptionPane.showConfirmDialog(null, panel, rb.getString("TablePuertosEquipo_addTitle"), javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
        if (result == javax.swing.JOptionPane.OK_OPTION) {
            // Get the entered values
            int cantidad;
            try {
                cantidad = Integer.parseInt(cantidadField.getText());
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableDialog_error") + ": " + rb.getString("TableDialog_speedColumn") + " " + rb.getString("TableDialog_numberRequired"), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(cantidad < 1) {
                javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableDialog_error") + ": " + rb.getString("TableDialog_speedColumn") + " " + rb.getString("TableDialog_numberRequired"), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            TipoPuerto tipoPuerto = (TipoPuerto) tipoPuertoComboBox.getSelectedItem();

            // Verify that all fields are complete
            if (tipoPuerto == null) {
                javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableDialog_allFieldsRequired"), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
            } else {
                // Add the new Puerto to the equipo
                Puerto puerto = new Puerto(cantidad, tipoPuerto);

                // Check if the port already exists in the device and add it to the device or show an error message
                try {
                    coordinator.addPuertoEquipo(equipo, puerto);
                } catch (InvalidPuertoEquipoException e) {
                    javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update the table
                javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
                model.addRow(new Object[]{cantidad, tipoPuerto.getCodigo()});

                // Sort the table
                javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter = (javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel>) table.getRowSorter();
                sorter.sort();

                // Show success message
                javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TablePuertosEquipo_addedSuccess"), rb.getString("TableDialog_success"), javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Handles the action performed when the "Modificar" button is clicked.
     * <p>
     * This method retrieves the selected row from the table, displays a form with the current values,
     * and updates the Puerto in the equipo and the table if the input is valid.
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
            int currentCantidad = (int) model.getValueAt(modelRow, 0);
            String currentTipoPuertoCodigo = (String) model.getValueAt(modelRow, 1);
            TipoPuerto currentTipoPuerto = coordinator.getTiposPuertos().get(currentTipoPuertoCodigo);

            // Create the form with the current values
            javax.swing.JTextField cantidadField = new javax.swing.JTextField(String.valueOf(currentCantidad), 10);
            javax.swing.JComboBox<TipoPuerto> tipoPuertoComboBox = new javax.swing.JComboBox<>(coordinator.getTiposPuertos().values().toArray(new TipoPuerto[0]));
            tipoPuertoComboBox.setSelectedItem(currentTipoPuerto);

            // Set a custom renderer to display only the codigo attribute
            tipoPuertoComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof TipoPuerto) {
                        setText(((TipoPuerto) value).getCodigo());
                    }
                    return c;
                }
            });

            javax.swing.JPanel panel = createFormPanel(cantidadField, tipoPuertoComboBox);

            // Show the JOptionPane
            int result = javax.swing.JOptionPane.showConfirmDialog(null, panel, rb.getString("TablePuertosEquipo_modifyTitle"), javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
            if (result == javax.swing.JOptionPane.OK_OPTION) {
                // Get the entered values
                int newCantidad;
                try {
                    newCantidad = Integer.parseInt(cantidadField.getText());
                } catch (NumberFormatException e) {
                    javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableDialog_error") + ": " + rb.getString("TableDialog_speedColumn") + " " + rb.getString("TableDialog_numberRequired"), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }
                TipoPuerto newTipoPuerto = (TipoPuerto) tipoPuertoComboBox.getSelectedItem();

                // Verify that all fields are complete
                if (newTipoPuerto == null) {
                    javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableDialog_allFieldsRequired"), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                } else {

                    // Update the Puerto in the equipo
                    Puerto puerto = new Puerto(currentCantidad, currentTipoPuerto);
                    Puerto newPuerto = new Puerto(newCantidad, newTipoPuerto);

                    // Check if the modification is valid and update the port or show an error message
                    try {
                        coordinator.modifyPuertoEquipo(equipo, puerto, newPuerto);
                    } catch (InvalidPuertoEquipoException e) {
                        javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Update the row in the table model
                    model.setValueAt(newCantidad, modelRow, 0);
                    model.setValueAt(newTipoPuerto.getCodigo(), modelRow, 1);

                    // Show success message
                    javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TablePuertosEquipo_modifiedSuccess"), rb.getString("TableDialog_success"), javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    /**
     * Handles the action performed when the "Eliminar" button is clicked.
     * <p>
     * This method retrieves the selected row from the table, confirms the deletion,
     * and removes the Puerto from the equipo and the table if confirmed.
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

            // Get the current values of the selected row
            int currentCantidad = (int) model.getValueAt(modelRow, 0);
            String currentTipoPuertoCodigo = (String) model.getValueAt(modelRow, 1);
            TipoPuerto currentTipoPuerto = coordinator.getTiposPuertos().get(currentTipoPuertoCodigo);

            // Show the confirmation message
            int confirm = javax.swing.JOptionPane.showConfirmDialog(null, rb.getString("TableDialog_confirmDelete") + " " + rb.getString("TablePuertosEquipo_portTypeColumn") + " " + currentTipoPuertoCodigo + "?", rb.getString("TablePuertosEquipo_deleteTitle"), javax.swing.JOptionPane.YES_NO_OPTION);
            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                // Remove the Puerto from the equipo
                Puerto currentPuerto = new Puerto(currentCantidad, currentTipoPuerto);
                // Try to remove the port from the device or show an error message
                try {
                    coordinator.removePuertoEquipo(equipo, currentPuerto);
                } catch (InvalidPuertoEquipoException e) {
                    javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Remove the row from the table model
                model.removeRow(modelRow);

                javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TablePuertosEquipo_deletedSuccess"), rb.getString("TableDialog_success"), javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Creates a form panel with the specified text field and combo box.
     * <p>
     * This method creates a panel with a grid layout and adds labels and fields for "Cantidad"
     * and "Tipo Puerto".
     *
     * @param cantidadField      the text field for the quantity
     * @param tipoPuertoComboBox the combo box for selecting the port type
     * @return the created form panel
     */
    private javax.swing.JPanel createFormPanel(javax.swing.JTextField cantidadField, javax.swing.JComboBox<TipoPuerto> tipoPuertoComboBox) {
        // Create the panel and set the layout
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(2, 1, 2, 5));

        // Add the fields and labels to the panel
        panel.add(new javax.swing.JLabel(rb.getString("TablePuertosEquipo_quantityColumn") + ":"));
        panel.add(cantidadField);
        panel.add(new javax.swing.JLabel(rb.getString("TablePuertosEquipo_portTypeColumn") + ":"));
        panel.add(tipoPuertoComboBox);

        return panel;
    }
}