package WorkerModel;

import java.io.Serializable;

/**
 * Represents a Junior worker associated with an Experience.
 */
public class Junior implements Serializable {
    private static final long serialVersionUID = 1L;

    private int trainingHoursCompleted; // Hours of training completed by the Junior
    private Experience experience; // The Experience associated with this Junior

    /**
     * Private constructor to create a Junior object.
     *
     * @param experience             The Experience object associated with this Junior.
     * @param trainingHoursCompleted The training hours completed by this Junior.
     */
    private Junior(Experience experience, int trainingHoursCompleted) {
        this.experience = experience;
        this.trainingHoursCompleted = trainingHoursCompleted;
    }

    /**
     * Creates a new Junior object and associates it with the provided Experience.
     *
     * @param experience             The Experience object to associate with the Junior.
     * @param trainingHoursCompleted The training hours completed by this Junior.
     * @return The created Junior object.
     * @throws Exception If the provided Experience is null or already has a Junior associated.
     */
    public static Junior createJunior(Experience experience, int trainingHoursCompleted) throws Exception {
        if (experience == null) {
            throw new Exception("Experience does not exist");
        }

        // Check if the experience already has a Junior associated
        if (experience.getExperienced() != null) {
            throw new Exception("This experience already has a Junior.");
        }

        Junior junior = new Junior(experience, trainingHoursCompleted);
        experience.addJunior(junior);

        return junior;
    }

    /**
     * Retrieves the number of training hours completed by this Junior.
     *
     * @return The number of training hours completed.
     */
    public int getTrainingHoursCompleted() {
        return trainingHoursCompleted;
    }
}
