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

    JButton stats;


    public UserView() {
        // Set the title of the JFrame
        super("Deadwood");
        // Set the exit option for the JFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create the JLayeredPane to hold the display, cards, dice and buttons
        bPane = getLayeredPane();

        // Create the deadwood board
        boardlabel = new JLabel();
        ImageIcon icon = new ImageIcon("board.jpg"); //936 by 702
        boardlabel.setIcon(icon);
        boardlabel.setBounds(250, 0, icon.getIconWidth(), icon.getIconHeight());

        // Add the board to the lowest layer
        bPane.add(boardlabel, new Integer(0));

        // Set the size of the GUI
        setSize(icon.getIconWidth() + 250, icon.getIconHeight());

        //creates action button
        stats = new JButton("Player Stat");
        stats.setBackground(Color.white);
        stats.setBounds(10, 341,100, 20); //x: icon.getIconWidth()+10
        stats.addMouseListener(new boardMouseListener());

        //adds button onto upper layer
        bPane.add(stats, new Integer(2));
    }


    class boardMouseListener implements MouseListener {

        // Code for the different button clicks
        public void mouseClicked(MouseEvent e) {

            if (e.getSource()== stats){
                //playerlabel.setVisible(true);
                System.out.println("Here's your player info....\n");
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
