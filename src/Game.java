import java.util.Scanner;
// Blackjack Dice Game with GUI by Ruchi Mangtani
public class Game {
    // Instance variables
    private Die tenDice;
    private Die sixDice;
    private int userSum;
    private int dealerSum;
    private boolean startGame;
    private GameView window;

    // Constructor that initializes the two die and the sum of the user & dealer
    public Game() {
        this.tenDice = new Die(10);
        this.sixDice = new Die(6);
        this.userSum = 0;
        this.dealerSum = 0;
        this.startGame = false;
        this.window = new GameView(this);
    }

    public boolean getStartGame() {
        return startGame;
    }

    // Method to play the game
    public void playGame() {
        Scanner input = new Scanner(System.in);
        int level = -1;

        // Loop continues running until user decides to quit the game by pressing
        // 0 on their keyboard
        while (level != 0) {
            System.out.println("Welcome to BlackJack Dice! Do you want to " +
                    "play level 1 or 2 or end game? (1/2/0)");
            level = input.nextInt();
            System.out.println("\n");

            // Calls the respective method depending on the level
            if (level == 1) {
                this.levelOne();
            }
            else if (level == 2) {
                this.levelTwo();
            }

            System.out.println("\n");
        }
    }

    // Method to print instructions for level one
    public void printLevelOneInstructions() {
        System.out.println("You will begin with a random number from a 10-sided dice.");
        System.out.println("You will then have the option to add the minimum " +
                "or maximum of 3 rolls to the sum.");
        System.out.println("Your goal is to get as close to 21 as possible.\n");
    }

    // Method to play level one
    public void levelOne() {
        Scanner input = new Scanner(System.in);
        this.printLevelOneInstructions();
        System.out.println("Type anything on the keyboard to start the game.");
        input.nextLine();
        startGame = true;
        this.userSum = this.tenDice.roll();
        window.repaint();
        System.out.println("Rolling dice.... The dice rolled " + this.userSum + ".");

        // While loop continuously asks user if they want to get the min or max
        // of three rolls or stay
        // Loop stops when user decides to stay or if the game is over
        String minOrMax = "";
        while (!minOrMax.equals("stay")) {
            System.out.println("Do you want to get the min or max of 3 rolls " +
                    "or stay? (min/max/stay)");
            minOrMax = input.nextLine();

            if (minOrMax.equals("min")) {
                this.userSum += this.tenDice.getMinRoll(3);
            }
            else if (minOrMax.equals("max")) {
                this.userSum += this.tenDice.getMaxRoll(3);
            }

            this.printSumRolls(this.userSum, "user");

            if (this.isGameOver(this.userSum))
                break;
        }

        this.levelOnePlayDealer();
        this.printWinner(this.userSum, this.dealerSum);
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

    // Method to print instructions for level 2
    public void printLevelTwoInstructions() {
        System.out.println("A 10-sided dice will be rolled twice and you " +
                "will be given the sum of the two rolls.");
        System.out.println("You will then have the option to hit (roll a " +
                "six-sided dice and add result to the sum) or stay " +
                "(keep the sum you have).");
        System.out.println("You're goal is to get as close to 21 as possible " +
                "without going over.\n");
    }

    // Method to play level two of the game
    public void levelTwo() {
        Scanner input = new Scanner(System.in);
        this.printLevelTwoInstructions();

        System.out.println("Rolling dice twice...");

        this.userSum = this.tenDice.sumRolls(2);
        this.printSumRolls(this.userSum, "user");

        System.out.println("Do you want to hit or stay? (h/s)");

        // input.next() stops reading after getting a space
        char hitOrStay = input.next().charAt(0);

        // User is continuously asked if they want to hit or stay until they
        // decide to stay or the game is over
        while (hitOrStay == 'h') {
            this.userSum += this.sixDice.roll();
            this.printSumRolls(this.userSum, "user");

            if (this.isGameOver(this.userSum))
                break;

            System.out.println("Do you want to hit or stay? (h/s)");
            hitOrStay = input.next().charAt(0);
        }

        this.levelTwoPlayDealer();
        this.printWinner(this.userSum, this.dealerSum);
    }

    // Method for the dealer's turn in level two
    public void levelTwoPlayDealer() {
        System.out.println("\n");
        this.dealerSum = this.tenDice.sumRolls(2);

        // The dealer hits if their sum is less than 17
        while (this.dealerSum < 17) {
            System.out.println("The dealer hits.");
            this.dealerSum += this.sixDice.roll();
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

    public static void main(String[] args) {
        Game g = new Game();
        g.playGame();
    }
}
