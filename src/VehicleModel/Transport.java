package VehicleModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Transport implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;                    // Unique identifier for each transport
    private String startingPoint;         // Starting point of the transport
    private String endingPoint;           // Ending point of the transport
    private String cargo;                 // Cargo being transported
    private LocalDate transportDate;      // Date of the transport

    private static List<Transport> allTransports = new ArrayList<>();  // List to store all transports
    private List<Vehicle> vehicles = new ArrayList<>();                // List to store vehicles assigned to this transport

    // Flag to indicate if the transport is being removed
    private boolean isBeingRemoved = false;

    /**
     * Constructor for creating a new Transport.
     *
     * @param startingPoint  Starting location of the transport
     * @param endingPoint    Ending location of the transport
     * @param cargo          Cargo to be transported
     * @param transportDate  Date of the transport
     * @param vehicles       List of vehicles assigned to the transport
     */
    public Transport(String startingPoint, String endingPoint, String cargo, LocalDate transportDate, List<Vehicle> vehicles) {
        this.id = UUID.randomUUID().toString();  // Generate a unique ID for the transport
        if (vehicles == null || vehicles.isEmpty() || vehicles.size() > 2) {
            throw new IllegalArgumentException("VehicleModel.Transport must have at least 1 and at most 2 vehicles.");
        }

        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.cargo = cargo;
        this.transportDate = transportDate;

        for (Vehicle vehicle : vehicles) {
            if (!vehicle.getTransports().contains(this)) {
                vehicle.addTransportQualif(this);  // Add transport qualification to each vehicle
            }
        }

        addTransport(this);  // Add this transport to the list of all transports
    }

    /**
     * Adds a vehicle to the transport if there are less than 2 vehicles already assigned.
     *
     * @param vehicle  Vehicle to be added
     */
    public void addVehicle(Vehicle vehicle) {
        if (this.vehicles.size() >= 2) {
            throw new IllegalStateException("Cannot add more than 2 vehicles to transport.");
        }
        if (!this.vehicles.contains(vehicle)) {
            this.vehicles.add(vehicle);
            if (!vehicle.getTransports().contains(this)) {
                vehicle.addTransportQualif(this);  // Add transport qualification to the vehicle
            }
        }
    }

    /**
     * Removes a vehicle from the transport if there are more than 1 vehicle assigned.
     *
     * @param vehicle  Vehicle to be removed
     */
    public void removeVehicle(Vehicle vehicle) {
        if (this.vehicles.size() <= 1 && !isBeingRemoved) {
            throw new IllegalStateException("You can't delete vehicle because transport must have at least 1 vehicle.");
        }
        if (this.vehicles.contains(vehicle)) {
            this.vehicles.remove(vehicle);
            if (vehicle.getTransports().contains(this)) {
                vehicle.removeTransportQualif(this.id);  // Remove transport qualification from the vehicle
            }
        }
    }

    /**
     * Adds a transport to the list of all transports.
     *
     * @param transport  Transport to be added
     */
    private static void addTransport(Transport transport) {
        allTransports.add(transport);
    }

    /**
     * Removes the transport from the system, also removes the transport qualification from all assigned vehicles.
     */
    public void removeTransport() {
        isBeingRemoved = true;
        // Create a new list to avoid ConcurrentModificationException
        for (Vehicle vehicle : new ArrayList<>(vehicles)) {
            if (vehicle.getTransports().contains(this)) {
                vehicle.removeTransportQualif(this.getId());
            }
        }
        allTransports.remove(this);
    }

    /**
     * Displays all transports in the system.
     */
    public static void showAllTransports() {
        System.out.println("Extent of the class: " + Transport.class.getName());
        for (Transport transport : allTransports) {
            System.out.println(transport);
        }
    }

    // Getters
    public String getId() {
        return id;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public static List<Transport> getAllTransports() {
        return allTransports;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public String getCargo() {
        return cargo;
    }

    public LocalDate getTransportDate() {
        return transportDate;
    }

    // Write extent of vehicles and engines to a stream
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(allTransports);
    }

    // Read extent of vehicles and engines from a stream
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        allTransports = (ArrayList<Transport>) stream.readObject();
    }

    /**
     * Returns a string representation of the transport, including its details and assigned vehicles.
     *
     * @return String representation of the transport
     */
    @Override
    public String toString() {
        String vehicle1 = vehicles.size() >= 1 ? vehicles.get(0).getBrand() + " " + vehicles.get(0).getLicencePlateNumber() : "No Vehicle";
        String vehicle2 = vehicles.size() == 2 ? vehicles.get(1).getBrand() + " " + vehicles.get(1).getLicencePlateNumber() : "No Vehicle";

        return "VehicleModel.Transport: " +
                "ID: " + id +
                ", StartingPoint: " + startingPoint +
                ", EndingPoint: " + endingPoint +
                ", Cargo: " + cargo +
                ", TransportDate: " + transportDate +
                ", Vehicle1: " + vehicle1 +
                ", Vehicle2: " + vehicle2;
    }
}