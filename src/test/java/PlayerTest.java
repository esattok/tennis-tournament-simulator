import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    private Player player(String hand, int experience, int surfaceSkill) {
        return new Player(1, hand, experience, Map.of("clay", surfaceSkill, "grass", surfaceSkill, "hard", surfaceSkill));
    }

    @Test
    void baselineScore_isOne() {
        Player p = player("right", 50, 5);
        Player opponent = player("right", 50, 5);
        assertEquals(1, p.calculateMatchScore("clay", opponent));
    }

    @Test
    void leftHandBonus_addsTwo() {
        Player p = player("left", 50, 5);
        Player opponent = player("right", 50, 5);
        assertEquals(3, p.calculateMatchScore("clay", opponent));
    }

    @Test
    void experienceBonus_addsThree_whenStrictlyGreater() {
        Player p = player("right", 100, 5);
        Player opponent = player("right", 50, 5);
        assertEquals(4, p.calculateMatchScore("clay", opponent));
    }

    @Test
    void experienceBonus_notApplied_whenEqual() {
        Player p = player("right", 50, 5);
        Player opponent = player("right", 50, 5);
        assertEquals(1, p.calculateMatchScore("clay", opponent));
    }

    @Test
    void surfaceSkillBonus_addsFour_whenStrictlyGreater() {
        Player p = player("right", 50, 9);
        Player opponent = player("right", 50, 5);
        assertEquals(5, p.calculateMatchScore("clay", opponent));
    }

    @Test
    void surfaceSkillBonus_notApplied_whenEqual() {
        Player p = player("right", 50, 5);
        Player opponent = player("right", 50, 5);
        assertEquals(1, p.calculateMatchScore("clay", opponent));
    }

    @Test
    void allBonuses_maxScoreIsTen() {
        Player p = player("left", 100, 9);
        Player opponent = player("right", 50, 5);
        assertEquals(10, p.calculateMatchScore("clay", opponent));
    }

    @Test
    void addExperience_incrementsBothFields() {
        Player p = player("right", 50, 5);
        p.addExperience(30);
        assertEquals(80, p.getExperience());
        assertEquals(30, p.getGainedExperience());
        assertEquals(50, p.getInitialExperience());
    }
}
