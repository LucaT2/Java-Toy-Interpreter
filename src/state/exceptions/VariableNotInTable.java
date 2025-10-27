package state.exceptions;

public class VariableNotInTable extends RuntimeException {
    public VariableNotInTable(String message) {
        super(message);
    }
}
