package VehicleModel;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class DeliveryTruck extends Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int maxCargoVolume;

    public DeliveryTruck(String brand, String model, String licencePlateNumber, int maxCargoVolume, String... functions) {
        super(brand, model, licencePlateNumber, functions);
        this.maxCargoVolume = maxCargoVolume;
    }

    // Display all vehicles of type DeliveryTruck
    public static void showAllVehicles() {
        System.out.println("Extent of the class: " + DeliveryTruck.class.getName());

        for (Vehicle vehicle : getAllVehicles()) {
            if (vehicle instanceof DeliveryTruck deliveryTruck) {
                System.out.println(deliveryTruck);
            }
        }
    }

    // Display all engines of type DeliveryTruck
    public static void showAllEngines() {
        System.out.println("Extent of the class: " + Engine.class.getName());

        for (Engine engine : getAllEngines()) {
            if (engine.getVehicle() instanceof DeliveryTruck) {
                System.out.println(engine);
            }
        }
    }

    @Override
    public void repair() {
        for (int i = 0; i < this.getAllMechanics().size(); i++) {
            startRepair(this.getAllMechanics().get(i));
        }
        simulateRepairProcess(3000, TimeUnit.MILLISECONDS);
        endRepair();
    }

    @Override
    public String toString() {
        return "Delivery Truck: " + super.toString() + ", maxCargoVolume: " + maxCargoVolume;
    }
}
