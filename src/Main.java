import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws Exception {
        Truck truck = new Truck("Toyota", "Avensis", "WGR 07487", 500, "Muzyka", "Szyberdach");
        Engine engine = Engine.createEngine(truck, "v12");

        DeliveryTruck deliveryTruck = new DeliveryTruck("BMW", "M4", "WGR123", 100);
        Engine engine2 = Engine.createEngine(deliveryTruck, "V8");


        Transport transport = new Transport("Wwa", "Wrocław", "ryby", LocalDate.now(), truck, null);
        /*Transport transport = new Transport("Wwa", "Wrocław", "ryby", LocalDate.now(), null, deliveryTruck);
        Transport transport = new Transport("Wwa", "Wrocław", "ryby", LocalDate.now(), truck, deliveryTruck);*/

        Transport.showAllTransports();

        transport.setVehicle2(deliveryTruck);

        Transport.showAllTransports();

        transport.removeVehicle(deliveryTruck);

        Transport.showAllTransports();
    }
}