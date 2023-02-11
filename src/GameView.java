// Blackjack Dice Game with GUI by Ruchi Mangtani
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameView extends JFrame {
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 700;
    ArrayList<Die> dieArr;
    private Game game;
    public GameView(Game theGame, ArrayList<Die> arr) {
        // Initializing JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Blackjack Dice Game");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setVisible(true);

        // Initializing other instance variables
        dieArr = arr;
        game = theGame;
    }

    public void paint(Graphics g) {
        // Welcome and instructions printed on window
        g.setFont(new Font("SERIF", Font.PLAIN, 45));
        g.drawString("Welcome to Blackjack Dice!", 50, 130);
        g.setFont(new Font("SERIF", Font.PLAIN, 15));
        g.setColor(Color.black);
        g.drawString("Instructions: You will begin with a random number from a 10-sided dice.", 50, 180);
        g.drawString("You will then have the option to add the minimum or maximum of 3 rolls to the sum.", 50, 200);
        g.drawString("Your goal is to get as close to 21 as possible without going over.", 50, 220);
        g.drawString("Type anything on the keyboard to start the game.", 50, 260);

        /**
         * Clears instructions from the window if the game has started. Draws each dice and the user's total sum.
         * After the game ends, both the user and dealer sum are printed on the window as well as the result of the game.
         */
        if (game.getHasGameStarted()) {
            g.setColor(Color.yellow);
            g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            g.setColor(Color.black);
            if (dieArr.size() == 1) {
                dieArr.get(0).draw(g, 38, 205);
            }
            else if (dieArr.size() == 3) {
                dieArr.get(0).draw(g, -220, 205);
                dieArr.get(1).draw(g, 38, 205);
                dieArr.get(2).draw(g, 275, 205);
            }
            g.setFont(new Font("SERIF", Font.PLAIN, 30));
            g.drawString("User sum: " + game.getSum("user"), 20, 90);
            if (game.getHasGameEnded()) {
                g.drawString("Dealer sum: " + game.getSum("dealer"), 20, 130);
                g.setFont(new Font("SERIF", Font.PLAIN, 20));
                g.setColor(Color.red);
                g.drawString(game.returnWinner(game.getSum("user"),game.getSum("dealer")), 80, 200);
            }
        }

    }
}
