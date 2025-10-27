package model.value;

import model.Type;

public record BooleanValue(boolean value) implements Value {

    @Override
    public Type getType() {
        return Type.BOOLEAN;
    }

}
