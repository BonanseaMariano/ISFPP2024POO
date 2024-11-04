package gui;

import controller.Coordinator;
import exceptions.InvalidDireccionIPException;
import gui.swingWorkers.PingAllTask;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ResourceBundle;

/**
 * PingProgressBarDialog is a JDialog that provides a user interface for performing ping operations.
 * It includes input fields for the IP address range, a progress bar, and an output area.
 */
public class PingRangeProgressBarDialog extends JDialog {
    /**
     * The width of the dialog in pixels.
     */
    private static final int WIDTH_DIALOG = 400;
    /**
     * The height of the dialog in pixels.
     */
    private static final int HEIGHT_DIALOG = 300;
    /**
     * The progress bar to show the progress of the ping tasks.
     */
    private JProgressBar progressBar;
    /**
     * The text area to display the ping results.
     */
    private JTextArea outputArea;
    /**
     * The text field for the starting IP address.
     */
    private JTextField ipFromField;
    /**
     * The text field for the ending IP address.
     */
    private JTextField ipToField;
    /**
     * The resource bundle for internationalization.
     */
    private final ResourceBundle rb;

    /**
     * Constructs a PingRangeProgressBarDialog with the specified parent frame, modality, and coordinator.
     *
     * @param parent      the parent frame
     * @param modal       whether the dialog is modal
     * @param coordinator the coordinator to get the resource bundle
     */
    public PingRangeProgressBarDialog(Frame parent, boolean modal, Coordinator coordinator) {
        super(parent, modal);
        this.rb = coordinator.getResourceBundle();
        initComponents();
        initStyle();
        this.setVisible(true);
    }

    /**
     * Initializes the style of the dialog.
     */
    private void initStyle() {
        this.setLocationRelativeTo(null);
        this.setTitle(rb.getString("GUI_pingRangeButton"));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Initializes the components of the dialog.
     */
    private void initComponents() {
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        ipFromField = new JTextField(10);
        ipToField = new JTextField(10);

        JButton startButton = new JButton(rb.getString("PingProgressBar_startPing"));
        startButton.addActionListener(_ -> startPing());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel(rb.getString("TableIpsEquipo_ipColumn") + " 1"));
        inputPanel.add(ipFromField);
        inputPanel.add(new JLabel(rb.getString("TableIpsEquipo_ipColumn") + " 2"));
        inputPanel.add(ipToField);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        add(inputPanel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(progressBar, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        add(new JScrollPane(outputArea), gbc);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        add(startButton, gbc);

        setPreferredSize(new Dimension(WIDTH_DIALOG, HEIGHT_DIALOG));
        pack();
    }

    /**
     * Starts the ping operation based on the IP address range provided by the user.
     */
    private void startPing() {
        String ipFrom = ipFromField.getText();
        String ipTo = ipToField.getText();

        // Generate the list of IP addresses to ping using Utils.ipRange if the IP addresses are valid
        List<String> ipAddresses;
        try {
            ipAddresses = Utils.ipRange(ipFrom, ipTo);
        } catch (InvalidDireccionIPException e) {
            JOptionPane.showMessageDialog(this, rb.getString("PingRangeProgressBar_invalidIP"), rb.getString("TableDialog_error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        PingAllTask task = new PingAllTask(ipAddresses, outputArea, progressBar);
        progressBar.setIndeterminate(false); // Ensure the progress bar is not in indeterminate mode
        progressBar.setValue(0); // Reset progress bar value
        task.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((Integer) evt.getNewValue());
            }
        });
        task.execute();
    }
}