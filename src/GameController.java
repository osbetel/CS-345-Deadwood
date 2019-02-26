/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-07
 * Time: 22:20
 * Deadwood
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class GameController {

    private ArrayList<Scene> scenes;
    private Board board;
    private Player[] players;
    private int daysToPlay;

    /**
     *
     * @param numPlayers
     */
    public GameController(int numPlayers) {

        this.board = new Board(numPlayers);
        this.scenes = createScenes();
        this.players = makePlayers(numPlayers);

        //TESTING CODE
        System.out.println();
        GameControllerTest.testAll(players, board, scenes);
//        System.out.println("input num of players: " + numPlayers);
//        System.out.println("number of players made: " + players.length);
//        System.out.println("number of days to play: " + daysToPlay);
//        System.out.println("player names: ");
//        for (Player p  : players) {
//            System.out.println(p.playerName);
//        }
//        System.out.println();
    }

    /**
     * Reads in scenes.txt and creates all 40 Scene cards, with proper budget values, Roles, etc.
     * See the Scenes class for more detail on the definition of a Scene.
     * @return Returns an ArrayList of all the Scene cards
     */
    public ArrayList<Scene> createScenes() {
        // This should read in a text file and generate all the scenes,
        // Place them into an array and return it to the game controller
        scenes = new ArrayList<>();

        try {
            File sceneFile = new File("Assets/scenes.txt");
            Scanner sc = new Scanner(sceneFile);
            sc.nextLine();

            while (sc.hasNextLine()) {
                String[] sceneData = sc.nextLine().split(";");
                int sceneNum = Integer.parseInt(sceneData[0].strip());
                String sceneTitle = sceneData[1].strip();
                int sceneBudget = Integer.parseInt(sceneData[2].strip());

                HashMap<String, Role> roles = new HashMap<>();

                for (int j = 3; j < sceneData.length; j += 2) {
                    Role r = new Role(sceneData[j].strip(),
                            Integer.parseInt(sceneData[j + 1].strip()),
                            true);
                    roles.put(sceneData[j].strip(), r);
                }
                scenes.add(new Scene(sceneNum, sceneTitle, sceneBudget, roles));
            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        return scenes;
    }

    /**
     * Creates num Players and puts them in an Array for use by the GameController
     * @param num Parameter is the number of Players in this game session. Easy.
     * @return Return an Array, Player[num]
     */
    private Player[] makePlayers(int num) {
        int startingMoney = 0;
        int startingCredits = 0;
        int startingRank = 1;
        int numOfDays = 4;
        Room startingRoom = board.getRooms().get("Trailer");

        //Determining potential special rules
        if (isYBetweenXAndZ(2, num, 3)) {
            numOfDays = 3;
        } else if (num == 4) {
            // do nothing
        } else if (num == 5) {
            startingCredits = 2;
        } else if (num == 6) {
            startingCredits = 4;
        } else if (isYBetweenXAndZ(7, num, 8)) {
            startingRank = 2;
        }


        // Now actually create num players
        Player[] newPlayers = new Player[num];
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < num; i++) {
            System.out.println("Please type player " + (i+1) + "'s name: ");
            String name = sc.nextLine();
            newPlayers[i] = new Player(name, startingRank, startingMoney, startingCredits, startingRoom);
        }

        this.daysToPlay = numOfDays;
        return newPlayers;
    }


    private boolean isYBetweenXAndZ(int X, int Y, int Z) {
        return X <= Y && Y <= Z;
    }


    private boolean parseInput(Player p, Scanner sc, String[] commands, String action,
                               int currentDay, boolean hasMoved, boolean hasTakenRole) {
        switch (action) {

            case "help":
                System.out.println("Here is a list of valid commands: " + Arrays.toString(commands));
                break;

            case "endturn":
                return true;

            case "move":    //todo: if a player types in the wrong case it breaks via null pointer ex
                System.out.print("Available rooms: ");
                String avrms = "";
                for (String rm : board.getRooms().keySet()) {
                    avrms += (rm + ", ");
                }
                System.out.println(avrms.strip());
                System.out.println("What room would you like to move to? : ");
                Room target = board.getRooms().get(sc.nextLine()); //todo need to add error checking for Assignment 3
                p.move(target);
                System.out.println("Moved to: " + target.roomName);
                hasMoved = true;
                if (target.currentScene != null) {
//                    System.out.println("Current Scene: " + target.currentScene.sceneName);
                    p.setCurrentScene(target.currentScene);
                }
                break;

            case "whereami":
                System.out.println("Current room: " + p.getCurrentRoom().roomName);
                break;

            case "whoami":
                System.out.println("Current player: " + p.playerName);
                break;

            case "getdollars":
                System.out.println("You have " + p.getDollars() + " dollars available.");
                break;

            case "getcredits":
                System.out.println("You have " + p.getCredits() + " credits available.");
                break;

            case "listrooms":
                System.out.print("All rooms: ");
                String allrm = "";
                for (String rm : board.getRooms().keySet()) {
                    allrm += (rm + ", ");
                }
                System.out.println(allrm.strip());
                break;

            case "listroles":
                HashMap<String, Role> mains = p.getCurrentScene().roles;
                HashMap<String, Role> extras = p.getCurrentRoom().extraRoles;

                String mainOut = "Main Roles: [";
                String extraOut = "Extra Roles: [";

                for (String mainKey : mains.keySet()) {
                    mainOut += mains.get(mainKey).title +", ";
                }

                for (String extraKey : extras.keySet()) {
                    extraOut += extras.get(extraKey).title + ", ";
                }

                System.out.println(mainOut.strip() + "]");
                System.out.println(extraOut.strip() + "]");
                break;

            case "currentscene":
                if (p.getCurrentScene() != null) {
                    System.out.println("The current Scene is: " + p.getCurrentScene());
                } else {
                    System.out.println("You are currently in the: " + p.getCurrentRoom().roomName + "," +
                            " which has no scenes.");
                }
                break;

            case "takerole":
                if (p.getCurrentScene() != null) {
                    System.out.println("Your current Scene is: " + p.getCurrentScene().sceneName);
                    System.out.print("Available roles: ");
                    HashMap<String, Role> roleMap = p.getCurrentScene().roles;
                    System.out.println(roleMap.keySet().toString());

                    System.out.println("What role would you like to take? : ");
                    Role take = roleMap.get(sc.nextLine());
                    p.setCurrentRole(take);
                    System.out.println("Took role: " + take.title);
                    hasTakenRole = true;
                } else {
                    System.out.println("This room has no Scenes or Roles.");
                }
                break;

            case "currentday":
                System.out.println("Current day: " + currentDay);
                break;

            default:
                System.out.println("Here is a list of valid commands: " + Arrays.toString(commands));
                break;
        }
        return hasMoved && hasTakenRole;
    }


    public void playgame() {

        Scanner sc = new Scanner(System.in);
        String[] commands = {"help", "endturn", "move", "whereami", "whoami",
                 "getdollars", "getcredits", "listrooms", "listroles", "currentscene",
                 "takerole", "currentday"};

        for (int currentDay = daysToPlay; currentDay > 0; currentDay--) {

            //DEAL OUT SCENES AT START OF A NEW DAY
            board.newDay(scenes);

            //for each players' turn
            for (Player p : players) {
                boolean endTurn = false;
                boolean hasMoved = false;
                boolean hasTakenRole = !(p.getCurrentRole() == null);

                System.out.println("========== It is " + p.playerName + "'s turn. ==========");
                System.out.print("Please input an action (type \"help\"): ");
                while (!endTurn) {
                    String action = sc.nextLine().toLowerCase();

                    endTurn = parseInput(p, sc, commands, action, currentDay, hasMoved, hasTakenRole);

                    System.out.println(); //a gap between the next block of input for the same player (or next one)
                }
            }

        }
    }
}
