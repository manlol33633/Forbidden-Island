public class PlayerMessenger extends Player{

    public PlayerMessenger() {
        super(Map.instance.findTileInMapByName("Silver Gate").row, Map.instance.findTileInMapByName("Silver Gate").col, "Messenger");
    }


    //@Override
    public void giveCard(TreasureCard tCard, Player recipient) {
        super.giveCard(tCard, recipient);
    }

    //@Override
    public boolean canGiveCard(TreasureCard tCard, Player recipient) {
        if (treasureCardHand.contains(tCard)) {
            return true;
        }
        return false;
    }

}
