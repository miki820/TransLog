import java.time.LocalDate;

public class VehicleService {
    private Vehicle vehicle;
    private Service service;

    private LocalDate startOfService;
    private LocalDate endOfService;

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
