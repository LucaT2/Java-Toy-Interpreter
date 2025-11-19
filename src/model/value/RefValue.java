package model.value;

import model.types.RefType;
import model.types.Type;

public record RefValue(int address, Type locationType) implements Value {


    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value getValue() {
        return new RefValue(address,locationType);
    }
    public int getAddress(){
        return address();
    }
}
