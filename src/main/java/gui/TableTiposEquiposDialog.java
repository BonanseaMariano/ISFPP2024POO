package gui;

import controller.Coordinator;
import models.TipoEquipo;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.Map;

/**
 * This class represents a dialog that displays a table with all the equipment types
 * in the system. It allows the user to add, modify and delete equipment types.
 */
public class TableTiposEquiposDialog extends javax.swing.JDialog {
    // Table displaying the equipment types
    private javax.swing.JTable table;
    // Width of the dialog
    private static final int WIDTH_DIALOG = 400;
    // Height of the dialog
    private static final int HEIGHT_DIALOG = 300;
    // Coordinator instance to manage the application's business logic
    private Coordinator coordinator;

    /**
     * Constructs a new TableTiposEquiposDialog.
     * <p>
     * This constructor initializes the dialog with the specified parent frame, modality, and coordinator.
     * It sets up the components, styles, and content of the dialog, and makes it visible.
     *
     * @param parent      the parent frame of the dialog
     * @param modal       specifies whether the dialog is modal
     * @param coordinator the coordinator instance to manage the application's business logic
     */
    public TableTiposEquiposDialog(java.awt.Frame parent, boolean modal, Coordinator coordinator) {
        super(parent, modal);
        this.coordinator = coordinator;
        initComponents();
        initStyle();
        initContent();
        this.setVisible(true);
    }

