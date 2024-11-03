package gui;

import controller.Coordinator;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * PingDialog is a custom JDialog for performing ping operations on selected equipment or IP addresses.
 */
public class PingDialog extends javax.swing.JDialog {

    /**
     * ComboBox for selecting an IP address or equipment for the ping operation.
     */
    private javax.swing.JComboBox<String> jComboBoxPing;
    /**
     * Coordinator instance
     */
    private final Coordinator coordinator;
    /**
     * Resource bundle for internationalization.
     */
    private final ResourceBundle rb;


    /**
     * Constructs a new PingDialog.
     *
     * @param parent      the parent frame of the dialog
     * @param modal       whether the dialog is modal
     * @param coordinator the Coordinator instance for managing interactions
     */
    public PingDialog(JFrame parent, boolean modal, Coordinator coordinator) {
        super(parent, modal);
        this.coordinator = coordinator;
        this.rb = coordinator.getResourceBundle();
        initComponents();
        initStyles();
    }

    /**
     * Initializes the visual styles of the dialog window.
     * Sets minimum size, centers the dialog on screen, makes it visible,
     * and sets the close operation to dispose the dialog when closed.
     */
    private void initStyles() {
        this.setMinimumSize(new Dimension(550, 400));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Initializes the components within the dialog, setting up the layout and configuring
     * necessary UI elements, such as labels, buttons, and combo boxes.
     */
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        JPanel bgPanel = new JPanel();
        JPanel mainPanel = new JPanel();
        jComboBoxPing = new javax.swing.JComboBox<>();
        JLabel jTitle = new JLabel();
        JButton jButton = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(rb.getString("Ping_title"));

        bgPanel.setLayout(new java.awt.GridBagLayout());

        jComboBoxPing.setModel(new javax.swing.DefaultComboBoxModel<>(coordinator.getEquiposIps()));

        jTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTitle.setText(rb.getString("Ping_enterIp"));

        jButton.setText(rb.getString("Ping_button"));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                        .addComponent(jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jComboBoxPing, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(83, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(jTitle)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxPing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton)
                                .addContainerGap(105, Short.MAX_VALUE))
        );

        jButton.addActionListener(this::ping);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 77;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        bgPanel.add(mainPanel, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bgPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bgPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }


    /**
     * Handles the ping action for the selected equipment or IP.
     * Displays a message dialog indicating the active or inactive status of the selected device.
     *
     * @param evt the event triggered when the ping button is clicked
     */
    private void ping(java.awt.event.ActionEvent evt) {
        String pingText = rb.getString("Ping_device") + " " + Objects.requireNonNull(jComboBoxPing.getSelectedItem()) + " " + rb.getString("Ping_status") + " : ";

        pingText += coordinator.ping(jComboBoxPing.getSelectedItem().toString()) ? rb.getString("TableEquipos_statusActive") : rb.getString("TableEquipos_statusInactive");
        JOptionPane.showMessageDialog(
                null,
                pingText,
                rb.getString("Ping_title"),
                JOptionPane.INFORMATION_MESSAGE
        );

    }

}
