import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Truck truck = new Truck("Toyota", "Avensis", "WGR 07487", 500, "Muzyka", "Szyberdach");
        Engine engine = Engine.createEngine(truck, "v12");

        DeliveryTruck deliveryTruck = new DeliveryTruck("BMW", "M4", "WGR123", 100);
        Engine engine2 = Engine.createEngine(deliveryTruck, "V8");

        List<Vehicle> vehicleList = Arrays.asList(truck);

        Transport transport = new Transport("Wwa", "Wrocław", "ryby", LocalDate.now(), vehicleList);

        Transport.showAllTransports();

        transport.addVehicle(deliveryTruck);

        Transport.showAllTransports();

        transport.removeVehicle(truck);

        Transport.showAllTransports();

        truck.addTransportQualif(transport);

        Transport.showAllTransports();

        truck.removeTransportQualif(transport.getId());

        Transport.showAllTransports();

        transport.removeTransport();

        Transport.showAllTransports();
        System.out.println(truck.getTransports());
        System.out.println(deliveryTruck.getTransports());
    }
}