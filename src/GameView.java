import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 700;
    private Game game;
    public GameView(Game game) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Blackjack Dice Game!");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        this.game = game;
    }

    public void paint(Graphics g) {
        g.setFont(new Font("SERIF", Font.PLAIN,15));
        g.drawString("Instructions: You will begin with a random number from a 10-sided dice.", 60, 80);
        g.drawString("You will then have the option to add the minimum or maximum of 3 rolls to the sum.", 60, 100);
        g.setColor(Color.white);
        if (game.getStartGame()) g.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
    }
}
