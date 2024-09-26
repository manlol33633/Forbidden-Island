
public class PlayerDiver extends Player {

    private boolean reachable;

    public PlayerDiver() {
        super(Map.instance.findTileInMapByName("Iron Gate").row, Map.instance.findTileInMapByName("Iron Gate").col, "Diver");
    }

    @Override
    public boolean canMove(int row, int col) {
        if (pawn.getRow() == row && pawn.getCol() == col) return false;

        if (super.canMove(row, col)) return true;

        if (Map.instance.getMap()[row][col] == null) return false;

        if (pawn.getRow() == row && pawn.getCol() == col) return false;

        reachable = false;

        boolean[][] visited = new boolean[6][6];
        floodFill(pawn.getRow(), pawn.getCol(), row, col, visited, 0);
        return reachable;
    }

    // This is very unoptimized, but it's 2am, so too bad
    private void floodFill(int r, int c, int endR, int endC, boolean[][] visited, int iter) {
        if (r == endR && c == endC) {
            reachable = true;
            return;
        }


        if (r > 5 || r < 0 || c > 5 || c < 0) {
            return;
        }

        if (visited[r][c]) {
            return;
        }


        if (Map.instance.getMap()[r][c] != null && iter > 0) {
            return;
        }

        visited[r][c] = true;

        int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
        for (int i = 0; i < 4; i++) {
            System.out.println("hi");
            floodFill(r + dr[i], c + dc[i], endR, endC, visited, iter + 1);
        }
    }


    // Kale's code
    /*


    //private variables to help track which tiles are movable by diver ability
    private boolean[][] mapOfMovableTiles = new boolean[6][6];
    private boolean[][] checkedTiles = new boolean[6][6];

    public void moveSpecial(int row, int col) {
        super.move(row, col);
    }

    //checks to see if a tile is reachable by water using the diver special move ability
    @Override
    public boolean canMove(int row, int col) {
        updateMapOfReachableTiles();

        if (!(row - col > 2 || row + col < 3) && mapOfMovableTiles[row - 1][col]) {
            return true;
        }
        if (!(col - row > 2 || row + col > 7) && mapOfMovableTiles[row + 1][col]) {
            return true;
        }
        if (!(col - row > 2 || row + col < 3) && mapOfMovableTiles[row][col - 1]) {
            return true;
        }
        if (!(row - col > 2 || row + col > 7) && mapOfMovableTiles[row][col + 1]) {
            return true;
        }
        return false;
    }

    private void updateMapOfReachableTiles() {
        mapOfMovableTiles = new boolean[6][6];
        checkedTiles = new boolean[6][6];
        checkNeighboringTiles(pawn.getRow(), pawn.getCol());
    }

    //recursive method that finds the branch of all movable tiles starting from a given position
    private void checkNeighboringTiles(int row, int col) {
        if (!(row - col > 2 || row + col < 3) &&
                Map.instance.getTileAtPosition(row - 1, col).getState() != TileState.normal &&
                checkedTiles[row - 1][col] == false) {
            mapOfMovableTiles[row - 1][col] = true;
            checkedTiles[row - 1][col] = true;
            checkNeighboringTiles(row - 1, col);
        }
        if (!(col - row > 2 || row + col > 7) &&
                Map.instance.getTileAtPosition(row + 1, col).getState() != TileState.normal &&
                checkedTiles[row + 1][col] == false) {
            mapOfMovableTiles[row + 1][col] = true;
            checkedTiles[row + 1][col] = true;
            checkNeighboringTiles(row + 1, col);
        }
        if (!(col - row > 2 || row + col < 3) &&
                Map.instance.getTileAtPosition(row, col - 1).getState() != TileState.normal &&
                checkedTiles[row][col - 1] == false) {
            mapOfMovableTiles[row][col - 1] = true;
            checkedTiles[row][col - 1] = true;
            checkNeighboringTiles(row, col - 1);
        }
        if (!(row - col > 2 || row + col > 7) &&
                Map.instance.getTileAtPosition(row, col + 1).getState() != TileState.normal &&
                checkedTiles[row][col + 1] == false) {
            mapOfMovableTiles[row][col + 1] = true;
            checkedTiles[row][col + 1] = true;
            checkNeighboringTiles(row, col + 1);
        }
    } */
}
