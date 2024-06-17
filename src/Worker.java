import java.time.LocalDate;
import java.time.Period;
import java.util.*;

enum EmployeeType{WORKER, DRIVER, MECHANIC}

public abstract class Worker {
    private String name;
    private String surname;

    private LocalDate birthDate;

    //Derived attribute
    private int age;
    private int seniority;
    private Optional<String> previousJob;
    private EnumSet<EmployeeType> employeeType = EnumSet.of(EmployeeType.WORKER);

    // Attribute for Mechanic class
    private String specialization;

    // Attribute for Driver class
    private int drivingLicenseNumber;

    // Attribute for Branch
    private Branch branch;

    // Each worker can have one experience
    private Experience experience;

    private static List<Worker> allWorkers = new ArrayList<>();
    private static Set<Experience> allExperiences = new HashSet<>();

    // Constructor with previous job
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
    }

    public static Worker createDriver(String name, String surname, LocalDate birthDate, int seniority, String previousJob, int drivingLicenseNumber, Branch branch) {
        Worker worker = new Worker(name, surname, birthDate, seniority, previousJob, branch){
            @Override
            public double countSalary() {
                return getDrivingLicenseNumber() * 1000;
            }
        };
        worker.employeeType.add(EmployeeType.DRIVER);
        worker.setDrivingLicenseNumber(drivingLicenseNumber);

        allWorkers.add(worker);
        if (branch != null) {
            branch.addWorker(worker);
        }
        return worker;
    }

    public static Worker createMechanic(String name, String surname, LocalDate birthDate, int seniority, String previousJob, String specialization, Branch branch){
        Worker worker = new Worker(name, surname, birthDate, seniority, previousJob, branch){
            @Override
            public double countSalary() {
                return getSpecialization().equals("trucks") ? 2000 : 1000;
            }
        };
        worker.employeeType.add(EmployeeType.MECHANIC);
        worker.setSpecialization(specialization);

        allWorkers.add(worker);
        if (branch != null) {
            branch.addWorker(worker);
        }
        return worker;
    }
    
    public static Worker createDriverMechanic(String name, String surname, LocalDate birthDate, int seniority, String previousJob, int drivingLicenseNumber, String specialization, Branch branch){
        Worker worker = new Worker(name, surname, birthDate, seniority, previousJob, branch){
            @Override
            public double countSalary() {
                return getSpecialization().equals("trucks") ? 2000 * getDrivingLicenseNumber() : 1000 * getDrivingLicenseNumber();
            }
        };
        worker.employeeType.add(EmployeeType.DRIVER);
        worker.employeeType.add(EmployeeType.MECHANIC);
        worker.setDrivingLicenseNumber(drivingLicenseNumber);
        worker.setSpecialization(specialization);

        allWorkers.add(worker);
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

    public static void showAllWorkers() {
        System.out.println("Extent of the class: " + Worker.class.getName());

        for (Worker worker : allWorkers) {
            System.out.println(worker);
        }
    }

    public static void showAllExperiences() {
        System.out.println("Extent of the class: " + Experience.class.getName());

        for (Experience experience : allExperiences) {
            System.out.println(experience);
        }
    }


    public abstract double countSalary();

    public static void removeWorker(Worker worker) {
        allWorkers.remove(worker);
        if (worker.branch != null) {
            worker.branch.removeWorker(worker);
        }
    }

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

    public String getSpecialization() {
        if(employeeType.contains(EmployeeType.MECHANIC)){
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
