import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tournament {
    private int id;
    private List<Player> players;
    private String surface;
    private String type;

    public Tournament(int id, List<Player> players, String surface, String type) {
        this.id = id;
        this.players = players;
        this.surface = surface;
        this.type = type;
    }

    public void simulate() {
        System.out.println("Simulating tournament #" + id + " (" + type + " on " + surface + ")");
        if ("elimination".equals(type)) {
            playElimination();
        } else {
            playLeague();
        }
    }

    private void playElimination() {
        if (players.size() < 2 || (players.size() & (players.size() - 1)) != 0) {
            throw new IllegalArgumentException(
                "Elimination tournament #" + id + " requires a power-of-2 player count, got: " + players.size()
            );
        }

        List<Player> remainingPlayers = new ArrayList<>(players);

        while (remainingPlayers.size() > 1) {
            List<Player> winners = new ArrayList<>();
            for (int i = 0; i < remainingPlayers.size(); i += 2) {
                Match match = new Match(remainingPlayers.get(i), remainingPlayers.get(i + 1), surface);
                Player winner = match.play();
                Player loser = (winner == remainingPlayers.get(i))
                    ? remainingPlayers.get(i + 1)
                    : remainingPlayers.get(i);

                winner.addExperience(20);
                loser.addExperience(10);
                winners.add(winner);
            }
            remainingPlayers = winners;
        }
    }

    private void playLeague() {
        List<Match> matches = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                matches.add(new Match(players.get(i), players.get(j), surface));
            }
        }

        Collections.shuffle(matches);

        for (Match match : matches) {
            Player winner = match.play();
            Player loser = (winner == match.getPlayer1()) ? match.getPlayer2() : match.getPlayer1();

            winner.addExperience(10);
            loser.addExperience(1);
        }
    }
}
