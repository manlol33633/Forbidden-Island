import java.util.*;

public class Map {
    private Tile[][] map;
    static Map instance;

    static int fireLeft = 2, earthLeft = 2, airLeft = 2, waterLeft = 2;

    private Tile[] tiles = {
        new Tile("Breaker's Bridge"),
        new Tile("Bronze Gate"),
        new Tile("Cave of Embers", "fire"),
        new Tile("Cave of Shadows", "fire"),
        new Tile("Cliffs of Abandon"),
        new Tile("Copper Gate"),
        new Tile("Coral Palace", "water"),
        new Tile("Crimson Forest"),
        new Tile("Dunes of Deception"),
        new Tile("Fool's Landing"),
        new Tile("Gold Gate"),
        new Tile("Howling Garden", "air"),
        new Tile("Iron Gate"),
        new Tile("Lost Lagoon"),
        new Tile("Misty Marsh"),
        new Tile("Observatory"),
        new Tile("Phantom Rock"),
        new Tile("Silver Gate"),
        new Tile("Temple Moon", "earth"),
        new Tile("Temple Sun", "earth"),
        new Tile("Tidal Palace", "water"),
        new Tile("Twilight Hollow"),
        new Tile("Watchtower"),
        new Tile("Whispering Garden", "air")
    };

    public Map() {
        if (Map.instance != null) {
            return;
        }
        createGrid();
        Map.instance = this;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public Tile[][] getMap() {
        return map;
    }

    private void createGrid() {
        map = new Tile[6][6];
        ArrayList<Tile> tilesList = new ArrayList<>(Arrays.asList(tiles));
        Collections.shuffle(tilesList);
        Queue<Tile> tileQueue = new LinkedList<>(tilesList);
        System.out.println(tileQueue.toString());

        int margin = 2;
        for (int r = 0; r < map.length; r++) {
            for (int c = margin; c < map.length - margin; c++) {
                Tile next = tileQueue.poll();
                next.row = r; next.col = c;
                map[r][c] = next;
            }

            if (r < map.length / 2 - 1) {
                margin--;
            } else if (r > map.length / 2 - 1) {
                margin++;
            }
        }
    }

    public Tile findTileInMapByName(String name) {
        for (Tile[] row : map) {
            for(Tile tile : row) {
                if (tile == null) continue;
                if (tile.getName().equals(name)) {
                    return tile;
                }
            }
        }

        return null;
    }

    public Tile getTileAtPosition(int row, int col) {
        return map[row][col];
    }
}
