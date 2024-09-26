import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Tile {
    private String name;
    int row = -1, col = -1;
    TileState state = TileState.normal;
    private BufferedImage currentImage, normalImage, floodedImage;
    private String tileTreasure = null;

    public Tile(String name) {
        this.name = name;
        try {
            normalImage = ImageIO.read(GamePanel.class.getResource("/Images/Tiles/" + name.replaceAll("\'", "") + ".png"));
            floodedImage = ImageIO.read(GamePanel.class.getResource("/Images/Tiles/" + name.replaceAll("\'", "") + "Flooded Final.png"));
            currentImage = normalImage;
        }
        catch (Exception e) {
            System.out.println("Exception in loading image for tile: " + name);
        }
    }

    public Tile(String name, String tileTreasure) {
        this.name = name;
        this.tileTreasure = tileTreasure;
        try {
            normalImage = ImageIO.read(GamePanel.class.getResource("/Images/Tiles/" + name.replaceAll("\'", "") + ".png"));
            floodedImage = ImageIO.read(GamePanel.class.getResource("/Images/Tiles/" + name.replaceAll("\'", "") + "Flooded Final.png"));
            currentImage = normalImage;
        }
        catch (Exception e) {
            System.out.println("Exception in loading image for tile: " + name);
        }
    }

    public void floodTile () {
        if (state == TileState.normal) {
            state = TileState.flooded;
            currentImage = floodedImage;
        } else {
            state = TileState.sunk;
            Map.instance.getMap()[row][col] = null;
            if (name.equals("Fool's Landing")) {
                GamePanel.instance.endGame("You Lose. Fool's Landing sunk.");
            }

            if (tileTreasure == null) return;
            switch (tileTreasure) {
                case "fire": Map.fireLeft--;
                if (Map.fireLeft == 0 && !TreasureManager.fireCaptured) {
                    GamePanel.instance.endGame("You lose. All fire treasure tiles sunk before the treasure was captured.");
                }
                break;
                case "air": Map.airLeft--;
                    if (Map.airLeft == 0 && !TreasureManager.airCaptured) {
                        GamePanel.instance.endGame("You lose. All air treasure tiles sunk before the treasure was captured.");
                    }
                    break;
                case "water": Map.waterLeft--;
                    if (Map.waterLeft == 0 && !TreasureManager.waterCaptured) {
                        GamePanel.instance.endGame("You lose. All water treasure tiles sunk before the treasure was captured.");
                    }
                    break;
                case "earth": Map.earthLeft--;
                    if (Map.earthLeft == 0 && !TreasureManager.earthCaptured) {
                        GamePanel.instance.endGame("You lose. All earth treasure tiles sunk before the treasure was captured.");
                    }break;
            }
        }
    }

    public void shoreUp() {
        if (state == TileState.flooded) {
            state = TileState.normal;
            currentImage = normalImage;
        }
    }

    public String getName() {
        return name;
    }

    public TileState getState() { return state; }

    public String getTileTreasure() { return tileTreasure; }

    public BufferedImage getCurrentImage() {
        return currentImage;
    }

    public String toString() {
        return name;
    }

    public boolean equals(Tile other) {
        return name.equals(other.getName());
    }
}
