import java.util.ArrayList;
import java.util.List;

public class Service {
    private int price;
    private String description;

    // List for all serviced vehicles
    private List<VehicleService> servicedVehicles = new ArrayList<>();

    public Service(int price, String description) {
        this.price = price;
        this.description = description;
    }

    public void addVehicleService(VehicleService vehicleService){
        servicedVehicles.add(vehicleService);
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
}
