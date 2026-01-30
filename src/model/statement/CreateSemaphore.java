package model.statement;


import javafx.util.Pair;
import model.exception.InvalidTypeException;
import model.types.Type;
import model.value.IntegerValue;
import model.value.Value;
import state.ProgramState;
import model.expression.Expression;
import state.exceptions.VariableNotInTableException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record CreateSemaphore(String variable, Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        Value number1 = expression.evaluate(state.symbolTable(), state.heap());
        if (number1 instanceof IntegerValue ){
            Integer intNumber = ((IntegerValue) number1).value();
            if (state.symbolTable().isDefined(variable)){
                if (state.symbolTable().LookUp(variable) instanceof IntegerValue){
                    List<Integer> threads = new ArrayList<>();
                    Pair<Integer, List<Integer>> semaphoreEntry = new Pair<> (intNumber, threads);
                    int address = state.semaphoreTable().add(semaphoreEntry);
                    state.symbolTable().updateValue(variable, new IntegerValue(address));
                }
                else{
                    throw new InvalidTypeException("Variable " + variable + " is not int");
                }
            }
            else{
                throw new VariableNotInTableException(variable);
            }
        }
        else{
            throw new InvalidTypeException("Expresssion does not evaluate to integer");
        }
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws Exception {
        Type typeExpr = expression.typecheck(typeEnv);
        Type typeVar = typeEnv.get(variable);

        if (typeExpr.equals(Type.INTEGER) && typeVar.equals(Type.INTEGER)) {
            return typeEnv;
        } else {
            throw new InvalidTypeException("CreateSemaphore: variable and expression must be integers");
        }    }
}
