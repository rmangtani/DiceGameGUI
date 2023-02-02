// Blackjack Dice Game with GUI by Ruchi Mangtani
public class Die
{
    /** Instance Variables **/
    private int sides;

    /** Constructors **/
    public Die() {
        sides = 6;
    }

    public Die(int numSides) {
        sides = numSides;
    }

    /** Methods **/
    public int getSides() {
        return sides;
    }

    /**
     * Returns a random int between 1 and
     * the number of sides on the Die
     */
    public int roll() {
        int num = (int)(Math.random()*sides) + 1;
        return num;
    }

    /**
     * Rolls the dice the numRolls times
     * and returns the max value of the rolls
     */
    public int getMaxRoll(int numRolls) {
        int max = Integer.MIN_VALUE;
        for(int i=0; i<numRolls; i++) {
            int num = roll();
            max = Math.max(max, num);
        }

        return max;
    }

    public int getMinRoll(int numRolls) {
        int min = Integer.MAX_VALUE;
        for(int i=0;i<numRolls;i++){
            int num = roll();
            min = Math.min(min, num);
        }
        return min;
    }

    // Returns the sum of numRolls number of rolls
    public int sumRolls(int numRolls) {
        int sum = 0;
        for (int i = 0; i < numRolls; i++){
            sum += this.roll();
        }
        return sum;
    }

    public String toString() {
        return "This is a " + sides + " sided die.";
    }
}
