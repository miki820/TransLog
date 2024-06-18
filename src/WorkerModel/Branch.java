package WorkerModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class Branch implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String address;
    public static int openHours = 10;
    public static int closeHours = 18;
    private static Set<Branch> allBranches = new TreeSet<>(new BranchComparator());
    private List<Worker> workers = new ArrayList<>();

    public Branch(String name, String address) {
        this.name = name;
        this.address = address;

        addBranch(this);
    }

    // Add a branch to the extent of all branches
    private static void addBranch(Branch branch) {
        allBranches.add(branch);
    }

    // Remove a branch from the extent of all branches
    public static void removeBranch(Branch branch) {
        if (branch != null) {
            branch.removeAllWorkers();
            allBranches.remove(branch);
        } else {
            throw new IllegalArgumentException("This branch doesn't exist");
        }
    }

    // Remove all workers from the branch
    private void removeAllWorkers() {
        for (Worker worker : workers) {
            Worker.removeWorker(worker);
        }
        workers.clear();
    }

    // Add a worker to the branch
    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    // Remove a worker from the branch
    public void removeWorker(Worker worker) {
        workers.remove(worker);
        Worker.removeWorker(worker);
    }

    // Get the name of the branch
    public String getName() {
        return name;
    }

    // Display all branches
    public static void showAllBranches() {
        System.out.println("Extent of the class: " + Branch.class.getName());

        for (Branch branch : allBranches) {
            System.out.println(branch);
        }
    }

    // Default comparator for branches
    private static class BranchComparator implements Comparator<Branch>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(Branch o1, Branch o2) {
            return o2.getName().compareTo(o1.getName());
        }
    }

    // Write extent of branches to a stream
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(allBranches);
    }

    // Read extent of branches from a stream
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        allBranches = (Set<Branch>) stream.readObject();
    }

    // Get all branches
    public static Set<Branch> getAllBranches() {
        return allBranches;
    }

    @Override
    public String toString() {
        return "Branch: " +
                "Name: " + name +
                ", Address: " + address;
    }
}
