package gui;

import controller.Coordinator;
import models.Ubicacion;

import java.util.Map;

/**
 * This class represents a dialog that displays a table with all the locations
 * in the system. It allows the user to add, modify and delete locations.
 */
public class TableUbicacionesDialog extends javax.swing.JDialog {
    Coordinator coordinator;

    public TableUbicacionesDialog(java.awt.Frame parent, boolean modal, Coordinator coordinator) {
        super(parent, modal);
        this.coordinator = coordinator;
        initComponents();
        initStyle();
        initContent();
        this.setVisible(true);
    }

    private void initStyle() {
        this.setLocationRelativeTo(null);
        this.setTitle("Ubicaciones");
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void initContent() {
        // Get the table model
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();

        // Clear existing rows
        model.setRowCount(0);

        // Add each location to the table model
        for (Map.Entry<String, Ubicacion> entry : coordinator.getUbicaciones().entrySet()) {
            Ubicacion ubicacion = entry.getValue();
            model.addRow(new Object[]{ubicacion.getCodigo(), ubicacion.getDescripcion()});
        }

        // Ordenar la tabla (Por algun motivo este no funciona pero el que esta puesto en agregar si, igual se puede ordenar manualmente)
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
        // Crear el formulario
        javax.swing.JTextField codigoField = new javax.swing.JTextField(10);
        javax.swing.JTextField descripcionField = new javax.swing.JTextField(20);
        javax.swing.JPanel panel = createFormPanel(codigoField, descripcionField, true);

        // Mostrar el JOptionPane
        int result = javax.swing.JOptionPane.showConfirmDialog(null, panel, "Agregar Ubicación", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
        if (result == javax.swing.JOptionPane.OK_OPTION) {
            // Obtener los valores ingresados
            String codigo = codigoField.getText();
            String descripcion = descripcionField.getText();

            // Verificar que todos los campos estén completos
            if (codigo.isEmpty() || descripcion.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error: todos los campos deben estar completos", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            } else {
                // Crear una nueva ubicación y agregarla a la tabla
                Ubicacion nuevaUbicacion = new Ubicacion(codigo, descripcion);
                try {
                    coordinator.addUbicacion(nuevaUbicacion);
                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Actualizar la tabla
                javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
                model.addRow(new Object[]{codigo, descripcion});

                // Ordenar la tabla
                javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter = (javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel>) table.getRowSorter();
                sorter.sort();

                // Mostrar mensaje de aprobación
                javax.swing.JOptionPane.showMessageDialog(null, "Ubicación agregada", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void modificarBTActionPerformed() {
        // Obtener el índice de la fila seleccionada
        int selectedRow = table.getSelectedRow();

        // Verificar si hay una fila seleccionada
        if (selectedRow != -1) {
            // Convertir el índice de la vista al índice del modelo
            int modelRow = table.convertRowIndexToModel(selectedRow);

            // Obtener el modelo de la tabla
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();

            // Obtener los valores actuales de la fila seleccionada
            String currentCodigo = (String) model.getValueAt(modelRow, 0);
            String currentDescripcion = (String) model.getValueAt(modelRow, 1);

            // Crear el formulario con los valores actuales
            javax.swing.JTextField codigoField = new javax.swing.JTextField(currentCodigo, 10);
            javax.swing.JTextField descripcionField = new javax.swing.JTextField(currentDescripcion, 20);
            javax.swing.JPanel panel = createFormPanel(codigoField, descripcionField, false);

            // Mostrar el JOptionPane
            int result = javax.swing.JOptionPane.showConfirmDialog(null, panel, "Modificar Ubicación", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
            if (result == javax.swing.JOptionPane.OK_OPTION) {
                // Obtener los valores ingresados
                String newDescripcion = descripcionField.getText();

                // Verificar que todos los campos estén completos
                if (newDescripcion.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Error: todos los campos deben estar completos", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                } else {
                    // Actualizar la ubicación en el coordinator
                    Ubicacion ubicacion = coordinator.getUbicaciones().get(currentCodigo);
                    ubicacion.setDescripcion(newDescripcion);
                    try {
                        coordinator.modifyUbicacion(ubicacion, ubicacion);
                    } catch (Exception e) {
                        javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Actualizar la fila en el modelo de la tabla
                    model.setValueAt(newDescripcion, modelRow, 1);

                    // Mostrar mensaje de aprobación
                    javax.swing.JOptionPane.showMessageDialog(null, "Ubicación modificada", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private void eliminarBTActionPerformed() {
        // Obtener el índice de la fila seleccionada
        int selectedRow = table.getSelectedRow();

        // Verificar si hay una fila seleccionada
        if (selectedRow != -1) {
            // Convertir el índice de la vista al índice del modelo
            int modelRow = table.convertRowIndexToModel(selectedRow);

            // Obtener el modelo de la tabla
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();

            // Obtener el código de la ubicación seleccionada
            String codigo = (String) model.getValueAt(modelRow, 0);

            // Mostrar el mensaje de confirmación
            int confirm = javax.swing.JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar la ubicación " + codigo, "Confirmar eliminación", javax.swing.JOptionPane.YES_NO_OPTION);
            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                // Eliminar la ubicación del coordinator
                try {
                    coordinator.deleteUbicacion(coordinator.getUbicaciones().get(codigo));
                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Eliminar la fila del modelo de la tabla
                model.removeRow(modelRow);

                javax.swing.JOptionPane.showMessageDialog(null, "Ubicación eliminada", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private javax.swing.JPanel createFormPanel(javax.swing.JTextField codigoField, javax.swing.JTextField descripcionField, boolean isEditable) {
        // Configurar la editabilidad del campo de código
        codigoField.setEditable(isEditable);

        // Crear el panel y configurar el layout
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(2, 1, 2, 5));

        // Agregar los campos y etiquetas al panel
        panel.add(new javax.swing.JLabel("Codigo:"));
        panel.add(codigoField);
        panel.add(new javax.swing.JLabel("Descripcion:"));
        panel.add(descripcionField);

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
