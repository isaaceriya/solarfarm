package learn.solar;

import learn.solar.data.SolarFileRepository;
import learn.solar.domain.SolarService;
import learn.solar.ui.Controller;
import learn.solar.ui.View;

public class App {
    public static void main(String[] args) {

        SolarFileRepository repository =
                new SolarFileRepository("./data/solar.csv");
        SolarService service = new SolarService(repository);
        View view = new View();

        Controller controller = new Controller(service, view);
        controller.run();
    }
}
