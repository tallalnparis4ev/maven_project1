package game;
import java.net.InetAddress;

public class NetworkInfo{
    public static String getClientHostAddress() {
        String ret = "";
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}