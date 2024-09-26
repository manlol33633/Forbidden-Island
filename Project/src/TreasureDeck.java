import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.*;

public class TreasureDeck {
    static GamePanel gamePanel;
    Deque<TreasureCard> deck;
    static BufferedImage discardPileImage;
    static ArrayList<TreasureCard> discard = new ArrayList<>();
    ArrayList<TreasureCard> cardList = new ArrayList<>();


    public TreasureDeck () {
        cardList.add(new TreasureCard("fire"));
        cardList.add(new TreasureCard("fire"));
        cardList.add(new TreasureCard("fire"));
        cardList.add(new TreasureCard("fire"));
        cardList.add(new TreasureCard("fire"));

        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));

        cardList.add(new TreasureCard("earth"));
        cardList.add(new TreasureCard("earth"));
        cardList.add(new TreasureCard("earth"));
        cardList.add(new TreasureCard("earth"));
        cardList.add(new TreasureCard("earth"));

        cardList.add(new TreasureCard("water"));
        cardList.add(new TreasureCard("water"));
        cardList.add(new TreasureCard("water"));
        cardList.add(new TreasureCard("water"));
        cardList.add(new TreasureCard("water"));

        cardList.add(new TreasureCard("Helicopter Lift"));
        cardList.add(new TreasureCard("Helicopter Lift"));
        cardList.add(new TreasureCard("Helicopter Lift"));

        cardList.add(new TreasureCard("WatersRise"));
        cardList.add(new TreasureCard("WatersRise"));
        cardList.add(new TreasureCard("WatersRise"));

        cardList.add(new TreasureCard("Sandbags"));
        cardList.add(new TreasureCard("Sandbags"));

        /*cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));
        cardList.add(new TreasureCard("air"));*/



        Collections.shuffle(cardList);

        deck = new LinkedList<TreasureCard>(cardList);

        for (int i = 0; i < Main.players.size(); i++) {
            for (int j = 0; j < 2; j++) {
                TreasureCard next = deck.remove();
                if (next.type.equals("WatersRise")) {
                    deck.addLast(next);
                    j--;
                    continue;
                }

                Main.players.get(i).treasureCardHand.add(next);
            }
        }
        //System.out.println(deck.toString());

        gamePanel.repaint();
    }

    public void drawTreasureCards(Player p) {

        for (int j = 0; j < 2; j++) {
            if (deck.isEmpty()) {
                ArrayList<TreasureCard> reshuffle = new ArrayList<>();

                reshuffle.addAll(discard);
                Collections.shuffle(reshuffle);
                deck.addAll(reshuffle);


                discardPileImage = null;
            }

            TreasureCard next = deck.remove();

            if (next.type.equals("WatersRise")) {
                discardPileImage = next.image;
                discard.add(next);
                WaterLevel.increaseWaterLevel();
                continue;
            }

            p.treasureCardHand.add(next);
        }

        //System.out.println(deck.toString());

        gamePanel.repaint();
    }
}
