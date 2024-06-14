import java.util.List;
import java.util.Set;

public class Vehicle {
    private String brand;
    private String model;

    // Licence plate number is unique
    private static Set<String> licencePlateNumber;

    // Functions are a repeatable attribute
    private List<String> functions;

    // Engine is a composite attribute
    private Engine engine;

    public void addEngine(Engine engine) throws Exception {
        if (this.engine == null) {
            this.engine = engine;
        } else {
            throw new Exception("Engine already exists");
        }
    }
}
