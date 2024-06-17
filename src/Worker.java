import java.time.LocalDate;
import java.time.Period;
import java.util.EnumSet;
import java.util.Optional;

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

    /*private List<Driver> drivers;
    private List<Mechanic> mechanics;*/

    // Constructor with previous job
    public Worker(String name, String surname, LocalDate birthDate, int seniority, String previousJob) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.age = Period.between(this.birthDate, LocalDate.now()).getYears();
        if (this.age < 18) {
            throw new IllegalArgumentException("Age must be at least 18 years.");
        }
        this.seniority = seniority;
        this.previousJob = Optional.ofNullable(previousJob);
    }

    public static Worker createDriver(String name, String surname, LocalDate birthDate, int seniority, String previousJob, int drivingLicenseNumber) {
        Worker worker = new Worker(name, surname, birthDate, seniority, previousJob){
            @Override
            public double countSalary() {
                return getDrivingLicenseNumber() * 1000;
            }
        };
        worker.employeeType.add(EmployeeType.DRIVER);
        worker.setDrivingLicenseNumber(drivingLicenseNumber);

        return worker;
    }

    public static Worker createMechanic(String name, String surname, LocalDate birthDate, int seniority, String previousJob, String specialization){
        Worker worker = new Worker(name, surname, birthDate, seniority, previousJob){
            @Override
            public double countSalary() {
                return getSpecialization().equals("trucks") ? 2000 : 1000;
            }
        };
        worker.employeeType.add(EmployeeType.MECHANIC);
        worker.setSpecialization(specialization);
        
        return worker;
    }
    
    public static Worker createDriverMechanic(String name, String surname, LocalDate birthDate, int seniority, String previousJob, int drivingLicenseNumber, String specialization){
        Worker worker = new Worker(name, surname, birthDate, seniority, previousJob){
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

    public abstract double countSalary();

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
}
