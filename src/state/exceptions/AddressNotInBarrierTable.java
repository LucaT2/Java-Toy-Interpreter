package state.exceptions;

public class AddressNotInBarrierTable extends RuntimeException {
    public AddressNotInBarrierTable(String message) {
        super(message);
    }
}
