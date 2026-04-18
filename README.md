# Tennis Tournament Simulator

A Java CLI application that simulates a tennis season across multiple tournaments and produces a ranked leaderboard based on experience gained.

## How It Works

The simulator reads players and tournaments from `input.json`, runs every tournament in sequence, and writes the final standings to `output.json`.

### Players

Each player has:
- **Hand** — `left` or `right` (left-handed players get a scoring bonus)
- **Experience** — accumulated over the season; affects win probability
- **Skills** — separate ratings for `clay`, `grass`, and `hard` surfaces

### Match Outcome

Matches are decided probabilistically. Each player receives a score based on the current match:

| Condition | Bonus |
|---|---|
| Participation | +1 |
| Left-handed | +2 |
| More experience than opponent | +3 |
| Higher surface skill than opponent | +4 |

The win probability for a player is `score / (score1 + score2)`. A random draw determines the winner, so upsets are possible but unlikely against a significantly stronger opponent.

### Tournament Formats

| Format | Description | Winner XP | Loser XP |
|---|---|---|---|
| `elimination` | Single-elimination bracket. Requires a power-of-2 player count. | +20 | +10 |
| `league` | Round-robin. Every player faces every other player once. | +10 | +1 |

Experience earned in earlier tournaments carries over and influences win probability in later ones.

### Ranking

After all tournaments, players are ranked by:
1. **Gained experience** (descending)
2. **Initial experience** as a tiebreaker (descending)

## Project Structure

```
tennis-tournament-simulator/
├── src/
│   ├── main/java/
│   │   ├── Player.java                 # Player model and match scoring logic
│   │   ├── Match.java                  # Single match with probabilistic outcome
│   │   ├── Tournament.java             # Elimination and league simulation
│   │   ├── SimulationResult.java       # Output model
│   │   └── TennisTournamentSimulator.java  # Entry point
│   └── test/java/
│       ├── PlayerTest.java
│       ├── MatchTest.java
│       └── TournamentTest.java
├── input.json      # Players and tournament schedule
├── pom.xml
└── .gitignore
```

## Requirements

- Java 21+
- Maven 3.6+

## Running

**Run the simulation:**
```bash
mvn exec:java
```

This reads `input.json` from the project root and writes results to `output.json`.

**Run the tests:**
```bash
mvn test
```

## Input Format

```json
{
  "players": [
    {
      "id": 1,
      "hand": "right",
      "experience": 50,
      "skills": { "clay": 7, "grass": 4, "hard": 6 }
    }
  ],
  "tournaments": [
    { "id": 1, "surface": "clay", "type": "elimination" },
    { "id": 2, "surface": "grass", "type": "league" }
  ]
}
```

## Output Format

```json
[
  { "order": 1, "player_id": 8, "gained_experience": 721, "total_experience": 821 },
  { "order": 2, "player_id": 5, "gained_experience": 680, "total_experience": 730 }
]
```
