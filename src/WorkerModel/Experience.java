package WorkerModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Experience implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Worker worker;
    private Senior senior;
    private Junior junior;
    private static List<Senior> allSeniors = new ArrayList<>();
    private static List<Junior> allJuniors = new ArrayList<>();

    public Experience(Worker worker) {
        this.worker = worker;
    }

    public static Experience createExperience(Worker worker) throws Exception {
        if (worker == null) {
            throw new Exception("WorkerModel.Worker does not exist");
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
                throw new IllegalStateException("Cannot set WorkerModel.Senior when WorkerModel.Junior is already set.");
            }
            if(allSeniors.contains(senior)){
                throw new IllegalStateException("This senior is already connected with some experience.");
            }

            this.senior = senior;
            allSeniors.add(senior);
        } else {
            throw new IllegalStateException("WorkerModel.Senior already exists in this WorkerModel.Experience");
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
                throw new IllegalStateException("Cannot set WorkerModel.Junior when WorkerModel.Senior is already set.");
            }
            if(allJuniors.contains(junior)){
                throw new IllegalStateException("This junior is already connected with some experience.");
            }

            this.junior = junior;
            allJuniors.add(junior);
        } else {
            throw new IllegalStateException("WorkerModel.Junior exists in this WorkerModel.Experience");
        }
    }

    public void removeJunior() {
        if (this.junior != null) {
            allJuniors.remove(this.junior);
            junior = null;
        }
    }



    // Custom serialization for WorkerModel.Experience
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(allJuniors);
        stream.writeObject(allSeniors);
    }

    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        allJuniors = (ArrayList<Junior>) stream.readObject();
        allSeniors = (ArrayList<Senior>) stream.readObject();
    }

    @Override
    public String toString() {
        return "WorkerModel.Experience: associated with WorkerModel.Worker " + worker.getName() + " " + worker.getSurname();
    }
}
