/*
 * Created by: Andrew Nguyen + Jade Jordan
 * Date: 2019-03-04
 * Time: 19:06
 * CS-345-Deadwood
 */

/*
 * This is going to be the primary class for the user's view (like BoardLayerListener)
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;


public class UserView extends JFrame {

    final GameController gc;

    JLayeredPane bPane;

    JLabel boardlabel;
    JLabel[] playerLabels;
    JLabel stats, tileInfo;

    HashMap<Scene, JLabel> sceneCards;

    JButton endTurn, rehearse, act, takeRole, rankUp;
    MouseListener statsPaneListener, boardListener;

    final int WIDTH_CONSTANT = 200;
    final int HEIGH_CONSTANT = 30;
    final int BOARD_WIDTH;
    final int BOARD_HEIGHT;

    /**
     * Constructor for the View
     * @param gc GameController as a parameter, as the View needs to know and be able to communicated
     *           with the GC.
     */
    public UserView(GameController gc) {
        super("Deadwood");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.gc = gc;

        //The main layered pane
        bPane = getLayeredPane();
        boardListener = new BoardMouseListener();
        statsPaneListener = new BoardMouseListener();
        sceneCards = new HashMap<>();

        //Putting the board image onto the pane
        boardlabel = new JLabel();
        ImageIcon icon = new ImageIcon("Assets/board0.jpg"); //936 by 702
        boardlabel.setIcon(icon);
        BOARD_WIDTH = icon.getIconWidth();
        BOARD_HEIGHT = icon.getIconHeight();
        boardlabel.setBounds(WIDTH_CONSTANT, 0, BOARD_WIDTH, BOARD_HEIGHT);
        boardlabel.addMouseListener(boardListener);
        bPane.add(boardlabel, 0);

        //Size of the GUI
        setSize(icon.getIconWidth() + WIDTH_CONSTANT + 10, icon.getIconHeight() + HEIGH_CONSTANT);

        // Begin a series of buttons that allow for certain game actions and commands
        rankUp = new JButton("Rank Up");
        rankUp.setBackground(Color.white);
        rankUp.setBounds(25, icon.getIconHeight() - 300, 150, 50);
        rankUp.addMouseListener(statsPaneListener);
        bPane.add(rankUp, 2);

        takeRole = new JButton("Take Role");
        takeRole.setBackground(Color.white);
        takeRole.setBounds(25, icon.getIconHeight() - 250, 150, 50);
        takeRole.addMouseListener(statsPaneListener);
        bPane.add(takeRole, 2);

        act = new JButton("Act");
        act.setBackground(Color.white);
        act.setBounds(25, icon.getIconHeight() - 200, 150, 50);
        act.addMouseListener(statsPaneListener);
        bPane.add(act, 2);

        rehearse = new JButton("Rehearse");
        rehearse.setBackground(Color.white);
        rehearse.setBounds(25, icon.getIconHeight() - 150, 150, 50);
        rehearse.addMouseListener(statsPaneListener);
        bPane.add(rehearse, 2);

        endTurn = new JButton("End Turn");
        endTurn.setBackground(Color.white);
        endTurn.setBounds(25, icon.getIconHeight() - 100, 150, 50);
        endTurn.addMouseListener(statsPaneListener);
        bPane.add(endTurn, 2);

        //Two labels to display player info and current room and scene info
        stats = new JLabel("Initial Label");
        stats.setBounds(3,10,200,240);
        stats.setFont (stats.getFont ().deriveFont (14.0f));
        bPane.add(stats,new Integer(2));

        tileInfo = new JLabel("Initial Label");
        tileInfo.setBounds(3,250,200,240);
        tileInfo.setFont (tileInfo.getFont ().deriveFont (14.0f));
        bPane.add(tileInfo,new Integer(2));
    }

    /**
     * This class captures mouse and button input to determine what actions to take
     */
    class BoardMouseListener implements MouseListener  { //KeyListener

        public void mouseClicked(MouseEvent e) {

            if (e.getSource() == boardlabel) {
                System.out.println("(" + e.getLocationOnScreen().x + ", " + e.getLocationOnScreen().y + ")");
                //intent is to move or take a role
                //to move, a player clicks once into a room
                //so define some bounds for all rooms
                //for ease we use the Scene card bounds to define room location
                Point location = e.getLocationOnScreen();

                //scan list of rooms to see which one it's inside of
                HashMap<String, Room> rooms = requestRoomsOnBoard();

                for (String key : rooms.keySet()) {
                    Room rm = rooms.get(key);
                    if (isInBounds(location, rm.getArea())) {
                        moveActivePlayer(rm);
                        break;
                    }
                }
            } else if (e.getSource() == takeRole) {
                String desiredRole = JOptionPane.showInputDialog("Which role? (case sensitive)");
                gc.takeRole(desiredRole);
            } else if (e.getSource() == rehearse) {
                gc.rehearse();
            } else if (e.getSource() == act) {
                gc.act();
            } else if (e.getSource() == endTurn) {
                gc.endTurn();
            } else if (e.getSource() == rankUp) {
                Player p = requestActivePlayer();

                if (p.getCurrentRoom() instanceof CastingOffice) {
                    String[] options = {"Credits", "Dollars"};
                    int usingCredits = JOptionPane.showOptionDialog(null,
                            "Pay for rank up with Credits or Dollars?", "Click a button",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                    if (usingCredits == 0) {
                        p.rankUp(true);
                        gc.endTurn();
                    } else {
                        p.rankUp(false);
                        gc.endTurn();
                    }
                }
            }
        }

        public void mousePressed(MouseEvent e) {
        }
        public void mouseReleased(MouseEvent e) {
        }
        public void mouseEntered(MouseEvent e) {
        }
        public void mouseExited(MouseEvent e) {
        }
    }

    private void moveActivePlayer(Room rm) {
        gc.movePlayer(rm);
    }

    /**
     * Determines if the mouse click is within a certain room's bounds or not
     * @param p Point object from mouse click
     * @param area Area of the bounds of the room
     * @return boolean
     */
    private boolean isInBounds(Point p, int[] area) {
        double x = p.getX();
        double y = p.getY();

        if (x >= (area[0] + WIDTH_CONSTANT) && x <= (area[0] + area[2] + WIDTH_CONSTANT)) {
            if (y >= (area[1] + HEIGH_CONSTANT) && y<= (area[1] + area[3] + HEIGH_CONSTANT)) {
                //point is inside room bound,
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the view and redraws players and existing scenes, also removes scenes that have been wrapped
     */
    public void update() {
        drawScenes(requestRoomsOnBoard()); //draw Scenes first so that they're underneath players
        clearPlayers();
        drawPlayers(requestPlayerLocations());
        displayPlayerStats(requestActivePlayer());
        displayCurrentRoomInfo(requestActivePlayer());
        this.show();
    }

    /**
     * Helper function to set the value of a JLabel which displays the active player's current room info
     * @param p Player obj
     */
    private void displayCurrentRoomInfo(Player p) {
        Room rm = p.getCurrentRoom();
        Scene sc = rm.getScene();
        String sName = "N/A";

        if (sc != null) {
            sName = sc.sceneName;
        }

        String tileInfoString = "<html>";
        tileInfoString += "Room: " + rm.roomName + "<br>";
        tileInfoString += "Scene: " + sName + "<br>";
        if (sc != null) {
            tileInfoString += "Available Roles: " + "<br>";
            tileInfoString += "On-Scene Roles:" + sc.roles.keySet() + "<br>Extras: " + rm.extraRoles.keySet();
        }
        tileInfoString += "</html>";
        tileInfo.setText(tileInfoString);
    }

    public void clearScene(Scene sc) {
        bPane.remove(sceneCards.remove(sc));
    }

    private void drawScenes(HashMap<String, Room> rooms) {
        for (String rm : rooms.keySet()) {
            drawScene(rooms.get(rm));
        }
    }

    private void drawScene(Room rm) {
        //These rooms have no scenes
        if (rm instanceof Trailer || rm instanceof CastingOffice) {
            return;
        }

        Scene currentScene = rm.currentScene;
        //if the view determines that the scene is already on the board (ie: inside sceneCards)
        if (sceneCards.containsKey(currentScene)) {
            return;
        }

        if (currentScene != null) {
            int[] coords = determineCoordOffset(rm);

            JLabel label = new JLabel();
            ImageIcon sceneIcon = new ImageIcon(currentScene.filepath);
            label.setIcon(sceneIcon);
            label.setBounds(coords[0], coords[1],
                    sceneIcon.getIconWidth(), sceneIcon.getIconHeight());
            sceneCards.put(currentScene, label);
            bPane.add(label, 3);
            bPane.moveToFront(label);
        }
    }

    private void clearPlayers() {
        for (JLabel player : playerLabels) {
            if (player != null) {
                bPane.remove(player);
                bPane.repaint();
                bPane.revalidate();
            }
        }
    }

    /**
     * @return returns a map of player's locations by room
     */
    private HashMap<Room, ArrayList<Player>> requestPlayerLocations() {
        return gc.getPlayerLocations();
    }

    private HashMap<String, Room> requestRoomsOnBoard() {
        return gc.getRoomsOnBoard();
    }

    /**
     * draws all the players' on the board
     */
    private void drawPlayers(HashMap<Room, ArrayList<Player>> locations) {
        //basically you gotta query the player, what room are they in?
        //and then define places you draw the players in that room (depends on num of players in room. Do 1 first)
        for (Room r : locations.keySet()) {
            ArrayList<Player> playersInRoom = locations.get(r);
            int i = 0;
            for (Player p : playersInRoom) {
                drawPlayer(p, i, 0);
                i += 10;
            }
        }
    }

    /**
     * Draws individual players. Called by drawPlayers()
     * @param p Player obj
     */
    private void drawPlayer(Player p, int xOffset, int yOffset) {

        int[] coords;
        Room currentRoom = p.getCurrentRoom();
        coords = determineCoordOffset(currentRoom);
        int playerNum = p.getPlayerNum();

        playerLabels[playerNum - 1] = new JLabel();
        JLabel label = playerLabels[playerNum - 1];
        ImageIcon playerIcon = new ImageIcon(p.getPlayerIcon());
        label.setIcon(playerIcon);
        label.setBounds(coords[0] + xOffset, coords[1] + yOffset,
                        playerIcon.getIconWidth(), playerIcon.getIconHeight());
        bPane.add(label, 3);
        bPane.moveToFront(label);
    }

    /**
     * This is currently unnecessary and deprecated, but if I wanted to draw
     * with specific offsets this is the function to be modified.
     * @param rm Room a player is going to
     * @return Returns a newly calculated area bound
     */
    private int[] determineCoordOffset(Room rm) {
        //Based on the Room, drawing players will occur in different places
        int[] baseCoords = rm.getArea();
        int x = baseCoords[0];
        int y = baseCoords[1];

        switch (rm.roomName) {
            case "Trailer":
                x += WIDTH_CONSTANT;
                break;
            case "Casting Office":
                x += WIDTH_CONSTANT;
                break;
            case "Main Street":
                x += WIDTH_CONSTANT;
                break;
            case "Saloon":
                x += WIDTH_CONSTANT;
                break;
            case "Bank":
                x += WIDTH_CONSTANT;
                break;
            case "Hotel":
                x += WIDTH_CONSTANT;
                break;
            case "Church":
                x += WIDTH_CONSTANT;
                break;
            case "Jail":
                x += WIDTH_CONSTANT;
                break;
            case "General Store":
                x += WIDTH_CONSTANT;
                break;
            case "Ranch":
                x += WIDTH_CONSTANT;
                break;
            case "Secret Hideout":
                x += WIDTH_CONSTANT;
                break;
            case "Train Station":
                x += WIDTH_CONSTANT;
                break;
        }
        int[] coords = {x, y};
        return coords;
    }

    private ArrayList<Player> requestAllPlayers() {
        return gc.getAllActivePlayers();
    }

    private Player requestActivePlayer() {
        return gc.getActivePlayer();
     }

    public int queryNumPlayers() {
        int numPlayers = Integer.parseInt(JOptionPane.showInputDialog("How many players?"));
        playerLabels = new JLabel[numPlayers];
        return numPlayers;
    }

    /**
     * Sets a JLabel value to display player's info
     * @param p Player obj
     */
    public void displayPlayerStats(Player p) {
        Scene s = p.getCurrentScene();
        Role r = p.getCurrentRole();
        String sName = "", rName = "";

        if (s != null) {
            sName = s.sceneName;
        }
        if (r != null) {
            rName = r.title;
        }

        String info = "<html>";
        info += p.playerName + "'s Stats<br>";
        info += "Rank: " + p.getRank() + "<br>";
        info += "Score: " + p.getScore() + "<br>";
        info += "Credits: " + p.getCredits() + "<br>";
        info += "Dollars: " + p.getDollars() + "<br>";
        info += "Current Room: " + p.getCurrentRoom().getRoomName() + "<br>";
        info += "Current Scene: " + sName + "<br>";
        info += "Current Role: " + rName + "<br>";
        info += "Practice Chips: " + p.getPracticeChips() + "</html>";
        stats.setText(info);
    }
}
