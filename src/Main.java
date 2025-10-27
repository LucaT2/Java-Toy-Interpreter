import controller.Controller;
import repository.ListRepository;
import repository.Repository;
import view.View;

void main() {
    IO.println("Starting Application...");
    Repository repository = new ListRepository();
    Controller controller = new Controller(repository);
    View view = new View(controller);
    view.run();
}