
public class PlayerEngineer extends Player {
    public PlayerEngineer() {
        super(Map.instance.findTileInMapByName("Bronze Gate").row, Map.instance.findTileInMapByName("Bronze Gate").col, "Engineer");
    }


   /* //@Override, passes two sets of tile coordinates, which can be the same tile
    public void shoreUp(int row1, int col1, int row2, int col2) {
        Map.instance.getTileAtPosition(row1,col1).shoreUp();
        Map.instance.getTileAtPosition(row2,col2).shoreUp();
    }


    public boolean canShoreUp(int row1, int col1, int row2, int col2) {
        if ((Math.abs(row1 - pawn.getRow()) + Math.abs(col1 - pawn.getCol()) <= 1) && Map.instance.getTileAtPosition(row1, col1).getState() == TileState.flooded) {
            if ((Math.abs(row2 - pawn.getRow()) + Math.abs(col2 - pawn.getCol()) <= 1) && Map.instance.getTileAtPosition(row2, col2).getState() == TileState.flooded) {
                return true;
            }
        }
        return false;
    }

    //public*/
}
