/*
* 
*/

import java.util.Scanner;

public class Main {
    private static final int MAX_CARS = 10;
    private static final int MAX_RENTALS = 10;
    private static final int RENTAL_RATE = 120;

    private static final Car[] carFleet = new Car[MAX_CARS];
    private static final Rental[] rentals = new Rental[MAX_RENTALS];
    private static int carCount = 0;
    private static int rentalCount = 0;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            printMenu();
            String option = scanner.nextLine().trim();
            switch (option) {
                case "1":
                    addCarToFleet();
                    break;
                case "2":
                    rentCar();
                    break;
                case "3":
                    returnCar();
                    break;
                case "4":
                    printCarFleet();
                    break;
                case "5":
                    printRentalSummary();
                    break;
                case "q":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("----------------------------------");
        System.out.println("# LTU Rent-a-car");
        System.out.println("----------------------------------");
        System.out.println("1. Add car to fleet");
        System.out.println("2. Rent a car");
        System.out.println("3. Return a car");
        System.out.println("4. Print car fleet");
        System.out.println("5. Print rental summary");
        System.out.println("q. End program");
        System.out.println("> Enter your option: ");
    }

    private static void addCarToFleet() {
        System.out.println("> Enter registration number: ");
        String registrationNumber = scanner.nextLine().trim();
        // Validate registration number format
        if (!isValidRegistrationNumber(registrationNumber)) {
            System.out.println("Invalid registration number format. Car not added to fleet.");
            return;
        }
        System.out.print("> Enter make and model: ");
        String makeAndModel = scanner.nextLine().trim();

        

        // Check for duplicate registration number
        if (isDuplicateRegistrationNumber(registrationNumber)) {
            System.out.println("Car with the same registration number already exists. Car not added to fleet.");
            return;
        }

        Car car = new Car(registrationNumber, makeAndModel);
        carFleet[carCount] = car;
        carCount++;
        System.out.println(car.makeAndModel + " with registration number " + car.registrationNumber + " was added to the car fleet.");
    }

    private static boolean isValidRegistrationNumber(String registrationNumber) {
        // Validate registration number format (3 uppercase letters followed by 3 digits)
        return registrationNumber.matches("[A-Z]{3}\\d{3}");
    }

    private static boolean isDuplicateRegistrationNumber(String registrationNumber) {
        for (int i = 0; i < carCount; i++) {
            if (carFleet[i].registrationNumber.equals(registrationNumber)) {
                return true;
            }
        }
        return false;
    }

    private static void rentCar() {
        System.out.print("> Enter the car's registration number: ");
        String registrationNumber = scanner.nextLine().trim();
        System.out.print("> Enter the time of pickup (HH:mm): ");
        String pickupTime = scanner.nextLine().trim();
        if (!isValidTime(pickupTime)) {
            System.out.println("Invalid time format. Car not rented.");
            return;
        }
        System.out.print("> Enter the renter's name: ");
        String renterName = scanner.nextLine().trim();

        Car car = findCarByRegistrationNumber(registrationNumber);
        if (car == null) {
            System.out.println("Car with registration number " + registrationNumber + " does not exist.");
            return;
        }

        if (car.isRented()) {
            System.out.println("Car with registration number " + registrationNumber + " is already rented.");
            return;
        }

        Rental rental = new Rental(car, renterName, pickupTime);
        rentals[rentalCount] = rental;
        rentalCount++;
        System.out.println("The car with registration number " + car.registrationNumber + " was rented by " +
                renterName + " at " + rental.pickupTime + ".");
    }

    private static void returnCar() {
        System.out.print("> Enter registration number: ");
        String registrationNumber = scanner.nextLine().trim();
        System.out.print("> Enter the time of return (HH:mm): ");
        String returnTime = scanner.nextLine().trim();
        if (!isValidTime(returnTime)) {
            System.out.println("Invalid time format. Car not returned.");
            return;
        }
        Car car = findCarByRegistrationNumber(registrationNumber);
        if (car == null) {
            System.out.println("Car with registration number " + registrationNumber + " does not exist.");
            return;
        }

        Rental rental = findRentalByCar(car);
        if (rental == null) {
            System.out.println("Car with registration number " + registrationNumber + " is not currently rented.");
            return;
        }

        rental.returnCar(returnTime);
        System.out.println("===================================");
        System.out.println("LTU Rent-a-car");
        System.out.println("===================================");
        System.out.println("Name: " + rental.renterName);
        System.out.println("Car: " + rental.car.makeAndModel + " (" + rental.car.registrationNumber + ")");
        System.out.println("Time: " + rental.pickupTime + "-" + rental.returnTime + " (" + rental.getRentalDuration() + " hours)");
        System.out.println("Total cost: " + rental.getRentalCost() + " SEK");
        System.out.println("===================================");
    }

