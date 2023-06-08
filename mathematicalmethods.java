/**
* This program is a calculator that performs various calculations. It allows the user to calculate the area and volume of shapes,
* such as circles and cones, based on user-provided dimensions. Additionally, it can compute the fractional representation
* of numbers by dividing a numerator by a denominator. The program prompts the user for inputs, performs the calculations,
* and displays the results. It provides a user-friendly interface to interactively perform multiple calculations until the user decides to quit.
*/

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("----------------------------------");
        System.out.println("# Test of area and volume methods"); 
        System.out.println("----------------------------------");
        calculateAreaAndVolume(); // Call the method to calculate area and volume
        System.out.println("----------------------------------");
        System.out.println("# Test of the fractional methods");
        System.out.println("----------------------------------");
        calculateFraction(); // Call the method to calculate fractions
    }

    private static void calculateAreaAndVolume() {
        while (true) {
            System.out.print("> ");
            if (scanner.hasNextInt()) {
                int radius = Math.abs(scanner.nextInt());
                if (radius == 0) {
                    // If radius is zero, print zero values for area and volume
                    System.out.println("Circle area: 0.00");
                    System.out.println("Cone area: 0.00");
                    System.out.println("Cone volume: 0.00\n");
                    continue;
                }

                if (scanner.hasNextInt()) {
                    int height = Math.abs(scanner.nextInt());

                    double circleArea = area(radius);
                    double coneArea = area(radius, height);
                    double coneVolume = volume(radius, height);

                    System.out.printf("r = %d h = %d%n", radius, height);
                    System.out.printf("Circle area: %.2f%n", circleArea);
                    System.out.printf("Cone area: %.2f%n", coneArea);
                    System.out.printf("Cone volume: %.2f%n%n", coneVolume);
                } else {
                    break; // Break the loop if invalid input is encountered
                }
            } else {
                String input = scanner.next();
                if (input.equalsIgnoreCase("q")) {
                    break; // Break the loop if "q" is entered to quit
                }
            }
        }
    }

    private static void calculateFraction() {
        System.out.print("> ");
        while (true) {

            if (scanner.hasNextInt()) {
                int numerator = scanner.nextInt();

                if (scanner.hasNextInt()) {
                    int denominator = scanner.nextInt();

                    int[] result = fraction(numerator, denominator);
                    printFraction(numerator, denominator, result);
                } else {
                    break; // Break the loop if invalid input is encountered
                }
            } else {
                String input = scanner.next();
                if (input.equalsIgnoreCase("q")) {
                    break; // Break the loop if "q" is entered to quit
                }
            }
        }
    }

    private static double area(int radius) {
        return Math.PI * Math.pow(radius, 2);
    }

    private static double area(int radius, int height) {
        double slantHeight = pythagoras(radius, height);
        return Math.PI * radius * slantHeight;
    }

    private static double pythagoras(int sideA, int sideB) {
        return Math.sqrt(Math.pow(sideA, 2) + Math.pow(sideB, 2));
    }

    private static double volume(int radius, int height) {
        return (1.0 / 3.0) * Math.PI * Math.pow(radius, 2) * height;
    }

    private static int[] fraction(int numerator, int denominator) {
        if (denominator == 0) {
            return null; // Return null if denominator is zero (division by zero)
        }
        if (numerator == 0) {
            return new int[]{0, 0, 0}; // Return [0, 0, 0] if numerator is zero
        }

        int[] result = new int[3];
        result[0] = numerator / denominator;
        result[1] = Math.abs(numerator % denominator);
        result[2] = Math.abs(denominator);

        int gcd = gcd(result[1], result[2]);
        result[1] /= gcd;
        result[2] /= gcd;

        return result;
    }

    private static int gcd(int a, int b) {
        if (b == 0) {
            return a; // Return the greatest common divisor using Euclid's algorithm
        }
        return gcd(b, a % b);
    }

    private static void printFraction(int numerator, int denominator, int[] parts) {
        if (parts == null) {
            System.out.printf("%d/%d = \"Error\"%n", numerator, denominator);
            return; // Print error message and return if parts is null
        }

        int whole = parts[0];
        int fractionNumerator = parts[1];
        int fractionDenominator = parts[2];

        if (fractionDenominator == 0) {
            System.out.printf("%d/%d = \"Error\"%n", numerator, denominator);
        } else if (whole == 0 && fractionNumerator == 0) {
            System.out.printf("%d/%d = 0%n", numerator, denominator);
        } else if (whole == 0) {
            System.out.printf("%d/%d = %d/%d%n", numerator, denominator, fractionNumerator, fractionDenominator);
        } else if (fractionNumerator == 0) {
            System.out.printf("%d/%d = %d%n", numerator, denominator, whole);
        } else {
            System.out.printf("%d/%d = %d %d/%d%n", numerator, denominator, whole, fractionNumerator, fractionDenominator);
        }
    }
}
