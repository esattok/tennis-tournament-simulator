import com.fasterxml.jackson.annotation.JsonProperty;

public class SimulationResult {
    @JsonProperty("order")
    private int order;

    @JsonProperty("player_id")
    private int playerId;

    @JsonProperty("gained_experience")
    private int gainedExperience;

    @JsonProperty("total_experience")
    private int totalExperience;

    public SimulationResult(int order, int playerId, int gainedExperience, int totalExperience) {
        this.order = order;
        this.playerId = playerId;
        this.gainedExperience = gainedExperience;
        this.totalExperience = totalExperience;
    }

    public int getOrder() { return order; }
    public int getPlayerId() { return playerId; }
    public int getGainedExperience() { return gainedExperience; }
    public int getTotalExperience() { return totalExperience; }
}
