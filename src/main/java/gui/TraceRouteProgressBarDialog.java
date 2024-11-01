package gui;

import controller.Coordinator;
import gui.swingWorkers.TraceRouteTask;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class TraceRouteProgressBarDialog extends JDialog {
    private static final int WIDTH_DIALOG = 400;
    private static final int HEIGHT_DIALOG = 300;
    private JProgressBar progressBar;
    private JTextArea outputArea;
    private JTextField ipField;
    private Coordinator coordinator;
    private ResourceBundle rb;

    public TraceRouteProgressBarDialog(Frame parent) {
        super(parent, true);
        initComponents();
        initStyle();
        this.setVisible(true);
    }

    public TraceRouteProgressBarDialog(Frame parent, Coordinator coordinator) {
        super(parent, true);
        this.coordinator = coordinator;
        this.rb = coordinator.getResourceBundle();
        initComponents();
        initStyle();
        this.setVisible(true);
    }

    private void initStyle() {
        this.setLocationRelativeTo(null);
        this.setTitle("TraceRoute Progress Bar Example");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        ipField = new JTextField("8.8.8.8", 15); // Default IP address

        JButton startButton = new JButton("Start TraceRoute");
        startButton.addActionListener(e -> startTraceRoute());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("IP Address:"));
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

    private void startTraceRoute() {
        String ipAddress = ipField.getText();
        TraceRouteTask task = new TraceRouteTask(ipAddress, progressBar, outputArea);
        task.execute();
    }
}