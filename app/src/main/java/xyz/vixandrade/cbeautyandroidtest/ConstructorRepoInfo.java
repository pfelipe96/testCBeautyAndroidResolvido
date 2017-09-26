package xyz.vixandrade.cbeautyandroidtest;

/**
 * Created by paulofelipeoliveirasouza on 25/09/17.
 */

public class ConstructorRepoInfo {

    private String repoName;
    private String repoURL;
    private int watchers;
    private int abertoIssues;

    public ConstructorRepoInfo(String name, String URL, int watchersCount, int openIssues ){
        repoName = name;
        repoURL = URL;
        watchers = watchersCount;
        abertoIssues = openIssues;
    }

    public int getAbertoIssues() {
        return abertoIssues;
    }

    public void setAbertoIssues(int abertoIssues) {
        this.abertoIssues = abertoIssues;
    }

    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
    }

    public String getRepoURL() {
        return repoURL;
    }

    public void setRepoURL(String repoURL) {
        this.repoURL = repoURL;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {

    }
}
