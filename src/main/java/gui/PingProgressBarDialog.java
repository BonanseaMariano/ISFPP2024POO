package gui;

import controller.Coordinator;
import gui.swingWorkers.PingTask;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * PingProgressBarDialog is a JDialog that provides a user interface for performing ping operations.
 * It includes input fields for the IP address and ping count, a progress bar, and an output area.
 */
public class PingProgressBarDialog extends JDialog {
    /**
     * The width of the dialog.
     */
    private static final int WIDTH_DIALOG = 400;
    /**
     * The height of the dialog.
     */
    private static final int HEIGHT_DIALOG = 300;
    /**
     * The progress bar that displays the progress of the ping operation.
     */
    private JProgressBar progressBar;
    /**
     * The text area that displays the output of the ping operation.
     */
    private JTextArea outputArea;
    /**
     * The text field for entering the IP address to ping.
     */
    private JTextField ipField;
    /**
     * The text field for entering the number of ping requests to send.
     */
    private JTextField pingCountField;
    /**
     * The resource bundle for accessing localized strings.
     */
    private final ResourceBundle rb;

    /**
     * Constructs a new PingProgressBarDialog.
     *
     * @param parent      the parent frame
     * @param modal       whether the dialog is modal
     * @param coordinator the coordinator for accessing resources
     */
    public PingProgressBarDialog(Frame parent, boolean modal, Coordinator coordinator) {
        super(parent, modal);
        this.rb = coordinator.getResourceBundle();
        initComponents();
        initStyle();
        this.setVisible(true);
    }

    /**
     * Initializes the style of the dialog.
     * Sets the location, title, and default close operation.
     */
    private void initStyle() {
        this.setLocationRelativeTo(null);
        this.setTitle(rb.getString("GUI_pingButton"));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Initializes the components of the dialog.
     * Sets up the layout, input fields, progress bar, output area, and start button.
     */
    private void initComponents() {
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        ipField = new JTextField(10);
        pingCountField = new JTextField(5);

        JButton startButton = new JButton(rb.getString("PingProgressBar_startPing"));
        startButton.addActionListener(_ -> startPing());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel(rb.getString("TableIpsEquipo_ipColumn")));
        inputPanel.add(ipField);
        inputPanel.add(new JLabel(rb.getString("PingProgressBar_pingCount")));
        inputPanel.add(pingCountField);

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
     * Starts the ping operation.
     * Reads the IP address and ping count from the input fields, validates the ping count,
     * and executes a PingTask if the validation is successful.
     */
    private void startPing() {
        String ipAddress = ipField.getText();
        int pingCount;
        try {
            // Parse the ping count from the text field and ensure it is a positive integer
            pingCount = Integer.parseInt(pingCountField.getText());
            if (pingCount <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            // Show an error message if the ping count is not a valid positive integer
            JOptionPane.showMessageDialog(this, rb.getString("PingProgressBar_invalidPingCount"), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Create and execute a PingTask with the provided IP address and ping count
        PingTask task = new PingTask(ipAddress, pingCount, progressBar, outputArea);
        task.execute();
    }
}