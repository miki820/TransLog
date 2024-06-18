package WorkerModel;

import java.io.Serializable;

public class Junior implements Serializable {
    private static final long serialVersionUID = 1L;
    private int trainingHoursCompleted;
    private Experience experience;

    private Junior(Experience experience, int trainingHoursCompleted) {
        this.experience = experience;
        this.trainingHoursCompleted = trainingHoursCompleted;
    }

    public static Junior createJunior(Experience experience, int trainingHoursCompleted) throws Exception {
        if (experience == null) {
            throw new Exception("WorkerModel.Experience does not exist");
        }

        // Check if the experience already has an experience
        if (experience.getExperienced() != null) {
            throw new Exception("This experience already has an experience.");
        }

        Junior junior = new Junior(experience, trainingHoursCompleted);
        experience.addJunior(junior);

        return junior;
    }


    public int getTrainingHoursCompleted() {
        return trainingHoursCompleted;
    }
}
