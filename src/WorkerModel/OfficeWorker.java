package WorkerModel;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents an office worker, extending the base Worker class.
 */
public class OfficeWorker extends Worker implements Serializable {
    private static final long serialVersionUID = 1L;

    private String position; // Position of the office worker (e.g., Manager, Director)

    /**
     * Constructor to initialize an OfficeWorker object.
     *
     * @param name         The name of the office worker.
     * @param surname      The surname of the office worker.
     * @param birthDate    The birth date of the office worker.
     * @param seniority    The seniority level of the office worker.
     * @param previousJob  The previous job experience of the office worker.
     * @param branch       The branch where the office worker works.
     * @param position     The position held by the office worker (e.g., Manager, Director).
     */
    public OfficeWorker(String name, String surname, LocalDate birthDate, int seniority, String previousJob, Branch branch, String position) {
        super(name, surname, birthDate, seniority, previousJob, branch);
        this.position = position;
    }

    /**
     * Calculates the salary of the office worker based on their position.
     *
     * @return The calculated salary.
     */
    @Override
    public double countSalary() {
        // Example salary calculation based on position
        return position.equals("Director") ? 2000 : 1000;
    }

    /**
     * Returns a string representation of the OfficeWorker object.
     *
     * @return A string representation including the class name and attributes.
     */
    @Override
    public String toString() {
        return "OfficeWorker: " + super.toString() +
                ", Position: " + position;
    }
}
