/*

   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane

*/

import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class BoardLayersListener extends JFrame {

  // JLabels
  JLabel boardlabel;
  JLabel cardlabel;
  JLabel playerlabel;
  JLabel mLabel;
  
  //JButtons
  JButton bAct;
  JButton bRehearse;
  JButton bMove;
  //Added by J
  JButton cUpgrade; //to upgrade with credits
  JButton mUpgrade; //to upgrade with money

  static int playerNum;
  //Player[] players;
  
  // JLayered Pane
  JLayeredPane bPane;
  
  // Constructor
  
  public BoardLayersListener() {
      
       // Set the title of the JFrame
       super("Deadwood");
       // Set the exit option for the JFrame
       setDefaultCloseOperation(EXIT_ON_CLOSE);
      
       // Create the JLayeredPane to hold the display, cards, dice and buttons
       bPane = getLayeredPane();
    
       // Create the deadwood board
       boardlabel = new JLabel();
       ImageIcon icon =  new ImageIcon("board.jpg");
       boardlabel.setIcon(icon); 
       boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
      
       // Add the board to the lowest layer
       bPane.add(boardlabel, new Integer(0));
      
       // Set the size of the GUI
       setSize(icon.getIconWidth()+200,icon.getIconHeight());
       
       // Add a scene card to this room
       cardlabel = new JLabel();
       ImageIcon cIcon =  new ImageIcon("01.png");
       cardlabel.setIcon(cIcon); 
       cardlabel.setBounds(20,65,cIcon.getIconWidth()+2,cIcon.getIconHeight());
       cardlabel.setOpaque(true);
      
       // Add the card to the lower layer
       bPane.add(cardlabel, new Integer(1));
       
      
        /*
        copied here to know the locations of these cards (from xml file)

        Train Station
         Crusty Prospector              playerlabel.setBounds(114,227,46,46); lvl 1
         Dragged by Train               playerlabel.setBounds(51,268,46,46); lvl 1
         ROle for Preacher with Bag     playerlabel.setBounds(114,320,46,46); lvl 2
         Role for Cyrus the Gunfighter  playerlabel.setBounds(49,356,46,46); lvl 4

       Secret Hideout
        CLumsy Pit Fighter  playerlabel.setBounds(435,719,46,46); lvl 1
        Thug with Knife     playerlabel.setBounds(521,719,46,46); lvl 2
        Dangerous Tom       playerlabel.setBounds(435,808,46,46); lvl 3
        Penny, who is lost  playerlabel.setBounds(521,808,46,46); lvl 3

       Church
        Dead man        playerlabel.setBounds(857,730,46,46); lvl 1
        Crying Woman    playerlabel.setBounds(858,809,46,46); lvl 2

       Hotel
        Sleeping Drunkard       playerlabel.setBounds(1111,469,46,46); lvl 1
        Faro Player             playerlabel.setBounds(1044,509,46,46); lvl 1
        Falls from Balcony      playerlabel.setBounds(1111,557,46,46); lvl  2
        Australian Bartneder    playerlabel.setBounds(1046,596,46,46); lvl 3

       Main Street
        Railword Worker         playerlabel.setBounds(637,22,46,46); lvl 1
        Falls of Roof           playerlabel.setBounds(720,22,46,46); lvl 2
        Woman in Black Dress    playerlabel.setBounds(637,105,46,46); lvl 2
        Mayor McGinty           playerlabel.setBounds(720,105,46,46); lvl 4

       Jail
        Prisoner In Cell        playerlabel.setBounds(519,25,46,46); lvl 2
        Feller in Irons         playerlabel.setBounds(519,105,46,46); lvl 3

       General Store
        Man in Overalls     playerlabel.setBounds(236,276,46,46); lvl 1
        Mister Keach        playerlabel.setBounds(236,358,46,46); lvl 3

       Ranch
        Shot in Leg     playerlabel.setBounds(412,608,46,46); lvl 1
        Saucy Fred      playerlabel.setBounds(488,525,46,46); lvl 2
        Man Under Horse playerlabel.setBounds(488,525,46,46); lvl 3

       Bank
        Suspicious Gentleman    playerlabel.setBounds(911,470,46,46); lvl 3
        Flustered Teller        playerlabel.setBounds(911,470,46,46); lvl 3

       Saloon
        Reluctant Farmer    playerlabel.setBounds(877,352,46,46); lvl 1
        Woman in Red Dress  playerlabel.setBounds(877,276,46,46); lvl 2
      */

       // Add a dice to represent a player. 
       // Role for Crusty the prospector. The x and y co-ordiantes are taken from Board.xml file
       playerlabel = new JLabel();
       ImageIcon pIcon = new ImageIcon("r2.png");
       playerlabel.setIcon(pIcon);
       //playerlabel.setBounds(114,227,pIcon.getIconWidth(),pIcon.getIconHeight());  
       playerlabel.setBounds(114,227,46,46);
       playerlabel.setVisible(false);
       bPane.add(playerlabel,new Integer(3));
      
       // Create the Menu for action buttons
       mLabel = new JLabel("MENU");
       mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
       bPane.add(mLabel,new Integer(2));

       // Create Action buttons
       bAct = new JButton("ACT");
       bAct.setBackground(Color.white);
       bAct.setBounds(icon.getIconWidth()+10, 30,100, 20);
       bAct.addMouseListener(new boardMouseListener());
       
       bRehearse = new JButton("REHEARSE");
       bRehearse.setBackground(Color.white);
       bRehearse.setBounds(icon.getIconWidth()+10,60,100, 20);
       bRehearse.addMouseListener(new boardMouseListener());
       
       bMove = new JButton("MOVE");
       bMove.setBackground(Color.white);
       bMove.setBounds(icon.getIconWidth()+10,90,100, 20);
       bMove.addMouseListener(new boardMouseListener());

       //added by J
       cUpgrade = new JButton ("Upgrade With Credits");
       cUpgrade.setBackground(Color.green);
       cUpgrade.setBounds(icon.getIconWidth()+10, 120, 100, 20);
       cUpgrade.addMouseListener(new boardMouseListener());

       mUpgrade = new JButton ("Upgrade With Money");
       mUpgrade.setBackground(Color.green);
       mUpgrade.setBounds(icon.getIconWidth()+10, 150, 100, 20);

       // Place the action buttons in the top layer
       bPane.add(bAct, new Integer(2));
       bPane.add(bRehearse, new Integer(2));
       bPane.add(bMove, new Integer(2));
  }
  
  // This class implements Mouse Events
  
  class boardMouseListener implements MouseListener{
  
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {
         
         if (e.getSource()== bAct){
            playerlabel.setVisible(true);
            System.out.println("Acting is Selected\n");
         }
         else if (e.getSource()== bRehearse){
            System.out.println("Rehearse is Selected\n");
         }
         else if (e.getSource()== bMove){
            System.out.println("Move is Selected\n");
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

   //public void createPlayers(Player[] players) {
    //create players based off of playerNum along with their icons
   //}



  public static void main(String[] args) {
  
    BoardLayersListener board = new BoardLayersListener();
    board.setVisible(true);
    
    // Take input from the user about number of players
    String input = (String)JOptionPane.showInputDialog(board, "How many players?");

    Integer playerNum = Integer.parseInt(input);
  }
}