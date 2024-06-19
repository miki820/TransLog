package VehicleModel;

import java.io.Serializable;

/**
 * The Engine class represents an engine that is associated with a specific vehicle.
 * It implements Serializable to allow the engine objects to be serialized.
 */
public class Engine implements Serializable {
    private static final long serialVersionUID = 1L; // Version control for the serialized class

    private final Vehicle vehicle; // The vehicle to which this engine belongs
    private final String name; // The name of the engine

    /**
     * Private constructor to ensure engines cannot be created without being associated with a vehicle.
     *
     * @param vehicle The vehicle to which this engine belongs.
     * @param name The name of the engine.
     */
    private Engine(Vehicle vehicle, String name) {
        this.vehicle = vehicle;
        this.name = name;
    }

    /**
     * Static factory method to create an engine associated with a vehicle.
     *
     * @param vehicle The vehicle to which the engine will be associated.
     * @param name The name of the engine.
     * @return A new Engine object.
     * @throws Exception if the vehicle does not exist.
     */
    public static Engine createEngine(Vehicle vehicle, String name) throws Exception {
        // Check if the vehicle exists
        if (vehicle == null) {
            throw new Exception("Vehicle doesn't exist");
        }

        // Create a new engine
        Engine engine = new Engine(vehicle, name);

        // Add the engine to the vehicle's list of engines (feedback association)
        vehicle.addEngine(engine);

        return engine;
    }

    /**
     * Gets the name of the engine.
     *
     * @return The name of the engine.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the vehicle to which this engine belongs.
     *
     * @return The vehicle associated with this engine.
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Returns a string representation of the Engine object.
     *
     * @return A string containing the engine name and the associated vehicle's brand and model.
     */
    @Override
    public String toString() {
        return "Engine: Name: " + getName() + ", Vehicle: " + getVehicle().getBrand() + " " + getVehicle().getModel();
    }
}
