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

        Engine engine = new Engine(vehicle, name);
        vehicle.addEngine(engine);

        return engine;
    }
}
