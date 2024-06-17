import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Truck truck = new Truck("Toyota", "Avensis", "WGR 07487", 500, "Muzyka", "Szyberdach");
        Engine engine = Engine.createEngine(truck, "v12");

        DeliveryTruck deliveryTruck = new DeliveryTruck("BMW", "M4", "WGR123", 100);
        Engine engine2 = Engine.createEngine(deliveryTruck, "V8");

        List<Vehicle> vehicleList = Arrays.asList(truck);

        Transport transport = new Transport("Wwa", "Wrocław", "ryby", LocalDate.now(), vehicleList);

        Branch branch = new Branch("Główna", "WWa");

        OfficeWorker officeWorker = new OfficeWorker("Adam", "Adach", LocalDate.of(2002, 10, 2), 1, "Lodziarnia", branch, "Dyrektor");
        Worker mechanic = Worker.createMechanic("Bartek", "Wąski", LocalDate.of(2002, 10, 2), 1, "Lodziarnia", "trucks", branch);
        Worker driver = Worker.createDriver("Rafal", "Malachowski", LocalDate.of(2002, 10, 2), 1, "Lodziarnia", 2, branch);

        Experience seniorExperience = new Experience(driver);
        Experience juniorExperience = new Experience(mechanic);

        Senior senior = Senior.createSenior(seniorExperience, 1000);
        Junior junior = Junior.createJunior(juniorExperience, 4);

        System.out.println(Worker.getAllWorkers());
        System.out.println(Worker.getAllExperiences());

        ExtentManager.saveExtent();
        ExtentManager.clearAllData();
        ExtentManager.loadExtent();

        Vehicle.showAllVehicles();
        Vehicle.showAllEngines();
        Worker.showAllWorkers();
        Worker.showAllExperiences();
        Branch.showAllBranches();
    }
}