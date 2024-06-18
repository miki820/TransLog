package VehicleModel;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Truck extends Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int capacity;

    public Truck(String brand, String model, String licencePlateNumber, int capacity, String... functions) {
        super(brand, model, licencePlateNumber, functions);
        this.capacity = capacity;
    }

    public static void showAllVehicles() {
        System.out.println("Extent of the class: " + Truck.class.getName());

        for (Vehicle vehicle : getAllVehicles()) {
            if (vehicle instanceof Truck truck){
                System.out.println(truck);
            }
        }
    }

    public static void showAllEngines() {
        System.out.println("Extent of the class: " + Engine.class.getName());

        for (Engine engine : getAllEngines()) {
            if (engine.getVehicle() instanceof Truck) {
                System.out.println(engine);
            }
        }
    }

    @Override
    public void repair() {
        for (int i = 0; i < this.getAllMechanics().size(); i++) {
            startRepair(this.getAllMechanics().get(i));
        }
        simulateRepairProcess(5000, TimeUnit.MILLISECONDS);
        endRepair();
    }

    @Override
    public String toString() {
        return "VehicleModel.Truck: " + super.toString() + ", capacity: " + capacity;
    }
}
