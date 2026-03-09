package GitHubUserActivity.cli;

import GitHubUserActivity.service.ApiManager;


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
        System.out.println(apiManager.getUsernameActivity(username));
    }
}
