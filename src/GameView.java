import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameView extends JFrame {
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 700;
    private Game game;
    ArrayList<Die> dieArr;
    public GameView(Game game, ArrayList<Die> dieArr) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Blackjack Dice Game");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        this.game = game;
        this.dieArr = dieArr;
    }

    public void paint(Graphics g) {
        g.setFont(new Font("SERIF", Font.PLAIN, 15));
        g.drawString("Instructions: You will begin with a random number from a 10-sided dice.", 50, 80);
        g.drawString("You will then have the option to add the minimum or maximum of 3 rolls to the sum.", 50, 100);
        g.drawString("Your goal is to get as close to 21 as possible without going over.", 50, 120);
        // TODO: make the welcome slide look better
        // Clear the instructions from the window if the game has started
        if (game.getHasGameStarted()) {
            g.setColor(Color.white);
            g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            g.setColor(Color.black);
            //if (game.getIsPlayersTurn()) {
                if (dieArr.size() == 1) {
                    dieArr.get(0).draw(g, 38, 205);
                } else if (dieArr.size() == 3) {
                    dieArr.get(0).draw(g, -220, 205);
                    dieArr.get(1).draw(g, 38, 205);
                    dieArr.get(2).draw(g, 275, 205);
                }
                g.drawString("User sum: " + game.getSum("User"), 20, 90);
            //}
            if (game.getHasGameEnded()) {
                //g.setFont(new Font("SERIF", Font.PLAIN, 40));
                //g.drawString("User sum: " + game.getSum("User"), 60, 90);
                g.drawString("Dealer sum: " + game.getSum("Dealer"), 20, 130);
                //g.setFont(new Font("SERIF", Font.PLAIN, 20));
                g.drawString(game.printWinner(game.getSum("user"),game.getSum("dealer")), 80, 200);
            }
            // TODO: maybe show all of the dealer's steps
            // TODO: do dealer & user's turn simulatenously??
        }

    }
}
