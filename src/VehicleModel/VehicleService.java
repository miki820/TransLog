package VehicleModel;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The VehicleService class represents the service performed on a vehicle.
 * It implements Serializable to allow vehicle service objects to be serialized.
 */
public class VehicleService implements Serializable {
    private static final long serialVersionUID = 1L; // Version control for the serialized class

    private final Vehicle vehicle; // The vehicle that is being serviced
    private final Service service; // The service being performed on the vehicle

    private final LocalDate startOfService; // The start date of the service
    private final LocalDate endOfService; // The end date of the service

    /**
     * Constructor to create a new VehicleService object.
     *
     * @param vehicle The vehicle that is being serviced.
     * @param service The service being performed on the vehicle.
     * @param startOfService The start date of the service.
     * @param endOfService The end date of the service.
     */
    public VehicleService(Vehicle vehicle, Service service, LocalDate startOfService, LocalDate endOfService) {
        this.vehicle = vehicle;
        this.service = service;
        this.startOfService = startOfService;
        this.endOfService = endOfService;

        // Add this VehicleService to the vehicle and service objects
        vehicle.addVehicleService(this);
        service.addVehicleService(this);
    }

    /**
     * Returns the vehicle associated with this service.
     *
     * @return The vehicle.
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Returns the service being performed on the vehicle.
     *
     * @return The service.
     */
    public Service getService() {
        return service;
    }

    /**
     * Returns the start date of the service.
     *
     * @return The start date.
     */
    public LocalDate getStartOfService() {
        return startOfService;
    }

    /**
     * Returns the end date of the service.
     *
     * @return The end date.
     */
    public LocalDate getEndOfService() {
        return endOfService;
    }
}
