module Homework.Toy.Interpreter {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jdi;

    opens view to javafx.fxml;
    exports view;
}