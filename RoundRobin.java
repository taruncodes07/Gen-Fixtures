import java.util.ArrayList;

public class RoundRobin {
    public static ArrayList<Player> play(ArrayList<Player> players, ArrayList<Player> allPlayers) {
        updatePlayers(players, allPlayers);
        if (players.isEmpty()) {
            System.out.println("No players available for round robin.");
            return players;
        }

        System.out.println("1. Players with at least N accumulated points advance");
        System.out.println("2. Top N players advance");
        int type = Input.readInt("Choose advancement type: ", 1, 2);
        int target;
        if (type == 1) {
            target = Input.readInt("Minimum accumulated points: ", 0, Integer.MAX_VALUE);
        } else {
            target = Input.readInt("Number of players to advance: ", 1, players.size());
        }
        int win = Input.readInt("Points for a win: ", 0, Integer.MAX_VALUE);
        int draw = Input.readInt("Points for a draw: ", 0, Integer.MAX_VALUE);
        int lose = Input.readInt("Points for a loss: ", 0, Integer.MAX_VALUE);

        playMatches(players, win, draw, lose);
        System.out.println("\nACCUMULATED POINTS");
        Main.showPoints(players);

        ArrayList<Player> advancing = new ArrayList<Player>();
        ArrayList<Player> sorted = Main.sortPlayers(players, true);
        if (type == 1) {
            for (Player player : sorted) {
                if (player.points >= target) {
                    advancing.add(player);
                }
            }
        } else {
            advancing = selectTop(sorted, target, win, draw, lose);
        }
        System.out.println("\nADVANCING PLAYERS");
        Main.showPlayers(advancing);
        return advancing;
    }

    public static void updatePlayers(ArrayList<Player> players, ArrayList<Player> allPlayers) {
        int absent = Input.readInt("How many current players are absent? ", 0, players.size());
        for (int i = 0; i < absent; i++) {
            while (true) {
                int number = Input.readInt("Absent player's serial number: ", 1, Integer.MAX_VALUE);
                Player player = Main.findPlayer(players, number);
                if (player != null) {
                    players.remove(player);
                    break;
                }
                System.out.println("That player is not in the current round. Try again.");
            }
        }

        int added = Input.readInt("How many new players are being added? ", 0, Integer.MAX_VALUE);
        for (int i = 0; i < added; i++) {
            while (true) {
                int number = Input.readInt("New player's serial number: ", 1, Integer.MAX_VALUE);
                if (Main.findPlayer(allPlayers, number) == null) {
                    Player player = new Player(number);
                    players.add(player);
                    allPlayers.add(player);
                    break;
                }
                System.out.println("That serial number is already registered. Use a new number.");
            }
        }
    }

    public static void playMatches(ArrayList<Player> players, int win, int draw, int lose) {
        System.out.println("\nROUND ROBIN FIXTURES (each pair plays once)");
        int match = 1;
        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                System.out.println("Match " + match + ": Player " + players.get(i).number
                        + " vs Player " + players.get(j).number);
                match++;
            }
        }
        if (match == 1) {
            System.out.println("No matches needed.");
        }

        match = 1;
        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                Player first = players.get(i);
                Player second = players.get(j);
                System.out.println("\nMatch " + match + ": Player " + first.number
                        + " vs Player " + second.number);
                System.out.println("1. Player " + first.number + " wins");
                System.out.println("2. Player " + second.number + " wins");
                System.out.println("0. Draw");
                int result = Input.readInt("Result: ", 0, 2);
                if (result == 1) {
                    first.points += win;
                    second.points += lose;
                } else if (result == 2) {
                    first.points += lose;
                    second.points += win;
                } else {
                    first.points += draw;
                    second.points += draw;
                }
                match++;
            }
        }
    }

    public static ArrayList<Player> selectTop(ArrayList<Player> sorted, int count,
            int win, int draw, int lose) {
        ArrayList<Player> selected = new ArrayList<Player>();
        if (count == sorted.size()) {
            selected.addAll(sorted);
            return selected;
        }

        long cutoff = sorted.get(count - 1).points;
        ArrayList<Player> tied = new ArrayList<Player>();
        for (Player player : sorted) {
            if (player.points > cutoff) {
                selected.add(player);
            } else if (player.points == cutoff) {
                tied.add(player);
            }
        }
        int needed = count - selected.size();
        if (needed == tied.size()) {
            selected.addAll(tied);
        } else {
            selected.addAll(breakTie(tied, needed, win, draw, lose));
        }
        return selected;
    }

    public static ArrayList<Player> breakTie(ArrayList<Player> tied, int needed,
            int win, int draw, int lose) {
        ArrayList<Player> selected = new ArrayList<Player>();
        while (needed > 0) {
            System.out.println("\nTIE-BREAK ROUND ROBIN: " + needed + " place(s) available");
            System.out.println("These points do not count towards tournament points.");
            // Copies keep the real players' accumulated points unchanged.
            ArrayList<Player> copies = new ArrayList<Player>();
            for (Player player : tied) {
                copies.add(new Player(player.number));
            }
            playMatches(copies, win, draw, lose);
            Main.showPoints(copies);
            ArrayList<Player> sorted = Main.sortPlayers(copies, true);
            long cutoff = sorted.get(needed - 1).points;
            ArrayList<Player> stillTied = new ArrayList<Player>();
            int qualified = 0;
            for (Player copy : sorted) {
                Player original = Main.findPlayer(tied, copy.number);
                if (copy.points > cutoff) {
                    selected.add(original);
                    qualified++;
                } else if (copy.points == cutoff) {
                    stillTied.add(original);
                }
            }
            needed = needed - qualified;
            if (needed == stillTied.size()) {
                selected.addAll(stillTied);
                break;
            }
            tied = stillTied;
            System.out.println("The cutoff is still tied. Play another tie-break round robin.");
            if (win == draw && draw == lose) {
                System.out.println("Equal scoring cannot break a tie. Enter tie-break scoring.");
                win = Input.readInt("Tie-break win points: ", 1, Integer.MAX_VALUE);
                draw = Input.readInt("Tie-break draw points: ", 0, win - 1);
                lose = Input.readInt("Tie-break loss points: ", 0, draw);
            }
        }
        return selected;
    }

}
