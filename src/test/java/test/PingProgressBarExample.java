package test;

import javax.swing.*;
import java.awt.*;

public class PingProgressBarExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ping Progress Bar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);

        JTextField ipField = new JTextField("8.8.8.8", 15); // Default IP address
        JTextField pingCountField = new JTextField("10", 5); // Default ping count

        JButton startButton = new JButton("Start Ping");
        startButton.addActionListener(e -> {
            String ipAddress = ipField.getText();
            int pingCount = Integer.parseInt(pingCountField.getText());
            PingTask task = new PingTask(ipAddress, pingCount, progressBar, outputArea);
            task.execute();
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("IP Address:"));
        inputPanel.add(ipField);
        inputPanel.add(new JLabel("Ping Count:"));
        inputPanel.add(pingCountField);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        frame.add(inputPanel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(progressBar, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        frame.add(new JScrollPane(outputArea), gbc);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        frame.add(startButton, gbc);

        frame.setVisible(true);
    }
}