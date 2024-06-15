import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Vehicle {
    private String brand;
    private String model;

    // Extent to store all Vehicles
    private static List<Vehicle> allVehicles = new ArrayList<>();

    // Licence plate number is unique
    private static Set<String> licencePlateNumber = new HashSet<>();

    // Functions are a repeatable attribute
    private List<String> functions = new ArrayList<>();

    // Engine is a composite attribute
    private Engine engine;

    // Set as a class attribute to store all engines to prohibit engine sharing
    private static Set<Engine> allEngines = new HashSet<>();

    public Vehicle(String brand, String model) {
        this.brand = brand;
        this.model = model;
        addVehicleToExtent(this);
    }

    public void addEngine(Engine engine) throws Exception {
        // Check if the vehicle has an engine
        if (this.engine == null) {

            // Check if the engine has been already added to any vehicle
            if (allEngines.contains(engine)){
                throw new Exception("This engine is already connected with some vehicle.");
            }

            // We add an engine and add it to set of all engines so that we know it is used
            this.engine = engine;
            allEngines.add(engine);

        } else {
            throw new Exception("Engine already exists in this vehicle");
        }
    }

    private static void addVehicleToExtent(Vehicle vehicle){
        allVehicles.add(vehicle);
    }

    private static void clear(){
        allVehicles.clear();
        allEngines.clear();
        //Engine.clear();
    }

    public static void removeVehicleFromExtent(Vehicle vehicle){
        allVehicles.remove(vehicle);
    }

    public static void removeVehicle(Vehicle vehicle) throws Exception {
        if (vehicle != null) {
            vehicle.removeEngine();
            removeVehicleFromExtent(vehicle);
        } else {
            throw new Exception("This vehicle doesn't exists");
        }
    }

    public void removeEngine(){
        if (this.engine != null){
            allEngines.remove(this.engine);

            // Delete of engine from extent while deleting engine in Vehicle
            //Engine.removeEngineFromExtent(this.engine);
            engine = null;
        }
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Engine getEngine() {
        return engine;
    }

    public static void showAllVehicles(){
        System.out.println("Extent of the class: " + Vehicle.class.getName());

        for (Vehicle vehicle : allVehicles) {
            System.out.println(vehicle);
        }
    }

    @Override
    public String toString() {
        String engineInfo = (engine != null) ? engine.getName() : "No engine";
        return "Vehicle: " + "Brand: " + getBrand() + ", Model: " + getModel() + ", Engine: " + engineInfo;
    }
}
