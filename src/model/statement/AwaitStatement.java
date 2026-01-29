package model.statement;

import javafx.util.Pair;
import model.types.Type;
import model.value.IntegerValue;
import state.ProgramState;
import state.exceptions.AddressNotInBarrierTable;
import state.exceptions.VariableNotInTableException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public record AwaitStatement(String variableName) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        int foundIndex;
        if (state.symbolTable().isDefined(variableName)) {
            foundIndex = ((IntegerValue) state.symbolTable().LookUp(variableName)).value();
        }
        else {
            throw new VariableNotInTableException(variableName);
        }
        if (state.barrierTable().isDefined(foundIndex)){
            Pair<Integer, List<Integer>> pair = state.barrierTable().lookUp(foundIndex);
            List<Integer> addresses = pair.getValue();
            int length = pair.getValue().size();
            int capacity = pair.getKey();
            if (capacity > length) {
                if (addresses.contains(state.id())) {
                    state.executionStack().push(this);
                }
                else{
                    addresses.add(state.id());
                    state.barrierTable().update(foundIndex,new Pair<>(capacity,addresses));
                    state.executionStack().push(this);
                }
            }
        }
        else{
            throw new AddressNotInBarrierTable("Address not in Barrier Table");
        }
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws Exception {
        Type type = typeEnv.get(variableName);
        if (type == null) {
            throw new Exception("Variable " + variableName + " is not defined in the type environment.");
        }
        if (type.equals(new Type.Integer())){
            return typeEnv;
        }
        else {
            throw new Exception("Variable " + variableName + " is not of type Integer");
        }
    }
}
