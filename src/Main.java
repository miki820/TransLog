import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws Exception {
        Truck truck = new Truck("Toyota", "Avensis", "WGR 07487", 500, "Muzyka", "Szyberdach");
        Engine engine = Engine.createEngine(truck, "v12");

        DeliveryTruck deliveryTruck = new DeliveryTruck("BMW", "M4", "WGR123", 100);
        Engine engine2 = Engine.createEngine(deliveryTruck, "V8");

        List<Vehicle> vehicleList = Arrays.asList(truck);

        Transport transport = new Transport("Wwa", "Wrocław", "ryby", LocalDate.now(), vehicleList);

        OfficeWorker officeWorker = new OfficeWorker("Adam", "Adach", LocalDate.of(2002, 10, 2), 1, "Lodziarnia", "Dyrektor");
        System.out.println(officeWorker);

        Worker mechanic = Worker.createMechanic("Bartek", "Wąski", LocalDate.of(2002, 10, 2), 1, "Lodziarnia", "trucks");
        System.out.println(mechanic);

        Worker driver = Worker.createDriver("Rafal", "Malachowski", LocalDate.of(2002, 10, 2), 1, "Lodziarnia", 2);
        System.out.println(driver);
    }
}