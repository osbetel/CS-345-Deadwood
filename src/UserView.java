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


public class UserView extends JFrame {

    JLayeredPane bPane;

    JLabel boardlabel;
    JLabel statsInfo;
    JLabel rules;
    JLabel parts;

    JButton stats;

    //changing background color
    //system print out on screen JFrame
    //player stats system print

    public UserView() {
        // Set the title of the JFrame
        super("Deadwood");
        // Set the exit option for the JFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create the JLayeredPane to hold the display, cards, dice and buttons
        bPane = getLayeredPane();

        // Create the deadwood board
        boardlabel = new JLabel();
        ImageIcon icon = new ImageIcon("board0.jpg"); //936 by 702
        boardlabel.setIcon(icon);
        boardlabel.setBounds(150, 0, icon.getIconWidth(), icon.getIconHeight());

        // Add the board to the lowest layer
        bPane.add(boardlabel, new Integer(0));

        // Set the size of the GUI
        setSize(icon.getIconWidth() + 150, icon.getIconHeight());

        //creates action button
        stats = new JButton("Player Stat");
        stats.setBackground(Color.white);
        stats.setBounds(25, 341,100, 20); //x: icon.getIconWidth()+10
        stats.addMouseListener(new boardMouseListener());

        //adds button onto upper layer
        bPane.add(stats, new Integer(2));

        //Creats player stats info onto board
        statsInfo = new JLabel("Here's your player info....");
        statsInfo.setBounds(3,365,125,20);
        //mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
        statsInfo.setVisible(false);
        bPane.add(statsInfo,new Integer(2));

        //creats rules at the top
        //can edit which rules to display
        rules = new JLabel("<html><u>Welcome to Deadwood</u><br/>Here's some of the few basic rules<br/> -Can only move to adjacent rooms <br/>-In a room, you can be an extra or a lead <br/> Can add more </html>", SwingConstants.CENTER);
        rules.setBounds(3,10,125,160);
        //mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
        bPane.add(rules,new Integer(2));

        //defines act, rehearse, move
        parts = new JLabel("<html><b>Act:</b> *insert acting rules <br/><b>Rehearse:</b> *insert rehearsing rules*<br/><b> Move: </b> Can choose to move to an adjacent room & take a role</html>");
        parts.setBounds(3,160,125,150);
        //mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
        bPane.add(parts,new Integer(2));

    }


    class boardMouseListener implements MouseListener {

        // Code for the different button clicks
        public void mouseClicked(MouseEvent e) {

            if (e.getSource()== stats){
                statsInfo.setVisible(true);
                // System.out.println("Here's your player info....\n Money: " + Player.getScore() + "\n ");
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

    public static void main(String[] args) {

        UserView board = new UserView();
        board.setVisible(true);

        // Take input from the user about number of players
        String input = (String)JOptionPane.showInputDialog(board, "How many players?");

        Integer playerNum = Integer.parseInt(input);
  }
}
