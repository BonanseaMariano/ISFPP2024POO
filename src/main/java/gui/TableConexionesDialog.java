package gui;

import controller.Coordinator;
import exceptions.InvalidConexionException;
import models.Conexion;
import models.Equipo;
import models.TipoPuerto;
import models.TipoCable;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This class represents a dialog that displays a table with all the connections
 * in the system. It allows the user to add, modify and delete connections.
 */
public class TableConexionesDialog extends javax.swing.JDialog {
    /**
     * Width of the dialog.
     */
    private static final int WIDTH_DIALOG = 400;
    /**
     * Height of the dialog.
     */
    private static final int HEIGHT_DIALOG = 300;
    /**
     * Coordinator instance that manages the connections and other related data.
     */
    private Coordinator coordinator;
    /**
     * Resource bundle for internationalization.
     */
    private ResourceBundle rb;
    /**
     * Table that displays the connections.
     */
    private javax.swing.JTable table;

    /**
     * Constructs a new TableConexionesDialog.
     * <p>
     * This constructor initializes the dialog with the specified parent frame, modality, and coordinator.
     * It sets up the components, styles, and content of the dialog, and makes it visible.
     *
     * @param parent      the parent frame of the dialog
     * @param modal       specifies whether dialog blocks user input to other top-level windows when shown
     * @param coordinator the coordinator instance that manages the connections and other related data
     */
    public TableConexionesDialog(java.awt.Frame parent, boolean modal, Coordinator coordinator) {
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
     * This method sets the dialog's location to be centered relative to its parent,
     * sets the title of the dialog to "Conexiones", and specifies the default close operation.
     */
    private void initStyle() {
        this.setLocationRelativeTo(null);
        this.setTitle(rb.getString("TableConexiones_title"));
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Initializes the content of the dialog.
     * <p>
     * This method retrieves the table model, clears any existing rows, and populates it with
     * the connections (Conexion) from the coordinator. Each connection's details are added as a new row.
     * Finally, the table is sorted.
     */
    private void initContent() {
        // Get the table model
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();

        // Clear existing rows
        model.setRowCount(0);

        // Add each Conexion to the table model
        for (Map.Entry<String, Conexion> entry : coordinator.getConexionesMap().entrySet()) {
            Conexion conexion = entry.getValue();
            model.addRow(new Object[]{
                    conexion.getEquipo1().getCodigo(),
                    conexion.getPuerto1().getCodigo(),
                    conexion.getEquipo2().getCodigo(),
                    conexion.getPuerto2().getCodigo(),
                    conexion.getTipoCable().getCodigo()
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
     * It configures the table model with columns for "Equipo 1", "Tipo Puerto 1", "Equipo 2", "Tipo Puerto 2", and "Tipo de Cable".
     * The table is set to be non-editable and sortable. The bottom panel contains buttons for adding, modifying, and deleting connections.
     */
    private void initComponents() {
        // Main background panel of the dialog
        JPanel bg = new JPanel();
        // Scroll pane that contains the table
        JScrollPane scrollPane = new JScrollPane();
        table = new javax.swing.JTable();
        // Panel at the bottom of the dialog containing action buttons
        JPanel bottomPanel = new JPanel();
        // Button to add a new connection
        JButton agregarBT = new JButton();
        // Button to modify a selected connection
        JButton modificarBT = new JButton();
        // Button to delete a selected connection
        JButton eliminarBT = new JButton();

        // Set the table model with 5 columns: "Equipo 1", "Tipo Puerto 1", "Equipo 2", "Tipo Puerto 2", and "Tipo de Cable"
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{rb.getString("TableConexiones_originDeviceColumn"), rb.getString("TableConexiones_originPortColumn"), rb.getString("TableConexiones_destinationDeviceColumn"), rb.getString("TableConexiones_destinationPortColumn"), rb.getString("TableConexiones_cableTypeColumn")}
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
     * This method creates a form to add a new connection (Conexion) by selecting the equipment (Equipo),
     * port types (TipoPuerto), and cable type (TipoCable). It verifies that all fields are complete,
     * creates a new connection, adds it to the coordinator, updates the table, and shows a success message.
     * If any field is incomplete or an error occurs, an error message is displayed.
     */
    private void agregarBTActionPerformed() {
        // Create the form
        javax.swing.JComboBox<Equipo> equipo1ComboBox = new javax.swing.JComboBox<>(coordinator.getEquipos().toArray(new Equipo[0]));
        javax.swing.JComboBox<TipoPuerto> tipoPuerto1ComboBox = new javax.swing.JComboBox<>(coordinator.getTiposPuertos().values().toArray(new TipoPuerto[0]));
        javax.swing.JComboBox<Equipo> equipo2ComboBox = new javax.swing.JComboBox<>(coordinator.getEquipos().toArray(new Equipo[0]));
        javax.swing.JComboBox<TipoPuerto> tipoPuerto2ComboBox = new javax.swing.JComboBox<>(coordinator.getTiposPuertos().values().toArray(new TipoPuerto[0]));
        javax.swing.JComboBox<TipoCable> tipoCableComboBox = new javax.swing.JComboBox<>(coordinator.getTiposCables().values().toArray(new TipoCable[0]));

        // Set custom renderer to display only the code
        equipo1ComboBox.setRenderer(new CodeRenderer<>());
        tipoPuerto1ComboBox.setRenderer(new CodeRenderer<>());
        equipo2ComboBox.setRenderer(new CodeRenderer<>());
        tipoPuerto2ComboBox.setRenderer(new CodeRenderer<>());
        tipoCableComboBox.setRenderer(new CodeRenderer<>());

        javax.swing.JPanel panel = createFormPanel(equipo1ComboBox, tipoPuerto1ComboBox, equipo2ComboBox, tipoPuerto2ComboBox, tipoCableComboBox);

        // Show the JOptionPane
        int result = javax.swing.JOptionPane.showConfirmDialog(null, panel, rb.getString("TableConexiones_addTitle"), javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
        if (result == javax.swing.JOptionPane.OK_OPTION) {
            // Get the entered values
            Equipo equipo1 = (Equipo) equipo1ComboBox.getSelectedItem();
            TipoPuerto tipoPuerto1 = (TipoPuerto) tipoPuerto1ComboBox.getSelectedItem();
            Equipo equipo2 = (Equipo) equipo2ComboBox.getSelectedItem();
            TipoPuerto tipoPuerto2 = (TipoPuerto) tipoPuerto2ComboBox.getSelectedItem();
            TipoCable tipoCable = (TipoCable) tipoCableComboBox.getSelectedItem();

            // Verify that all fields are complete
            if (equipo1 == null || tipoPuerto1 == null || equipo2 == null || tipoPuerto2 == null || tipoCable == null) {
                javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableDialog_allFieldsRequired"), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
            } else {
                // Create a new Conexion and add it to the table
                Conexion nuevaConexion = new Conexion(tipoCable, equipo1, tipoPuerto1, equipo2, tipoPuerto2);
                try {
                    coordinator.addConnection(nuevaConexion);
                } catch (InvalidConexionException e) {
                    javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update the table
                javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
                model.addRow(new Object[]{
                        equipo1.getCodigo(),
                        tipoPuerto1.getDescripcion(),
                        equipo2.getCodigo(),
                        tipoPuerto2.getDescripcion(),
                        tipoCable.getDescripcion()
                });

                // Sort the table
                javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter = (javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel>) table.getRowSorter();
                sorter.sort();

                // Show success message
                javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableConexiones_addedSuccess"), rb.getString("TableDialog_success"), javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Handles the action performed when the "Modificar" button is clicked.
     * <p>
     * This method retrieves the selected row from the table, creates a form with the current values,
     * and allows the user to modify the connection (Conexion). It verifies that all fields are complete,
     * updates the connection in the coordinator, updates the table, and shows a success message.
     * If any field is incomplete or an error occurs, an error message is displayed.
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
            String currentEquipo1 = (String) model.getValueAt(modelRow, 0);
            String currentTipoPuerto1 = (String) model.getValueAt(modelRow, 1);
            String currentEquipo2 = (String) model.getValueAt(modelRow, 2);
            String currentTipoPuerto2 = (String) model.getValueAt(modelRow, 3);
            String currentTipoCable = (String) model.getValueAt(modelRow, 4);

            // Create the conexion with the current values
            Conexion oldConexion = coordinator.getConexionesMap().get(currentEquipo1 + "-" + currentEquipo2);

            // Create the form with the current values
            javax.swing.JComboBox<Equipo> equipo1ComboBox = new javax.swing.JComboBox<>(coordinator.getEquipos().toArray(new Equipo[0]));
            equipo1ComboBox.setSelectedItem(coordinator.getEquiposMap().get(currentEquipo1));
            javax.swing.JComboBox<TipoPuerto> tipoPuerto1ComboBox = new javax.swing.JComboBox<>(coordinator.getTiposPuertos().values().toArray(new TipoPuerto[0]));
            tipoPuerto1ComboBox.setSelectedItem(coordinator.getTiposPuertos().get(currentTipoPuerto1));
            javax.swing.JComboBox<Equipo> equipo2ComboBox = new javax.swing.JComboBox<>(coordinator.getEquipos().toArray(new Equipo[0]));
            equipo2ComboBox.setSelectedItem(coordinator.getEquiposMap().get(currentEquipo2));
            javax.swing.JComboBox<TipoPuerto> tipoPuerto2ComboBox = new javax.swing.JComboBox<>(coordinator.getTiposPuertos().values().toArray(new TipoPuerto[0]));
            tipoPuerto2ComboBox.setSelectedItem(coordinator.getTiposPuertos().get(currentTipoPuerto2));
            javax.swing.JComboBox<TipoCable> tipoCableComboBox = new javax.swing.JComboBox<>(coordinator.getTiposCables().values().toArray(new TipoCable[0]));
            tipoCableComboBox.setSelectedItem(coordinator.getTiposCables().get(currentTipoCable));

            // Set custom renderer to display only the code
            equipo1ComboBox.setRenderer(new CodeRenderer<>());
            tipoPuerto1ComboBox.setRenderer(new CodeRenderer<>());
            equipo2ComboBox.setRenderer(new CodeRenderer<>());
            tipoPuerto2ComboBox.setRenderer(new CodeRenderer<>());
            tipoCableComboBox.setRenderer(new CodeRenderer<>());

            javax.swing.JPanel panel = createFormPanel(equipo1ComboBox, tipoPuerto1ComboBox, equipo2ComboBox, tipoPuerto2ComboBox, tipoCableComboBox);

            // Show the JOptionPane
            int result = javax.swing.JOptionPane.showConfirmDialog(null, panel, rb.getString("TableConexiones_modifyTitle"), javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
            if (result == javax.swing.JOptionPane.OK_OPTION) {
                // Get the entered values
                Equipo newEquipo1 = (Equipo) equipo1ComboBox.getSelectedItem();
                TipoPuerto newTipoPuerto1 = (TipoPuerto) tipoPuerto1ComboBox.getSelectedItem();
                Equipo newEquipo2 = (Equipo) equipo2ComboBox.getSelectedItem();
                TipoPuerto newTipoPuerto2 = (TipoPuerto) tipoPuerto2ComboBox.getSelectedItem();
                TipoCable newTipoCable = (TipoCable) tipoCableComboBox.getSelectedItem();

                // Verify that all fields are complete
                if (newEquipo1 == null || newTipoPuerto1 == null || newEquipo2 == null || newTipoPuerto2 == null || newTipoCable == null) {
                    javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableDialog_allFieldsRequired"), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                } else {
                    // Create the conexion with the new values
                    Conexion newConexion = new Conexion(newTipoCable, newEquipo1, newTipoPuerto1, newEquipo2, newTipoPuerto2);

                    try {
                        coordinator.modifyConnection(oldConexion, newConexion);
                    } catch (InvalidConexionException e) {
                        javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Update the row in the table model
                    model.setValueAt(newEquipo1.getCodigo(), modelRow, 0);
                    model.setValueAt(newTipoPuerto1.getDescripcion(), modelRow, 1);
                    model.setValueAt(newEquipo2.getCodigo(), modelRow, 2);
                    model.setValueAt(newTipoPuerto2.getDescripcion(), modelRow, 3);
                    model.setValueAt(newTipoCable.getDescripcion(), modelRow, 4);

                    // Show success message
                    javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableConexiones_modifiedSuccess"), rb.getString("TableDialog_success"), javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    /**
     * Handles the action performed when the "Eliminar" button is clicked.
     * <p>
     * This method retrieves the selected row from the table, confirms the deletion with the user,
     * and if confirmed, deletes the connection (Conexion) from the coordinator and removes the row from the table.
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

            // Get the code of the selected Conexion
            String equipo1 = (String) model.getValueAt(modelRow, 0);
            String equipo2 = (String) model.getValueAt(modelRow, 2);

            // Show the confirmation message
            int confirm = javax.swing.JOptionPane.showConfirmDialog(null, rb.getString("TableDialog_confirmDelete") + " " + rb.getString("TableConexiones_name") + " " + equipo1 + " -> " + equipo2 + "?", rb.getString("TableConexiones_deleteTitle"), javax.swing.JOptionPane.YES_NO_OPTION);
            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                // Delete the Conexion from the coordinator
                try {
                    coordinator.deleteConnection(coordinator.getConexionesMap().get(equipo1 + "-" + equipo2));
                } catch (InvalidConexionException e) {
                    javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), rb.getString("TableDialog_error"), javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Remove the row from the table model
                model.removeRow(modelRow);

                javax.swing.JOptionPane.showMessageDialog(null, rb.getString("TableConexiones_deletedSuccess"), rb.getString("TableDialog_success"), javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Creates a form panel with the specified combo boxes and layout.
     * <p>
     * This method sets up a panel with a grid layout containing labels and combo boxes
     * for selecting "Equipo 1", "Tipo Puerto 1", "Equipo 2", "Tipo Puerto 2", and "Tipo de Cable".
     *
     * @param equipo1ComboBox     the combo box for selecting the first equipment
     * @param tipoPuerto1ComboBox the combo box for selecting the first port type
     * @param equipo2ComboBox     the combo box for selecting the second equipment
     * @param tipoPuerto2ComboBox the combo box for selecting the second port type
     * @param tipoCableComboBox   the combo box for selecting the cable type
     * @return the created form panel
     */
    private javax.swing.JPanel createFormPanel(javax.swing.JComboBox<Equipo> equipo1ComboBox, javax.swing.JComboBox<TipoPuerto> tipoPuerto1ComboBox, javax.swing.JComboBox<Equipo> equipo2ComboBox, javax.swing.JComboBox<TipoPuerto> tipoPuerto2ComboBox, javax.swing.JComboBox<TipoCable> tipoCableComboBox) {
        // Create the panel and set the layout
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(5, 1, 2, 5));

        // Add the fields and labels to the panel
        panel.add(new javax.swing.JLabel(rb.getString("TableConexiones_originDeviceColumn") + ":"));
        panel.add(equipo1ComboBox);
        panel.add(new javax.swing.JLabel(rb.getString("TableConexiones_originPortColumn") + ":"));
        panel.add(tipoPuerto1ComboBox);
        panel.add(new javax.swing.JLabel(rb.getString("TableConexiones_destinationDeviceColumn") + ":"));
        panel.add(equipo2ComboBox);
        panel.add(new javax.swing.JLabel(rb.getString("TableConexiones_destinationPortColumn") + ":"));
        panel.add(tipoPuerto2ComboBox);
        panel.add(new javax.swing.JLabel(rb.getString("TableConexiones_cableTypeColumn") + ":"));
        panel.add(tipoCableComboBox);

        return panel;
    }

    /**
     * Custom renderer to display only the id for the specified types.
     * <p>
     * This class extends DefaultListCellRenderer to customize the rendering of items in a JList.
     * It overrides the getListCellRendererComponent method to display only the id of the items
     * if they are instances of Equipo, TipoPuerto, or TipoCable.
     *
     * @param <T> the type of the items in the list
     */
    private static class CodeRenderer<T> extends DefaultListCellRenderer {
        /**
         * Returns a component that has been configured to display the specified value.
         *
         * @param list         the JList we're painting
         * @param value        the value returned by list.getModel().getElementAt(index)
         * @param index        the cell's index
         * @param isSelected   true if the specified cell was selected
         * @param cellHasFocus true if the specified cell has the focus
         * @return a component whose paint() method will render the specified value
         */
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Equipo) {
                setText(((Equipo) value).getCodigo());
            } else if (value instanceof TipoPuerto) {
                setText(((TipoPuerto) value).getCodigo());
            } else if (value instanceof TipoCable) {
                setText(((TipoCable) value).getCodigo());
            }
            return c;
        }
    }
}