import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TennisTournamentSimulator {
    public static void main(String[] args) {
        try {
            System.out.println("Simulation started.");

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> input = objectMapper.readValue(
                new File("input.json"),
                new TypeReference<Map<String, Object>>() {}
            );

            List<Map<String, Object>> playersInput = (List<Map<String, Object>>) input.get("players");
            List<Player> players = new ArrayList<>();
            for (Map<String, Object> playerData : playersInput) {
                int id = (int) playerData.get("id");
                String hand = (String) playerData.get("hand");
                int experience = (int) playerData.get("experience");
                Map<String, Integer> skills = (Map<String, Integer>) playerData.get("skills");
                players.add(new Player(id, hand, experience, skills));
            }

            List<Map<String, Object>> tournamentsInput = (List<Map<String, Object>>) input.get("tournaments");
            for (Map<String, Object> tournamentData : tournamentsInput) {
                int id = (int) tournamentData.get("id");
                String surface = (String) tournamentData.get("surface");
                String type = (String) tournamentData.get("type");
                new Tournament(id, players, surface, type).simulate();
            }

            System.out.println("All tournaments completed.");

            players.sort(
                Comparator.comparingInt(Player::getGainedExperience).reversed()
                    .thenComparing(Comparator.comparingInt(Player::getInitialExperience).reversed())
            );

            List<SimulationResult> results = new ArrayList<>();
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
                results.add(new SimulationResult(i + 1, player.getId(), player.getGainedExperience(), player.getExperience()));
            }

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("output.json"), results);
            System.out.println("Results saved to output.json.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
