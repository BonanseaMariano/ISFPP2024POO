package utils;

public class Utils {
    public static boolean validateIP(String ip) {
        if (!ip.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")) {
            return false;
        }
        return true;
    }
}
