package gui;

import controller.Coordinator;
import models.TipoCable;

import java.util.Map;

/**
 * This class represents a dialog that displays a table with all the cable types
 * in the system. It allows the user to add, modify and delete cable types.
 */
public class TableTiposCablesDialog extends javax.swing.JDialog {
    Coordinator coordinator;

    public TableTiposCablesDialog(java.awt.Frame parent, boolean modal, Coordinator coordinator) {
        super(parent, modal);
        this.coordinator = coordinator;
        initComponents();
        initStyle();
        initContent();
        this.setVisible(true);
    }

    private void initStyle() {
        this.setLocationRelativeTo(null);
        this.setTitle("Tipos de Cables");
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void initContent() {
        // Get the table model
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();

        // Clear existing rows
        model.setRowCount(0);

        // Add each TipoCable to the table model
        for (Map.Entry<String, TipoCable> entry : coordinator.getTiposCables().entrySet()) {
            TipoCable tipoCable = entry.getValue();
            model.addRow(new Object[]{tipoCable.getCodigo(), tipoCable.getDescripcion(), tipoCable.getVelocidad()});
        }

        // Sort the table
        javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter = (javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel>) table.getRowSorter();
        sorter.sort();
    }

    private void initComponents() {
        bg = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        bottomPanel = new javax.swing.JPanel();
        agregarBT = new javax.swing.JButton();
        modificarBT = new javax.swing.JButton();
        eliminarBT = new javax.swing.JButton();

        // Set the table model with 3 columns: "Codigo", "Descripcion", and "Velocidad"
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Codigo", "Descripcion", "Velocidad"}
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

        pack();
    }

    private void agregarBTActionPerformed() {
        // Create the form
        javax.swing.JTextField codigoField = new javax.swing.JTextField(10);
        javax.swing.JTextField descripcionField = new javax.swing.JTextField(20);
        javax.swing.JTextField velocidadField = new javax.swing.JTextField(10);
        javax.swing.JPanel panel = createFormPanel(codigoField, descripcionField, velocidadField, true);

        // Show the JOptionPane
        int result = javax.swing.JOptionPane.showConfirmDialog(null, panel, "Agregar TipoCable", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
        if (result == javax.swing.JOptionPane.OK_OPTION) {
            // Get the entered values
            String codigo = codigoField.getText();
            String descripcion = descripcionField.getText();
            int velocidad;
            try {
                velocidad = Integer.parseInt(velocidadField.getText());
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error: la velocidad debe ser un número", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verify that all fields are complete
            if (codigo.isEmpty() || descripcion.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error: todos los campos deben estar completos", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            } else {
                // Create a new TipoCable and add it to the table
                TipoCable nuevoTipoCable = new TipoCable(codigo, descripcion, velocidad);
                try {
                    coordinator.addTipoCable(nuevoTipoCable);
                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update the table
                javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
                model.addRow(new Object[]{codigo, descripcion, velocidad});

                // Sort the table
                javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter = (javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel>) table.getRowSorter();
                sorter.sort();

                // Show success message
                javax.swing.JOptionPane.showMessageDialog(null, "TipoCable agregado", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

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
            int result = javax.swing.JOptionPane.showConfirmDialog(null, panel, "Modificar TipoCable", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
            if (result == javax.swing.JOptionPane.OK_OPTION) {
                // Get the entered values
                String newDescripcion = descripcionField.getText();
                int newVelocidad;
                try {
                    newVelocidad = Integer.parseInt(velocidadField.getText());
                } catch (NumberFormatException e) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Error: la velocidad debe ser un número", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verify that all fields are complete
                if (newDescripcion.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Error: todos los campos deben estar completos", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                } else {
                    // Update the TipoCable in the coordinator
                    TipoCable tipoCable = coordinator.getTiposCables().get(currentCodigo);
                    tipoCable.setDescripcion(newDescripcion);
                    tipoCable.setVelocidad(newVelocidad);
                    try {
                        coordinator.modifyTipoCable(tipoCable, tipoCable);
                    } catch (Exception e) {
                        javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Update the row in the table model
                    model.setValueAt(newDescripcion, modelRow, 1);
                    model.setValueAt(newVelocidad, modelRow, 2);

                    // Show success message
                    javax.swing.JOptionPane.showMessageDialog(null, "TipoCable modificado", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private void eliminarBTActionPerformed() {
        // Get the index of the selected row
        int selectedRow = table.getSelectedRow();

        // Verify if a row is selected
        if (selectedRow != -1) {
            // Convert the view index to the model index
            int modelRow = table.convertRowIndexToModel(selectedRow);

            // Get the table model
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();

            // Get the code of the selected TipoCable
            String codigo = (String) model.getValueAt(modelRow, 0);

            // Show the confirmation message
            int confirm = javax.swing.JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el TipoCable " + codigo, "Confirmar eliminación", javax.swing.JOptionPane.YES_NO_OPTION);
            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                // Delete the TipoCable from the coordinator
                try {
                    coordinator.deleteTipoCable(coordinator.getTiposCables().get(codigo));
                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Remove the row from the table model
                model.removeRow(modelRow);

                javax.swing.JOptionPane.showMessageDialog(null, "TipoCable eliminado", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private javax.swing.JPanel createFormPanel(javax.swing.JTextField codigoField, javax.swing.JTextField descripcionField, javax.swing.JTextField velocidadField, boolean isEditable) {
        // Set the editability of the code field
        codigoField.setEditable(isEditable);

        // Create the panel and set the layout
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(3, 1, 2, 5));

        // Add the fields and labels to the panel
        panel.add(new javax.swing.JLabel("Codigo:"));
        panel.add(codigoField);
        panel.add(new javax.swing.JLabel("Descripcion:"));
        panel.add(descripcionField);
        panel.add(new javax.swing.JLabel("Velocidad:"));
        panel.add(velocidadField);

        return panel;
    }

    private javax.swing.JButton agregarBT;
    private javax.swing.JPanel bg;
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JButton eliminarBT;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton modificarBT;
    private javax.swing.JTable table;
}