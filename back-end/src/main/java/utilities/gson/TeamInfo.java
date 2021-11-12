package game;

import java.util.List;

public class TeamInfo {
    private String command;
    private String pid;
    private List<List<String>> teamPids;
    private List<List<String>> teamNames;

    public TeamInfo(String command, String pid, List<List<String>> teamPids, List<List<String>> teamNames) {
        this.command = command;
        this.pid = pid;
        this.teamPids = teamPids;
        this.teamNames = teamNames;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<List<String>> getTeamPids() {
        return teamPids;
    }

    public void setTeamPids(List<List<String>> teamPids) {
        this.teamPids = teamPids;
    }

    public List<List<String>> getTeamNames() {
        return teamNames;
    }

    public void setTeamNames(List<List<String>> teamNames) {
        this.teamNames = teamNames;
    }
}
