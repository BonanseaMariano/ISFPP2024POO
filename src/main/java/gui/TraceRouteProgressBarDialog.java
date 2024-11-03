package gui;

import controller.Coordinator;
import gui.swingWorkers.TraceRouteTask;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * TraceRouteProgressBarDialog is a JDialog that provides a user interface for performing traceroute operations.
 * It includes an input field for the IP address, a progress bar, and an output area.
 */
public class TraceRouteProgressBarDialog extends JDialog {
    /**
     * The width of the dialog.
     */
    private static final int WIDTH_DIALOG = 400;
    /**
     * The height of the dialog.
     */
    private static final int HEIGHT_DIALOG = 300;
    /**
     * The progress bar that displays the progress of the traceroute operation.
     */
    private JProgressBar progressBar;
    /**
     * The text area that displays the output of the traceroute operation.
     */
    private JTextArea outputArea;
    /**
     * The text field for entering the IP address to trace.
     */
    private JTextField ipField;
    /**
     * The resource bundle for accessing localized strings.
     */
    private final ResourceBundle rb;

    /**
     * Constructs a new TraceRouteProgressBarDialog.
     *
     * @param parent      the parent frame
     * @param modal       whether the dialog is modal
     * @param coordinator the coordinator for accessing resources
     */
    public TraceRouteProgressBarDialog(Frame parent, boolean modal, Coordinator coordinator) {
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
        this.setTitle(rb.getString("GUI_tracerouteButton"));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Initializes the components of the dialog.
     * Sets up the layout, input field, progress bar, output area, and start button.
     */
    private void initComponents() {
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        ipField = new JTextField(15);

        JButton startButton = new JButton(rb.getString("TraceRouteProgressBar_startTraceRoute"));
        startButton.addActionListener(_ -> startTraceRoute());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel(rb.getString("TableIpsEquipo_ipColumn")));
        inputPanel.add(ipField);

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
     * Starts the traceroute operation.
     * Reads the IP address from the input field and executes a TraceRouteTask.
     */
    private void startTraceRoute() {
        String ipAddress = ipField.getText();
        TraceRouteTask task = new TraceRouteTask(ipAddress, progressBar, outputArea);
        task.execute();
    }
}