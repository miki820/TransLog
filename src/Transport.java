import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Transport implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String startingPoint;
    private String endingPoint;
    private String cargo;
    private LocalDate transportDate;

    private static List<Transport> allTransports = new ArrayList<>();
    private List<Vehicle> vehicles = new ArrayList<>();

    // Flag to indicate if the transport is being removed
    private boolean isBeingRemoved = false;

    public Transport(String startingPoint, String endingPoint, String cargo, LocalDate transportDate, List<Vehicle> vehicles) {
        this.id = UUID.randomUUID().toString();
        if (vehicles == null || vehicles.isEmpty() || vehicles.size() > 2) {
            throw new IllegalArgumentException("Transport must have at least 1 and at most 2 vehicles.");
        }

        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.cargo = cargo;
        this.transportDate = transportDate;

        for (Vehicle vehicle : vehicles) {
            if (!vehicle.getTransports().contains(this)) {
                vehicle.addTransportQualif(this);
            }
        }

        addTransport(this);
    }

    public void addVehicle(Vehicle vehicle) {
        if (this.vehicles.size() >= 2) {
            throw new IllegalStateException("Cannot add more than 2 vehicles to transport.");
        }
        if (!this.vehicles.contains(vehicle)) {
            this.vehicles.add(vehicle);
            if (!vehicle.getTransports().contains(this)) {
                vehicle.addTransportQualif(this);
            }
        }
    }

    public void removeVehicle(Vehicle vehicle){
        if (this.vehicles.size() <= 1 && !isBeingRemoved) {
            throw new IllegalStateException("You can't delete vehicle because transport must have at least 1 vehicle.");
        }
        if (this.vehicles.contains(vehicle)) {
            this.vehicles.remove(vehicle);
            if (vehicle.getTransports().contains(this)) {
                vehicle.removeTransportQualif(this.id);
            }
        }
    }

    private static void addTransport(Transport transport) {
        allTransports.add(transport);
    }

    public void removeTransport() {
        isBeingRemoved = true;
        //New list to avoid ConcurrentModificationException
        for(Vehicle vehicle : new ArrayList<>(vehicles)) {
            if (vehicle.getTransports().contains(this)){
                vehicle.removeTransportQualif(this.getId());
            }
        }
        allTransports.remove(this);
    }

    public static void showAllTransports() {
        System.out.println("Extent of the class: " + Transport.class.getName());
        for (Transport transport : allTransports) {
            System.out.println(transport);
        }
    }

    public String getId() {
        return id;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    @Override
    public String toString() {
        String vehicle1 = vehicles.size() >= 1 ? vehicles.get(0).getBrand() + " " + vehicles.get(0).getLicencePlateNumber() : "No Vehicle";
        String vehicle2 = vehicles.size() == 2 ? vehicles.get(1).getBrand() + " " + vehicles.get(1).getLicencePlateNumber() : "No Vehicle";

        return "Transport: " +
                "ID: " + id +
                ", StartingPoint: " + startingPoint +
                ", EndingPoint: " + endingPoint +
                ", Cargo: " + cargo +
                ", TransportDate: " + transportDate +
                ", Vehicle1: " + vehicle1 +
                ", Vehicle2: " + vehicle2;
    }
}
