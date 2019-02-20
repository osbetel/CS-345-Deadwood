/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-07
 * Time: 13:00
 * Deadwood
 */

public class CastingOffice extends Room {

    int[] dollarCosts;
    int[] creditCost;

    public CastingOffice() {
        super("Casting Office", 0, 0);
        this.dollarCosts = new int[] {2,3,4,5,6}; //todo: Just put the costs in later
    }

}
