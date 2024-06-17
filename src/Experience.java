public class Experience {
    private final Worker worker;

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

    @Override
    public String toString() {
        return "Experience: associated with Worker " + worker.getName() + " " + worker.getSurname();
    }
}
