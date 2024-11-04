package gui.swingWorkers;

import javax.swing.*;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.*;

/**
 * A SwingWorker class that pings a list of IP addresses and updates the output area and progress bar.
 */
public class PingAllTask extends SwingWorker<Void, String> {
    /**
     * The list of IP addresses to ping.
     */
    private final List<String> ipAddresses;

    /**
     * The JTextArea to display the ping results.
     */
    private final JTextArea outputArea;

    /**
     * The JProgressBar to show the progress of the ping tasks.
     */
    private final JProgressBar progressBar;

    /**
     * Constructs a PingAllTask with the specified IP addresses, output area, and progress bar.
     *
     * @param ipAddresses the list of IP addresses to ping
     * @param outputArea  the JTextArea to display the ping results
     * @param progressBar the JProgressBar to show the progress of the ping tasks
     */
    public PingAllTask(List<String> ipAddresses, JTextArea outputArea, JProgressBar progressBar) {
        this.ipAddresses = ipAddresses;
        this.outputArea = outputArea;
        this.progressBar = progressBar;
    }

    /**
     * Performs the ping tasks in the background.
     *
     * @return null
     */
    @Override
    protected Void doInBackground() {
        int totalIps = ipAddresses.size();
        int completedTasks = 0;

        // Create a thread pool with a fixed number of threads
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CompletionService<String> completionService = new ExecutorCompletionService<>(executor);

        // Submit ping tasks to the completion service
        for (String ip : ipAddresses) {
            completionService.submit(() -> {
                boolean isReachable = ping(ip);
                return ip + " is " + (isReachable ? "reachable" : "unreachable");
            });
        }

        // Process the results as they complete
        try {
            for (int i = 0; i < totalIps; i++) {
                Future<String> resultFuture = completionService.take();
                String result = resultFuture.get();
                publish(result);
                completedTasks++;
                setProgress((int) ((completedTasks / (double) totalIps) * 100));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        return null;
    }

    /**
     * Processes the results of the ping tasks and updates the output area.
     *
     * @param chunks the list of ping results
     */
    @Override
    protected void process(List<String> chunks) {
        for (String result : chunks) {
            outputArea.append(result + "\n");
        }
    }

    /**
     * Called when all ping tasks are done. Sets the progress bar to 100%.
     */
    @Override
    protected void done() {
        progressBar.setValue(100);
    }

    /**
     * Pings the specified IP address.
     *
     * @param ip the IP address to ping
     * @return true if the IP address is reachable, false otherwise
     */
    private boolean ping(String ip) {
        try {
            InetAddress inet = InetAddress.getByName(ip);
            return inet.isReachable(2000); // Timeout in milliseconds
        } catch (Exception e) {
            return false;
        }
    }
}