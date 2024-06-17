import java.util.*;

public class Branch {
    private String name;
    private String address;
    public static int openHours = 10;
    public static int closeHours = 18;
    private static Set<Branch> allBranches = new TreeSet<>((o1, o2) -> o2.getName().compareTo(o1.getName()));
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

    @Override
    public String toString() {
        return "Branch: " +
                "Name: " + name +
                ", Address: " + address;
    }
}
