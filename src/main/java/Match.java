public class Match {
    private Player player1;
    private Player player2;
    private String surface;

    public Match(Player player1, Player player2, String surface) {
        this.player1 = player1;
        this.player2 = player2;
        this.surface = surface;
    }

    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }

    public Player play() {
        int score1 = player1.calculateMatchScore(surface, player2);
        int score2 = player2.calculateMatchScore(surface, player1);

        double winProbability1 = (double) score1 / (score1 + score2);

        return Math.random() <= winProbability1 ? player1 : player2;
    }
}
