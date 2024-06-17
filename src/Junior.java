public class Junior {
    private int trainingHoursCompleted;
    private Experience experience;


    public Junior(Experience experience, int trainingHoursCompleted) {
        this.trainingHoursCompleted = trainingHoursCompleted;
    }

    public static Experience createExperienced(Experience experience, int salarySupplement) throws Exception {
        if (experience == null) {
            throw new Exception("Experience does not exist");
        }

        // Check if the experience already has an experience
        if (experience.getExperienced() != null) {
            throw new Exception("This experience already has an experience.");
        }

        Junior junior = new Junior(experience, salarySupplement);
        experience.addJunior(junior);

        return experience;
    }


    public int getTrainingHoursCompleted() {
        return trainingHoursCompleted;
    }
}
