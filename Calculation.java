/**
* This program calculates the potential solar panel energy production and the profit it generates based on user input. The program uses the user's input for the month, day, sunrise, and sunset times to calculate the number of hours of sunlight available. The program then uses this information and some constants (such as the panel's efficiency and the solar radiation in the user's area) to calculate the potential energy production and profit from the solar panels. If the user inputs an invalid date or time, the program will output an error message and stop running. Finally, the program will output the number of sun hours and the potential energy production and profit for the user's solar panel system.
*/
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Declare Constants
        final int NUM_OF_PANELS = 26;
        final double PANEL_HEIGHT = 1;
        final double SOLAR_RADIATION = 166;
        final double PANEL_WIDTH = 1.7;
        final double PANEL_AREA = PANEL_WIDTH * PANEL_HEIGHT;
        final double EFFICIENCY = 0.2;
        final double ELECTRIC_PRICE = 0.9;
        final int[] DAYS_IN_MONTH = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // Variables: hour, minutes, month, day
        int sunriseHour, sunriseMinute, sunsetHour, sunsetMinute, month, day;
        double sunHours;

        // Scanner for user input
        Scanner scanner = new Scanner(System.in).useDelimiter("\\W+");

        // Ask user for date
        System.out.print("Enter today's date [mm-dd]> ");
        month = scanner.nextInt();
        day = scanner.nextInt();
        scanner.nextLine(); // consume the remaining newline character

      // Validate month and day
        if (month < 1 || month > 12) {
            System.out.println("Invalid month");
            System.exit(0);
        } else if (day < 1 || day > DAYS_IN_MONTH[month]) {
            System.out.println("Invalid day for the given month");
            System.exit(0);
        } else {
            System.out.println("Valid date");
        }

        // Ask user for sunrise
        System.out.print("Enter the time of sunrise [hh:mm]> ");
        sunriseHour = scanner.nextInt();
        sunriseMinute = scanner.nextInt();
        scanner.nextLine(); // consume the remaining newline character

        // Validate sunrise
        if (sunriseHour < 0 || sunriseHour > 23 || sunriseMinute < 0 || sunriseMinute > 59) {
        System.out.println("Invalid sunrise time");
        System.exit(0);
        }

        // Ask user for sunset
        System.out.print("Enter the time of sunset [hh:mm]> ");
        sunsetHour = scanner.nextInt();
        sunsetMinute = scanner.nextInt();
        scanner.nextLine(); // consume the remaining newline character

        // Validate sunset
        if (sunsetHour < 0 || sunsetHour > 23 || sunsetMinute < 0 || sunsetMinute > 59) {
        System.out.println("Invalid sunset time");
        System.exit(0);
        }
      
        // Calculate sun hours
        sunHours = (sunsetHour + sunsetMinute / 60.0) - (sunriseHour + sunriseMinute / 60.0);

        // Check if sunset is later than sunrise
        if (sunHours <= 0) {
            System.out.println("Sunset time is earlier than sunrise time");
            System.exit(0);
        }

        // Calculate production
        double production = SOLAR_RADIATION * EFFICIENCY * PANEL_AREA * sunHours * NUM_OF_PANELS / 1000;

        // Calculate profit
        double profit = production * ELECTRIC_PRICE;

        // Print results
        System.out.printf("Sun hours: %.2f hours\n", sunHours);
        System.out.printf("The production on %d/%d is: %.2f kWh to a value of: SEK %.2f", month, day, production,
                profit);
    }
}