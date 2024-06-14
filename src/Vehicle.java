import java.util.List;
import java.util.Set;

public class Vehicle {
    private String brand;
    private String model;
    private static Set<String> licencePlateNumber;
    private List<String> functions;
    private Engine engine;

    public void addEngine(Engine engine) throws Exception {
        if (this.engine == null) {
            this.engine = engine;
        } else {
            throw new Exception("Engine already exists");
        }
    }
}
