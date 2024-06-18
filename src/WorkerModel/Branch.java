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

    private static void addBranch(Branch branch) {
        allBranches.add(branch);
    }

    public static void removeBranch(Branch branch) {
        if (branch != null) {
            branch.removeAllWorkers();
            allBranches.remove(branch);
        } else {
            throw new IllegalArgumentException("This branch doesn't exist");
        }
    }

    private void removeAllWorkers() {
        for (Worker worker : workers) {
            Worker.removeWorker(worker);
        }
        workers.clear();
    }

    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    public void removeWorker(Worker worker) {
        workers.remove(worker);
        Worker.removeWorker(worker);
    }

    public String getName() {
        return name;
    }

    public static void showAllBranches() {
        System.out.println("Extent of the class: " + Branch.class.getName());

        for (Branch branch : allBranches) {
            System.out.println(branch);
        }
    }

    // Default comparator is not serializable
    private static class BranchComparator implements Comparator<Branch>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(Branch o1, Branch o2) {
            return o2.getName().compareTo(o1.getName());
        }
    }

    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(allBranches);
    }

    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        allBranches = (Set<Branch>) stream.readObject();
    }

    public static Set<Branch> getAllBranches() {
        return allBranches;
    }

    @Override
    public String toString() {
        return "WorkerModel.Branch: " +
                "Name: " + name +
                ", Address: " + address;
    }
}
