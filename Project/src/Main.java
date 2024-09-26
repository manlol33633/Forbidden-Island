import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

import static java.lang.System.*;

public class Main {
    public static FloodDeck floodDeck;
    static TreasureDeck treasureDeck;
    public static ArrayList<Player> players = new ArrayList<>(), playerPool = new ArrayList<>();
    public static Player currentPlayer;

    public static void main(String[] args) {
        out.println("Game Seed: " + RandomUtil.seed);
        Map map = new Map();

        playerPool.add(new PlayerNavigator());
        playerPool.add(new PlayerDiver());
        playerPool.add(new PlayerEngineer());
        playerPool.add(new PlayerPilot());
        playerPool.add(new PlayerExplorer());
        playerPool.add(new PlayerMessenger());

        Collections.shuffle(playerPool);

        int playerCount = 4;
        try { playerCount = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of players (2 - 4)"));  } catch (Exception e) { playerCount = 4; }

        if (playerCount < 2) playerCount = 2; if (playerCount > 4) playerCount = 4;

        int startingWater = 1;
        try { startingWater = Integer.parseInt(JOptionPane.showInputDialog("Enter the startingWaterLevel (1 - 4)")); } catch (Exception e) { startingWater = 1; }

        if (startingWater < 1) startingWater = 1; if (startingWater > 4) startingWater = 4;

        WaterLevel.waterMarkerLevel = startingWater;
        if (startingWater >= 3) {
            WaterLevel.waterLevel = 3;
        }


        for (int i = 0; i < playerCount; i++) {
            players.add(playerPool.remove(0));
        }

        MainMenuFrame graphics = new MainMenuFrame("Main Menu");
        floodDeck = new FloodDeck();
        treasureDeck = new TreasureDeck();
    }
}
