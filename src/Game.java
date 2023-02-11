// Blackjack Dice Game with GUI by Ruchi Mangtani
import java.util.ArrayList;
import java.util.Scanner;
public class Game {
    private ArrayList<Die> dieArr;
    private Die tenDice;
    private int userSum;
    private int dealerSum;
    private boolean hasGameStarted;
    private boolean hasGameEnded;
    private GameView window;

    public Game() {
        dieArr = new ArrayList<Die>();
        tenDice = new Die(10, window);
        userSum = 0;
        dealerSum = 0;
        hasGameStarted = false;
        hasGameEnded = false;
        window = new GameView(this, dieArr);
    }

    public boolean getHasGameStarted() {
        return hasGameStarted;
    }

    public boolean getHasGameEnded() {
        return hasGameEnded;
    }

    public int getSum(String playerName) {
        if (playerName.equals("user")) {
            return userSum;
        }
        if (playerName.equals("dealer")) {
            return dealerSum;
        }
        return 0;
    }

    public void printInstructions() {
        System.out.println("Welcome to BlackJack Dice!\n");
        System.out.println("You will begin with a random number from a 10-sided dice.");
        System.out.println("You will then have the option to add the minimum " +
                "or maximum of 3 rolls to the sum.");
        System.out.println("Your goal is to get as close to 21 as possible.\n");
    }

    /**
     * Game begins with instructions. The user and the dealer's starting roll is played and 3 dice are added to dieArr for
     * the user's turns. The winner is returned after the user and dealer have done all of their turns.
     */
    public void playGame() {
        Scanner input = new Scanner(System.in);
        printInstructions();
        System.out.println("Type anything on the keyboard to start the game");
        input.nextLine();
        hasGameStarted = true;
        window.repaint();

        dieArr.add(new Die(10, window));
        userSum = dieArr.get(0).roll();
        System.out.println("Rolling dice.... The dice rolled " + userSum + ".");
        dieArr.add(new Die(10, window));
        dieArr.add(new Die(10, window));
        dealerSum = tenDice.roll();

        playUser();
        playDealer();
        hasGameEnded = true;
        window.repaint();
        System.out.println(returnWinner(userSum, dealerSum));
    }

    /**
     * While loop continuously asks user if they want to get the min or max of three rolls or stay.
     * The min or max of the three rolls are added to the user's total sum each iteration of the loop.
     * Loop stops when user decides to stay or if the game is over.
     **/
    public void playUser() {
        Scanner input = new Scanner(System.in);
        String minOrMax = "";

        while (!minOrMax.equals("stay")) {
            System.out.println("Do you want to get the min or max of 3 rolls or stay? (min/max/stay)");
            minOrMax = input.nextLine();
            if ((minOrMax.equals("min")) || (minOrMax.equals("max"))) {
                if (minOrMax.equals("min")) {
                    userSum += minRoll();
                }
                else if (minOrMax.equals("max")) {
                    userSum += maxRoll();
                }

                window.repaint();
                System.out.println("The sum of the your rolls is " + userSum + ".");
                if (isGameOver(userSum))
                    break;
            }
        }
    }

    /**
     * If the dealer's sum is less than 14, the maximum of 3 rolls are added to dealerSum.
     * If the dealer's sum is from 14-17 inclusive, the minimum of 3 rolls are added to dealerSum.
     */
    public void playDealer() {
        while (dealerSum < 18) {
            if (dealerSum < 14) {
                System.out.println("The dealer gets the maximum of 3 rolls.");
                dealerSum += tenDice.getMaxRoll(3);
            }
            else {
                System.out.println("The dealer gets the minimum of 3 rolls.");
                dealerSum += tenDice.getMinRoll(3);
            }
        }
        System.out.println("The sum of the dealer's rolls is " + dealerSum + ".\n");
    }

    /**
     * Each dice in dieArr are rolled.
     * @return the minimum roll.
     */
    public int minRoll() {
        int min = 11;
        for (int i = 0; i < dieArr.size(); i++) {
            dieArr.get(i).roll();
            if (dieArr.get(i).getLastRoll() < min) {
                min = dieArr.get(i).getLastRoll();
            }
        }
        return min;
    }

    /**
     * Each dice in dieArr are rolled.
     * @return the maximum roll.
     */
    public int maxRoll() {
        int max = 0;
        for (int i = 0; i < dieArr.size(); i++) {
            dieArr.get(i).roll();
            if (dieArr.get(i).getLastRoll() > max) {
                max = dieArr.get(i).getLastRoll();
            }
        }
        return max;
    }

     /** Method to check if the game is over (if the sum is >= 21)
     * @param sum
     * @return true if the game is over, false if the game is not over
     */
    public boolean isGameOver(int sum) {
        if (sum > 21) {
            System.out.println("Bust! Your sum is over 21.");
            return true;
        }
        if (sum == 21) {
            return true;
        }
        return false;
    }

     /**
     * @param userSum
     * @param dealerSum
     * @return a String stating the result of the game
     */
    public String returnWinner(int userSum, int dealerSum) {
        if (dealerSum > 21 && userSum > 21) {
            return ("You and the dealer lost. No one wins.");
        }
        if (dealerSum > 21) {
            return ("The dealer bust. You win!");
        }
        if (userSum > 21) {
            return ("The dealer wins. Better luck next time.");
        }
        if (dealerSum == 21 && userSum == 21) {
            return ("You and the dealer both equal 21. Tie.");
        }
        if (dealerSum == 21) {
            return ("The dealer wins. Better luck next time!");
        }
        if (userSum == 21) {
            return ("You win!");
        }
        if (dealerSum > userSum) {
            return ("The dealer is closer to 21. You lost.");
        }
        if (userSum > dealerSum) {
            return ("You're closer to 21. You win!");
        }
        return "The sums of both of your rolls are equal. Tie.";
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.playGame();
    }
}
