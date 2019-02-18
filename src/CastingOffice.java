/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-07
 * Time: 13:00
 * Deadwood
 */

public class CastingOffice extends Room {

    int[] rankCosts;

    public CastingOffice() {
        super("Casting Office", 0, 0);
        this.rankCosts = new int[] {1,2,3,4,5,6}; //todo: Just put the costs in later
    }

//    public void rankUp(Player player, int currencyChoice) {
//        if (currencyChoice == 0) {
//            //rank up and deduct credits
//        } else {
//            //rank up and deduct money
//        }
//    }
}
