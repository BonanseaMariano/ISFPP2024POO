package utils;

import exceptions.InvalidDireccionIPException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        return new Comparator<>() {
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

    /**
     * Generates a list of IP addresses in the range from ipFrom to ipTo.
     *
     * @param ipFrom the starting IP address
     * @param ipTo   the ending IP address
     * @return a list of IP addresses in the range
     * @throws InvalidDireccionIPException if the IP addresses are invalid
     */
    public static List<String> ipRange(String ipFrom, String ipTo) throws InvalidDireccionIPException {
        List<String> ipList = new ArrayList<>();
        if (!validateIP(ipFrom) || !validateIP(ipTo)) {
            throw new InvalidDireccionIPException("");
        }

        long start = ipToLong(ipFrom);
        long end = ipToLong(ipTo);

        for (long ip = start; ip <= end; ip++) {
            ipList.add(longToIp(ip));
        }

        return ipList;
    }

    /**
     * Converts an IP address from its string representation to a long.
     *
     * @param ipAddress the IP address in string format
     * @return the IP address as a long
     */
    private static long ipToLong(String ipAddress) {
        String[] octets = ipAddress.split("\\.");
        long result = 0;
        for (int i = 0; i < 4; i++) {
            result |= Long.parseLong(octets[i]) << (24 - (8 * i));
        }
        return result;
    }

    /**
     * Converts an IP address from its long representation to a string.
     *
     * @param ip the IP address as a long
     * @return the IP address in string format
     */
    private static String longToIp(long ip) {
        return ((ip >> 24) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                (ip & 0xFF);
    }
}