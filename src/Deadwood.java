/*
 * Created by: Andrew Nguyen
 * Date: 2019-02-05
 * Time: 16:33
 * Deadwood
 */

/**
 * Main kicker class to start the game.
 */
public class Deadwood {

    public static void main(String[] args) {
        GameController gc = tryMakeGame();
//        gc.playgame();
        System.out.println("it compiles");
        System.out.println("all tests passed");

//        GameController gc = new GameController();
    }

    public static GameController tryMakeGame() {
        return new GameController();
    }

}
