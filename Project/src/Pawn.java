import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Pawn {

    private Color color;
    private int row;
    private int col;
    public String name;

    private BufferedImage image, icon;

    public Pawn(String str) {
        row = 1;
        col = 1;
    }

    public Pawn(int r, int c, String name) {
        row = r; col = c;
        try {
            image = ImageIO.read(GamePanel.class.getResource("/Images/Pawns/" + name + "Pawn.png"));
            icon = ImageIO.read(GamePanel.class.getResource("/Images/Pawns/" + name + "Icon.png"));
        } catch (Exception e) {
            System.out.println("Exception loading pawn image");
        }
        this.name = name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getIcon() {
        return icon;
    }

    public int getRow() { return row; }

    public int getCol() { return col; }

    public void setRow(int r) { row = r; }

    public void setCol(int c) { col = c; }

}
