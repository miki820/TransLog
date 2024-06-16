import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract class Vehicle {
    private final String brand;
    private final String model;
    private boolean underRepair;

    // Set to check if Licence plate number is unique
    private static final Set<String> licencePlateNumberSet = new HashSet<>();
    private String licencePlateNumber;

    // Functions are a repeatable attribute
    private final List<String> functions = new ArrayList<>();

    // Engine is a composite attribute
    private Engine engine;

    // Extent to store all Vehicles
    private static final List<Vehicle> allVehicles = new ArrayList<>();

    // List as a class attribute to store all engines to prohibit engine sharing and to have an extent of all engines
    private static final List<Engine> allEngines = new ArrayList<>();

    // List to store all services for this vehicle
    private List<VehicleService> vehicleServices = new ArrayList<>();

    // List to store all transports
    private List<Transport> transports = new ArrayList<>();

    public Vehicle(String brand, String model, String licencePlateNumber, String... functions) {
        this.brand = brand;
        this.model = model;
        if (checkLicencePlateNumber(licencePlateNumber)) {
            throw new IllegalArgumentException("This licence plate number already exists");
        }
        this.licencePlateNumber = licencePlateNumber;
        for (String function : functions) {
            addFunction(function);
        }
        this.underRepair = false;
        addVehicle(this);
    }

    // Method that adds engine to a vehicle
    public void addEngine(Engine engine) throws Exception {
        // Check if the vehicle has an engine
        if (this.engine == null) {

            // Check if the engine has been already added to any vehicle
            if (allEngines.contains(engine)) {
                throw new Exception("This engine is already connected with some vehicle.");
            }

            // We add an engine and add it to set of all engines so that we know it is used
            this.engine = engine;
            allEngines.add(engine);

        } else {
            throw new Exception("Engine already exists in this vehicle");
        }
    }

    // Method that removes only engine from a vehicle
    public void removeEngine() {
        if (this.engine != null) {
            allEngines.remove(this.engine);
            engine = null;
        }
    }

    private static void addVehicle(Vehicle vehicle) {
        allVehicles.add(vehicle);
        licencePlateNumberSet.add(vehicle.getLicencePlateNumber());
    }

    // Method that removes vehicle with its engine
    public static void removeVehicle(Vehicle vehicle) throws Exception {
        if (vehicle != null) {
            vehicle.removeEngine();
            licencePlateNumberSet.remove(vehicle.getLicencePlateNumber());
            remove(vehicle);
        } else {
            throw new Exception("This vehicle doesn't exists");
        }
    }

    private static void remove(Vehicle vehicle) {
        allVehicles.remove(vehicle);
    }

    public static void clear() {
        allVehicles.clear();
        allEngines.clear();
    }

    private static boolean checkLicencePlateNumber(String licencePlateNumber) {
        return licencePlateNumberSet.contains(licencePlateNumber);
    }

    public void setLicencePlateNumber(String licencePlateNumber) {
        if (checkLicencePlateNumber(this.licencePlateNumber)) {
            throw new IllegalArgumentException("This licence plate number already exists");
        }
        licencePlateNumberSet.remove(this.licencePlateNumber);
        this.licencePlateNumber = licencePlateNumber;
        licencePlateNumberSet.add(this.licencePlateNumber);
    }

    public void addFunction(String function) {
        if (function != null && !function.trim().isEmpty()) {
            functions.add(function);
        }
    }

    public void deleteFunction(String function) {
        if (!functions.contains(function)) {
            throw new IllegalArgumentException("Vehicle doesn't have this function");
        } else {
            functions.remove(function);
        }
    }

    public static void showAllVehicles() {
        System.out.println("Extent of the class: " + Vehicle.class.getName());

        for (Vehicle vehicle : allVehicles) {
            System.out.println(vehicle);
        }
    }

    public static void showAllEngines() {
        System.out.println("Extent of the class: " + Engine.class.getName());

        for (Engine engine : allEngines) {
            System.out.println(engine);
        }
    }

    public abstract void repair();

    public void startRepair() {
        if (underRepair) {
            throw new IllegalStateException("Vehicle is already under repair");
        }
        underRepair = true;
        System.out.println("Vehicle is now under repair");
    }

    public void simulateRepairProcess(long duration, TimeUnit timeUnit) {
        try {
            System.out.println("Repairing: " + brand + " " + model);
            Thread.sleep(timeUnit.toMillis(duration));
            System.out.println("Vehicle repair completed.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted while repairing vehicle.");
        }
    }

    public void endRepair() {
        if (!underRepair) {
            throw new IllegalStateException("Vehicle is not under repair");
        }
        underRepair = false;
        System.out.println("Vehicle repair completed");
    }

    public void checkRepairStatus() {
        if (underRepair) {
            throw new IllegalStateException("Vehicle is under repair and cannot be used.");
        }
    }

    public void addVehicleService(VehicleService vehicleService) {
        vehicleServices.add(vehicleService);
    }

    public List<VehicleService> getVehicleServices() {
        return vehicleServices;
    }

    public void assignTransport(Transport transport) {
        if (transport.getVehicle1() != null && transport.getVehicle2() != null) {
            throw new IllegalArgumentException("This transport has already two vehicles assigned");
        } else if (!transports.contains(transport)) {
            transports.add(transport);
            if(transport.getVehicle1() == null){
                transport.setVehicle(this);
            } else if (transport.getVehicle2() == null){
                transport.setVehicle(this);
            }
        } else {
            throw new IllegalArgumentException("This vehicle is already assigned to this transport");
        }
    }

    public void removeTransport(Transport transport) {
        if (transport == null) {
            throw new IllegalArgumentException("Transport cannot be null");
        } else {
            if (!transports.contains(transport)) {
                throw new IllegalArgumentException("There is no such transport assigned to this vehicle");
            } else {
                transports.remove(transport);
                if (transport.getVehicle1() == this) {
                    transport.setVehicle1(null);
                } else if (transport.getVehicle2() == this) {
                    transport.setVehicle1(null);
                }
            }
        }
    }

    public List<Transport> getAllTransports() {
        return transports;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Engine getEngine() {
        return engine;
    }

    public String getLicencePlateNumber() {
        return licencePlateNumber;
    }

    public static List<Vehicle> getAllVehicles() {
        return allVehicles;
    }

    public static List<Engine> getAllEngines() {
        return allEngines;
    }

    public static List<Truck> getAllTrucks() {
        List<Truck> trucks = new ArrayList<>();
        for (Vehicle vehicle : allVehicles) {
            if (vehicle instanceof Truck) {
                trucks.add((Truck) vehicle);
            }
        }
        return trucks;
    }

    public static List<DeliveryTruck> getAllDeliveryTrucks() {
        List<DeliveryTruck> deliveryTrucks = new ArrayList<>();
        for (Vehicle vehicle : allVehicles) {
            if (vehicle instanceof DeliveryTruck) {
                deliveryTrucks.add((DeliveryTruck) vehicle);
            }
        }
        return deliveryTrucks;
    }

    @Override
    public String toString() {
        String engineInfo = (engine != null) ? getEngine().getName() : "No engine";
        String functionsInfo;

        if (functions.isEmpty()) {
            functionsInfo = "No Functions";
        } else {
            StringBuilder functionsBuilder = new StringBuilder();
            for (String function : functions) {
                functionsBuilder.append(function).append(" ");
            }
            functionsInfo = functionsBuilder.toString().trim();
        }

        return "Brand: " + getBrand() + ", Model: " + getModel() + ", Engine: " + engineInfo + ", Plate Number: " + licencePlateNumber + ", Functions: " + functionsInfo;
    }
}
