package gui;

import controller.Coordinator;
import models.Equipo;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class represents a dialog that displays a table with all the equipment in the system.
 * It allows the user to add, modify and delete equipment.
 */
public class TableEquiposDialog extends JDialog {
    // Table that displays the equipment
    private JTable table;
    // Width of the dialog
    private static final int WIDTH_DIALOG = 650;
    // Height of the dialog
    private static final int HEIGHT_DIALOG = 500;
    // Coordinator instance that manages the equipment and other related data
    private Coordinator coordinator;


    /**
     * Constructs a new TableEquiposDialog.
     * <p>
     * This constructor initializes the dialog with the specified parent frame, modality, and coordinator.
     * It sets up the components, styles, and content of the dialog, and makes it visible.
     *
     * @param parent      the parent frame of the dialog
     * @param modal       specifies whether dialog blocks user input to other top-level windows when shown
     * @param coordinator the coordinator instance that manages the equipment and other related data
     */
    public TableEquiposDialog(Frame parent, boolean modal, Coordinator coordinator) {
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
     * This method sets the dialog's location to be centered relative to its parent,
     * sets the title of the dialog to "Equipos", and specifies the default close operation.
     */
    private void initStyle() {
        this.setLocationRelativeTo(null);
        this.setTitle("Equipos");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Initializes the content of the dialog.
     * <p>
     * This method retrieves the table model, clears any existing rows, and populates it with
     * the equipment from the coordinator. Each equipment's details are added as a new row.
     * Finally, the table is sorted.
     */
    private void initContent() {
        // Get the table model
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();

        // Clear existing rows
        model.setRowCount(0);

        // Add each Equipo to the table model
        List<Equipo> equipos = coordinator.getEquipos();
        for (Equipo equipo : equipos) {
            model.addRow(new Object[]{
                    equipo.getCodigo(),
                    equipo.getDescripcion(),
                    equipo.getMarca(),
                    equipo.getModelo(),
                    equipo.getTipoEquipo().getCodigo(),
                    equipo.getUbicacion().getCodigo(),
                    "Ver Puertos",
                    "Ver IPs",
                    equipo.isEstado() ? "Activo" : "Inactivo"
            });
        }

        // Sort the table
        javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter = (javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel>) table.getRowSorter();
        sorter.sort();
    }

    /**
     * Initializes the components of the dialog.
     * <p>
     * This method sets up the main panel, scroll pane, table, and bottom panel with buttons.
     * It configures the table model with columns for "Codigo", "Descripcion", "Marca", "Modelo", "Tipo Equipo", "Ubicacion", "Puertos", "Direcciones Ip", and "Estado".
     * The table is set to be non-editable and sortable. The bottom panel contains buttons for adding, modifying, and deleting equipment.
     */
    private void initComponents() {
        // Main background panel of the dialog
        JPanel bg = new JPanel();
        // Scroll pane that contains the table
        JScrollPane scrollPane = new JScrollPane();
        table = new JTable();
        // Panel at the bottom of the dialog containing action buttons
        JPanel bottomPanel = new JPanel();
        // Button to add a new equipment
        JButton agregarBT = new JButton();
        // Button to modify a selected equipment
        JButton modificarBT = new JButton();
        // Button to delete a selected equipment
        JButton eliminarBT = new JButton();

        // Set the table model with the specified columns
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Codigo", "Descripcion", "Marca", "Modelo", "Tipo Equipo", "Ubicacion", "Puertos", "Direcciones Ip", "Estado"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6 || column == 7; // Make only the "Puertos" and "Direcciones Ip" columns editable
            }
        };

        // Create a TableRowSorter and set it to the table
        javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter = new javax.swing.table.TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Set the table model
        table.setModel(model);

        // Set the selection mode to select rows
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add custom cell editors for the "Puertos" and "Direcciones Ip" columns
        table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), this::openPuertosDialog));
        table.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(), this::openIpsDialog));

        // Create a cell renderer that centers the text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Apply the renderer to each column
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        scrollPane.setViewportView(table);

        bottomPanel.setLayout(new GridLayout(1, 3, 5, 0));

        agregarBT.setText("Agregar");
        agregarBT.setCursor(new Cursor(Cursor.HAND_CURSOR));
        agregarBT.addActionListener(_ -> agregarBTActionPerformed());
        bottomPanel.add(agregarBT);

        modificarBT.setText("Modificar");
        modificarBT.setCursor(new Cursor(Cursor.HAND_CURSOR));
        modificarBT.addActionListener(_ -> modificarBTActionPerformed());
        bottomPanel.add(modificarBT);

        eliminarBT.setText("Eliminar");
        eliminarBT.setCursor(new Cursor(Cursor.HAND_CURSOR));
        eliminarBT.addActionListener(_ -> eliminarBTActionPerformed());
        bottomPanel.add(eliminarBT);

        GroupLayout bgLayout = new GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
                bgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(bgLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(bgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(bottomPanel, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                                .addContainerGap())
        );
        bgLayout.setVerticalGroup(
                bgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(bgLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bottomPanel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        getContentPane().add(bg, BorderLayout.CENTER);

        // Set the preferred size of the dialog
        setPreferredSize(new java.awt.Dimension(WIDTH_DIALOG, HEIGHT_DIALOG));

        pack();
    }

    /**
     * Handles the action performed when the "Agregar" button is clicked.
     * <p>
     * This method opens the DataEquiposDialog to add a new equipment.
     */
    private void agregarBTActionPerformed() {
        new DataEquiposDialog(this, true, coordinator, null);
        initContent(); // Refresh the table content after adding
    }

    /**
     * Handles the action performed when the "Modificar" button is clicked.
     * <p>
     * This method retrieves the selected row from the table and opens the DataEquiposDialog to modify the equipment.
     */
    private void modificarBTActionPerformed() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            String codigo = (String) table.getModel().getValueAt(modelRow, 0);
            Equipo equipo = coordinator.getEquiposMap().get(codigo);
            new DataEquiposDialog(this, true, coordinator, equipo);
            initContent(); // Refresh the table content after modifying
        }
    }

    /**
     * Handles the action performed when the "Eliminar" button is clicked.
     * <p>
     * This method retrieves the selected row from the table, confirms the deletion,
     * and if confirmed, deletes the equipment from the coordinator and removes the row from the table.
     */
    private void eliminarBTActionPerformed() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            String codigo = (String) table.getModel().getValueAt(modelRow, 0);
            Equipo equipo = coordinator.getEquiposMap().get(codigo);

            int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el equipo " + codigo + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    coordinator.deleteEquipo(equipo);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ((javax.swing.table.DefaultTableModel) table.getModel()).removeRow(modelRow);
                JOptionPane.showMessageDialog(null, "Equipo eliminado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Opens the TablePuertosEquipoDialog for the selected equipment.
     *
     * @param equipo the selected equipment
     */
    private void openPuertosDialog(Equipo equipo) {
        new TablePuertosEquipoDialog(this, true, coordinator, equipo);
    }

    /**
     * Opens the TableIpsDialog for the selected equipment.
     *
     * @param equipo the selected equipment
     */
    private void openIpsDialog(Equipo equipo) {
        new TableIpsEquipoDialog(this, true, coordinator, equipo);
    }

    /**
     * Custom cell editor that renders a button in a table cell.
     */
    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private Consumer<Equipo> action;
        private Equipo equipo;

        public ButtonEditor(JCheckBox checkBox, Consumer<Equipo> action) {
            super(checkBox);
            this.action = action;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            // Retrieve the Equipo object correctly
            String codigo = (String) table.getModel().getValueAt(row, 0);
            equipo = coordinator.getEquiposMap().get(codigo);
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                action.accept(equipo);
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}