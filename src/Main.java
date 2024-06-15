public class Main {
    public static void main(String[] args) throws Exception {
        Vehicle vehicle = new Vehicle("Toyota", "Avensis");
        Vehicle vehicle2 = new Vehicle("Renuwa", "Citroen");
        Engine engine = Engine.createEngine(vehicle, "V12");


        System.out.println(vehicle);
        System.out.println(engine);

        Vehicle.showAllVehicles();
        Engine.showAllEngines();

        Engine.showAllEngines();
        Vehicle.showAllVehicles();

        //jak zarzadzac listą ekstensji w czesci z poziomu calosci
    }
}