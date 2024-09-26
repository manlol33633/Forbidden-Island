import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class TreasureCard {

    public String type;
    public BufferedImage image;

    public TreasureCard(String t) {
        type = t;

        try {
            image = ImageIO.read(GamePanel.class.getResource("/Images/TreasureCards/" + t + ".png"));
        }
        catch (Exception e) {
            System.out.println("Error loading treasure card image");
        }
    }


    public String toString() {
        return type;
    }
}
