/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-05
 * Time: 16:34
 * Deadwood
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Board {
    private final HashMap<String, Room> rooms;
    private int numOfScenes;

    /**
     * Board constructor. Also constructs the Rooms (Board can't exist without rooms)
     */
    @SuppressWarnings("Check the typecast in the Board constructor if there are errors")
    public Board(HashMap<String, Room> rms) {

        // rms already constructed by ParseXML class, need to connect neighboring Rooms
        for (String r : rms.keySet()) {
            Room rm = rms.get(r);

            if (rm instanceof CastingOffice || rm instanceof Trailer) {
                continue;
            }

            for (String neighbor : rm.neighbors.keySet()) {
                rm.neighbors.put(neighbor, rms.get(neighbor));
            }
        }
        this.rooms = rms;
    }

    /**
     * Deals out scene cards onto the board
     * @param available a list of scene cards that can be used. Will consume up to 10 or less (there's 40 cards)
     */
    public void dealScenes(ArrayList<Scene> available) {

        Random randint = new Random();

        for (String rm : this.rooms.keySet()) {
            if (!(rm.equals("Casting Office") || rm.equals("Trailer"))) {
                if (available.size() > 0) {
                    int nextScene = randint.nextInt(available.size());
                    this.rooms.get(rm).setScene(available.get(nextScene));
                    available.remove(nextScene);
                }
            }
        }
    }

    public int getNumOfScenes() {
        return numOfScenes;
    }

    public void setNumOfScenes(int numOfScenes) {
        this.numOfScenes = numOfScenes;
    }

    /**
     * @return returns the last scene card on the board. We use this to set the payout to nothing for a final scene.
     */
    public Scene getFinalScene() {
        for (String r : rooms.keySet()) {
            Room curr = rooms.get(r);
            if (curr.currentScene != null) {
                return curr.currentScene;
            }
        }
        return null;
    }

    public HashMap<String, Room> getRooms() {
        return rooms;
    }
}
