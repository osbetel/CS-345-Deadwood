/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-05
 * Time: 16:34
 * Deadwood
 */

import java.util.Map;

public class Scene {
    private int shotsRemaining;

    private Map<String, Role> roles; // We can use it as <Role, payout information>, including bonus payouts
    //MAP IS NOT A CLASS. It's an interface. Choose HashMap or TreeMap (red-black tree) for actual usage

//    private int[] payouts; // [main role, side role, etc...]
//    private int[] bonusPay;

    public Scene() {
        //todo
    }

    public Scene(Map<String, Integer> roles, int shotsRemaining){
        //todo: check rank see if they succeed
    }

    public int removeShot() {
        this.shotsRemaining -= 1;
        return this.shotsRemaining;
    }

    public Role getRole(String r) {
        return roles.get(r);
    }

    public void rehearse(Player actor) {

    }

    private void wrapScene() {
        try {
            if (shotsRemaining > 0) {
                throw new Exception("this scene shouldn't be finished yet!");
            } else {
                //todo: finish the scene, assign payouts, etc, destroy scene (or file it away as used)
            }
        } catch (Exception ex) {
            //todo: do something
        }
    }

    private void payout() {
        //todo: for each role filled in this.roles, payout
    }

    private void payoutBonus() {
        //todo: should only be used upon wrapping the scene
    }



}
