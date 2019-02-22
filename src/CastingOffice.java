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
        this.dollarCosts = new int[] {0, 0, 4, 10, 18, 28, 40}; //0 in the 0th and 1st place in the list so price can correlate with index
        this.creditCost = new int[] {0, 0, 5, 10, 15, 20, 25};
    }

}
