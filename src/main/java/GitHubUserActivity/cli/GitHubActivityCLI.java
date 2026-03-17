package GitHubUserActivity.cli;

import GitHubUserActivity.model.Event;
import GitHubUserActivity.service.ApiManager;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;


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
        cli.getUserActivity(username);
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

    private Map<String, Map<String, Integer>> getUsernameActivity(String username) {
        List<Event> events =  apiManager.getUsernameActivity(username);

        if(events.isEmpty()) return null;

        Map<String, Map<String, Integer>> eventsDescription = new TreeMap<>();

        for(Event event: events) {
            String eventType = event.getType();
            String repoName = event.getRepo().get("name");

            boolean typeAlreadyExists = eventsDescription.containsKey(eventType);
            if(!typeAlreadyExists) {
                eventsDescription.put(eventType, new TreeMap<String, Integer>());
            }

            boolean repoAlreadyExists = eventsDescription.get(eventType).containsKey(repoName);
            if(!repoAlreadyExists) {
                eventsDescription.get(eventType).put(repoName, 0);
            }

            int eventCount = eventsDescription.get(eventType).get(repoName) + 1;
            eventsDescription.get(eventType).put(repoName, eventCount);
        };

        return eventsDescription;
    }

    public void getUserActivity(String username) {
        Map<String, Map<String, Integer>> userActivity = getUsernameActivity(username);

        userActivity.forEach((eventType, eventMap) -> {
            switch (eventType) {
                case "CreateEvent":
                    showCreateEvents(eventMap);
                    break;
                case "PushEvent":
                    showPushEvents(eventMap);
                    break;
                case "WatchEvent":
                    showWatchEvents(eventMap);
                    break;
                default:
                    System.out.println("Event " + eventType + " not found!");
            }
        });
    }

    private void showCreateEvents(Map<String, Integer> createEvents) {
        System.out.println("Create Events: ");
        for(String eventDesc: createEvents.keySet()) {
            System.out.println("Create a repository " + eventDesc);
        }
    }

    private void showPushEvents(Map<String, Integer> pushEvents) {
        System.out.println("Push Events: ");
        pushEvents.forEach((eventDesc, quantity) -> {
            System.out.println("Pushed " + quantity + " commits to " + eventDesc + " repository");
        });
    }

    private void showWatchEvents(Map<String, Integer> watchEvents) {
        System.out.println("Watch Events: ");

        for(String eventDesc: watchEvents.keySet()) {
            System.out.println("Starred  " + eventDesc);
        }
    }
}
