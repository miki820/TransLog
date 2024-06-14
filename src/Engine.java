public class Engine {
    private Vehicle vehicle;
    private String name;

    private Engine(Vehicle vehicle, String name) {
        this.vehicle = vehicle;
        this.name = name;
    }

    public static Engine createEngine(Vehicle vehicle, String name) throws Exception {
        if (vehicle == null) {
                throw new Exception("Car doesn't exists");
        }

        // Create a new engine
        Engine engine = new Engine(vehicle, name);

        // Add to the Vehicle
        vehicle.addEngine(engine);

        return engine;
    }
}
