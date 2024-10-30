package utils;

import java.util.Comparator;

/**
 * Utility class containing various utility methods.
 */
public class Utils {

    /**
     * Validates if the given IP address is in the correct format.
     *
     * @param ip the IP address to validate
     * @return true if the IP address is valid, false otherwise
     */
    public static boolean validateIP(String ip) {
        if (!ip.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")) {
            return false;
        }
        return true;
    }

    /**
     * Provides a comparator for comparing IP addresses.
     *
     * @return a Comparator for comparing IP addresses
     */
    public static Comparator<String> ipComparator() {
        return new Comparator<String>() {
            @Override
            public int compare(String ip1, String ip2) {
                String[] octets1 = ip1.split("\\.");
                String[] octets2 = ip2.split("\\.");

                for (int i = 0; i < 4; i++) {
                    int octet1 = Integer.parseInt(octets1[i]);
                    int octet2 = Integer.parseInt(octets2[i]);

                    if (octet1 != octet2) {
                        return Integer.compare(octet1, octet2);
                    }
                }
                return 0; // They are equal
            }
        };
    }
}