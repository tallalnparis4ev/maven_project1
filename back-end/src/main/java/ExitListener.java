package game;

public class ExitListener implements Runnable {


    private game.NetworkCom exitChannel;
    private game.Game cur;
    public ExitListener(game.NetworkCom exitChannel, game.Game cur){
        this.exitChannel = exitChannel;
        this.cur = cur;
    }

    @Override
    public void run() {
        try {
            String cmd =  exitChannel.read();
            cur.ready.set(true);
            cur.clear(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
