package state.exceptions;

public class AddressNotInLockTable extends RuntimeException {
    public AddressNotInLockTable(String message) {
        super(message);
    }
}
