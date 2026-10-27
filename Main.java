import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Player> allPlayers = new ArrayList<Player>();
        ArrayList<Player> players = new ArrayList<Player>();

        System.out.println("MATCH FIXTURE GENERATOR");
        int count = Input.readInt("Number of participants: ", 1, Integer.MAX_VALUE);
        for (int i = 0; i < count; i++) {
            Player player = new Player(i + 1);
            allPlayers.add(player);
            players.add(player);
        }

        boolean firstRound = true;
        while (true) {
            showPlayers(players);
            System.out.println("1. Round robin");
            System.out.println("2. Knockout");
            if (!firstRound) {
                System.out.println("0. Finish tournament");
            }
            int choice = Input.readInt("Choose: ", firstRound ? 1 : 0, 2);
            if (choice == 0) {
                showResults(players, allPlayers);
                break;
            }
            if (choice == 1) {
                players = RoundRobin.play(players, allPlayers);
                firstRound = false;
            } else {
                Knockout.play(players);
                break;
            }
        }
    }

    public static Player findPlayer(ArrayList<Player> players, int number) {
        for (Player player : players) {
            if (player.number == number) {
                return player;
            }
        }
        return null;
    }

    public static void showPlayers(ArrayList<Player> players) {
        System.out.print("\nRemaining players: ");
        if (players.isEmpty()) {
            System.out.print("none");
        }
        for (Player player : players) {
            System.out.print(player.number + " ");
        }
        System.out.println();
    }

    // A simple selection sort, using either points or serial numbers.
    public static ArrayList<Player> sortPlayers(ArrayList<Player> players, boolean byPoints) {
        ArrayList<Player> sorted = new ArrayList<Player>(players);
        for (int i = 0; i < sorted.size(); i++) {
            int best = i;
            for (int j = i + 1; j < sorted.size(); j++) {
                Player a = sorted.get(j);
                Player b = sorted.get(best);
                if ((byPoints && a.points > b.points)
                        || (byPoints && a.points == b.points && a.number < b.number)
                        || (!byPoints && a.number < b.number)) {
                    best = j;
                }
            }
            Player temporary = sorted.get(i);
            sorted.set(i, sorted.get(best));
            sorted.set(best, temporary);
        }
        return sorted;
    }

    public static void showPoints(ArrayList<Player> players) {
        System.out.println("Player\tTotal points");
        for (Player player : sortPlayers(players, true)) {
            System.out.println(player.number + "\t" + player.points);
        }
    }

    public static void showResults(ArrayList<Player> remaining, ArrayList<Player> allPlayers) {
        System.out.println("\nFINAL RESULTS");
        showPlayers(remaining);
        if (remaining.size() == 1) {
            System.out.println("Winner: Player " + remaining.get(0).number);
        } else {
            System.out.println("Tournament finished without a single champion.");
        }
        System.out.println("All registered players (points from main round robins only):");
        showPoints(allPlayers);
    }
}
