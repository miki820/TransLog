package VehicleModel;

import WorkerModel.EmployeeType;
import WorkerModel.Worker;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String brand;
    private final String model;

    // Flag that informs if vehicle is under repair
    private boolean underRepair;

    // Set to check if Licence plate number is unique
    private static final Set<String> licencePlateNumberSet = new HashSet<>();
    private String licencePlateNumber;

    // Functions are a repeatable attribute
    private final List<String> functions = new ArrayList<>();

    // Engine is a composite attribute
    private Engine engine;

    // Extent to store all Vehicles
    private static List<Vehicle> allVehicles = new ArrayList<>();

    // List as a class attribute to store all engines to prohibit engine sharing and to have an extent of all engines
    private static List<Engine> allEngines = new ArrayList<>();

    // List to store all services for this vehicle
    private List<VehicleService> vehicleServices = new ArrayList<>();

    // List to store all assigned transports
    private List<Transport> transports = new ArrayList<>();

    // Map to do qualification association
    private Map<String, Transport> transportsQualif = new TreeMap<>();

    // Lists to store all assigned drivers and mechanics to vehicle
    private List<Worker> allDrivers = new ArrayList<>();
    private List<Worker> allMechanics = new ArrayList<>();

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

            // Add engine to the vehicle and to the set of all engines
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

    // Add vehicle to the extent of all vehicles
    private static void addVehicle(Vehicle vehicle) {
        allVehicles.add(vehicle);
        licencePlateNumberSet.add(vehicle.getLicencePlateNumber());
    }

    // Method that removes vehicle with its engine
    public static void removeVehicle(Vehicle vehicle) throws Exception {
        if (vehicle != null) {
            vehicle.removeEngine();
            allVehicles.remove(vehicle);
        } else {
            throw new Exception("This vehicle doesn't exist");
        }
    }

    // Check if a license plate number already exists
    private static boolean checkLicencePlateNumber(String licencePlateNumber) {
        return licencePlateNumberSet.contains(licencePlateNumber);
    }

    // Set the license plate number
    public void setLicencePlateNumber(String licencePlateNumber) {
        if (checkLicencePlateNumber(this.licencePlateNumber)) {
            throw new IllegalArgumentException("This licence plate number already exists");
        }
        licencePlateNumberSet.remove(this.licencePlateNumber);
        this.licencePlateNumber = licencePlateNumber;
        licencePlateNumberSet.add(this.licencePlateNumber);
    }

    // Add a function to the vehicle
    public void addFunction(String function) {
        if (function != null && !function.trim().isEmpty()) {
            functions.add(function);
        }
    }

    // Delete a function from the vehicle
    public void deleteFunction(String function) {
        if (!functions.contains(function)) {
            throw new IllegalArgumentException("Vehicle doesn't have this function");
        } else {
            functions.remove(function);
        }
    }

    // Display all vehicles
    public static void showAllVehicles() {
        System.out.println("Extent of the class: " + Vehicle.class.getName());
        for (Vehicle vehicle : allVehicles) {
            System.out.println(vehicle);
        }
    }

    // Display all engines
    public static void showAllEngines() {
        System.out.println("Extent of the class: " + Engine.class.getName());
        for (Engine engine : allEngines) {
            System.out.println(engine);
        }
    }

    // Write extent of vehicles and engines to a stream
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(allVehicles);
        stream.writeObject(allEngines);
    }

    // Read extent of vehicles and engines from a stream
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        allVehicles = (ArrayList<Vehicle>) stream.readObject();
        allEngines = (ArrayList<Engine>) stream.readObject();
    }

    // Abstract method to be implemented by subclasses for repair
    public abstract void repair();

    // Start repair of the vehicle
    public void startRepair(Worker mechanic) {
        if (underRepair) {
            throw new IllegalStateException("Vehicle is already under repair");
        }
        if (allMechanics.size() >= 2) {
            throw new IllegalStateException("Vehicle cannot be repaired by more than 2 mechanics.");
        }
        if (!allMechanics.contains(mechanic)) {
            allMechanics.add(mechanic);
        }
        if (mechanic == null || !mechanic.getEmployeeType().contains(EmployeeType.MECHANIC)) {
            throw new IllegalArgumentException("A valid mechanic must be assigned to start the repair");
        }
        underRepair = true;
        mechanic.addRepairedVehicle(this);
        System.out.println("Vehicle is now under repair by " + mechanic.getName());
    }

    // Simulate repair process
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

    // End repair of the vehicle
    public void endRepair() {
        if (!underRepair) {
            throw new IllegalStateException("Vehicle is not under repair");
        }
        underRepair = false;
        for (Worker mechanic : allMechanics) {
            mechanic.removeRepairedVehicle(this);
        }
        System.out.println("Vehicle repair completed");
    }

    // Check repair status of the vehicle
    public void checkRepairStatus() {
        if (underRepair) {
            throw new IllegalStateException("Vehicle is under repair and cannot be used.");
        }
    }

    // Add a service to the vehicle
    public void addVehicleService(VehicleService vehicleService) {
        vehicleServices.add(vehicleService);
    }

    // Get all services of the vehicle
    public List<VehicleService> getVehicleServices() {
        return vehicleServices;
    }

    // Add a transport qualification to the vehicle
    public void addTransportQualif(Transport newTransport) {
        if (!transportsQualif.containsKey(newTransport.getId())) {
            transportsQualif.put(newTransport.getId(), newTransport);

            if (!newTransport.getVehicles().contains(this)) {
                newTransport.addVehicle(this);
            }
        }
    }

    // Find a transport qualification by ID
    public Transport findTransportQualif(String id) throws Exception {
        if (transportsQualif.containsKey(id)) {
            throw new Exception("Unable to find this transport: " + id);
        }

        return transportsQualif.get(id);
    }

    // Remove a transport qualification by ID
    public void removeTransportQualif(String id){
        if (transportsQualif.containsKey(id)) {
            Transport transport = transportsQualif.remove(id);
            if (transport.getVehicles().contains(this)) {
                transport.removeVehicle(this);
            }
        } else {
            throw new IllegalArgumentException("Transport with id: " + id + " not found.");
        }
    }

    // Add a driver to the vehicle
    public void addDriver(Worker driver){
        if (allDrivers.size() >= 2) {
            throw new IllegalStateException("Vehicle cannot have more than 2 drivers.");
        }
        if(!allDrivers.contains(driver)){
            allDrivers.add(driver);
            driver.addDrivenVehicle(this);
        }
    }

    // Remove a driver from the vehicle
    public void removeDriver(Worker driver) {
        if (allDrivers.contains(driver)) {
            allDrivers.remove(driver);
            driver.removeDrivenVehicle(this);
        }
    }

    // Add a mechanic to the vehicle
    public void addMechanic(Worker mechanic){
        if (allMechanics.size() >= 2) {
            throw new IllegalStateException("Vehicle cannot be repaired by more than 2 mechanics.");
        }
        if(!allMechanics.contains(mechanic)){
            allMechanics.add(mechanic);
            mechanic.addRepairedVehicle(this);
        }
    }

    // Remove a mechanic from the vehicle
    public void removeMechanic(Worker mechanic) {
        if (allMechanics.contains(mechanic)) {
            allMechanics.remove(mechanic);
            mechanic.removeRepairedVehicle(this);
        }
    }

    // Get all transports of the vehicle
    public List<Transport> getTransports() {
        return transports;
    }

    // Get brand of the vehicle
    public String getBrand() {
        return brand;
    }

    // Get model of the vehicle
    public String getModel() {
        return model;
    }

    // Get engine of the vehicle
    public Engine getEngine() {
        return engine;
    }

    // Get license plate number of the vehicle
    public String getLicencePlateNumber() {
        return licencePlateNumber;
    }

    // Get all vehicles
    public static List<Vehicle> getAllVehicles() {
        return allVehicles;
    }

    // Get all engines
    public static List<Engine> getAllEngines() {
        return allEngines;
    }

    // Get all trucks
    public static List<Truck> getAllTrucks() {
        List<Truck> trucks = new ArrayList<>();
        for (Vehicle vehicle : allVehicles) {
            if (vehicle instanceof Truck) {
                trucks.add((Truck) vehicle);
            }
        }
        return trucks;
    }

    // Get all delivery trucks
    public static List<DeliveryTruck> getAllDeliveryTrucks() {
        List<DeliveryTruck> deliveryTrucks = new ArrayList<>();
        for (Vehicle vehicle : allVehicles) {
            if (vehicle instanceof DeliveryTruck) {
                deliveryTrucks.add((DeliveryTruck) vehicle);
            }
        }
        return deliveryTrucks;
    }

    // Get all drivers
    public List<Worker> getAllDrivers() {
        return allDrivers;
    }

    // Get all mechanics
    public List<Worker> getAllMechanics() {
        return allMechanics;
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
