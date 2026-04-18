import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MatchTest {

    private Player player(int id, String hand, int experience, int skill) {
        return new Player(id, hand, experience, Map.of("clay", skill));
    }

    @Test
    void play_returnsOneOfTheTwoPlayers() {
        Player p1 = player(1, "right", 50, 5);
        Player p2 = player(2, "right", 50, 5);
        Match match = new Match(p1, p2, "clay");
        Player winner = match.play();
        assertTrue(winner == p1 || winner == p2);
    }

    @Test
    void play_strongerPlayerWinsMoreOften() {
        // Player 1 has max score (10), player 2 has min score (1).
        // Expected win rate for p1 is 10/11 ≈ 90.9%. Over 500 trials, p1 should win at least 70%.
        int p1Wins = 0;
        int trials = 500;
        for (int i = 0; i < trials; i++) {
            Player p1 = player(1, "left", 100, 10);
            Player p2 = player(2, "right", 1, 1);
            if (new Match(p1, p2, "clay").play() == p1) p1Wins++;
        }
        assertTrue(p1Wins > trials * 0.70, "Strong player should win >70% of matches, won: " + p1Wins + "/" + trials);
    }
}
