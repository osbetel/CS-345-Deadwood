/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-07
 * Time: 13:00
 * Deadwood
 */

public class CastingOffice extends Room {

    public final int[] dollarCost;
    public final int[] creditCost;

    public CastingOffice() {
        super("Casting Office", 0, null);
        this.dollarCost = new int[] {4, 10, 18, 28, 40};
        this.creditCost = new int[] {5, 10, 15, 20, 25};
    }

}
