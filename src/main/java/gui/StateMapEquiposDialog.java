package gui;

import controller.Coordinator;
import models.Equipo;
import utils.LoggerUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * StateMapEquiposDialog is a custom JDialog for displaying the status of devices.
 */
public class StateMapEquiposDialog extends javax.swing.JDialog {

    /**
     * Table displaying the list of devices and their statuses.
     */
    private javax.swing.JTable jTable;
    /**
     * Coordinator instance
     */
    private final Coordinator coordinator;
    /**
     * Resource bundle for internationalization.
     */
    private final ResourceBundle rb;


    /**
     * Constructs a new StateMapEquiposDialog form.
     *
     * @param parent      the parent Frame that owns this dialog
     * @param modal       specifies whether the dialog blocks user input to other top-level windows when shown
     * @param coordinator the Coordinator instance responsible for handling data and actions related to this dialog
     */
    public StateMapEquiposDialog(java.awt.Frame parent, boolean modal, Coordinator coordinator) {
        super(parent, modal);
        this.coordinator = coordinator;
        this.rb = coordinator.getResourceBundle();
        initComponents();
        initContent();
        initStyle();
    }

    /**
     * Initializes the visual style and configuration of the dialog window.
     * Sets the minimum size, centers the dialog on screen, and configures the close operation.
     */
    private void initStyle() {
        this.setMinimumSize(new Dimension(600, 500));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Populates the content of the table with device status data.
     * Clears existing rows, retrieves the list of devices and their statuses from the Coordinator,
     * adds each entry to the table, and then sorts the rows by default.
     */
    private void initContent() {
        // Get the table model
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable.getModel();

        // Clear existing rows
        model.setRowCount(0);

        // Add each TipoCable to the table model
        for (Map.Entry<Equipo, Boolean> set : coordinator.mapStatus().entrySet()) {
            model.addRow(new Object[]{set.getKey().getCodigo(), set.getValue() ? rb.getString("TableEquipos_statusActive") : rb.getString("TableEquipos_statusInactive")});
        }
        // Sort the table
        javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter = (javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel>) jTable.getRowSorter();
        sorter.sort();
    }

    /**
     * Initializes and configures the components within the dialog.
     * Sets up the table layout, assigns a non-editable model, applies a row sorter for sorting,
     * and centers the text within the table cells.
     */
    private void initComponents() {

        // Variables declaration - do not modify//GEN-BEGIN:variables
        javax.swing.JPanel jPanel = new javax.swing.JPanel();
        javax.swing.JScrollPane jScrollPane = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(rb.getString("StateMap_title"));

        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{rb.getString("StateMap_deviceColumn"), rb.getString("TableEquipos_statusColumn")}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };

        // Create a TableRowSorter and set it to the table
        javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter = new javax.swing.table.TableRowSorter<>(model);
        jTable.setRowSorter(sorter);

        // Set the table model
        jTable.setModel(model);

        // Create a cell renderer that centers the text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Apply the renderer to each column
        for (int i = 0; i < jTable.getColumnCount(); i++) {
            jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        jScrollPane.setViewportView(jTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel, java.awt.BorderLayout.CENTER);

        pack();
    }

}
