package GitHubUserActivity.model;

import java.util.Map;

public class Event {
    private String type;
    private Map<String, String> actor;
    private Map<String,String> repo;

    public Event(String type, Map<String,String> actor, Map<String, String> repo) {
        this.type = type;
        this.actor = actor;
        this.repo = repo;
    }

    public String getType() {
        return type;
    }

    public Map<String,String> getActor() {
        return actor;
    }

    public Map<String,String> getRepo() {
        return repo;
    }
}
