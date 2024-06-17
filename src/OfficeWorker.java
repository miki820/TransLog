import java.time.LocalDate;

public class OfficeWorker extends Worker {
    private String position;
    public OfficeWorker(String name, String surname, LocalDate birthDate, int seniority, String previousJob, String position) {
        super(name, surname, birthDate, seniority, previousJob);
        this.position = position;
    }

    @Override
    public double countSalary() {
        return position.equals("Dyrektor") ? 2000 : 1000;
    }

    @Override
    public String toString() {
        return "OfficeWorker: " + super.toString() +
                ", Position: " + position;
    }
}
