package VehicleModel;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * The Truck class represents a truck which is a type of vehicle.
 * It implements Serializable to allow truck objects to be serialized.
 */
public class Truck extends Vehicle implements Serializable {
    private static final long serialVersionUID = 1L; // Version control for the serialized class

    private final int capacity; // The cargo capacity of the truck

    /**
     * Constructor to create a new Truck object.
     *
     * @param brand The brand of the truck.
     * @param model The model of the truck.
     * @param licencePlateNumber The license plate number of the truck.
     * @param capacity The cargo capacity of the truck.
     * @param functions Additional functions or features of the truck.
     */
    public Truck(String brand, String model, String licencePlateNumber, int capacity, String... functions) {
        super(brand, model, licencePlateNumber, functions);
        this.capacity = capacity;
    }

    /**
     * Displays all vehicles of type Truck.
     * Iterates through all vehicles and prints those that are instances of Truck.
     */
    public static void showAllVehicles() {
        System.out.println("Extent of the class: " + Truck.class.getName());

        for (Vehicle vehicle : getAllVehicles()) {
            if (vehicle instanceof Truck truck) {
                System.out.println(truck);
            }
        }
    }

    /**
     * Displays all engines associated with trucks.
     * Iterates through all engines and prints those that belong to a Truck.
     */
    public static void showAllEngines() {
        System.out.println("Extent of the class: " + Engine.class.getName());

        for (Engine engine : getAllEngines()) {
            if (engine.getVehicle() instanceof Truck) {
                System.out.println(engine);
            }
        }
    }

    /**
     * Simulates the repair process for the truck.
     * Initiates the repair for each mechanic associated with the truck,
     * simulates the repair process for 5 seconds, and then ends the repair.
     */
    @Override
    public void repair() {
        for (int i = 0; i < this.getAllMechanics().size(); i++) {
            startRepair(this.getAllMechanics().get(i));
        }
        simulateRepairProcess(5000, TimeUnit.MILLISECONDS);
        endRepair();
    }

    /**
     * Returns a string representation of the Truck object.
     *
     * @return A string containing the truck's details including its capacity.
     */
    @Override
    public String toString() {
        return "Truck: " + super.toString() + ", capacity: " + capacity;
    }
}
