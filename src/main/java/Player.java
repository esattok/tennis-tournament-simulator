import java.util.Map;

public class Player {
    private int id;
    private String hand;
    private int experience;
    private int initialExperience;
    private int gainedExperience;
    private Map<String, Integer> skills;

    public Player(int id, String hand, int experience, Map<String, Integer> skills) {
        this.id = id;
        this.hand = hand;
        this.experience = experience;
        this.initialExperience = experience;
        this.gainedExperience = 0;
        this.skills = skills;
    }

    public int getId() { return id; }
    public String getHand() { return hand; }
    public int getExperience() { return experience; }
    public int getInitialExperience() { return initialExperience; }
    public int getGainedExperience() { return gainedExperience; }
    public Map<String, Integer> getSkills() { return skills; }

    public void addExperience(int amount) {
        this.experience += amount;
        this.gainedExperience += amount;
    }

    public int calculateMatchScore(String surface, Player opponent) {
        int score = 1;

        if ("left".equals(hand)) {
            score += 2;
        }

        if (this.experience > opponent.experience) {
            score += 3;
        }

        if (this.skills.getOrDefault(surface, 0) > opponent.skills.getOrDefault(surface, 0)) {
            score += 4;
        }

        return score;
    }
}
