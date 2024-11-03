package gui;

import controller.Coordinator;
import models.Conexion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * TraceRouteDialog is a custom JDialog for performing trace route operations between two selected devices.
 */
public class TraceRouteDialog extends javax.swing.JDialog {

    /**
     * Combo box to select the first device for trace route operation.
     */
    private javax.swing.JComboBox<String> jComboBoxEquipo1;

    /**
     * Combo box to select the second device for trace route operation.
     */
    private javax.swing.JComboBox<String> jComboBoxEquipo2;

    /**
     * Coordinator instance
     */
    private final Coordinator coordinator;
    /**
     * Resource bundle for internationalization.
     */
    private final ResourceBundle rb;
    /**
     * Width of the dialog.
     */
    private static final int WIDTH_DIALOG = 550;
    /**
     * Height of the dialog.
     */
    private static final int HEIGHT_DIALOG = 400;

    /**
     * Creates a new TraceRouteDialog instance.
     *
     * @param parent      the parent frame of the dialog
     * @param modal       whether the dialog is modal
     * @param coordinator the coordinator managing application logic
     */
    public TraceRouteDialog(java.awt.Frame parent, boolean modal, Coordinator coordinator) {
        super(parent, modal);
        this.coordinator = coordinator;
        this.rb = coordinator.getResourceBundle();
        initComponents();
        initStyles();
    }

    /**
     * Initializes the styles and layout of the dialog.
     * Sets the minimum size, centers the dialog on the screen,
     * makes it visible, and defines the default close operation.
     */
    private void initStyles() {
        this.setMinimumSize(new Dimension(WIDTH_DIALOG, HEIGHT_DIALOG));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Initializes the components of the dialog.
     * Sets up the layout, labels, combo boxes, buttons, and their properties.
     * Configures event listeners and adds components to the dialog.
     */
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        JPanel jPanelBG = new JPanel();
        JPanel jPanelBox = new JPanel();
        JLabel titleEquipo1 = new JLabel();
        JLabel titleEquipo2 = new JLabel();
        jComboBoxEquipo2 = new javax.swing.JComboBox<>();
        jComboBoxEquipo1 = new javax.swing.JComboBox<>();
        JButton jButton = new JButton();
        String[] equipos = coordinator.getEquiposKeys();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(rb.getString("TraceRoute_title"));

        jPanelBG.setVerifyInputWhenFocusTarget(false);

        jPanelBox.setLayout(new java.awt.GridBagLayout());

        titleEquipo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleEquipo1.setText(rb.getString("TraceRoute_device") + " 1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 65;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(85, 45, 0, 0);
        jPanelBox.add(titleEquipo1, gridBagConstraints);

        titleEquipo2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleEquipo2.setText(rb.getString("TraceRoute_device") + " 2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 65;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(85, 10, 0, 33);
        jPanelBox.add(titleEquipo2, gridBagConstraints);

        jComboBoxEquipo2.setModel(new javax.swing.DefaultComboBoxModel<>(equipos));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 10, 0, 33);
        jPanelBox.add(jComboBoxEquipo2, gridBagConstraints);

        jComboBoxEquipo1.setModel(new javax.swing.DefaultComboBoxModel<>(equipos));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 45, 0, 0);
        jPanelBox.add(jComboBoxEquipo1, gridBagConstraints);

        jButton.setText(rb.getString("TableDialog_searchButton"));
        jButton.addActionListener(this::traceRoute);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 9, 110, 0);
        jPanelBox.add(jButton, gridBagConstraints);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanelBG);
        jPanelBG.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanelBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanelBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanelBG, java.awt.BorderLayout.CENTER);

        pack();
    }

    /**
     * Handles the trace route action event by finding and displaying the shortest path between two selected devices.
     * Retrieves the selected devices from the combo boxes, calculates the shortest path using the coordinator,
     * and displays the route along with the maximum bandwidth in a dialog.
     *
     * @param evt the action event that triggers the trace route operation
     */
    private void traceRoute(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        StringBuilder routeText = new StringBuilder(rb.getString("TraceRoute_shortestPathBetween") + Objects.requireNonNull(jComboBoxEquipo1.getSelectedItem()) + " " + rb.getString("TraceRoute_and") + " " + Objects.requireNonNull(jComboBoxEquipo2.getSelectedItem()) + " " + rb.getString("TraceRoute_is") + ":\n");
        try {
            List<Conexion> listConexionaux = coordinator.shortestPath(coordinator.getEquipo(jComboBoxEquipo1.getSelectedItem().toString()), coordinator.getEquipo(jComboBoxEquipo2.getSelectedItem().toString()));
            for (Conexion c : listConexionaux) {
                routeText.append(c.getEquipo1().getCodigo()).append(" -> ").append(c.getEquipo2().getCodigo()).append("\n");
            }

            routeText.append("\n" + rb.getString("TraceRoute_maxBandwidth") + ": ").append(coordinator.maxBandwith(listConexionaux).toString()).append(" MB");
            JOptionPane.showMessageDialog(
                    null,
                    routeText.toString(),
                    rb.getString("TraceRoute_title"),
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    rb.getString("TraceRoute_pathNotFound"),
                    rb.getString("TraceRoute_title"),
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

    }

}
