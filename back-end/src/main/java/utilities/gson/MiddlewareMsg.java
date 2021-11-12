package game;
import java.util.List;
// Object used to read/write JSON rules for trick-taking games
public class MiddlewareMsg {
    private String command;
    private List<String> data;

    public MiddlewareMsg(String command, List<String> data){
        this.command = command;
        this.data = data;
    }

    public void setCommand(String command){this.command = command;}
    public void setData(List<String> data){this.data = data;}

    public String getCommand() {
        return command;
    }

    public List<String> getData() {
        return data;
    }

}
