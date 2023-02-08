import java.util.ArrayList;
import java.util.Scanner;
// Blackjack Dice Game with GUI by Ruchi Mangtani
public class Game {
    // Instance variables
    private Die tenDice;
    private Die sixDice;
    private ArrayList<Die> dieArr;
    private int userSum;
    private int dealerSum;
    private boolean startGame;

    private GameView window;

    // Constructor that initializes the two die and the sum of the user & dealer
    public Game() {
        this.tenDice = new Die(10, window);
        this.sixDice = new Die();
        this.userSum = 0;
        this.dealerSum = 0;
        this.startGame = false;
        this.dieArr = new ArrayList<Die>();
        userSum = 0;
        this.window = new GameView(this, dieArr);
    }

    public boolean getStartGame() {
        return startGame;
    }

    public int getUserSum() {
        return userSum;
    }

    // Method to play the game
    public void playGame() {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to BlackJack Dice!");
        System.out.println("\n");
        this.printLevelOneInstructions();
        System.out.println("Type anything on the keyboard to start the game");
        input.nextLine();
        startGame = true;
        // Clearing the window (removing the instructions from the screen)
        window.repaint();
        dieArr.add(new Die(10, window));
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
                this.addMinRoll();
            }
            else if (minOrMax.equals("max")) {
                this.addMaxRoll();
            }
            window.repaint();

            this.printSumRolls(this.userSum, "user");

            if (this.isGameOver(this.userSum))
                break;
        }

        this.levelOnePlayDealer();
        this.printWinner(this.userSum, this.dealerSum);
    }

    // Method to print instructions for level one
    public void printLevelOneInstructions() {
        System.out.println("You will begin with a random number from a 10-sided dice.");
        System.out.println("You will then have the option to add the minimum " +
                "or maximum of 3 rolls to the sum.");
        System.out.println("Your goal is to get as close to 21 as possible.\n");
    }

    // The dealer's turn for level one of the game
    public void levelOnePlayDealer() {
        this.dealerSum = this.tenDice.roll();
        System.out.println("\n");

        // While the dealer's sum is less than 18, roll the dice
        while (this.dealerSum < 18) {

            // Roll the max of 3 rolls if the dealer's sum is less than 12
            if (this.dealerSum < 12) {
                System.out.println("The dealer gets the maximum of 3 rolls.");
                this.dealerSum += this.tenDice.getMaxRoll(3);
            }

            /**
             * Else, the dealer's sum is greater than or equal to 12, so roll
             * the minimum of 3 rolls instead
             */
            else {
                System.out.println("The dealer gets the minimum of 3 rolls.");
                this.dealerSum += this.tenDice.getMinRoll(3);
            }
        }
        this.printSumRolls(this.dealerSum, "dealer");
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
    public void printWinner(int userSum, int dealerSum) {
        if (this.dealerSum > 21 && this.userSum > 21) {
            System.out.println("You and the dealer both lost. No one wins.");
        }
        else if (this.dealerSum > 21) {
            System.out.println("The dealer bust. You win!");
        }
        else if (this.userSum > 21) {
            System.out.println("The dealer wins. Better luck next time.");
        }
        else if (this.dealerSum == 21 && this.userSum == 21) {
            System.out.println("You and the dealer both equal 21. Tie.");
        }
        else if (this.dealerSum == 21) {
            System.out.println("The dealer wins. Better luck next time!");
        }
        else if (this.userSum == 21) {
            System.out.println("You win!");
        }
        else if (this.dealerSum > this.userSum) {
            System.out.println("The dealer is closer to 21. You lost.");
        }
        else if (this.userSum > this.dealerSum) {
            System.out.println("You're closer to 21. You win!");
        }
        else {
            System.out.println("The sums of both of your rolls are equal. Tie.");
        }
    }

    public void addMinRoll() {
        int min = 11;
        for (int i = 0; i < dieArr.size(); i++) {
            dieArr.get(i).roll();
            if (dieArr.get(i).getLastRoll() < min) {
                min = dieArr.get(i).getLastRoll();
            }
        }
        userSum += min;
    }

    public void addMaxRoll() {
        int max = 0;
        for (int i = 0; i < dieArr.size(); i++) {
            dieArr.get(i).roll();
            if (dieArr.get(i).getLastRoll() > max) {
                max = dieArr.get(i).getLastRoll();
            }
        }
        userSum += max;
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.playGame();
    }
}
