public class Main {
    public static void main(String[] args) throws Exception {
        Vehicle vehicle = new Vehicle("Toyota", "Avensis");
        Vehicle vehicle2 = new Vehicle("Renuwa", "Citroen");
        Engine engine = Engine.createEngine(vehicle, "V12");
        Engine engine2 = Engine.createEngine(vehicle2, "V12");


        Vehicle.showAllVehicles();
        Vehicle.showAllEngines();
        Vehicle.clear();
        Vehicle.showAllVehicles();
        Vehicle.showAllEngines();


    }
}