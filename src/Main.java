public class Main {
    public static void main(String[] args) throws Exception {
        Vehicle vehicle = new Vehicle("Toyota", "Avensis", "WGR 07487", "Muzyka", "Szyberdach");
        Engine engine = Engine.createEngine(vehicle, "v12");

        Vehicle vehicle2 = new Vehicle("BMW", "M4", "WGR 07488", "Muzyka", "Szyberdach");
        Engine engine2 = Engine.createEngine(vehicle2, "V8");

        vehicle2.removeEngine();
        vehicle2.addEngine(engine2);

        vehicle2.addFunction("Ogien");
        vehicle2.deleteFunction("Szyberdach");

        Vehicle.removeVehicle(vehicle);
        vehicle2.removeEngine();

        Vehicle.showAllVehicles();
        Vehicle.showAllEngines();
    }
}