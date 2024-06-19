package VehicleModel;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * DeliveryTruck is a specific type of Vehicle with an additional property for maximum cargo volume.
 * It implements Serializable for object serialization.
 */
public class DeliveryTruck extends Vehicle implements Serializable {
    private static final long serialVersionUID = 1L; // Version control for the serialized class

    private final int maxCargoVolume; // Maximum cargo volume the delivery truck can hold

    /**
     * Constructor to initialize a DeliveryTruck object.
     *
     * @param brand Brand of the truck.
     * @param model Model of the truck.
     * @param licencePlateNumber License plate number of the truck.
     * @param maxCargoVolume Maximum cargo volume the truck can hold.
     * @param functions Additional functions the truck can perform.
     */
    public DeliveryTruck(String brand, String model, String licencePlateNumber, int maxCargoVolume, String... functions) {
        super(brand, model, licencePlateNumber, functions);
        this.maxCargoVolume = maxCargoVolume;
    }

    /**
     * Display all vehicles of type DeliveryTruck.
     */
    public static void showAllVehicles() {
        System.out.println("Extent of the class: " + DeliveryTruck.class.getName());

        // Iterate through all vehicles and display those that are instances of DeliveryTruck
        for (Vehicle vehicle : getAllVehicles()) {
            if (vehicle instanceof DeliveryTruck deliveryTruck) {
                System.out.println(deliveryTruck);
            }
        }
    }

    /**
     * Display all engines associated with DeliveryTruck vehicles.
     */
    public static void showAllEngines() {
        System.out.println("Extent of the class: " + Engine.class.getName());

        // Iterate through all engines and display those associated with DeliveryTruck vehicles
        for (Engine engine : getAllEngines()) {
            if (engine.getVehicle() instanceof DeliveryTruck) {
                System.out.println(engine);
            }
        }
    }

    /**
     * Perform repair operations on the DeliveryTruck.
     * Invokes repair operations for each mechanic associated with the vehicle,
     * simulates the repair process, and then ends the repair.
     */
    @Override
    public void repair() {
        // Start repair process for each mechanic
        for (int i = 0; i < this.getAllMechanics().size(); i++) {
            startRepair(this.getAllMechanics().get(i));
        }
        // Simulate repair process for a specified duration
        simulateRepairProcess(3000, TimeUnit.MILLISECONDS);
        // End the repair process
        endRepair();
    }

    /**
     * Return a string representation of the DeliveryTruck.
     *
     * @return String containing the details of the DeliveryTruck.
     */
    @Override
    public String toString() {
        return "Delivery Truck: " + super.toString() + ", maxCargoVolume: " + maxCargoVolume;
    }
}
