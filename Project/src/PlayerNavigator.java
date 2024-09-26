public class PlayerNavigator extends Player{
    public PlayerNavigator() {
        super(Map.instance.findTileInMapByName("Gold Gate").row, Map.instance.findTileInMapByName("Gold Gate").col, "Navigator");
    }

    public void moveOtherPlayer(Player p, int row, int col) {
        p.pawn.setRow(row);
        p.pawn.setCol(col);
    }

    /*public boolean canMoveOtherPlayer(Player p, int row, int col) {
        if (this == p) return false;
        if (p.pawn.getRow() == row && p.pawn.getCol() == col) return false;

        if (Math.abs(p.pawn.getRow() - row) + Math.abs(p.pawn.getCol() - col) <= 2) {
            int rowDiff = p.pawn.getRow() - row, colDiff = p.pawn.getCol() - col;

            if (Map.instance.getTileAtPosition(row, col) == null) {
                return false;
            }

            if (Math.abs(rowDiff) == 2) {
                if (Map.instance.getTileAtPosition(p.pawn.getRow() + rowDiff / 2, p.getPawn().getCol()) == null) {
                    return false;
                }
            } else if (Math.abs(colDiff) == 2) {
                if (Map.instance.getTileAtPosition(p.pawn.getRow(), p.getPawn().getCol() + colDiff / 2) == null) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;

    }*/

    private boolean reachable;
    public boolean canMoveOtherPlayer(Player p, int row, int col) {
        if (this == p) return false;
        if (p.pawn.getRow() == row && p.pawn.getCol() == col) return false;
        if (Math.abs(p.pawn.getRow() - row) + Math.abs(p.pawn.getCol() - col) > 2) return false;
        if (Map.instance.getTileAtPosition(row, col) == null) return false;

        reachable = false;

        boolean[][] visited = new boolean[6][6];
        floodFill(p.pawn.getRow(), p.pawn.getCol(), row, col, visited, 0);
        return reachable;
    }

    private void floodFill(int r, int c, int endR, int endC, boolean[][] visited, int iter) {
        if (r == endR && c == endC) {
            reachable = true;
            return;
        }

        if (iter > 1) {
            return;
        }

        if (r > 5 || r < 0 || c > 5 || c < 0) {
            return;
        }

        if (visited[r][c]) {
            return;
        }


        if (Map.instance.getMap()[r][c] == null) {
            return;
        }

        visited[r][c] = true;

        int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
        for (int i = 0; i < 4; i++) {
            floodFill(r + dr[i], c + dc[i], endR, endC, visited, iter + 1);
        }
    }
}
