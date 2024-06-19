package Extent;

import VehicleModel.Transport;
import VehicleModel.Vehicle;
import WorkerModel.Branch;
import WorkerModel.Worker;

import java.io.*;

/**
 * ExtentManager is a utility class responsible for managing the persistence
 * of the extents (collections of all instances) of various models in the application.
 * It provides methods to save and load the extents to/from a file, and to clear all data.
 */
public class ExtentManager {
    // The name of the file where the extents will be serialized
    private static final String FILE_NAME = "translog.ser";

    /**
     * Saves the extents of Vehicle, Worker, Branch, and Transport models to a file.
     * The extents are serialized using ObjectOutputStream.
     */
    public static void saveExtent() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            // Serialize and save the extent of each model
            Vehicle.writeExtent(out);
            Worker.writeExtent(out);
            Branch.writeExtent(out);
            Transport.writeExtent(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the extents of Vehicle, Worker, Branch, and Transport models from a file.
     * The extents are deserialized using ObjectInputStream.
     */
    public static void loadExtent() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            // Deserialize and load the extent of each model
            Vehicle.readExtent(in);
            Worker.readExtent(in);
            Branch.readExtent(in);
            Transport.readExtent(in);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears all data from the extents of Vehicle, Worker, Branch, and Transport models.
     * This is useful for resetting the application state.
     */
    public static void clearAllData() {
        // Clear all data from each model's extent
        Vehicle.getAllVehicles().clear();
        Vehicle.getAllEngines().clear();
        Worker.getAllWorkers().clear();
        Worker.getAllExperiences().clear();
        Branch.getAllBranches().clear();
        Transport.getAllTransports().clear();
    }
}
