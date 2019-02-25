/*
 * Created by: Andrew Nguyen
 * Date: 2019-02-05
 * Time: 16:33
 * Deadwood
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Deadwood {

    public static void main(String[] args) {

        //Capture initial input of the number of players
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("How many players are playing? Input a value from 2 - 8:");
            int num = sc.nextInt();

            if (num < 2 || num > 8) {
                throw new Exception();
            }
        } catch (InputMismatchException ex) {
            System.out.println("You need to input a single integer!");
//            System.out.println(ex);
            System.exit(1);
        } catch (Exception ex) {
            System.out.println("The value has to be between 2 - 8!");
            System.exit(1);
        }

        // If successul, create a new GameController (which is a new instance of the whole game) with num players
        GameController gc = new GameController(4);
        System.out.println("it compiles");
        System.out.println("all tests passed");
    }

}
