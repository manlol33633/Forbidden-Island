public class WaterLevel {
    public static int waterMarkerLevel = 1;
    public static int waterLevel = 2;


    public static void increaseWaterLevel() {
        Main.floodDeck.WatersRise();
        waterMarkerLevel++;

        if (waterMarkerLevel == 10) {
            GamePanel.instance.endGame("You Lose. The water level got too high.");
        }

        if (waterMarkerLevel >= 3) {
            waterLevel = 3;
        }
        if (waterMarkerLevel >= 6) {
            waterLevel = 4;
        }
        if (waterMarkerLevel >= 8) {
            waterLevel = 5;
        }
    }
}
