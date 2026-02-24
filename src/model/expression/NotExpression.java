package model.expression;

import com.sun.jdi.BooleanType;
import model.exception.InvalidTypeException;
import model.types.Type;
import model.value.BooleanValue;
import model.value.Value;
import state.Heap;
import state.SymbolTable;

import java.util.Map;

public record NotExpression(Expression expression)  implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) {
        Value val = expression.evaluate(symbolTable, heap);
        if (val.getType().equals(Type.BOOLEAN)) {
            boolean b = ((BooleanValue) val).value();
            return new BooleanValue(!b);
        }
        else{
            throw new InvalidTypeException("Condition expression is not a boolean");
        }
    }

    @Override
    public Type typecheck(Map<String, Type> typeEnv) throws Exception {
        Type type = expression.typecheck(typeEnv);
        if (type.equals(Type.BOOLEAN)) {
            return new Type.Boolean();
        }
        else  throw new InvalidTypeException("Negation argument is not a boolean");
    }
}
