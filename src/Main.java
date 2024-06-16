import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws Exception {
        Truck truck = new Truck("Toyota", "Avensis", "WGR 07487", 500, "Muzyka", "Szyberdach");
        Engine engine = Engine.createEngine(truck, "v12");

        DeliveryTruck deliveryTruck = new DeliveryTruck("BMW", "M4", "WGR123", 100);
        Engine engine2 = Engine.createEngine(deliveryTruck, "V8");

        Service service = new Service(1000, "Wymiana oleju");
        Service service2 = new Service(1500, "Naprawa Silnika");

        VehicleService vehicleService = new VehicleService(truck, service, LocalDate.now(), LocalDate.now().plusDays(1));
        VehicleService vehicleService2 = new VehicleService(truck, service2, LocalDate.now(), LocalDate.now().plusDays(2));

        for (VehicleService serviceHistory : truck.getVehicleServices()) {
            System.out.println(serviceHistory.getService());
        }

        for (VehicleService vehicleHistory : service.getServicedVehicles()) {
            System.out.println(vehicleHistory.getVehicle());
        }

        for (VehicleService vehicleHistory : service2.getServicedVehicles()) {
            System.out.println(vehicleHistory.getVehicle());
        }
    }
}