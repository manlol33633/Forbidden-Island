import java.util.ArrayList;

public class Player {

    //generic attributes each player will have
    AdventurerCard adventurerCard;
    Pawn pawn;
    ArrayList<TreasureCard> treasureCardHand = new ArrayList<TreasureCard>();

    //player specific attributes that are only instantiated if ha

    public Player() {}

    //constructs new Player given String parameter adventurer
    public Player(String adventurer) {
        adventurerCard = new AdventurerCard(adventurer);
        pawn = new Pawn(adventurer);
    }

    public Player(int row, int col, String name) {
       pawn = new Pawn(row, col, name);
    }

    //moves player Pawn to given tile coordinates
    public void move(int row, int col) {
        pawn.setRow(row);
        pawn.setCol(col);
    }

    //checks if a tile can be moved to considering current position, returns boolean
    public boolean canMove(int row, int col) {
        if (((Math.abs(row - pawn.getRow()) == 1) && (Math.abs(col - pawn.getCol()) == 0)) ||
            ((Math.abs(row - pawn.getRow()) == 0) && (Math.abs(col - pawn.getCol()) == 1))) {
            if (Map.instance.getTileAtPosition(row,col) == null) {
                return false;
            }
            return true;
        }
        return false;
    }

    //shores up tile with given coordinates
    public void shoreUp(int row, int col) {
        Map.instance.getTileAtPosition(row,col).shoreUp();
    }

    //checks if tile can be shored up
    public boolean canShoreUp(int row, int col) {
        if (Map.instance.getTileAtPosition(row, col) != null && (Math.abs(row - pawn.getRow()) + Math.abs(col - pawn.getCol()) <= 1) && Map.instance.getTileAtPosition(row, col).getState() == TileState.flooded) {
            return true;
        }
        return false;
    }

    //removes 4 treasureCards from the treasureCardHand
    public void captureTreasure(String treasure) {
        TreasureCard differentCard = null;
        for (TreasureCard tCard: treasureCardHand) {
            if (!tCard.type.equals(treasure))
                differentCard = tCard;
        }
        treasureCardHand = new ArrayList<TreasureCard>();
        if (differentCard != null) {
            treasureCardHand.add(differentCard);
        }

    }

    //checks if conditions are met to be able to capture given treasure
    public boolean canCaptureTreasure(String treasure) {
        switch (treasure) {
            case "fire":
                if (TreasureManager.fireCaptured) return false;
            case "water":
                if (TreasureManager.waterCaptured) return false;
            case "air":
                if (TreasureManager.airCaptured) return false;
            case "earth":
                if (TreasureManager.earthCaptured) return false;
        }

        if (Map.instance.getTileAtPosition(pawn.getRow(), pawn.getCol()).getTileTreasure() != null && Map.instance.getTileAtPosition(pawn.getRow(), pawn.getCol()).getTileTreasure().equals(treasure)) {
            int treasureCardCount = 0;
            TreasureCard differentCard = null;
            for (TreasureCard tCard: treasureCardHand) {
                if (tCard.type.equals(treasure))
                    treasureCardCount++;
                else
                    differentCard = tCard;
            }

            if (treasureCardCount >= 4)
                return true;
        }
        return false;
    }

    //gives TreasureCard tcard to specified recipient
    public void giveCard(TreasureCard tCard, Player recipient) {
        treasureCardHand.remove(tCard);
        recipient.treasureCardHand.add(tCard);
    }

    //checks if conditions for TreasureCard to be given to recipient player
    public boolean canGiveCard(TreasureCard tCard, Player recipient) {
        if (pawn.getRow() == recipient.pawn.getRow() && pawn.getCol() == recipient.pawn.getCol()) {
            if (treasureCardHand.contains(tCard)) {
                return true;
            }
        }
        return false;
    }

    //removes TreasureCard from treasureCardHand
    public void discard(TreasureCard tCard) {
        treasureCardHand.remove(tCard);
    }
  
    public Pawn getPawn() {
        return pawn;
    }
}
