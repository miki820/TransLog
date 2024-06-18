package VehicleModel;

import VehicleModel.Service;
import VehicleModel.Vehicle;

import java.io.Serializable;
import java.time.LocalDate;

public class VehicleService implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Vehicle vehicle;
    private final Service service;

    private final LocalDate startOfService;
    private final LocalDate endOfService;

    public VehicleService(Vehicle vehicle, Service service, LocalDate startOfService, LocalDate endOfService) {
        this.vehicle = vehicle;
        this.service = service;
        this.startOfService = startOfService;
        this.endOfService = endOfService;

        vehicle.addVehicleService(this);
        service.addVehicleService(this);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Service getService() {
        return service;
    }

    public LocalDate getStartOfService() {
        return startOfService;
    }

    public LocalDate getEndOfService() {
        return endOfService;
    }
}
