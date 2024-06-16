import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Transport {
    private String startingPoint;
    private String endingPoint;
    private String cargo;
    private LocalDate transportDate;

    private Vehicle vehicle1;
    private Vehicle vehicle2;

    private static List<Transport> allTransports = new ArrayList<>();

    public Transport(String startingPoint, String endingPoint, String cargo, LocalDate transportDate, Vehicle vehicle1, Vehicle vehicle2) {
        if (vehicle1 == null && vehicle2 == null) {
            throw new IllegalArgumentException("At least one vehicle must be assigned to the transport.");
        }

        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.cargo = cargo;
        this.transportDate = transportDate;

        if (vehicle1 != null) {
            vehicle1.assignTransport(this);
        }
        if (vehicle2 != null) {
            vehicle2.assignTransport(this);
        }

        addTransport(this);
    }

    public Transport(String startingPoint, String endingPoint, String cargo, LocalDate transportDate, Vehicle vehicle1) {
        this(startingPoint, endingPoint, cargo, transportDate, vehicle1, null);
    }

    public static void showAllTransports() {
        System.out.println("Extent of the class: " + Transport.class.getName());

        for (Transport transport : allTransports) {
            System.out.println(transport);
        }
    }

    private static void addTransport(Transport transport) {
        allTransports.add(transport);
    }

    public void removeTransport() {
        remove(this);
    }

    private static void remove(Transport transport) {
        transport.removeVehicles();
        allTransports.remove(transport);
    }

    private void removeVehicles() {
        if (vehicle1 != null) {
            vehicle1.removeTransport(this);
            vehicle1 = null;
        }
        if (vehicle2 != null) {
            vehicle2.removeTransport(this);
            vehicle2 = null;
        }
    }

    public void removeVehicle(Vehicle vehicle) {
        if (vehicle == null){
            throw new IllegalArgumentException("Vehicle cannot be null");
        }

        if (vehicle == vehicle1) {
            if (vehicle2 == null) {
                throw new IllegalArgumentException("Cannot remove the vehicle. The transport must have at least one vehicle assigned.");
            }
            vehicle1.removeTransport(this);
            vehicle1 = null;
        } else if (vehicle == vehicle2) {
            if (vehicle1 == null) {
                throw new IllegalArgumentException("Cannot remove the vehicle. The transport must have at least one vehicle assigned.");
            }
            vehicle2.removeTransport(this);
            vehicle2 = null;
        } else {
            throw new IllegalArgumentException("This vehicle is not assigned to the transport");
        }
    }

    private void setVehicle1(Vehicle vehicle1) {
        this.vehicle1 = vehicle1;
    }

    private void setVehicle2(Vehicle vehicle2) {
        this.vehicle2 = vehicle2;
    }

    public void setVehicle(Vehicle vehicle) {
        if (this.vehicle1 == null) {
            setVehicle1(vehicle);
        } else if (this.vehicle2 == null) {
            setVehicle2(vehicle);
        } else {
            throw new IllegalStateException("Both vehicle1 and vehicle2 are already set.");
        }
    }

    public Vehicle getVehicle1() {
        return vehicle1;
    }

    public Vehicle getVehicle2() {
        return vehicle2;
    }

    @Override
    public String toString() {
        return "Transport: " +
                "StartingPoint: " + startingPoint +
                ", EndingPoint: " + endingPoint +
                ", Cargo: " + cargo +
                ", TransportDate: " + transportDate +
                ", Vehicle1: " + (vehicle1 != null ? vehicle1.getBrand() + " " + vehicle1.getLicencePlateNumber() : "No Vehicle") +
                ", Vehicle2: " + (vehicle2 != null ? vehicle2.getBrand() + " " + vehicle2.getLicencePlateNumber() : "No Vehicle");
    }
}
