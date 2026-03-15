package GitHubUserActivity.cli;

import GitHubUserActivity.model.Event;
import GitHubUserActivity.service.ApiManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GitHubActivityCLI {

    private ApiManager apiManager;

    public GitHubActivityCLI() {
        this.apiManager = new ApiManager();
    }

    public static void main(String[] args) {
        GitHubActivityCLI cli = new GitHubActivityCLI();
        if(!cli.checkArgs(args)) {
            return;
        }
        String username = cli.getUsername(args);
        cli.getUsernameActivity(username);
    }

    private boolean checkArgs(String[] args) {
        if (args.length > 1) {
            System.out.println("Muitos argumentos fornecidos. Esperado: 1. Recebido: " + args.length);
            return false;
        }
        if (getUsername(args) == null) {
            System.out.println("Poucos argumentos fornecidos. Esperado: 1. Recebido: " + args.length);
            return false;
        }
        return true;
    }

    private String getUsername(String[] args) {
        if (args.length > 0) {
            return args[0];
        }
        return null;
    }

    private void getUsernameActivity(String username) {
        List<Event> events =  apiManager.getUsernameActivity(username);
        if(events.isEmpty()) return;
    }

    private int getEventCount(List<Event> events, String eventDescription) {
        int eventCount = 0;
        for(Event e: events) {
            if(e.getType().equals(eventDescription)) eventCount++;
        }
        return eventCount;
    }

    
}
