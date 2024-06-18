package WorkerModel;

import java.io.Serializable;
import java.time.LocalDate;

public class OfficeWorker extends Worker implements Serializable {
    private static final long serialVersionUID = 1L;

    private String position;
    public OfficeWorker(String name, String surname, LocalDate birthDate, int seniority, String previousJob, Branch branch, String position) {
        super(name, surname, birthDate, seniority, previousJob, branch);
        this.position = position;
    }

    @Override
    public double countSalary() {
        return position.equals("Dyrektor") ? 2000 : 1000;
    }

    @Override
    public String toString() {
        return "WorkerModel.OfficeWorker: " + super.toString() +
                ", Position: " + position;
    }
}
