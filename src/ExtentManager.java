import java.io.*;

public class ExtentManager {
    private static final String FILE_NAME = "translog.ser";

    public static void saveExtent() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            Vehicle.writeExtent(out);
            Worker.writeExtent(out);
            Branch.writeExtent(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadExtent() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Vehicle.readExtent(in);
            Worker.readExtent(in);
            Branch.readExtent(in);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void clearAllData() {
        Vehicle.getAllVehicles().clear();
        Vehicle.getAllEngines().clear();
        Worker.getAllWorkers().clear();
        Worker.getAllExperiences().clear();
        Branch.getAllBranches().clear();
    }
}
