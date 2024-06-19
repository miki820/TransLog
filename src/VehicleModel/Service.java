package VehicleModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Service class represents a service that can be performed on a vehicle.
 * It implements Serializable to allow service objects to be serialized.
 */
public class Service implements Serializable {
    private static final long serialVersionUID = 1L; // Version control for the serialized class

    private int price; // The price of the service
    private String description; // A description of the service

    // Static list to store all services (extent of the Service class)
    private static List<Service> allServices = new ArrayList<>();

    // List to store all vehicle services associated with this service
    private List<VehicleService> servicedVehicles = new ArrayList<>();

    /**
     * Constructor to create a new Service object.
     *
     * @param price The price of the service.
     * @param description A description of the service.
     */
    public Service(int price, String description) {
        this.price = price;
        this.description = description;

        // Add the new service to the list of all services
        addService(this);
    }

    /**
     * Adds a VehicleService to the list of serviced vehicles for this service.
     *
     * @param vehicleService The VehicleService to be added.
     */
    public void addVehicleService(VehicleService vehicleService) {
        servicedVehicles.add(vehicleService);
    }

    /**
     * Adds a service to the list of all services.
     *
     * @param service The Service object to be added.
     */
    private static void addService(Service service) {
        allServices.add(service);
    }

    /**
     * Gets the list of all vehicle services associated with this service.
     *
     * @return A list of VehicleService objects.
     */
    public List<VehicleService> getServicedVehicles() {
        return servicedVehicles;
    }

    /**
     * Gets the price of the service.
     *
     * @return The price of the service.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Gets the description of the service.
     *
     * @return The description of the service.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a string representation of the Service object.
     *
     * @return A string containing the price and description of the service.
     */
    @Override
    public String toString() {
        return "Service: " +
                "Price: " + price +
                ", Description: " + description;
    }
}
