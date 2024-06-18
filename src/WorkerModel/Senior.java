package WorkerModel;

import java.io.Serializable;

public class Senior implements Serializable {
    private static final long serialVersionUID = 1L;

    private int salarySupplement;
    private Experience experience;

    private Senior(Experience experience, int salarySupplement) {
        this.experience = experience;
        this.salarySupplement = salarySupplement;
    }

    public static Senior createSenior(Experience experience, int salarySupplement) throws Exception {
        if (experience == null) {
            throw new Exception("WorkerModel.Experience does not exist");
        }

        // Check if the experience already has an experience
        if (experience.getExperienced() != null) {
            throw new Exception("This experience already has an experience.");
        }

        Senior senior = new Senior(experience, salarySupplement);
        experience.addSenior(senior);

        return senior;
    }

    public int getSalarySupplement() {
        return salarySupplement;
    }
}
