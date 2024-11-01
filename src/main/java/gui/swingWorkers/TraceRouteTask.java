package gui.swingWorkers;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * TraceRouteTask is a SwingWorker that performs a traceroute operation in the background.
 * It updates a progress bar and a text area with the results of the traceroute operation.
 */
public class TraceRouteTask extends SwingWorker<Void, Integer> {
    private final String ipAddress;
    private final JProgressBar progressBar;
    private final JTextArea outputArea;

    /**
     * Constructs a new TraceRouteTask.
     *
     * @param ipAddress   the IP address to trace route
     * @param progressBar the progress bar to update with the progress of the traceroute operation
     * @param outputArea  the text area to update with the results of the traceroute operation
     */
    public TraceRouteTask(String ipAddress, JProgressBar progressBar, JTextArea outputArea) {
        this.ipAddress = ipAddress;
        this.progressBar = progressBar;
        this.outputArea = outputArea;
    }

    /**
     * Executes the traceroute command in the background.
     * Reads the output of the traceroute command and updates the progress bar and text area.
     *
     * @return null
     * @throws Exception if an error occurs during the execution of the traceroute command
     */
    @Override
    protected Void doInBackground() throws Exception {
        String command = "tracert " + ipAddress;
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        int progress = 0;

        while ((line = reader.readLine()) != null) {
            outputArea.append(line + "\n");
            progress++;
            int progressPercentage = (int) ((progress / 30.0) * 100); // Assuming a max of 30 hops
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