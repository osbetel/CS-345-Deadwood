
import java.util.*;

/**
 * Scene class represents a Scene card.
 */
public class Scene {
    public final String sceneName;
    public final String filepath;
    public final int budget;
    public final int sceneNumber;
    public final String text;
    public final HashMap<String, Role> roles;   //Remember that Rooms and Scenes each have their own Roles

    public boolean payout, wrapped;
    public ArrayList<Player> playersInScene;

    public Scene(String sceneName, String filepath, int budget,
                 int sceneNumber, String text, HashMap<String, Role> roles) {
        this.sceneName = sceneName;
        this.filepath = filepath;
        this.budget = budget;
        this.sceneNumber = sceneNumber;
        this.text = text;
        this.roles = roles;

        payout = true; //all scenes payout except the last scene of the day
        wrapped = false;
        playersInScene = new ArrayList<>(); //empty at first
    }


    public Role getRole(String r) {
        return roles.get(r);
    }


    /**
     * Called by Players. Lists the available Roles for them to take.
     */
    public void listRoles() {
        System.out.println("Available roles are: ");
        for (String key : roles.keySet()) {
            System.out.println(roles.get(key));
        }
    }

    /**
     * Scenes keep track of which Players are performing on them
     * in order to deal with paying out bonuses.
     * @param ply Player object
     */
    public void addPlayerOnScene(Player ply) {
        playersInScene.add(ply);
    }

    /**
     * Finishes the Scene. Pays out the standard sum and then pays out bonuses.
     */
    public void wrapScene() {

        ArrayList<Player> onScene = new ArrayList<>(), offScene = new ArrayList<>();

        if (payout) {   //if payout is false then it is the last scene of the day

            int[] rolls = new int[budget];
            for (int i = 0; i < rolls.length; i++) {
                rolls[i] = rollDie();
            }
            Arrays.sort(rolls);

            onScene = new ArrayList<>();
            offScene = new ArrayList<>();

            for (Player p : playersInScene) {
                if (p.getCurrentRole().isMainRole()) {
                    onScene.add(p);
                } else {
                    offScene.add(p);
                }
            }

            if (onScene.size() > 0) {
                int i = 0;
                while (i < rolls.length) {
                    for (Player p : onScene) {
                        p.addDollars(rolls[i]);
                        i += 1;
                        if (i >= rolls.length) {
                            break;
                        }
                    }
                }
            }
        }   //end if payout

        for (Player p : offScene) {
            p.addDollars(2);
            p.setCurrentRole(null);
            p.setCurrentScene(null);
            p.setHasTakenRole(false);
            p.setPracticeChips(0);
        }

        for (Player p : onScene) {
            p.setCurrentRole(null);
            p.setCurrentScene(null);
            p.setHasTakenRole(false);
            p.setPracticeChips(0);
        }

        wrapped = true; //this tells the game controller to remove the scene from the board
        System.out.println("Scene \"" + sceneName + "\" wrapped!");
        System.out.println("Bonuses paid!");
    }

    /**
     * Generates a random number for a die roll
     * @return
     */
    private int rollDie() {
        Random r = new Random();
        return r.nextInt(6) + 1;
    }

    public boolean isWrapped() {
        return wrapped;
    }

    public String getSceneName() {
        return sceneName;
    }

    /**
     * For use if this is the last Scene on the board for a given day.
     * @param payout boolean
     */
    public void setPayout(boolean payout) {
        this.payout = payout;
    }

}
