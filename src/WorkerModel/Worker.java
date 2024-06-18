package WorkerModel;

import VehicleModel.Vehicle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public abstract class Worker implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String surname;
    private LocalDate birthDate;

    // Derived attribute for age
    private int age;
    private int seniority;
    private transient Optional<String> previousJob;
    private String previousJobSerialized;
    private EnumSet<EmployeeType> employeeType = EnumSet.of(EmployeeType.WORKER);

    // Attribute for Mechanic class
    private String specialization;

    // Attribute for Driver class
    private int drivingLicenseNumber;

    // Attribute for Branch
    private Branch branch;

    // Each worker can have one experience
    private Experience experience;

    // Static list to store all workers
    private static List<Worker> allWorkers = new ArrayList<>();

    // Static set to store all experiences
    private static Set<Experience> allExperiences = new HashSet<>();

    // Lists to store vehicles driven and repaired by the worker
    private List<Vehicle> drivenVehicles = new ArrayList<>();
    private List<Vehicle> repairedVehicles = new ArrayList<>();

    public Worker(String name, String surname, LocalDate birthDate, int seniority, String previousJob, Branch branch) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.age = Period.between(this.birthDate, LocalDate.now()).getYears();
        if (this.age < 18) {
            throw new IllegalArgumentException("Age must be at least 18 years.");
        }
        this.seniority = seniority;
        this.previousJob = Optional.ofNullable(previousJob);
        this.branch = branch;

        allWorkers.add(this);
        if (branch != null) {
            branch.addWorker(this);
        }

        this.experience = new Experience(this);
        allExperiences.add(experience);
    }

    // Static factory method to create a Driver
    public static Worker createDriver(String name, String surname, LocalDate birthDate, int seniority, String previousJob, int drivingLicenseNumber, Branch branch) {
        Worker worker = new Worker(name, surname, birthDate, seniority, previousJob, branch) {
            @Override
            public double countSalary() {
                return getDrivingLicenseNumber() * 1000;
            }
        };
        worker.employeeType.add(EmployeeType.DRIVER);
        worker.setDrivingLicenseNumber(drivingLicenseNumber);

        if (branch != null) {
            branch.addWorker(worker);
        }
        return worker;
    }

    // Static factory method to create a Mechanic
    public static Worker createMechanic(String name, String surname, LocalDate birthDate, int seniority, String previousJob, String specialization, Branch branch) {
        Worker worker = new Worker(name, surname, birthDate, seniority, previousJob, branch) {
            @Override
            public double countSalary() {
                return getSpecialization().equals("trucks") ? 2000 : 1000;
            }
        };
        worker.employeeType.add(EmployeeType.MECHANIC);
        worker.setSpecialization(specialization);

        if (branch != null) {
            branch.addWorker(worker);
        }
        return worker;
    }

    // Static factory method to create a DriverMechanic
    public static Worker createDriverMechanic(String name, String surname, LocalDate birthDate, int seniority, String previousJob, int drivingLicenseNumber, String specialization, Branch branch) {
        Worker worker = new Worker(name, surname, birthDate, seniority, previousJob, branch) {
            @Override
            public double countSalary() {
                return getSpecialization().equals("trucks") ? 2000 * getDrivingLicenseNumber() : 1000 * getDrivingLicenseNumber();
            }
        };
        worker.employeeType.add(EmployeeType.DRIVER);
        worker.employeeType.add(EmployeeType.MECHANIC);
        worker.setDrivingLicenseNumber(drivingLicenseNumber);
        worker.setSpecialization(specialization);

        return worker;
    }

    // Method that adds experience to a worker
    public void addExperience(Experience experience) throws Exception {
        if (this.experience == null) {
            if (allExperiences.contains(experience)) {
                throw new Exception("This experience is already connected with some worker.");
            }

            this.experience = experience;
            allExperiences.add(experience);

        } else {
            throw new Exception("Experience already exists for this worker");
        }
    }

    // Method that removes only experience from a worker
    public void removeExperience() {
        if (this.experience != null) {
            allExperiences.remove(this.experience);
            experience = null;
        }
    }

    public Experience getExperience() {
        return experience;
    }

    // Abstract method to be implemented by subclasses for salary calculation
    public abstract double countSalary();

    // Static method to remove a worker
    public static void removeWorker(Worker worker) {
        allWorkers.remove(worker);
        if (worker.branch != null) {
            worker.branch.removeWorker(worker);
        }
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        this.age = Period.between(this.birthDate, LocalDate.now()).getYears();
        if (this.age < 18) {
            throw new IllegalArgumentException("Age must be at least 18 years.");
        }
    }

    public int getAge() {
        return calculateAge();
    }

    public int calculateAge() {
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }

    public int getSeniority() {
        return seniority;
    }

    public void setSeniority(int seniority) {
        this.seniority = seniority;
    }

    public Optional<String> getPreviousJob() {
        return previousJob;
    }

    public void clearPreviousJob() {
        this.previousJob = Optional.empty();
    }

    public void setPreviousJob(String previousJob) {
        this.previousJob = Optional.of(previousJob);
    }

    public EnumSet<EmployeeType> getEmployeeType() {
        return employeeType;
    }

    public String getSpecialization() {
        if (employeeType.contains(EmployeeType.MECHANIC)) {
            return specialization;
        }
        throw new IllegalArgumentException("This Worker is not a Mechanic");
    }

    public void setSpecialization(String specialization) {
        if (employeeType.contains(EmployeeType.MECHANIC)) {
            this.specialization = specialization;
        } else {
            throw new IllegalArgumentException("This Worker is not a Mechanic");
        }
    }

    public int getDrivingLicenseNumber() {
        if (employeeType.contains(EmployeeType.DRIVER)) {
            return drivingLicenseNumber;
        }
        throw new IllegalArgumentException("This Worker is not a Driver");
    }

    public void setDrivingLicenseNumber(int drivingLicenseNumber) {
        if (employeeType.contains(EmployeeType.DRIVER)) {
            this.drivingLicenseNumber = drivingLicenseNumber;
        } else {
            throw new IllegalArgumentException("This Worker is not a Driver");
        }
    }

    // Methods to add and remove driven vehicles
    public void addDrivenVehicle(Vehicle vehicle) {
        if (!employeeType.contains(EmployeeType.DRIVER)) {
            throw new IllegalStateException("This worker is not a driver.");
        }
        if (!drivenVehicles.contains(vehicle)) {
            drivenVehicles.add(vehicle);
            vehicle.addDriver(this);
        }
    }

    public void removeDrivenVehicle(Vehicle vehicle) {
        if (drivenVehicles.contains(vehicle)) {
            drivenVehicles.remove(vehicle);
            vehicle.removeDriver(this);
        }
    }

    // Methods to add and remove repaired vehicles
    public void addRepairedVehicle(Vehicle vehicle) {
        if (!employeeType.contains(EmployeeType.MECHANIC)) {
            throw new IllegalStateException("This worker is not a mechanic.");
        }
        if (!repairedVehicles.contains(vehicle)) {
            repairedVehicles.add(vehicle);
            vehicle.addMechanic(this);
        }
    }

    public void removeRepairedVehicle(Vehicle vehicle) {
        if (repairedVehicles.contains(vehicle)) {
            repairedVehicles.remove(vehicle);
            vehicle.removeMechanic(this);
        }
    }

    // Display all workers
    public static void showAllWorkers() {
        System.out.println("Extent of the class: " + Worker.class.getName());
        for (Worker worker : allWorkers) {
            System.out.println(worker);
        }
    }

    // Display all experiences
    public static void showAllExperiences() {
        System.out.println("Extent of the class: " + Experience.class.getName());
        for (Experience experience : allExperiences) {
            System.out.println(experience);
        }
    }

    // Methods to handle optional attribute
    private void writeObject(ObjectOutputStream oos) throws IOException {
        previousJobSerialized = previousJob.orElse(null);
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        previousJob = Optional.ofNullable(previousJobSerialized);
    }

    // Methods to write and read extent
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(allWorkers);
        stream.writeObject(allExperiences);
    }

    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        allWorkers = (ArrayList<Worker>) stream.readObject();
        allExperiences = (Set<Experience>) stream.readObject();
    }

    // Getters for static lists
    public static List<Worker> getAllWorkers() {
        return allWorkers;
    }

    public static Set<Experience> getAllExperiences() {
        return allExperiences;
    }

    // Getters for driven and repaired vehicles
    public List<Vehicle> getDrivenVehicles() {
        return drivenVehicles;
    }

    public List<Vehicle> getRepairedVehicles() {
        return repairedVehicles;
    }

    @Override
    public String toString() {
        String type = "Worker";
        if (employeeType.contains(EmployeeType.DRIVER) && employeeType.contains(EmployeeType.MECHANIC)) {
            type = "DriverMechanic";
        } else if (employeeType.contains(EmployeeType.DRIVER)) {
            type = "Driver";
        } else if (employeeType.contains(EmployeeType.MECHANIC)) {
            type = "Mechanic";
        }

        return  "Type: " + type +
                ", Name: " + name +
                ", Surname: " + surname +
                ", BirthDate: " + birthDate +
                ", Age: " + age +
                ", Seniority: " + seniority +
                ", PreviousJob: " + previousJob.orElse("None") +
                ", Specialization: " + (employeeType.contains(EmployeeType.MECHANIC) ? specialization : "None") +
                ", DrivingLicenseNumber: " + (employeeType.contains(EmployeeType.DRIVER) ? drivingLicenseNumber : "None") +
                ", Branch: " + (branch != null ? branch.getName() : "None");
    }
}
