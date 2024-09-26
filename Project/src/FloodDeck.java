import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.*;

public class FloodDeck {
    static GamePanel gamePanel;
    Deque<Tile> deck;
    ArrayList<Tile> discard = new ArrayList<>();
    BufferedImage discardPileImage;

    public FloodDeck () {
        ArrayList<Tile> tilesList = new ArrayList<>(Arrays.asList(Map.instance.getTiles()));
        Collections.shuffle(tilesList);

        deck = new LinkedList<>(tilesList);

        for (int i = 0; i < 6; i++) {
            Tile next = deck.pop();
            next.floodTile();
            discard.add(next);

            if (i == 5) {
                try {
                    discardPileImage = ImageIO.read(
                            GamePanel.class.getResource("/Images/FloodCards/FloodCard" +
                                    (next.getName().replaceAll("\'", "").replaceAll(" ", ""))
                                    + ".png"
                            )
                    );
                } catch (Exception e) {
                    System.out.println("Error loading flood card");
                    System.out.println("/Images/FloodCards/FloodCard" +
                            (next.getName().replaceAll("\'", "").replaceAll(" ", ""))
                            + ".png");
                }
            }
        }
        gamePanel.repaint();
    }

    public void floodMap() {
        for (int i = 0; i < WaterLevel.waterLevel; i++) {
            if (deck.isEmpty()) {
                ArrayList<Tile> tilesList = new ArrayList<>(Arrays.asList(Map.instance.getTiles()));
                Collections.shuffle(tilesList);

                deck = new LinkedList<>(tilesList);
                discardPileImage = null;
            }

            Tile next = deck.remove();

            if (next.state == TileState.sunk) {
                i--;
                continue;
            }

            discard.add(next);
            next.floodTile();

            if (i == WaterLevel.waterLevel - 1) {
                try {
                    discardPileImage = ImageIO.read(
                            GamePanel.class.getResource("/Images/FloodCards/FloodCard" +
                                    (next.getName().replaceAll("\'", "").replaceAll(" ", ""))
                                    + ".png"
                            )
                    );
                } catch (Exception e) {
                    System.out.println("Error loading flood card");
                    System.out.println("/Images/FloodCards/FloodCard" +
                            (next.getName().replaceAll("\'", "").replaceAll(" ", ""))
                            + ".png");
                }
            }
        }
        gamePanel.repaint();
    }

    public void WatersRise () {
        Collections.shuffle(discard);

        for (Tile t : discard)
            deck.addFirst(t);

        discardPileImage = null;
    }
}
