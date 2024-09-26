import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TreeSet;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel implements MouseListener, KeyListener {
    public static GamePanel instance;
    private BufferedImage backGroundImage,floodCardBackImage,treasureCardBackImage, waterMarkerImage;

    private BufferedImage fireImage, earthImage, waterImage, airImage;

    boolean takingAction = false;

    private boolean playerIsMoving = false, playerIsShoringUp = false, playerIsDiscarding = false,
            playerIsGivingCard = false, usingSandbag = false, navigatorUsingSpecial = false, navigatorSelectedPlayerToMove = false, usingHelicopter = false;
    private Player playerMoving = null, playerShoringUp = null, playerDiscarding = null, playerGivingCard = null, navigatorPlayerToMove;
    private ArrayList<Player> playersToHelicopter = new ArrayList<>();
    int playerDiscardingIndex;
    TreasureCard cardGiving = null;
    int shoreUps = 0;

    boolean pilotSpecialMove = false;

    private Font font;

    private int moves = 3;
    private int currentPlayerIndex;

    public GamePanel(){
        if (instance == null)
            instance = this;

        FloodDeck.gamePanel = this;
        TreasureDeck.gamePanel = this;
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        currentPlayerIndex = (int) (Math.random() * Main.players.size());
        Main.currentPlayer = Main.players.get(currentPlayerIndex);

        try {
            InputStream is = GamePanel.class.getResourceAsStream("/Fonts/Neuton-Regular.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            System.out.println("Exception loading font");
        }

        try {
            backGroundImage = ImageIO.read(GamePanel.class.getResource("/Images/background.png"));
            floodCardBackImage = ImageIO.read(GamePanel.class.getResource("/Images/FloodCardBack.png"));
            treasureCardBackImage = ImageIO.read(GamePanel.class.getResource("/Images/TreasureCardBack.png"));
            waterMarkerImage = ImageIO.read(GamePanel.class.getResource("/Images/Watermeter.png"));

            fireImage = ImageIO.read(GamePanel.class.getResource("/Images/Treasures/FEU.png"));
            earthImage = ImageIO.read(GamePanel.class.getResource("/Images/Treasures/TERRE.png"));
            waterImage = ImageIO.read(GamePanel.class.getResource("/Images/Treasures/EAU.png"));
            airImage = ImageIO.read(GamePanel.class.getResource("/Images/Treasures/AIR.png"));

        }
        catch(Exception E)
        {
            System.out.println("Error loading imaged in game panel");
            return;
        }
    }
    public void paint(Graphics g){
        g.drawImage(backGroundImage, 0, 0, 1600, 960, null);

        drawMap(g);
        drawWaterMaker(g);
        drawDecks(g);
        drawFloodDeck(g);
        drawPlayers(g);


        drawActionUI(g);
        drawTreasure(g);
        drawPlayerInventories(g);

        if (playerIsMoving) {
            drawMoveMarkers(g, playerMoving);
        } else if (playerIsShoringUp) {
            drawShoreUpMarkers(g, playerShoringUp);
        } else if (playerIsDiscarding) {
            drawDiscardMarkers(g, playerDiscardingIndex);
        } else if (playerIsGivingCard) {
            drawGiveCardMarkers(g, playerGivingCard);
        } else if (usingSandbag) {
            drawSandbagMarkers(g);
        } else if (navigatorUsingSpecial) {
            drawNavigatorSpecialMarkers(g);
        } else if (usingHelicopter) {
            drawHelicopterMarkers(g);
        }

    }

    private void drawActionUI(Graphics g) {
        g.setColor(new Color(220, 220, 220));

        g.fillRoundRect(1056, 668, 514, 241, 16, 16);


        g.setColor(new Color(190, 190, 190));
        g.fillRoundRect(1173, 708, 386, 124, 16, 16);
        g.setColor(new Color(190, 190, 190));
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(70, 70, 70));
        g2d.setStroke(new BasicStroke(2.0F));
        g.drawRoundRect(1173, 708, 386, 124, 16, 16);


        g.setFont(font.deriveFont(30f));
        g.setColor(Color.BLACK);
        g.drawString("Claimed Treasures", 1270, 700);

        g.setFont(font.deriveFont(18f));
        g.drawString("Press E to End Turn", 1165, 850);
        g.drawString("Press S to Shore Up", 1165, 870);
        g.drawString("Click on a tile to move a pawn", 1165, 890);

        g.drawString("Click on a card to give or use it", 1365, 850);
        g.drawString("Press C to capture a treasure", 1365, 870);
        g.drawString("Press Q for special actions", 1365, 890);
        g.setFont(font.deriveFont(14f));
        g.drawString("(Pilot & Navigator)", 1365, 905);



        g.setFont(font.deriveFont(20f));
        g.drawString("Actions", 1086, 890);

        g.setFont(font.deriveFont(80f));
        g.drawString(moves + "", 1092, 860);

        g.drawImage(Main.currentPlayer.getPawn().getImage(), 1086, 700, 56, 80, null);
    }

    private void drawPlayerInventories(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRoundRect(1056, 56, 514, 600, 16, 16);

        int iconX = 1400, iconOffsetY = 85, iconSpacing = 145;

        for (int i = 0; i < Main.players.size(); i++) {
            g.drawImage(Main.players.get(i).getPawn().getIcon(), iconX, iconSpacing * i + iconOffsetY, 150, 150, null);
            g.setFont(font.deriveFont(30f));
            g.setColor(Color.WHITE);
            g.drawString(Main.players.get(i).getPawn().name, iconX + 10, iconSpacing * i + iconOffsetY + 5);
        }

        int cardOffsetX = 1062, cardOffsetY = 108, cardSpacingX = 70, cardSpacingY = 145;

        for (int r = 0; r < Main.players.size(); r++) {
            for (int c = 0; c < Main.players.get(r).treasureCardHand.size(); c++) {
                g.drawImage(Main.players.get(r).treasureCardHand.get(c).image, cardOffsetX + cardSpacingX * c, cardOffsetY + cardSpacingY * r, 68, 100, null);
            }
        }
    }

    public void drawNavigatorSpecialMarkers(Graphics g) {
        g.setColor(new Color(255, 255, 255, 175));
        if (!navigatorSelectedPlayerToMove) {
            g.setColor(new Color(255, 0, 0, 175));
            int iconX = 1450, iconOffsetY = 136, iconSpacing = 145;

            for (int i = 0; i < Main.players.size(); i++) {
                if (Main.players.get(i) == Main.currentPlayer) continue;
                g.fillRoundRect(iconX, iconOffsetY + iconSpacing * i, 50, 50, 50, 50);
            }
        } else {
            int offsetX = 108, offsetY = 155, spacing = 120;
            boolean canTakeAction = false;

            PlayerNavigator p = (PlayerNavigator) Main.currentPlayer;

            for (int r = 0; r < 6; r++) {
                for (int c = 0; c < 6; c++) {
                    if (p.canMoveOtherPlayer(navigatorPlayerToMove, r, c)) {
                        canTakeAction = true;
                        g.fillRoundRect(offsetX + c * spacing, offsetY + r * spacing, 50, 50, 50, 50);
                    }
                }
            }


            if (!canTakeAction) {
                navigatorUsingSpecial = false;
                navigatorSelectedPlayerToMove = false;
                takingAction = false;
                repaint();
            }
        }
    }

    public void drawHelicopterMarkers(Graphics g) {
        g.setColor(new Color(255, 0, 0, 175));
        int iconX = 1450, iconOffsetY = 136, iconSpacing = 145;

        for (int i = 0; i < Main.players.size(); i++) {
            if (playersToHelicopter.contains(Main.players.get(i))) continue;
            if (Main.players.get(i).pawn.getCol() != Main.currentPlayer.pawn.getCol() || Main.players.get(i).pawn.getRow() != Main.currentPlayer.pawn.getRow()) continue;
            g.fillRoundRect(iconX, iconOffsetY + iconSpacing * i, 50, 50, 50, 50);
        }
        g.setColor(new Color(255, 255, 255, 175));
        if (playersToHelicopter.size() > 0) {
            int offsetX = 108, offsetY = 155, spacing = 120;
            for (int r = 0; r < 6; r++) {
                for (int c = 0; c < 6; c++) {
                    if (Map.instance.getTileAtPosition(r, c) != null) {
                        g.fillRoundRect(offsetX + c * spacing, offsetY + r * spacing, 50, 50, 50, 50);
                    }
                }
            }
        }
    }


    private void drawGiveCardMarkers(Graphics g, Player p) {
        g.setColor(new Color(255, 255, 255, 175));
        int cardOffsetX = 1062, cardOffsetY = 108, cardSpacingX = 70, cardSpacingY = 145;

        for (int i = 0; i < Main.players.size(); i++) {
            if (Main.players.get(i) == playerGivingCard) {
                continue;
            }

            if (playerGivingCard.pawn.getRow() != Main.players.get(i).pawn.getRow() &&
                    playerGivingCard.pawn.getCol() != Main.players.get(i).pawn.getCol() &&
                    !(playerGivingCard instanceof PlayerMessenger)) {
                continue;
            }

            g.fillRoundRect(cardOffsetX + cardSpacingX * Main.players.get(i).treasureCardHand.size() + 10, cardOffsetY + cardSpacingY * i + 20, 50, 50, 50, 50);
        }
    }

    private void drawDiscardMarkers(Graphics g, int index) {
        int cardOffsetX = 1062, cardOffsetY = 108, cardSpacingX = 70, cardSpacingY = 145;

        g.setColor(new Color(255, 255, 255, 175));

        for (int c = 0; c < playerDiscarding.treasureCardHand.size(); c++) {
            g.fillRoundRect(cardOffsetX + cardSpacingX * c + 10, cardOffsetY + cardSpacingY * index + 20, 50, 50, 50, 50);
        }

    }

    private void drawShoreUpMarkers(Graphics g, Player p) {
        boolean canTakeAction = false;
        int offsetX = 108, offsetY = 155, spacing = 120;
        g.setColor(new Color(255, 255, 255, 175));

        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                if (p.canShoreUp(r, c)) {
                    canTakeAction = true;
                    g.fillRoundRect(offsetX + c * spacing, offsetY + r * spacing, 50, 50, 50, 50);
                }
            }
        }

        if (!canTakeAction) {
            playerIsShoringUp = false;
            takingAction = false;
            if (shoreUps > 0) {
                moves--;
            }
            repaint();
        }

    }

    private void drawTreasure(Graphics g) {
        if (TreasureManager.waterCaptured) {
            g.drawImage(waterImage, 1361, 723, 101, 101, null);
        } else {
            g.drawImage(waterImage, 107, 66, 168, 165, null);
        }

        if (TreasureManager.airCaptured) {
            g.drawImage(airImage, 1291, 730, 80, 80, null);
        } else {
            g.drawImage(airImage, 109, 731, 150, 143, null);
        }

        if (TreasureManager.fireCaptured) {
            g.drawImage(fireImage, 1451, 724, 103, 95, null);
        } else {
            g.drawImage(fireImage, 590, 75, 155, 152, null);
        }

        if (TreasureManager.earthCaptured) {
            g.drawImage(earthImage, 1187, 724, 96, 92, null);
        } else {
            g.drawImage(earthImage, 581, 723, 176, 166, null);
        }
    }

    private void drawSandbagMarkers(Graphics g) {
        boolean canTakeAction = false;

        int offsetX = 108, offsetY = 155, spacing = 120;
        g.setColor(new Color(255, 255, 255, 175));

        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                if (Map.instance.getTileAtPosition(r, c) != null && Map.instance.getTileAtPosition(r, c).getState() == TileState.flooded) {
                    canTakeAction = true;
                    g.fillRoundRect(offsetX + c * spacing, offsetY + r * spacing, 50, 50, 50, 50);
                }
            }
        }


        if (!canTakeAction) {
            usingSandbag = false;
            takingAction = false;
        }
    }

    private void drawMoveMarkers(Graphics g, Player p) {
        boolean canTakeAction = false;
        int offsetX = 108, offsetY = 155, spacing = 120;
        g.setColor(new Color(255, 255, 255, 175));

        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                if (p.canMove(r, c) ||  (pilotSpecialMove && ((PlayerPilot)playerMoving).canMoveSpecial(r, c))) {
                    canTakeAction = true;
                    g.fillRoundRect(offsetX + c * spacing, offsetY + r * spacing, 50, 50, 50, 50);
                }
            }
        }

        if (!canTakeAction) {
            playerIsMoving = false;
            takingAction = false;
            if (Map.instance.getMap()[p.getPawn().getRow()][p.getPawn().getCol()] == null) {
                endGame("You Lose. Player has sunk");
            }
        }
    }

    private void drawPlayers(Graphics g) {
        //Tile info
        int offsetX = 73, offsetY = 130, spacing = 120;

        int index = 0;
        for (Player player : Main.players) {
            Pawn p = player.pawn;
            g.drawImage(p.getImage(), offsetX + p.getCol() * spacing + index * 15, offsetY + p.getRow() * spacing + index * 15, 30, 45, null);
            index++;
        }

    }

    private void drawMap(Graphics g) {
        //Draw translucent rectangle behind the map
        int marginX = 22, marginY = 55, containerWidth = 1021, containerHeight = 850;

        g.setColor(new Color(0, 0, 0, 150));
        g.fillRoundRect(marginX, marginY, containerWidth, containerHeight, 16, 16);

        // Draw the actual map
        int tileSize = 120, offsetX = 73, offsetY = 120, spacing = 120;

        Tile[][] map = Map.instance.getMap();
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                if (map[r][c] == null) {
                    continue;
                }
                g.drawImage(map[r][c].getCurrentImage(), offsetX + c * spacing, offsetY + r * spacing, tileSize, tileSize, null);
            }
        }
    }
    private void drawDecks(Graphics g){
        g.drawImage(treasureCardBackImage, 782, 65, 128, 188, null);
        g.drawImage(Main.treasureDeck.discardPileImage, 782 + 130, 65,128, 188, null);
    }
    private void drawWaterMaker(Graphics g) {
        g.drawImage(waterMarkerImage, 869, 262, 160, 440, null);

        //water level

        int x = 839;
        int yOffset = 630;
        int ySpacing = 37;
        g.setColor(Color.red);
        g.fillRoundRect(x, yOffset - ySpacing * (WaterLevel.waterMarkerLevel - 1), 75, 15, 15, 15);
    }
    private void drawFloodDeck(Graphics g) {
        g.drawImage(floodCardBackImage, 782, 708, 128, 188, null);
        g.drawImage(Main.floodDeck.discardPileImage, 910, 708, 128, 188, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Tile info
        int offsetX = 73, offsetY = 120, spacing = 120;
        int clickCol = (e.getX() - offsetX) / spacing;
        int clickRow = (e.getY() - offsetY) / spacing;

        Tile tileClicked = null;

        try {
            tileClicked = Map.instance.getTileAtPosition(clickRow, clickCol);
        } catch (Exception ex) {

        }

        if (Main.currentPlayer.pawn.getRow() == clickRow && Main.currentPlayer.pawn.getCol() == clickCol && !takingAction) {
            playerIsMoving = true;
            takingAction = true;
            playerMoving = Main.currentPlayer;
            repaint();
        }

        if (playerIsMoving && tileClicked != null && (playerMoving.canMove(clickRow, clickCol) || (pilotSpecialMove &&  ((PlayerPilot)playerMoving).canMoveSpecial(clickRow, clickCol)))) {
            playerIsMoving = false;
            takingAction = false;
            if (!pilotSpecialMove)
                playerMoving.move(clickRow, clickCol);
            else
                ((PlayerPilot)playerMoving).moveSpecial(clickRow, clickCol);
            pilotSpecialMove = false;
            moves--;
            correctPlayerSink();
            repaint();
        } else if (playerIsShoringUp && tileClicked != null && playerShoringUp.canShoreUp(clickRow, clickCol)) {
            tileClicked.shoreUp();
            shoreUps++;
            if (playerShoringUp instanceof PlayerEngineer && shoreUps < 2) {

            } else {
                playerIsShoringUp = false;
                takingAction = false;
                moves--;
            }
            repaint();
        }

        // Card Info
        int cardOffsetX = 1062, cardOffsetY = 108, cardSpacingX = 70, cardSpacingY = 145;
        int clickPlayer = (e.getY() - cardOffsetY) / cardSpacingY;
        int clickCard = (e.getX() - cardOffsetX) / cardSpacingX;

        TreasureCard cardClicked = null;

        try {
            cardClicked = Main.players.get(clickPlayer).treasureCardHand.get(clickCard);
        }
        catch (Exception ex) {

        }

        if (playerIsDiscarding && cardClicked != null && playerDiscarding.treasureCardHand.contains(cardClicked)) {
            playerDiscarding.discard(cardClicked);
            Main.treasureDeck.discardPileImage = cardClicked.image;
            Main.treasureDeck.discard.add(cardClicked);
            if (playerDiscarding.treasureCardHand.size() > 5) {
                discard(playerDiscardingIndex);
            } else {
                takingAction = false;
                playerIsDiscarding = false;
            }
            repaint();
        } else if (!takingAction && cardClicked != null && clickPlayer == currentPlayerIndex) {

            boolean canGive = false;

            for (Player p : Main.players) {
                if (p == Main.players.get(clickPlayer)) continue;
                if (Main.players.get(clickPlayer).pawn.getCol() == p.pawn.getCol() && Main.players.get(clickPlayer).pawn.getRow() == p.pawn.getRow()) {
                    canGive = true;
                    break;
                }
            }

            if (!cardClicked.type.equals("WatersRise") && !cardClicked.type.equals("Sandbags") && !cardClicked.type.equals("Helicopter Lift") && (canGive || Main.players.get(clickPlayer) instanceof PlayerMessenger)) {
                takingAction = true;
                playerIsGivingCard = true;
                playerGivingCard = Main.players.get(clickPlayer);
                cardGiving = cardClicked;
                repaint();
            }
        } else if (playerIsGivingCard) {
            try {
                Player recipient = Main.players.get(clickPlayer);

                if (clickCard == recipient.treasureCardHand.size() && recipient != Main.currentPlayer) {
                    takingAction = false;
                    playerIsGivingCard = false;
                    playerGivingCard.giveCard(cardGiving, recipient);
                    if (recipient.treasureCardHand.size() > 5) {
                        discard(Main.players.indexOf(recipient));
                    }
                    moves--;
                }

            }
            catch (Exception ex) {

            }
            repaint();
        }

        if (!takingAction && cardClicked != null && cardClicked.type.equals("Sandbags")) {
            usingSandbag = true;
            takingAction = true;
            Main.treasureDeck.discardPileImage = cardClicked.image;
            Main.treasureDeck.discard.add(cardClicked);
            Main.players.get(clickPlayer).treasureCardHand.remove(cardClicked);
            repaint();
        } else if (usingSandbag && tileClicked != null && tileClicked.state == TileState.flooded) {
            tileClicked.shoreUp();
            takingAction = false;
            usingSandbag = false;
            repaint();
        } else if (!takingAction && cardClicked != null && cardClicked.type.equals("Helicopter Lift")) {
            boolean win = true;

            if (!TreasureManager.earthCaptured || !TreasureManager.airCaptured || !TreasureManager.waterCaptured || !TreasureManager.fireCaptured) {
                win = false;
            }

            for (Player p : Main.players) {
                if (Map.instance.getTileAtPosition(p.pawn.getRow(), p.pawn.getCol()) != Map.instance.findTileInMapByName("Fool's Landing")) {
                    win = false;
                }
            }

            if (win) {
                endGame("You Win! All pawns on Fool's Landing, all treasure captured, and you safely helicopter everyone away!");
            } else {
                usingHelicopter = true;
                playersToHelicopter = new ArrayList<>();
                takingAction = true;
                Main.treasureDeck.discardPileImage = cardClicked.image;
                Main.treasureDeck.discard.add(cardClicked);
                Main.players.get(clickPlayer).treasureCardHand.remove(cardClicked);
                repaint();
            }
        }

        if (usingHelicopter) {
            if (Main.players.get(clickPlayer) != null && clickCard >= 5) {
                playersToHelicopter.add(Main.players.get(clickPlayer));
                repaint();
            }

            if (playersToHelicopter.size() > 0 && tileClicked != null) {
                for (Player p : playersToHelicopter) {
                    p.move(tileClicked.row, tileClicked.col);
                }
                usingHelicopter = false;
                takingAction = false;
                repaint();
            }
        }


        if (navigatorUsingSpecial) {
            if (!navigatorSelectedPlayerToMove && Main.players.get(clickPlayer) != null && clickCard >= 5) {
                navigatorSelectedPlayerToMove = true;
                navigatorPlayerToMove = Main.players.get(clickPlayer);
                repaint();
            } else if (tileClicked != null && ((PlayerNavigator)Main.currentPlayer).canMoveOtherPlayer(navigatorPlayerToMove, tileClicked.row, tileClicked.col)) {
                ((PlayerNavigator)Main.currentPlayer).moveOtherPlayer(navigatorPlayerToMove, tileClicked.row, tileClicked.col);
                navigatorUsingSpecial = false;
                navigatorSelectedPlayerToMove = false;
                takingAction = false;
                moves--;
                repaint();
            }

        }




        if (moves <= 0) {
            nextTurn();
        }
    }

    private void nextTurn() {
        Main.floodDeck.floodMap();
        Main.treasureDeck.drawTreasureCards(Main.currentPlayer);
        correctPlayerSink();
        playerDiscardingIndex = currentPlayerIndex;

        if (Main.currentPlayer.treasureCardHand.size() > 5) {
            discard(currentPlayerIndex);
        }

        moves = 3;
        currentPlayerIndex++;
        currentPlayerIndex %= Main.players.size();
        Main.currentPlayer = Main.players.get(currentPlayerIndex);

        if (Main.currentPlayer instanceof PlayerPilot && moves <= 0) {
            ((PlayerPilot) Main.currentPlayer).startTurn();
        }

        repaint();
    }

    private void correctPlayerSink() {
        boolean hadToCorrect = false;
        for (Player p : Main.players) {
            if (Map.instance.getMap()[p.getPawn().getRow()][p.getPawn().getCol()] == null) {
                playerIsMoving = true;
                takingAction = true;
                playerMoving = p;
                moves++;
                moves = Math.min(moves, 3);
                Main.currentPlayer = playerMoving;
                hadToCorrect = true;
                repaint();
            }
        }

        if (!hadToCorrect) {
            if (Main.players.get(playerDiscardingIndex).treasureCardHand.size() > 5) {
                discard(playerDiscardingIndex);
            }

            Main.currentPlayer = Main.players.get(currentPlayerIndex);
            repaint();
        }
    }

    private void discard(int index) {
        playerDiscardingIndex = index;
        playerIsDiscarding = true;
        takingAction = true;
        playerDiscarding = Main.players.get(index);
        moves = Math.min(moves, 3);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) {
        if (!takingAction && e.getKeyChar() == 's') {
            playerIsShoringUp = true;
            takingAction = true;
            playerShoringUp = Main.currentPlayer;
            shoreUps = 0;
            repaint();
        } else if (!takingAction && e.getKeyChar() == 'e') {
            nextTurn();
        } else if (!takingAction && e.getKeyChar() == 'c') {
            if (Main.currentPlayer.canCaptureTreasure("fire")) {
                Main.currentPlayer.captureTreasure("fire");
                TreasureManager.fireCaptured = true;
                moves--;
                repaint();
            } else if (Main.currentPlayer.canCaptureTreasure("water")) {
                Main.currentPlayer.captureTreasure("water");
                TreasureManager.waterCaptured = true;
                moves--;
                repaint();
            } else if (Main.currentPlayer.canCaptureTreasure("earth")) {
                Main.currentPlayer.captureTreasure("earth");
                TreasureManager.earthCaptured = true;
                moves--;
                repaint();
            } else if (Main.currentPlayer.canCaptureTreasure("air")) {
                Main.currentPlayer.captureTreasure("air");
                TreasureManager.airCaptured = true;
                moves--;
                repaint();
            }
        } else if (!takingAction && e.getKeyChar() == 'q') {
            if (Main.currentPlayer instanceof PlayerPilot) {
                if (((PlayerPilot) Main.currentPlayer).abilityIsAvailable) {
                    playerIsMoving = true;
                    takingAction = true;
                    playerMoving = Main.currentPlayer;
                    pilotSpecialMove = true;
                    repaint();
                }
            } else if (Main.currentPlayer instanceof PlayerNavigator) {
                takingAction = true;
                navigatorUsingSpecial = true;
                navigatorSelectedPlayerToMove = false;
                repaint();
            }
        }

        if (moves <= 0) {
            nextTurn();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    public void endGame(String message) {
        removeMouseListener(this);
        removeKeyListener(this);
        JOptionPane.showMessageDialog(null, message);
    }
}
