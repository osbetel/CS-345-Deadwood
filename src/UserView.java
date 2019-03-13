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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;



public class UserView extends JFrame {

    static GameController gc;

    JLayeredPane bPane;

    JLabel boardlabel;
    JLabel statsInfo;
    JLabel rules;
    JLabel parts;
    JLabel[] playerLabels;

    JButton stats;
    JButton move;
    JButton act;

    ImageIcon[] playerIm;

    static int numPlayers;

    JTextField textField = new JTextField(0);
    String textContent;



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
        boardlabel.setBounds(150, 0, icon.getIconWidth(), icon.getIconHeight());

        // Add the board to the lowest layer
        bPane.add(boardlabel, 0);

        // Set the size of the GUI
        setSize(icon.getIconWidth() + 150, icon.getIconHeight());

        //creates action button
        stats = new JButton("Player Stat");
        stats.setBackground(Color.white);
        stats.setBounds(25, 341,100, 20); //x: icon.getIconWidth()+10
        stats.addMouseListener(new boardMouseListener());

        move = new JButton("Move");
        move.setBackground(Color.white);
        move.setBounds(25, 366,100, 20); //x: icon.getIconWidth()+10
        move.addMouseListener(new boardMouseListener());

        act = new JButton("Act");
        act.setBackground(Color.white);
        act.setBounds(25, 388,100, 20); //x: icon.getIconWidth()+10
        act.addMouseListener(new boardMouseListener());


        //text for user input
        textField.setBounds(3,415,145,100);
        textContent = textField.getText();
        //textField.setToolTipText("Please enter some text here");
        textField.setToolTipText("<html><b><font color=red>"
                + "Please enter a command here" + "</font></b></html>");
        textField.setSelectionColor(Color.YELLOW);
        textField.setSelectedTextColor(Color.RED);
        textField.setHorizontalAlignment(JTextField.CENTER);



        //button for move
        //button for
        //GameC
        //Execute command button

        //adds button onto upper layer
        bPane.add(stats, new Integer(2));
        bPane.add(act, new Integer(2));
        bPane.add(move, new Integer(2));
        bPane.add(textField, new Integer (2));
        //Creates player stats info onto board
        statsInfo = new JLabel();
        statsInfo.setBounds(3,520,125,170);
        //mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
        statsInfo.setVisible(false);
        bPane.add(statsInfo,new Integer(2));

        //creats rules at the top
        //can edit which rules to display
        rules = new JLabel("<html><u>List Of Commands</u><br/>Help, Endturn, Move, Whereami, Whoami,    Getdollars <br/>Getredits,    Listrooms,   Listroles, Currentscene, Currentroom, Takerole, Currentday, Rehearse, Score, Act </html>", SwingConstants.CENTER);
        rules.setBounds(3,10,125,160);
        rules.setFont (rules.getFont ().deriveFont (11.0f));
        bPane.add(rules,new Integer(2));
        //"help", "endturn", "move", "whereami", "whoami",
        //                 "getdollars", "getcredits", "listrooms", "listroles", "currentscene",
        //                 "takerole", "currentday", "rehearse", "score","act", "currentrole"};

        //defines act, rehearse, move
        parts = new JLabel("<html><b>Act:</b> *insert acting rules <br/><b>Rehearse:</b> *insert rehearsing rules*<br/><b> Move: </b> Can choose to move to an adjacent room & take a role</html>");
        parts.setBounds(3,160,125,150);
        //mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
        bPane.add(parts,new Integer(2));

    }


    class boardMouseListener implements MouseListener  { //KeyListener


        //use playerLabel.setText (String)
        // Code for the different button clicks
        public void mouseClicked(MouseEvent e) {

            if (e.getSource() == stats) {
                displayPlayerStats(requestActivePlayer());
                //statsInfo.setVisible(true); //not needed for presentation tomorrow
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

//        public void keyPressed(KeyEvent e) {
//            int key = e.getKeyCode();
//            if (key == KeyEvent.VK_M ) {
//                //Player.move(roomchoice/*location det by room chosen*/);
//            }
//        }
    }

    private Player requestActivePlayer() {
        return gc.getActivePlayer();
    }

    public void displayPlayerStats(Player p) {
        String info = "Here's ";
        info += p.playerName + " player \n information Rank: ";
        info += p.getRank() + "\n Score: ";
        info += p.getScore() + "\n Credits: ";
        info += p.getCredits() + "\n Dollars: ";
        info += p.getDollars() + "\n";
        statsInfo.setText(info);
    }

    public void playerIcons (Player[] players) {
        int numPlayers = players.length;
        playerIm = new ImageIcon[numPlayers];
        String image;

        for (int i = 0; i < numPlayers; i++) {
            image = "b" + Math.random() * ( 0 - 6 );
            playerIm[i] = new ImageIcon(image);
        }

    }

    public void boardPlayerIcons () {
        numPlayers = playerIm.length;
    }


    public static void main(String[] args) {
        //GameController gc;
        //Player person = new Player();

        UserView board = new UserView(gc);
        board.setVisible(true);

        // Take input from the user abouty number of players
        String input = (String)JOptionPane.showInputDialog(board, "How many players?");

        Integer playerNum = Integer.parseInt(input);
  }
}
