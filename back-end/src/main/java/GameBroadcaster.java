package game;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GameBroadcaster implements Runnable{
    private AtomicInteger players;
    private int totalPlayers;
    private AtomicBoolean finished;
    private int hostPort;
    private String gameName;
    private String multiAddress = "239.40.40.6"; //Group ip would be 239.40.40.6
    private final int multiPort = 1903; //Group port 1903
    private String name;

    public GameBroadcaster(AtomicInteger players, int totalPlayers, AtomicBoolean finished, int port, String gameName,
                           String name){
        this.players = players;
        this.totalPlayers = totalPlayers;
        this.finished = finished;
        this.hostPort = port;
        this.gameName = gameName;
        this.name = name;
    }

    public void broadcast() throws IOException, InterruptedException {

        String clientIp = NetworkInfo.getClientHostAddress();
        Charset cs = StandardCharsets.UTF_8;
        MulticastSocket sender = new MulticastSocket();
        InetAddress group = InetAddress.getByName(multiAddress);
        sender.joinGroup(group);
        String sendLeft = gameName+":";
        String sendRight = totalPlayers+":"+clientIp+":"+hostPort+":"+name;
        while (!finished.get()){
            String send = sendLeft+players.get()+":"+sendRight;
            byte[] message = send.getBytes(cs);
            DatagramPacket datagramPacket = new DatagramPacket(message,message.length,group,multiPort);
            sender.send(datagramPacket);
            Thread.sleep(1000);
        }
        sender.leaveGroup(group);
    }

    public void run(){
        try {
            broadcast();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}