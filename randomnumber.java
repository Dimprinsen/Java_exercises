/**
* This program generates a specified number of random integers between 0 and 999 and stores them in an integer array.
* It then sorts the even numbers in increasing order and the odd numbers in decreasing order using a temporary array.
* The program prints the unsorted and sorted numbers as well as the count of even and odd numbers.
* If the user enters a number that is too large for the system to handle, an error message is displayed.
*/

import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;

public class Main {

    public static void main(String[] args) {
        
        // Ask user for input
        Scanner sc = new Scanner(System.in);
        System.out.print("How many random numbers in the range 0 - 999 are desired? ");
        int count;

        try {
            count = sc.nextInt();
    
            // Check if count is within the valid range
            if (count < 0 || count > Integer.MAX_VALUE) {
            System.out.println("Invalid input. The system does not support it.");
            return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer value.");
            return;
        }

        // Allocate memory for array
        int[] numbers;
        int[] evens;
        int[] odds;
        try {
            numbers = new int[count];
            evens = new int[count];
            odds = new int[count];
        } catch (OutOfMemoryError e) {
            System.out.println("Unable to allocate memory for " + count + " numbers.");
            return;
        }
        
        // Generate random numbers and populate array
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            numbers[i] = rand.nextInt(1000);
        }
        
        // Print unsorted numbers
        System.out.println("\nHere are the random numbers:");
        for (int i = 0; i < count; i++) {
            System.out.print(numbers[i] + " ");
        }
        System.out.println();
        
        // Sort even and odd numbers
        int evenCount = 0;
        int oddCount = 0;
        for (int i = 0; i < count; i++) {
            if (numbers[i] % 2 == 0) {
                evens[evenCount++] = numbers[i];
            } else {
                odds[oddCount++] = numbers[i];
            }
        }
        
        // Sort even array
        for (int i = 0; i < evenCount - 1; i++) {
            for (int j = i + 1; j < evenCount; j++) {
                if (evens[i] > evens[j]) {
                    int temp = evens[i];
                    evens[i] = evens[j];
                    evens[j] = temp;
                }
            }
        }
        
        // Sort odd array
        for (int i = 0; i < oddCount - 1; i++) {
            for (int j = i + 1; j < oddCount; j++) {
                if (odds[i] < odds[j]) {
                    int temp = odds[i];
                    odds[i] = odds[j];
                    odds[j] = temp;
                }
            }
        }
        
        // Print sorted numbers and counts
        System.out.println("\nHere are the random numbers arranged:");
        for (int i = 0; i < evenCount; i++) {
            System.out.print(evens[i] + " ");
        }
        System.out.print("- ");
        for (int i = 0; i < oddCount; i++) {
            System.out.print(odds[i] + " ");
        }
        System.out.println("\n");
        System.out.println("Of the above " + count + " numbers, " + evenCount + " were even and " + oddCount + " odd");
        
    }

}