    private static void printCarFleet() {
        System.out.println("LTU Rent-a-car car fleet:");
        System.out.println("Fleet:");
        System.out.println("Model                 Numberplate       Status");
        for (int i = 0; i < carCount; i++) {
            System.out.printf("%-20s%-18s%s\n", carFleet[i].makeAndModel, carFleet[i].registrationNumber, carFleet[i].getStatus());
        }
        System.out.println("Total number of cars: " + carCount);
        System.out.println("Total number of available cars: " + countAvailableCars());
    }

    private static int countAvailableCars() {
        int count = 0;
        for (int i = 0; i < carCount; i++) {
            if (!carFleet[i].isRented()) {
                count++;
            }
        }
        return count;
    }
    private static boolean isValidTime(String time) {
        return time.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]");
    }
    private static void printRentalSummary() {
        System.out.println("LTU Rent-a-car rental summary:");
        System.out.println("Rentals:");
        System.out.println("Name               Numberplate   Pickup   Return   Cost");
        for (int i = 0; i < rentalCount; i++) {
            System.out.printf("%-18s%-14s%-9s%-9s%s\n", rentals[i].renterName, rentals[i].car.registrationNumber,
                    rentals[i].pickupTime, rentals[i].returnTime, rentals[i].getRentalCost());
        }
        System.out.println("Total number of rentals: " + rentalCount);
        System.out.println("Total revenue: " + calculateTotalRevenue() + " SEK");
    }

    private static int calculateTotalRevenue() {
        int totalRevenue = 0;
        for (int i = 0; i < rentalCount; i++) {
            if (rentals[i].isReturned()) {
                totalRevenue += rentals[i].getRentalCost();
            }
        }
        return totalRevenue;
    }

    private static Car findCarByRegistrationNumber(String registrationNumber) {
        for (int i = 0; i < carCount; i++) {
            if (carFleet[i].registrationNumber.equals(registrationNumber)) {
                return carFleet[i];
            }
        }
        return null;
    }

    private static Rental findRentalByCar(Car car) {
        for (int i = 0; i < rentalCount; i++) {
            if (rentals[i].car == car && !rentals[i].isReturned()) {
                return rentals[i];
            }
        }
        return null;
    }

    private static class Car {
        private final String registrationNumber;
        private final String makeAndModel;
        private boolean rented;

        public Car(String registrationNumber, String makeAndModel) {
            this.registrationNumber = registrationNumber;
            this.makeAndModel = makeAndModel;
            this.rented = false;
        }

        /**
         * Returns the status of the car: "Rented" if it is currently rented, "Available" otherwise.
         *
         * @return the status of the car
         */
        public String getStatus() {
            return rented ? "Rented" : "Available";
        }

        /**
         * Checks if the car is currently rented.
         *
         * @return true if the car is rented, false otherwise
         */
        public boolean isRented() {
            return rented;
        }
    }

    private static class Rental {
        private final Car car;
        private final String renterName;
        private final String pickupTime;
        private String returnTime;

        public Rental(Car car, String renterName, String pickupTime) {
            this.car = car;
            this.renterName = renterName;
            this.pickupTime = pickupTime;
            this.returnTime = "";
            car.rented = true;
        }

        /**
         * Sets the return time for the rental.
         *
         * @param returnTime the time of return
         */
        public void returnCar(String returnTime) {
            this.returnTime = returnTime;
            car.rented = false;
        }

        /**
         * Checks if the car has been returned.
         *
         * @return true if the car has been returned, false otherwise
         */
        public boolean isReturned() {
            return !returnTime.equals("");
        }

        /**
         * Calculates the duration of the rental in hours.
         *
         * @return the duration of the rental in hours
         */
        public int getRentalDuration() {
            if (pickupTime == null || returnTime == null || returnTime.isEmpty()) {
                return 0;
            }
            String[] pickupParts = pickupTime.split(":");
            String[] returnParts = returnTime.split(":");

            int pickupHour = Integer.parseInt(pickupParts[0]);
            int pickupMinute = Integer.parseInt(pickupParts[1]);
            int returnHour = Integer.parseInt(returnParts[0]);
            int returnMinute = Integer.parseInt(returnParts[1]);

            int durationHour = returnHour - pickupHour;
            int durationMinute = returnMinute - pickupMinute;

            if (durationMinute < 0) {
                durationHour--;
                durationMinute += 60;
            }

            return durationHour + (durationMinute > 0 ? 1 : 0);
        }

        /**
         * Calculates the cost of the rental.
         *
         * @return the cost of the rental
         */
        public int getRentalCost() {
            return getRentalDuration() * RENTAL_RATE;
        }
    }
}