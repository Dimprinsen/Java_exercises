/**
* This program is a game called "Dice Game 12". The objective is to roll three dice and try to get a sum of 12.
* Each dice can only be rolled once per round.
* The program allows the player to choose which dice to roll. It keeps track of the number of wins and losses. 
* The game continues until the player chooses to exit.
* It includes error handling for invalid inputs and displays the current dice values, sum, and win/loss count after each round.
*/

import java.util.Random;
import java.util.Scanner;

public class Main {
  
    public static void main(String[] args) {
        // Constants
        final int TARGET_SUM = 12;
        final int NUM_DICE = 3;

        // Variables
        int[] diceValues = new int[NUM_DICE];
        boolean[] isDiceRolled = new boolean[NUM_DICE];
        int winCount = 0;
        int lossCount = 0;
        boolean gameOver = false;
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Dice Game 12. You must roll 1-3 dice and try to get the sum of 12.\n");

        while (!gameOver) {
            // Reset variables for a new round
            for (int i = 0; i < NUM_DICE; i++) {
                diceValues[i] = 0;
                isDiceRolled[i] = false;
            }

            int numDiceRolled = 0; // Track the number of dice rolled in a round
            int sum = 0; // Track the sum of the rolled dice

            while (numDiceRolled < NUM_DICE) {
                System.out.print("Enter which dice you want to roll [1, 2, 3] (exit with q): ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("q")) {
                    gameOver = true;  // Exit the game loop
                    break;
                }

                int diceNumber;
                try {
                    diceNumber = Integer.parseInt(input);  // Convert input to an integer
                } catch (NumberFormatException e) {
                    System.out.println("Sorry, that is an invalid entry. Try again. Valid entries are 1, 2, 3, and q\n");
                    continue;  // Restart the loop to get another valid input
                }

                if (diceNumber < 1 || diceNumber > NUM_DICE) {
                    System.out.println("Sorry, that is an invalid entry. Try again. Valid entries are 1, 2, 3, and q\n");
                    continue;  // Restart the loop to get another valid input
                }

                if (isDiceRolled[diceNumber - 1]) {
                    System.out.println("Sorry, you have already rolled that dice. Try again.\n");
                    continue;  // Restart the loop to get another valid input
                }

                diceValues[diceNumber - 1] = random.nextInt(6) + 1;  // Roll the selected dice and store the value
                isDiceRolled[diceNumber - 1] = true;
                numDiceRolled++; // Increment the number of dice rolled

                sum += diceValues[diceNumber - 1]; // Increment the sum with the rolled dice value

                for (int value : diceValues) {
                    System.out.print(value + " ");
                }
                System.out.println("Sum: " + sum + " #win: " + winCount + " #loss: " + lossCount);
            }

            if (gameOver) {
                break;
            }

            if (sum == TARGET_SUM) {
                System.out.println("You won!!\n");
                winCount++;  // Increment the win count
            } else if (sum > TARGET_SUM) {
                System.out.println("You lost!!\n");
                lossCount++;  // Increment the loss count
            } else {
                System.out.println("You neither won nor lost the game.\n");
            }

            System.out.println("Next round!\n");
        }

        System.out.println("#win: " + winCount + " #loss: " + lossCount);
        System.out.println("Game Over!");
    }
}
