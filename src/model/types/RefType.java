package model.types;

import model.value.RefValue;
import model.value.Value;

public class RefType implements Type{
    Type inner;
    public RefType(Type inner) {
        this.inner = inner;
    }
    Type getInner() {
        return inner;
    }
    public boolean equals(Object other) {
        if (other instanceof RefType) {
            return inner.equals(((RefType) other).getInner());
        }
        return false;
    }

    @Override
    public java.lang.String toString() {
        return "RefType{" +
                "inner=" + inner +
                '}';
    }

    @Override
    public Value getDefaultValue() {
        return new RefValue(0,inner);
    }
}
