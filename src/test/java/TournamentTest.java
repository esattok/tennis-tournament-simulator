import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TournamentTest {

    private List<Player> createPlayers(int count) {
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            players.add(new Player(i, "right", i * 10, Map.of("clay", i % 10 + 1, "grass", 5, "hard", 5)));
        }
        return players;
    }

    @Test
    void elimination_allPlayersGainExperience() {
        List<Player> players = createPlayers(8);
        new Tournament(1, players, "clay", "elimination").simulate();
        for (Player p : players) {
            assertTrue(p.getGainedExperience() > 0, "Player " + p.getId() + " should have gained experience");
        }
    }

    @Test
    void league_allPlayersGainExperience() {
        List<Player> players = createPlayers(4);
        new Tournament(1, players, "clay", "league").simulate();
        for (Player p : players) {
            assertTrue(p.getGainedExperience() > 0, "Player " + p.getId() + " should have gained experience");
        }
    }

    @Test
    void elimination_throwsException_whenPlayerCountIsNotPowerOfTwo() {
        List<Player> players = createPlayers(5);
        Tournament tournament = new Tournament(1, players, "clay", "elimination");
        assertThrows(IllegalArgumentException.class, tournament::simulate);
    }

    @Test
    void elimination_throwsException_whenSinglePlayer() {
        List<Player> players = createPlayers(1);
        Tournament tournament = new Tournament(1, players, "clay", "elimination");
        assertThrows(IllegalArgumentException.class, tournament::simulate);
    }

    @Test
    void elimination_winner_hasHighestExperienceGain() {
        // In a 2-player elimination, winner gets 20 XP and loser gets 10 XP.
        List<Player> players = createPlayers(2);
        new Tournament(1, players, "clay", "elimination").simulate();
        int maxGained = players.stream().mapToInt(Player::getGainedExperience).max().orElse(0);
        assertEquals(20, maxGained);
    }
}
