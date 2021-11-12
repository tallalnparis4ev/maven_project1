package game;

import java.io.*;
import java.net.Socket;

import com.google.gson.*;


public class NetworkCom{
    private boolean isMid;
    private Socket connection;
    private PrintWriter writer;
    private JsonStreamParser reader;
    public NetworkCom(Socket connect, boolean isMid){
        this.connection = connect;
        try{
            this.writer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));
            this.reader = new JsonStreamParser(new InputStreamReader(connection.getInputStream()));
        }
        catch (Exception e){}
        this.isMid = isMid;
    }

    public void send(String move){
        try {
            Thread.sleep(100);
        }catch (Exception e){}
        if(this.connection==null) return;
        writer.print(move);
        writer.flush();
    }

    public String read(){
        if(this.connection==null) return null;
        return reader.next().getAsJsonObject().toString();
    }

    public void close(){
        try {
            connection.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getConnection() {
        return connection;
    }
}

