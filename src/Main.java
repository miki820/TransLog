public class Main {
    public static void main(String[] args) throws Exception {
        Truck vehicle = new Truck("Toyota", "Avensis", "WGR 07487", 500, "Muzyka", "Szyberdach");
        Engine engine = Engine.createEngine(vehicle, "v12");

        DeliveryTruck vehicle2 = new DeliveryTruck("BMW", "M4", "WGR123", 100);
        Engine engine2 = Engine.createEngine(vehicle2, "V8");

        System.out.println("=========================");
        Vehicle.showAllVehicles();
        Vehicle.showAllEngines();
        System.out.println("=========================");
        Truck.showAllVehicles();
        Truck.showAllEngines();
        System.out.println("=========================");
        DeliveryTruck.showAllVehicles();
        DeliveryTruck.showAllEngines();
    }
}