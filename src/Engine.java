import java.util.ArrayList;
import java.util.List;

public class Engine {
    private Vehicle vehicle;
    private String name;

    // Extent to store all engines
    private static List<Engine> allEngines = new ArrayList<>();

    // Private constructor so that we can't create part on its own
    private Engine(Vehicle vehicle, String name) {
        this.vehicle = vehicle;
        this.name = name;
        addEngineToExtent(this);
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

    public static void addEngineToExtent(Engine engine) {
        allEngines.add(engine);
    }

    public static void clear(){
        allEngines.clear();
    }

    public static void removeEngineFromExtent(Engine engine) {
        allEngines.remove(engine);
    }

    public String getName() {
        return name;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public static void showAllEngines(){
        System.out.println("Extent of the class: " + Engine.class.getName());

        for (Engine engine : allEngines) {
            System.out.println(engine);
        }
    }

    @Override
    public String toString() {
        return "Engine: " + " Name: " + getName() + ", Vehicle: " + getVehicle().getBrand() + " " + getVehicle().getModel();
    }
}
