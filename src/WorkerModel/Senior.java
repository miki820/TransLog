package WorkerModel;

import java.io.Serializable;

/**
 * Represents a Senior worker associated with an Experience.
 */
public class Senior implements Serializable {
    private static final long serialVersionUID = 1L;

    private int salarySupplement; // Salary supplement for the Senior
    private Experience experience; // The Experience associated with this Senior

    /**
     * Private constructor to create a Senior object.
     *
     * @param experience       The Experience object associated with this Senior.
     * @param salarySupplement The salary supplement for this Senior.
     */
    private Senior(Experience experience, int salarySupplement) {
        this.experience = experience;
        this.salarySupplement = salarySupplement;
    }

    /**
     * Creates a new Senior object and associates it with the provided Experience.
     *
     * @param experience       The Experience object to associate with the Senior.
     * @param salarySupplement The salary supplement for this Senior.
     * @return The created Senior object.
     * @throws Exception If the provided Experience is null or already has a Senior associated.
     */
    public static Senior createSenior(Experience experience, int salarySupplement) throws Exception {
        if (experience == null) {
            throw new Exception("Experience does not exist");
        }

        // Check if the experience already has a Senior associated
        if (experience.getExperienced() != null) {
            throw new Exception("This experience already has a Senior.");
        }

        Senior senior = new Senior(experience, salarySupplement);
        experience.addSenior(senior);

        return senior;
    }

    /**
     * Retrieves the salary supplement for this Senior.
     *
     * @return The salary supplement.
     */
    public int getSalarySupplement() {
        return salarySupplement;
    }
}
