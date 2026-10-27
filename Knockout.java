import java.util.ArrayList;
import java.util.Collections;

public class Knockout {
    public static void play(ArrayList<Player> players) {
        if (players.isEmpty()) {
            System.out.println("No players available. No knockout winner.");
            return;
        }
        Player runnerUp = null;
        int round = 1;
        while (players.size() > 1) {
            System.out.println("\nKNOCKOUT ROUND " + round);
            System.out.println("1. Random pairings");
            System.out.println("2. Serial number pairings");
            int choice = Input.readInt("Choose pairing order: ", 1, 2);
            players = Main.sortPlayers(players, false);
            if (choice == 1) {
                Collections.shuffle(players);
            }

            System.out.println("\nFIXTURES");
            for (int i = 0; i + 1 < players.size(); i += 2) {
                System.out.println("Match " + (i / 2 + 1) + ": Player " + players.get(i).number
                        + " vs Player " + players.get(i + 1).number);
            }
            if (players.size() % 2 == 1) {
                System.out.println("Player " + players.get(players.size() - 1).number
                        + " gets a bye to the next round.");
            }

            ArrayList<Player> winners = new ArrayList<Player>();
            for (int i = 0; i + 1 < players.size(); i += 2) {
                Player first = players.get(i);
                Player second = players.get(i + 1);
                System.out.println("\nPlayer " + first.number + " vs Player " + second.number);
                System.out.println("1. Player " + first.number + " wins");
                System.out.println("2. Player " + second.number + " wins");
                int result = Input.readInt("Result: ", 1, 2);
                if (result == 1) {
                    winners.add(first);
                    if (players.size() == 2) {
                        runnerUp = second;
                    }
                } else {
                    winners.add(second);
                    if (players.size() == 2) {
                        runnerUp = first;
                    }
                }
            }
            if (players.size() % 2 == 1) {
                winners.add(players.get(players.size() - 1));
            }
            players = winners;
            Main.showPlayers(players);
            round++;
        }
        System.out.println("\nFINAL KNOCKOUT RESULTS");
        System.out.println("First place: Player " + players.get(0).number);
        if (runnerUp != null) {
            System.out.println("Second place: Player " + runnerUp.number);
        } else {
            System.out.println("Second place: none (only one player entered knockout).");
        }
    }
}
