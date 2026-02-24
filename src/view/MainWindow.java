package view;

import controller.Controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.value.Value;
import state.ProgramState;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainWindow {
    private Controller controller;

    private TextField numberOfProgramStates;
    private TableView<Map.Entry<Integer, Value>> heapTable;
    private ListView<String> outList;
    private ListView<String> fileTable;
    private ListView<Integer> programStateIdentifiers;
    private TableView<Map.Entry<String, Value>> symbolView;
    private ListView<String> exeStackView;
    private Button runOneStepButton;
    private List<ProgramState> allProgramStates = new java.util.ArrayList<>();
    private TableView<Map.Entry<Integer, Integer>> lockTable;
    private TableView<Map.Entry<Integer, Integer>> latchTable;
    private TableView<BarrierTableRow> barrierTableView;
    private TableView<SemaphoreRow> semaphoreTable;

    public MainWindow(Controller controller) {
        this.controller = controller;
    }

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Main Window");

        VBox mainLayout = new VBox(10);
        
        // 2(a) the number of PrgStates as a TextField
        HBox topBox = new HBox(10);
        topBox.getChildren().add(new Label("Number of Program States:"));
        numberOfProgramStates = new TextField();
        numberOfProgramStates.setEditable(false);
        topBox.getChildren().add(numberOfProgramStates);
        mainLayout.getChildren().add(topBox);

        GridPane tablesGrid = new GridPane();
        tablesGrid.setHgap(10);
        tablesGrid.setVgap(10);

        // 2(b) the HeapTable as a TableView with two columns: address and Value
        VBox heapBox = new VBox(5);
        heapBox.getChildren().add(new Label("Heap Table"));
        heapTable = new TableView<>();
        TableColumn<Map.Entry<Integer, Value>, Integer> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        TableColumn<Map.Entry<Integer, Value>, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        heapTable.getColumns().add(addressColumn);
        heapTable.getColumns().add(valueColumn);
        heapBox.getChildren().add(heapTable);
        tablesGrid.add(heapBox, 0, 0);

        // 2(c) the Out as a ListView
        VBox outBox = new VBox(5);
        outBox.getChildren().add(new Label("Out"));
        outList = new ListView<>();
        outBox.getChildren().add(outList);
        tablesGrid.add(outBox, 1, 0);

        // 2(d) the FileTable as a ListView
        VBox fileBox = new VBox(5);
        fileBox.getChildren().add(new Label("File Table"));
        fileTable = new ListView<>();
        fileBox.getChildren().add(fileTable);
        tablesGrid.add(fileBox, 2, 0);

        // 2(e) the list of PrgState identifiers as a ListView
        VBox prgIdBox = new VBox(5);
        prgIdBox.getChildren().add(new Label("Program State Identifiers"));
        programStateIdentifiers = new ListView<>();
        prgIdBox.getChildren().add(programStateIdentifiers);
        tablesGrid.add(prgIdBox, 0, 1);

        // 2(f) a Table View with two columns: variable name and Value (SymTable)
        VBox symBox = new VBox(5);
        symBox.getChildren().add(new Label("Symbol Table"));
        symbolView = new TableView<>();
        TableColumn<Map.Entry<String, Value>, String> varNameColumn = new TableColumn<>("Variable Name");
        varNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        TableColumn<Map.Entry<String, Value>, String> varValueColumn = new TableColumn<>("Value");
        varValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        symbolView.getColumns().add(varNameColumn);
        symbolView.getColumns().add(varValueColumn);
        symBox.getChildren().add(symbolView);
        tablesGrid.add(symBox, 1, 1);

        // 2(g) a List View which displays the ExeStack
        VBox stackBox = new VBox(5);
        stackBox.getChildren().add(new Label("Execution Stack"));
        exeStackView = new ListView<>();
        stackBox.getChildren().add(exeStackView);
        tablesGrid.add(stackBox, 2, 1);

        //mainLayout.getChildren().add(tablesGrid);

        // Latch Table
        VBox latchBox = new VBox(5);
        latchBox.getChildren().add(new Label("Latch Table"));
        latchTable = new TableView<>();

        TableColumn<Map.Entry<Integer, Integer>, Integer> indexCol = new TableColumn<>("Address");
        indexCol.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());

        TableColumn<Map.Entry<Integer, Integer>, Integer> valueCol = new TableColumn<>("Value");
        valueCol.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue()).asObject());

        latchTable.getColumns().addAll(indexCol, valueCol);
        latchBox.getChildren().add(latchTable);
        tablesGrid.add(latchBox, 1, 2);
        //LOCK Table
        VBox lockBox = new VBox(5);
        lockBox.getChildren().add(new Label("Lock Table"));
        lockTable = new TableView<>();
        TableColumn<Map.Entry<Integer, Integer>, Integer> lockVarNameColumn = new TableColumn<>("Address");
        lockVarNameColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        TableColumn<Map.Entry<Integer, Integer>, Integer> lockVarValueColumn = new TableColumn<>("Value");
        lockVarValueColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue()).asObject());
        lockTable.getColumns().add(lockVarNameColumn);
        lockTable.getColumns().add(lockVarValueColumn);
        lockBox.getChildren().add(lockTable);
        tablesGrid.add(lockBox, 0, 2);
        mainLayout.getChildren().add(tablesGrid);

        VBox barrierBox = new VBox(5);
        barrierBox.getChildren().add(new Label("Barrier Table"));
        barrierTableView = new TableView<>();

        TableColumn<BarrierTableRow, Integer> indexColBar = new TableColumn<>("Index");
        indexColBar.setCellValueFactory(new PropertyValueFactory<>("index"));

        TableColumn<BarrierTableRow, Integer> valueColBar = new TableColumn<>("Value");
        valueColBar.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<BarrierTableRow, String> listCol = new TableColumn<>("List of Values");
        listCol.setCellValueFactory(new PropertyValueFactory<>("list"));

        barrierTableView.getColumns().addAll(indexColBar, valueColBar, listCol);        barrierBox.getChildren().add(barrierTableView);

        // Place it in the grid (e.g., column 1, row 2)
        tablesGrid.add(barrierBox, 2, 2);


        VBox semaphoreBox = new VBox(5);
        semaphoreBox.getChildren().add(new Label("Semaphore Table"));
        semaphoreTable = new TableView<>();

        TableColumn<SemaphoreRow, Integer> indexColSem = new TableColumn<>("Index");
        indexColSem.setCellValueFactory(new PropertyValueFactory<>("index"));

        TableColumn<SemaphoreRow, Integer> valueColSem = new TableColumn<>("Max Permits");
        valueColSem.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<SemaphoreRow, String> listColSem = new TableColumn<>("Active Threads");
        listColSem.setCellValueFactory(new PropertyValueFactory<>("threads"));

        semaphoreTable.getColumns().addAll(indexColSem, valueColSem, listColSem);
        semaphoreBox.getChildren().add(semaphoreTable);

        // Place it in the grid (e.g., column 1, row 2)
        tablesGrid.add(semaphoreBox, 0, 3);



        // 2(h) A button "Run one step"
        runOneStepButton = new Button("Run one step");
        runOneStepButton.setOnAction(e -> {
            try {
                controller.oneStepForAllPrograms();
                updateUI();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });
        mainLayout.getChildren().add(runOneStepButton);

        programStateIdentifiers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateSymbolTableAndStack(newValue);
        });

        Scene scene = new Scene(mainLayout, 800, 600);
        stage.setScene(scene);
        updateUI();
        stage.show();
    }

    private void updateUI() {
        List<ProgramState> programStates = controller.getProgramStates();

        // Sync allProgramStates with currently active ones, but keep finished ones
        for (ProgramState ps : programStates) {
            if (allProgramStates.stream().noneMatch(p -> p.id() == ps.id())) {
                allProgramStates.add(ps);
            }
        }
        
        // 2(a)
        numberOfProgramStates.setText(String.valueOf(programStates.size()));

        // 2(b)
        if (!allProgramStates.isEmpty()) {
            Map<Integer, Value> heapContents = allProgramStates.get(0).heap().getHeapMap();
            heapTable.setItems(FXCollections.observableArrayList(heapContents.entrySet()));
            heapTable.refresh();
        } else {
            heapTable.setItems(FXCollections.emptyObservableList());
        }

        // 2(c)
        if (!allProgramStates.isEmpty()) {
            outList.setItems(FXCollections.observableArrayList(
                    allProgramStates.get(0).out().getContents().stream().map(Object::toString).collect(Collectors.toList())
            ));
        }

        // 2(d)
        if (!allProgramStates.isEmpty()) {
            fileTable.setItems(FXCollections.observableArrayList(
                    allProgramStates.get(0).fileTable().getContents().keySet().stream().map(Object::toString).collect(Collectors.toList())
            ));
        }

        // 2(e)
        List<Integer> ids = allProgramStates.stream().map(ProgramState::id).collect(Collectors.toList());
        programStateIdentifiers.setItems(FXCollections.observableArrayList(ids));

        if (!ids.isEmpty()) {
            Integer selectedId = programStateIdentifiers.getSelectionModel().getSelectedItem();
            if (selectedId == null) {
                programStateIdentifiers.getSelectionModel().selectFirst();
                selectedId = programStateIdentifiers.getSelectionModel().getSelectedItem();
            }
            updateSymbolTableAndStack(selectedId);
        } else {
            symbolView.setItems(FXCollections.emptyObservableList());
            exeStackView.setItems(FXCollections.emptyObservableList());
        }

        // Update Lock Table
        if (!allProgramStates.isEmpty()) {
            Map<Integer, Integer> locks = allProgramStates.get(0).lockTable().getLockTable();
            lockTable.setItems(FXCollections.observableArrayList(locks.entrySet()));
            lockTable.refresh();
        } else {
            lockTable.setItems(FXCollections.emptyObservableList());
        }

        // Latch Table Update
        if (!allProgramStates.isEmpty()) {
            Map<Integer, Integer> latchContents = allProgramStates.get(0).latchTable().getLatchMap();

            latchTable.setItems(FXCollections.observableArrayList(latchContents.entrySet()));
            latchTable.refresh();
        } else if (!allProgramStates.isEmpty()) {
            Map<Integer, Integer> latchContents = allProgramStates.get(0).latchTable().getLatchMap();
            latchTable.setItems(FXCollections.observableArrayList(latchContents.entrySet()));
        } else {
            latchTable.setItems(FXCollections.emptyObservableList());
        }

        if (!allProgramStates.isEmpty()) {
            var barrierMap = allProgramStates.get(0).barrierTable().getBarrierTable();

            List<BarrierTableRow> tableLines = barrierMap.entrySet().stream()
                    .map(e -> new BarrierTableRow(
                            e.getKey(),
                            e.getValue().getKey(),
                            e.getValue().getValue()
                    ))
                    .collect(Collectors.toList());

            // 3. Set items
            barrierTableView.setItems(FXCollections.observableArrayList(tableLines));
            barrierTableView.refresh();
        } else {
            barrierTableView.setItems(FXCollections.emptyObservableList());
        }

        if (!allProgramStates.isEmpty()) {
            // We use the semaphore table from the first program state (shared across all)
            var semTable = allProgramStates.get(0).semaphoreTable().getSemaphoreTable();

            List<SemaphoreRow> rows = semTable.entrySet().stream()
                    .map(e -> new SemaphoreRow(
                            e.getKey(),              // The Address
                            e.getValue().getKey(),   // The Max Permits
                            e.getValue().getValue()  // The List of Thread IDs
                    ))
                    .collect(Collectors.toList());

            semaphoreTable.setItems(FXCollections.observableArrayList(rows));
            semaphoreTable.refresh();
        } else {
            semaphoreTable.setItems(FXCollections.emptyObservableList());
        }

    }

    private void updateSymbolTableAndStack(Integer id) {
        if (id == null) return;
        ProgramState selectedPrg = allProgramStates.stream()
                .filter(ps -> ps.id() == id)
                .findFirst()
                .orElse(null);

        if (selectedPrg != null) {
            // 2(f)
            symbolView.setItems(FXCollections.observableArrayList(selectedPrg.symbolTable().getContents().entrySet()));
            symbolView.refresh();
            // 2(g)
            List<String> stackList = selectedPrg.executionStack().getContents().stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
            exeStackView.setItems(FXCollections.observableArrayList(stackList));
        }
    }
}
