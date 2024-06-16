import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws Exception {
        Truck truck = new Truck("Toyota", "Avensis", "WGR 07487", 500, "Muzyka", "Szyberdach");
        Engine engine = Engine.createEngine(truck, "v12");

        DeliveryTruck deliveryTruck = new DeliveryTruck("BMW", "M4", "WGR123", 100);
        Engine engine2 = Engine.createEngine(deliveryTruck, "V8");

        DeliveryTruck deliveryTruck2 = new DeliveryTruck("Reno", "xx", "WGR124", 100);
        Engine engine3 = Engine.createEngine(deliveryTruck2, "V9");

        Transport transport1 = new Transport("Wwa", "Wrocław", "ryby", LocalDate.now(), truck);
        /*Transport transport2 = new Transport("Wwa", "Wrocław", "ryby", LocalDate.now(), truck, null);
        Transport transport3 = new Transport("Wwa", "Wrocław", "ryby", LocalDate.now(), null, deliveryTruck);
        Transport transport4 = new Transport("Wwa", "Wrocław", "ryby", LocalDate.now(), truck, deliveryTruck);*/



        Transport.showAllTransports();

        transport1.setVehicle(deliveryTruck);

        System.out.println(transport1.getVehicle1());
        System.out.println(transport1.getVehicle2());

        Transport.showAllTransports();

        transport1.removeVehicle(deliveryTruck);

        System.out.println(transport1.getVehicle1());
        System.out.println(transport1.getVehicle2());

        System.out.println(transport1);
    }
}