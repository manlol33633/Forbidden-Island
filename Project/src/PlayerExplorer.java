
public class PlayerExplorer extends Player{
    public PlayerExplorer() {
        super(Map.instance.findTileInMapByName("Copper Gate").row, Map.instance.findTileInMapByName("Copper Gate").col, "Explorer");
    }


    public void move(int row, int col) {
        super.move(row, col);
    }

    //@Override
    public boolean canMove(int row, int col) {
        if (pawn.getRow() == row && pawn.getCol() == col) return false;

        if ((Math.abs(row - pawn.getRow()) <= 1) && (Math.abs(col - pawn.getCol()) <= 1)) {
            if (Map.instance.getTileAtPosition(row,col) == null) {
                return false;
            }
            return true;
        }
        return false;
    }

    public void shoreUp(int row, int col) {
        super.shoreUp(row, col);
    }

    //@Override
    public boolean canShoreUp(int row, int col) {
        if (Map.instance.getMap()[row][col] != null && Map.instance.getMap()[row][col].getState() == TileState.flooded && Math.abs(row - pawn.getRow()) <= 1 && Math.abs(col - pawn.getCol()) <= 1) {
            return true;
        }
        return false;
    }

}
