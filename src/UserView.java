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

    static GameController gc;

    JLayeredPane bPane;

    JLabel boardlabel;
    JLabel statsInfo;
    JLabel rules;
    JLabel parts;
    JLabel[] playerLabels;
    JLabel[] activeScenes;
    //used for displaying player info in displayPlayerInfo()
    JLabel[] playerInfo = {new JLabel("Player 1"), new JLabel("Player 2"), new JLabel("Player 3"), new JLabel("Player 4"), new JLabel("Player 5"), new JLabel("Player 6"), new JLabel("Player 7"), new JLabel("Player 8")};;

    JButton stats, endTurn;


    final int WITDH_CONSTANT = 200;
    final int HEIGH_CONSTANT = 30;

    static int numPlayers;


    //changing background color
    //system print out on screen JFrame
    //player stats system print

    public UserView(GameController gc) {
        // Set the title of the JFrame
        super("Deadwood");
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        this.gc = gc;


        // Create the JLayeredPane to hold the display, cards, dice and buttons
        bPane = getLayeredPane();

        // Create the deadwood board
        boardlabel = new JLabel();
        ImageIcon icon = new ImageIcon("Assets/board0.jpg"); //936 by 702
        boardlabel.setIcon(icon);
        boardlabel.setBounds(WITDH_CONSTANT, 0, icon.getIconWidth(), icon.getIconHeight());

        // Add the board to the lowest layer
        bPane.add(boardlabel, 0);

        // Set the size of the GUI
        setSize(icon.getIconWidth() + WITDH_CONSTANT + 10, icon.getIconHeight() + HEIGH_CONSTANT);

        //creates action buttons
        //delete for final draft
//        stats = new JButton("Player Stat");
//        stats.setBackground(Color.white);
//        stats.setBounds(25, 341,100, 20); //x: icon.getIconWidth()+10
//        stats.addMouseListener(new boardMouseListener());

        endTurn = new JButton("End Turn");
        endTurn.setBackground(Color.white);
        endTurn.setBounds(25, 25, 100, 20);
        endTurn.addMouseListener(new boardMouseListener());


        //adds button onto upper layer
        //bPane.add(stats, 2); //delete for final draft
        bPane.add(endTurn, 2);


        //Creates player stats info onto board
        //delete
//        statsInfo = new JLabel("2d");
//        statsInfo.setBounds(3,520,125,20);
//        //mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
//        statsInfo.setVisible(false);
//        bPane.add(statsInfo,2);

        //creates rules at the top
        //can edit which rules to display
//        rules = new JLabel("<html><u>List Of Commands</u><br/>Help, Endturn, Move, Whereami, Whoami,    Getdollars <br/>Getredits,    Listrooms,   Listroles, Currentscene, Currentroom, Takerole, Currentday, Rehearse, Score, Act </html>", SwingConstants.CENTER);
//        rules.setBounds(3,10,125,160);
//        rules.setFont (rules.getFont ().deriveFont (11.0f));
//        bPane.add(rules,new Integer(2));

        //defines act, rehearse, move
//        parts = new JLabel("<html><b>Act:</b> *insert acting rules <br/><b>Rehearse:</b> *insert rehearsing rules*<br/><b> Move: </b> Can choose to move to an adjacent room & take a role</html>");
//        parts.setBounds(3,160,125,150);
//        //mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
//        bPane.add(parts,2);

    }

    class boardMouseListener implements MouseListener  { //KeyListener

        //use playerLabel.setText (String)
        // Code for the different button clicks
        public void mouseClicked(MouseEvent e) {

            //still have for testing but won't be used in final
            if (e.getSource() == stats) {
               // displayPlayerStats(requestActivePlayer());
                displayerPlayerInfo(requestAllPlayers(), numPlayers);
            } else if (e.getSource() == endTurn) {
                //drawPlayers(requestPlayerLocations());
                //where end turn method would go

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

    public void update() {
        drawScenes(requestRoomsOnBoard()); //draw Scenes first so that they're underneath players
        drawPlayers(requestPlayerLocations());
    }

    private void drawScenes(HashMap<String, Room> rooms) {
        for (String rm : rooms.keySet()) {
            drawScene(rooms.get(rm));
        }
    }

    private void drawScene(Room rm) {
        if (rm instanceof Trailer || rm instanceof CastingOffice) {
            return;
        }

        Scene currentScene = rm.currentScene;
        if (currentScene != null) {
            int[] coords = determineCoordOffset(rm);

            JLabel label = new JLabel();
            ImageIcon sceneIcon = new ImageIcon(currentScene.filepath);
            label.setIcon(sceneIcon);
            label.setBounds(coords[0], coords[1],
                    sceneIcon.getIconWidth(), sceneIcon.getIconHeight());
            bPane.add(label, 3);
            bPane.moveToFront(label);
        }
    }

    private HashMap<Room, ArrayList<Player>> requestPlayerLocations() {
        return gc.getPlayerLocations();
    }

    private HashMap<String, Room> requestRoomsOnBoard() {
        return gc.getRoomsOnBoard();
    }

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

    //draws player icons onto the board
    private void drawPlayer(Player p, int xOffset, int yOffset) {
        Room currentRoom = p.getCurrentRoom();
        int[] coords = determineCoordOffset(currentRoom);
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

    private int[] determineCoordOffset(Room rm) {
        //Based on the Room, drawing players will occur in different places
        int[] baseCoords = rm.getArea();
        int x = baseCoords[0];
        int y = baseCoords[1];

        switch (rm.roomName) {
            case "Trailer":
                x += WITDH_CONSTANT;
                break;
            case "Casting Office":
                x += WITDH_CONSTANT;
                break;
            case "Main Street":
                x += WITDH_CONSTANT;
                break;
            case "Saloon":
                x += WITDH_CONSTANT;
                break;
            case "Bank":
                x += WITDH_CONSTANT;
                break;
            case "Hotel":
                x += WITDH_CONSTANT;
                break;
            case "Church":
                x += WITDH_CONSTANT;
                break;
            case "Jail":
                x += WITDH_CONSTANT;
                break;
            case "General Store":
                x += WITDH_CONSTANT;
                break;
            case "Ranch":
                x += WITDH_CONSTANT;
                break;
            case "Secret Hideout":
                x += WITDH_CONSTANT;
                break;
            case "Train Station":
                x += WITDH_CONSTANT;
                break;
        }
        int[] coords = {x, y};
        return coords;
    }

    //request all of the players (used in displayPlayerInfo())
    private Player[] requestAllPlayers() {
        return gc.getAllActivePlayers();
    }

    private Player requestCurrentPlayer() {
        return gc.getActivePlayer();
     }

     private Player initEndTurn(Player[] players) {
        return gc.endTurn(requestAllPlayers());
    }

    //something from this was deleted, but I'm not sure what it was (-.-)
    private Player initMove(Player currPlayer) {
        return gc.
    }

    public int queryNumPlayers() {
        int numPlayers = Integer.parseInt(JOptionPane.showInputDialog("How many players?"));
        playerLabels = new JLabel[numPlayers];
        return numPlayers;
    }

    //used to display one player's info
    //delete for final draft
//    public void displayPlayerStats(Player p) {
//        String info = "Here's ";
//        info += p.playerName + " player \n information Rank: ";
//        info += p.getRank() + "\n Score: ";
//        info += p.getScore() + "\n Credits: ";
//        info += p.getCredits() + "\n Dollars: ";
//        info += p.getDollars() + "\n";
//        statsInfo.setText(info);
//        //statsInfo.setHorizontalAlignment(JTextField.TOP);
//    }



    public void displayerPlayerInfo (Player[] p, int playerNum) {
        int numPlayers = p.length;

        for (int i = 0; i < numPlayers; i++) {
            playerInfo[i].setHorizontalTextPosition(JLabel.LEFT);
            playerInfo[i].setVerticalTextPosition(JLabel.TOP);
            //for the current player
            if(i == playerNum && playerNum >= 0) {
                String info = "It's ";
                info += p[i].playerName + " turn \n information Rank: ";
                info += p[i].getRank() + "\n Score: ";
                info += p[i].getScore() + "\n Credits: ";
                info += p[i].getCredits() + "\n Dollars: ";
                info += p[i].getDollars() + "\n";
                playerInfo[i].setText(info);
            }
            else {
                String info = "Here's ";
                info += p[i].playerName + " player information \n Rank: ";
                info += p[i].getRank() + "\n Score: ";
                info += p[i].getScore() + "\n Credits: ";
                info += p[i].getCredits() + "\n Dollars: ";
                info += p[i].getDollars() + "\n";
                playerInfo[i].setText(info);
            }
        }
        //to remove players not playing
        for(; numPlayers < 8; numPlayers++){
            playerInfo[numPlayers].setText("");
        }
    }

}
