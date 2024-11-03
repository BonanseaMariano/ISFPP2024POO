package gui;

import controller.Coordinator;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

public class PingRangeDialog extends javax.swing.JDialog {

    /**
     * ComboBox for selecting the first IP address to ping.
     */
    private javax.swing.JComboBox<String> jComboBoxPing1;

    /**
     * ComboBox for selecting the second IP address to ping.
     */
    private javax.swing.JComboBox<String> jComboBoxPing2;
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
    private static final int WIDTH_DIALOG = 650;
    /**
     * Height of the dialog.
     */
    private static final int HEIGHT_DIALOG = 500;

    /**
     * Creates a new instance of PingRangeDialog.
     *
     * @param parent   The parent frame of the dialog.
     * @param modal    Indicates whether the dialog should be modal.
     * @param coordinator The Coordinator instance used to manage connectivity data.
     */
    public PingRangeDialog(java.awt.Frame parent, boolean modal, Coordinator coordinator) {
        super(parent, modal);
        this.coordinator = coordinator;
        this.rb = coordinator.getResourceBundle();
        initComponents();
        initStyles();
    }

    /**
     * Initializes the styles for the dialog.
     * This method sets the minimum size of the dialog, centers it on the screen,
     * makes it visible, and sets the default close operation to dispose of the dialog
     * when it is closed.
     */
    private void initStyles() {
        this.setMinimumSize(new Dimension(WIDTH_DIALOG,HEIGHT_DIALOG));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * It sets up the components of the dialog, including the background panel,
     * input fields for IP addresses, and a button to initiate the ping range action.
     * It also configures the layout and adds components to the panel.
     */
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        JPanel jPanelBG = new JPanel();
        JPanel jPanelBox = new JPanel();
        JLabel jTitlePing1 = new JLabel();
        JLabel jTitlePing2 = new JLabel();
        jComboBoxPing2 = new javax.swing.JComboBox<>();
        jComboBoxPing1 = new javax.swing.JComboBox<>();
        JButton jButton = new JButton();
        String[] ips = coordinator.getEquiposIps();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(rb.getString("PingRange_title"));

        jPanelBG.setVerifyInputWhenFocusTarget(false);

        jPanelBox.setLayout(new java.awt.GridBagLayout());

        jTitlePing1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTitlePing1.setText(rb.getString("PingRange_enterIp")+ " 1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 65;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(85, 45, 0, 0);
        jPanelBox.add(jTitlePing1, gridBagConstraints);

        jTitlePing2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTitlePing2.setText(rb.getString("PingRange_enterIp")+ " 2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 65;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(85, 10, 0, 33);
        jPanelBox.add(jTitlePing2, gridBagConstraints);

        jComboBoxPing2.setModel(new javax.swing.DefaultComboBoxModel<>(ips));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 10, 0, 33);
        jPanelBox.add(jComboBoxPing2, gridBagConstraints);

        jComboBoxPing1.setModel(new javax.swing.DefaultComboBoxModel<>(ips));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 45, 0, 0);
        jPanelBox.add(jComboBoxPing1, gridBagConstraints);

        jButton.setText(rb.getString("Ping_button"));
        jButton.addActionListener(this::rangePings);
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
     * Executes a ping operation over a range of IP addresses selected in the GUI and displays
     * the ping status for each IP address in the range. This method retrieves two IP addresses
     * from combo boxes `jComboBoxPing1` and `jComboBoxPing2` and includes all IPs in the range
     * between these two addresses, using the comparator from the `Utils` class.
     * If the two IPs are the same, only one IP is pinged.
     *
     * Each IP in the resulting range is pinged using the `pingRange` method from the `coordinator` object.
     * The ping status (active or inactive) is then formatted and shown in a dialog.
     *
     * @param evt the action event that triggers the ping operation
     */
    private void rangePings(java.awt.event.ActionEvent evt) {

        StringBuilder pingRangeText = new StringBuilder(rb.getString("PingRange_pingStatus")+": ");
        String ip1 = (String) jComboBoxPing1.getSelectedItem();
        String ip2 = (String) jComboBoxPing2.getSelectedItem();
        Collection<String> ipsCollection = new ArrayList<>();

        ipsCollection.add(ip1);
        assert ip1 != null;
        if (!ip1.equals(ip2)) {
            for(String ip : coordinator.getEquiposIps()) {
                if (Utils.ipComparator().compare(ip, ip1) > 0 && Utils.ipComparator().compare(ip, ip2) < 0) {
                    ipsCollection.add(ip);
                }
            }
            ipsCollection.add(ip2);
        }

        for(Map.Entry<String, Boolean> entry : coordinator.pingRange(ipsCollection).entrySet()) {
            pingRangeText.append("\n").append(entry.getKey()).append(": ").append(entry.getValue() ? rb.getString("TableEquipos_statusActive"): rb.getString("TableEquipos_statusInactive"));
        }

        JOptionPane.showMessageDialog(
                null,
                pingRangeText.toString(),
                rb.getString("PingRange_title"),
                JOptionPane.INFORMATION_MESSAGE
        );

    }

}
