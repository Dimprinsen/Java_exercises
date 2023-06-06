/**
* This program calculates and prints a table of charging power and * time for an electric vehicle, based on different combinations of * charging current and voltage.
*/

public class Main {
    
    // Define the battery capacity as a constant
    public static final double BATTERY_CAPACITY = 35.8; // kWh
    
    // Main method
    public static void main(String[] args) {
        
        // Define the current and voltage values for each row in the table
        double[][] values = {{10.0, 230.0}, {16.0, 230.0}, {10.0, 400.0}, {16.0, 400.0}, {32.0, 400.0}}; // current, voltage
        
        // Print the table headers
        System.out.println("Current(A)\tVoltage(V)\tCharging Power(kW)\tCharging Time(h)");
        
        // Iterate over the current and voltage values and compute the charging power and time for each combination
        for (double[] arr : values) {
            double current = arr[0];
            double voltage = arr[1];
            double chargingPower;
            
            // Compute the charging power based on the voltage value
            if (voltage == 230.0) {
                chargingPower = (current * voltage) / 1000.0; // kW
            } else {
                chargingPower = (current * voltage * Math.sqrt(3)) / 1000.0; // kW (for three-phase charging at 400 V)
            }
            
            // Compute the charging time based on the battery capacity and charging power
            double chargingTime = BATTERY_CAPACITY / chargingPower; // h
            
            // Print the results for this combination of current and voltage
            System.out.printf("%.1f\t\t%.1f\t\t%.2f\t\t\t%.2f\n", current, voltage, chargingPower, chargingTime);
        }
    }
}