    /**
     * Initializes the style of the dialog.
     * <p>
     * This method sets the dialog's location to the center of the screen,
     * sets the title of the dialog, and specifies the default close operation.
     */
    private void initStyle() {
        // Set the dialog's location to the center of the screen
        this.setLocationRelativeTo(null);

        // Set the title of the dialog
        this.setTitle("Tipos de Equipos");

        // Specify the default close operation
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Initializes the content of the dialog.
     * <p>
     * This method retrieves the table model, clears any existing rows,
     * adds each TipoEquipo to the table model, and sorts the table.
     */
    private void initContent() {
        // Get the table model
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();

        // Clear existing rows
        model.setRowCount(0);

        // Add each TipoEquipo to the table model
        for (Map.Entry<String, TipoEquipo> entry : coordinator.getTiposEquipos().entrySet()) {
            TipoEquipo tipoEquipo = entry.getValue();
            model.addRow(new Object[]{tipoEquipo.getCodigo(), tipoEquipo.getDescripcion()});
        }

        // Sort the table
        javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter = (javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel>) table.getRowSorter();
        sorter.sort();
    }

    /**
     * Initializes the components of the dialog.
     * <p>
     * This method sets up the main background panel, the scroll pane containing the table,
     * the bottom panel with action buttons, and configures the table model and its sorter.
     * It also sets the layout for the background panel and adds the components to the dialog's content pane.
     */
    private void initComponents() {
        // Main background panel of the dialog
        javax.swing.JPanel bg = new javax.swing.JPanel();
        // Scroll pane that contains the table
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        // Panel at the bottom of the dialog containing action buttons
        javax.swing.JPanel bottomPanel = new javax.swing.JPanel();
        // Button to add a new equipment type
        javax.swing.JButton agregarBT = new javax.swing.JButton();
        // Button to modify a selected equipment type
        javax.swing.JButton modificarBT = new javax.swing.JButton();
        // Button to delete a selected equipment type
        javax.swing.JButton eliminarBT = new javax.swing.JButton();

        // Set the table model with 2 columns: "Codigo" and "Descripcion"
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Codigo", "Descripcion"}
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

        agregarBT.setText("Agregar");
        agregarBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        agregarBT.addActionListener(_ -> agregarBTActionPerformed());
        bottomPanel.add(agregarBT);

        modificarBT.setText("Modificar");
        modificarBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        modificarBT.addActionListener(_ -> modificarBTActionPerformed());
        bottomPanel.add(modificarBT);

        eliminarBT.setText("Eliminar");
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
                                        .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                                        .addComponent(bottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE))
                                .addContainerGap())
        );
        bgLayout.setVerticalGroup(
                bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(bgLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
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
     * This method creates a form to add a new equipment type, displays it in a JOptionPane,
     * and processes the input to add the new equipment type to the table and the coordinator.
     */
    private void agregarBTActionPerformed() {
        // Create the form
        javax.swing.JTextField codigoField = new javax.swing.JTextField(10);
        javax.swing.JTextField descripcionField = new javax.swing.JTextField(20);
        javax.swing.JPanel panel = createFormPanel(codigoField, descripcionField, true);

        // Show the JOptionPane
        int result = javax.swing.JOptionPane.showConfirmDialog(null, panel, "Agregar Tipo de Equipo", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
        if (result == javax.swing.JOptionPane.OK_OPTION) {
            // Get the entered values
            String codigo = codigoField.getText();
            String descripcion = descripcionField.getText();

            // Verify that all fields are complete
            if (codigo.isEmpty() || descripcion.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error: todos los campos deben estar completos", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            } else {
                // Create a new TipoEquipo and add it to the table
                TipoEquipo nuevoTipoEquipo = new TipoEquipo(codigo, descripcion);
                try {
                    coordinator.addTipoEquipo(nuevoTipoEquipo);
                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update the table
                javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
                model.addRow(new Object[]{codigo, descripcion});

                // Sort the table
                javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter = (javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel>) table.getRowSorter();
                sorter.sort();

                // Show success message
                javax.swing.JOptionPane.showMessageDialog(null, "Tipo de Equipo agregado", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Handles the action performed when the "Modificar" button is clicked.
     * <p>
     * This method retrieves the selected row from the table, displays a form to modify the equipment type,
     * and updates the equipment type in the table and the coordinator if the user confirms the changes.
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

            // Create the form with the current values
            javax.swing.JTextField codigoField = new javax.swing.JTextField(currentCodigo, 10);
            javax.swing.JTextField descripcionField = new javax.swing.JTextField(currentDescripcion, 20);
            javax.swing.JPanel panel = createFormPanel(codigoField, descripcionField, false);

            // Show the JOptionPane
            int result = javax.swing.JOptionPane.showConfirmDialog(null, panel, "Modificar Tipo de Equipo", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
            if (result == javax.swing.JOptionPane.OK_OPTION) {
                // Get the entered values
                String newDescripcion = descripcionField.getText();

                // Verify that all fields are complete
                if (newDescripcion.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Error: todos los campos deben estar completos", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                } else {
                    // Update the TipoEquipo in the coordinator
                    TipoEquipo tipoEquipo = coordinator.getTiposEquipos().get(currentCodigo);
                    tipoEquipo.setDescripcion(newDescripcion);
                    try {
                        coordinator.modifyTipoEquipo(tipoEquipo, tipoEquipo);
                    } catch (Exception e) {
                        javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Update the row in the table model
                    model.setValueAt(newDescripcion, modelRow, 1);

                    // Show success message
                    javax.swing.JOptionPane.showMessageDialog(null, "Tipo de Equipo modificado", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    /**
     * Handles the action performed when the "Eliminar" button is clicked.
     * <p>
     * This method retrieves the selected row from the table, confirms the deletion with the user,
     * and removes the equipment type from the table and the coordinator if the user confirms the deletion.
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

            // Get the code of the selected TipoEquipo
            String codigo = (String) model.getValueAt(modelRow, 0);

            // Show the confirmation message
            int confirm = javax.swing.JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el Tipo de Equipo " + codigo, "Confirmar eliminación", javax.swing.JOptionPane.YES_NO_OPTION);
            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                // Delete the TipoEquipo from the coordinator
                try {
                    coordinator.deleteTipoEquipo(coordinator.getTiposEquipos().get(codigo));
                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Remove the row from the table model
                model.removeRow(modelRow);

                // Show success message
                javax.swing.JOptionPane.showMessageDialog(null, "Tipo de Equipo eliminado", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Creates a form panel with the specified fields and layout.
     * <p>
     * This method sets the editability of the code field, creates a panel with a grid layout,
     * and adds the provided text fields and their corresponding labels to the panel.
     *
     * @param codigoField      the text field for the code
     * @param descripcionField the text field for the description
     * @param isEditable       specifies whether the code field should be editable
     * @return the created form panel
     */
    private javax.swing.JPanel createFormPanel(javax.swing.JTextField codigoField, javax.swing.JTextField descripcionField, boolean isEditable) {
        // Set the editability of the code field
        codigoField.setEditable(isEditable);

        // Create the panel and set the layout
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(2, 1, 2, 5));

        // Add the fields and labels to the panel
        panel.add(new javax.swing.JLabel("Codigo:"));
        panel.add(codigoField);
        panel.add(new javax.swing.JLabel("Descripcion:"));
        panel.add(descripcionField);

        return panel;
    }

}