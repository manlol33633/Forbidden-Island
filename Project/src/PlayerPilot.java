public class PlayerPilot extends Player{
    public PlayerPilot() {
        super(Map.instance.findTileInMapByName("Fool's Landing").row, Map.instance.findTileInMapByName("Fool's Landing").col, "Pilot");
    }

    //Since pilot can only use special move once per turn, he has to have boolean which turns false after using in a turn
     boolean abilityIsAvailable = true;

    public void startTurn() {
        abilityIsAvailable = true;
    }

    public void moveSpecial(int row, int col) {
        abilityIsAvailable = false;
        super.move(row, col);
    }

    public boolean canMoveSpecial(int row, int col) {
        if (abilityIsAvailable && Map.instance.getTileAtPosition(row, col) != null && !(pawn.getRow() == row && pawn.getCol() == col))
            return true;
        return false;
    }

}