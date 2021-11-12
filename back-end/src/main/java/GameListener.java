package game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GameListener implements Runnable{
    private AtomicBoolean finished;
    private NetworkCom middleware;
    private String multiAddress = "239.40.40.6"; //Group ip would be 239.40.40.6
    private final int multiPort = 1903; //Group port 1903

    public GameListener(AtomicBoolean finished, NetworkCom middleware){
        this.finished = finished;
        this.middleware = middleware;
    }

    public void listen() throws IOException {
        byte[] buffer = new byte[1000];
        MulticastSocket listener = new MulticastSocket(multiPort);
        InetAddress group = InetAddress.getByName(multiAddress);
        listener.joinGroup(group);
        DatagramPacket gameInfo = new DatagramPacket(buffer, buffer.length);
        while (!finished.get()){
            listener.receive(gameInfo);
            byte[] newBuffer = new byte[gameInfo.getLength()];
            System.arraycopy(buffer,0,newBuffer,0,newBuffer.length);
            String[] msgSplit = new String(newBuffer).split(":");
            ArrayList<String> data = new ArrayList<>();
            data.add(msgSplit[0]);
            data.add(msgSplit[1]);
            data.add(msgSplit[2]);
            data.add(msgSplit[3]);
            data.add(msgSplit[4]);
            if(msgSplit.length>5) data.add(msgSplit[5]);
            else{data.add("Unknown");}
            MiddlewareMsg gameOption = new MiddlewareMsg("gameOption",data);
            middleware.send(JsonFiller.writeMidMsg(gameOption));
        }
        listener.leaveGroup(group);
    }
    public void run() {
        try {
            listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


