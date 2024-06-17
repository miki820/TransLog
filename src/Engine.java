import java.io.Serializable;

public class Engine implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final Vehicle vehicle;
    private final String name;

    // Private constructor so that we can't create part on its own
    private Engine(Vehicle vehicle, String name) {
        this.vehicle = vehicle;
        this.name = name;
    }

    // Create Engine but first vehicle must exist because part can't exist on its own
    public static Engine createEngine(Vehicle vehicle, String name) throws Exception {
        // Check if vehicle exists
        if (vehicle == null) {
            throw new Exception("Car doesn't exists");
        }

        // Create a new engine
        Engine engine = new Engine(vehicle, name);

        // Add to the Vehicle because of feedback association
        vehicle.addEngine(engine);

        return engine;
    }

    public String getName() {
        return name;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public String toString() {
        return "Engine: " + "Name: " + getName() + ", Vehicle: " + getVehicle().getBrand() + " " + getVehicle().getModel();
    }
}
