import java.util.ArrayList;
import java.util.Scanner;
// Blackjack Dice Game with GUI by Ruchi Mangtani
public class Game {
    // Instance variables
    private ArrayList<Die> dieArr;
    private int userSum;
    private int dealerSum;
    private boolean hasGameStarted;
    private boolean hasGameEnded;
    private boolean isPlayersTurn;
    private GameView window;

    // Constructor that initializes the two die and the sum of the user & dealer
    public Game() {

        this.userSum = 0;
        this.dealerSum = 0;
        this.hasGameStarted = false;
        this.isPlayersTurn = false;
        this.hasGameEnded = false;
        this.dieArr = new ArrayList<Die>();
        this.userSum = 0;
        this.window = new GameView(this, dieArr);
    }

    public boolean getHasGameStarted() {
        return hasGameStarted;
    }
    public boolean getIsPlayersTurn() {
        return isPlayersTurn;
    }

    public boolean getHasGameEnded() {
        return hasGameEnded;
    }

    public void setHasGameEnded(boolean hasGameEnded) {
        this.hasGameEnded = hasGameEnded;
    }

    public int getSum(String playerName) {
        if (playerName.equals("User")) {
            return userSum;
        }
        if (playerName.equals("Dealer")) {
            return dealerSum;
        }
        return 0;
    }



    public void printInstructions() {
        System.out.println("You will begin with a random number from a 10-sided dice.");
        System.out.println("You will then have the option to add the minimum " +
                "or maximum of 3 rolls to the sum.");
        System.out.println("Your goal is to get as close to 21 as possible.\n");
    }

    // Method to play the game
    public void playGame() {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to BlackJack Dice!");
        System.out.println("\n");
        this.printInstructions();
        System.out.println("Type anything on the keyboard to start the game");
        input.nextLine();
        hasGameStarted = true;
        isPlayersTurn = true;
        // Clearing the window (removing the instructions from the screen)
        dieArr.add(new Die(10, window));
        window.repaint();
        userSum = dieArr.get(0).roll();
        System.out.println("Rolling dice.... The dice rolled " + userSum + ".");
        dieArr.add(new Die(10, window));
        dieArr.add(new Die(10, window));

        // While loop continuously asks user if they want to get the min or max
        // of three rolls or stay
        // Loop stops when user decides to stay or if the game is over.
        String minOrMax = "";
        while (!minOrMax.equals("stay")) {
            System.out.println("Do you want to get the min or max of 3 rolls " +
                    "or stay? (min/max/stay)");
            minOrMax = input.nextLine();

            if (minOrMax.equals("min")) {
                userSum += getMinRoll();
            }
            else if (minOrMax.equals("max")) {
                userSum += getMaxRoll();
            }
            window.repaint();

            this.printSumRolls(this.userSum, "user");

            if (this.isGameOver(this.userSum))
                break;
        }
        isPlayersTurn = false;
        this.playDealer();
        hasGameEnded = true;
        window.repaint();
        System.out.println(printWinner(this.userSum, this.dealerSum));
    }

    // The dealer's turn
    public void playDealer() {
        this.dealerSum = dieArr.get(0).roll();
        System.out.println("\n");

        // While the dealer's sum is less than 18, roll the dice
        while (this.dealerSum < 18) {

            // Roll the max of 3 rolls if the dealer's sum is less than 12
            if (this.dealerSum < 12) {
                System.out.println("The dealer gets the maximum of 3 rolls.");
                this.dealerSum += getMaxRoll();
            }

            /**
             * Else, the dealer's sum is greater than or equal to 12, so roll
             * the minimum of 3 rolls instead
             */
            else {
                System.out.println("The dealer gets the minimum of 3 rolls.");
                this.dealerSum += getMinRoll();
            }
        }
        this.printSumRolls(this.dealerSum, "dealer");
    }

    public int getMinRoll() {
        int min = 11;
        for (int i = 0; i < dieArr.size(); i++) {
            dieArr.get(i).roll();
            if (dieArr.get(i).getLastRoll() < min) {
                min = dieArr.get(i).getLastRoll();
            }
        }
        return min;
    }

    public int getMaxRoll() {
        int max = 0;
        for (int i = 0; i < dieArr.size(); i++) {
            dieArr.get(i).roll();
            if (dieArr.get(i).getLastRoll() > max) {
                max = dieArr.get(i).getLastRoll();
            }
        }
        return max;
    }

    // Method to print the user or the dealer's sum of their rolls
    public void printSumRolls(int sum, String player) {
        if (player.equals("user")) {
            System.out.println("The sum of the your rolls is " + sum + ".");
        }
        else {
            System.out.println("The sum of the dealer's rolls is " + sum + ".\n");
        }
    }

    // Method to check if the game is over (if the sum is >= 21)
    // Returns true if the game is over
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

    // Method to find and print the winner of the game
    public String printWinner(int userSum, int dealerSum) {
        String winner = "";
        if (this.dealerSum > 21 && this.userSum > 21) {
            return "You and the dealer both lost. No one wins.";
        }
        else if (this.dealerSum > 21) {
            return ("The dealer bust. You win!");
        }
        else if (this.userSum > 21) {
            return ("The dealer wins. Better luck next time.");
        }
        else if (this.dealerSum == 21 && this.userSum == 21) {
            return ("You and the dealer both equal 21. Tie.");
        }
        else if (this.dealerSum == 21) {
            return ("The dealer wins. Better luck next time!");
        }
        else if (this.userSum == 21) {
            System.out.println("You win!");
        }
        else if (this.dealerSum > this.userSum) {
            return ("The dealer is closer to 21. You lost.");
        }
        else if (this.userSum > this.dealerSum) {
            return ("You're closer to 21. You win!");
        }
        return "The sums of both of your rolls are equal. Tie.";

        //return winner;
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.playGame();
    }
}
