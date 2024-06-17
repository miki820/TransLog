import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Service {
    private int price;
    private String description;

    // Extent to store all Services
    private static List<Service> allServices = new ArrayList<>();

    // List for all serviced vehicles
    private List<VehicleService> servicedVehicles = new ArrayList<>();

    public Service(int price, String description) {
        this.price = price;
        this.description = description;

        addService(this);
    }

    public void addVehicleService(VehicleService vehicleService){
        servicedVehicles.add(vehicleService);
    }

    private static void addService(Service service) {
        allServices.add(service);
    }

    public List<VehicleService> getServicedVehicles() {
        return servicedVehicles;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Service: " +
                "Price: " + price +
                ", Description: " + description;
    }
}
