import java.util.ArrayList;
import java.util.List;

public class Experience {
    private final Worker worker;
    private Senior senior;
    private Junior junior;
    private static final List<Senior> allSeniors = new ArrayList<>();
    private static final List<Junior> allJuniors = new ArrayList<>();

    public Experience(Worker worker) {
        this.worker = worker;
    }

    public static Experience createExperience(Worker worker) throws Exception {
        if (worker == null) {
            throw new Exception("Worker does not exist");
        }

        // Check if the worker already has an experience
        if (worker.getExperience() != null) {
            throw new Exception("This worker already has an experience.");
        }

        Experience experience = new Experience(worker);
        worker.addExperience(experience);

        return experience;
    }

    public Worker getWorker() {
        return worker;
    }

    public Senior getExperienced() {
        return senior;
    }

    public Junior getInexperienced() {
        return junior;
    }

    public void addSenior(Senior senior){
        if(this.senior == null){
            if(this.junior != null) {
                throw new IllegalStateException("Cannot set Senior when Junior is already set.");
            }
            if(allSeniors.contains(senior)){
                throw new IllegalStateException("This senior is already connected with some experience.");
            }

            this.senior = senior;
            allSeniors.add(senior);
        } else {
            throw new IllegalStateException("Senior already exists in this Experience");
        }
    }

    public void removeSenior() {
        if (this.senior != null) {
            allSeniors.remove(this.senior);
            senior = null;
        }
    }

    public void addJunior(Junior junior){
        if(this.junior == null){
            if(this.senior != null) {
                throw new IllegalStateException("Cannot set Junior when Senior is already set.");
            }
            if(allJuniors.contains(junior)){
                throw new IllegalStateException("This junior is already connected with some experience.");
            }

            this.junior = junior;
            allJuniors.add(junior);
        } else {
            throw new IllegalStateException("Junior exists in this Experience");
        }
    }

    public void removeJunior() {
        if (this.junior != null) {
            allJuniors.remove(this.junior);
            junior = null;
        }
    }

    @Override
    public String toString() {
        return "Experience: associated with Worker " + worker.getName() + " " + worker.getSurname();
    }
}
