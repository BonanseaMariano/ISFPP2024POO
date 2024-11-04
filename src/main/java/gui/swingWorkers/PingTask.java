package gui.swingWorkers;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * PingTask is a SwingWorker that performs a ping operation in the background.
 * It updates a progress bar and a text area with the results of the ping operation.
 */
public class PingTask extends SwingWorker<Void, Integer> {
    /**
     * The IP address to ping.
     */
    private final String ipAddress;
    /**
     * The number of ping requests to send.
     */
    private final int pingCount;
    /**
     * The progress bar to update with the progress of the ping operation.
     */
    private final JProgressBar progressBar;
    /**
     * The text area to update with the results of the ping operation.
     */
    private final JTextArea outputArea;

    /**
     * Constructs a new PingTask.
     *
     * @param ipAddress   the IP address to ping
     * @param pingCount   the number of ping requests to send
     * @param progressBar the progress bar to update with the progress of the ping operation
     * @param outputArea  the text area to update with the results of the ping operation
     */
    public PingTask(String ipAddress, int pingCount, JProgressBar progressBar, JTextArea outputArea) {
        this.ipAddress = ipAddress;
        this.pingCount = pingCount;
        this.progressBar = progressBar;
        this.outputArea = outputArea;
    }

    /**
     * Executes the ping command in the background.
     * Reads the output of the ping command and updates the progress bar and text area.
     *
     * @return null
     * @throws Exception if an error occurs during the execution of the ping command
     */
    @Override
    protected Void doInBackground() throws Exception {
        String command = "ping -n " + pingCount + " " + ipAddress;
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        int progress = 0;

        while ((line = reader.readLine()) != null) {
            outputArea.append(line + "\n");
            progress++;
            int progressPercentage = (int) ((progress / (float) pingCount) * 100);
            publish(progressPercentage);
            setProgress(progressPercentage);
        }

        process.waitFor();
        return null;
    }

    /**
     * Processes the published progress values and updates the progress bar.
     *
     * @param chunks the list of progress values to process
     */
    @Override
    protected void process(java.util.List<Integer> chunks) {
        for (int progress : chunks) {
            progressBar.setValue(progress);
        }
    }

    /**
     * Called when the background task is done.
     * Sets the progress bar to 100%.
     */
    @Override
    protected void done() {
        progressBar.setValue(100);
    }
}