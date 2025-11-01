package model.value;

import model.Type;

public record StringValue(String value) implements Value {
    @Override
    public Type getType() {
        return Type.STRING;
    }

    @Override
    public Value getValue() {
        return new StringValue(value);
    }

}
