package model.statement;

import model.Type;
import model.exception.InvalidFileExpression;
import model.exception.InvalidTypeException;
import model.exception.VariableNotDefinedException;
import model.expression.Expression;
import model.expression.VariableExpression;
import model.value.IntegerValue;
import model.value.Value;
import state.ProgramState;

import java.io.BufferedReader;

public record ReadFile(Expression expression, String var_name) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        Value filename = expression.evaluate(state.symbolTable());
        if (filename.getType() != Type.STRING) {
            throw new InvalidTypeException("Cannot read file because type is not a string");
        }
        if (state.symbolTable().LookUp(var_name) == null) {
            throw new VariableNotDefinedException();
        }
        var variable = state.symbolTable().LookUp(var_name);
        if (variable.getType() != Type.INTEGER) {
            throw new InvalidTypeException("Variable " + var_name + " is not a number");
        }
        try {
            BufferedReader fileReader = state.fileTable().lookUp(variable);
            if (fileReader == null){
                throw new VariableNotDefinedException();
            }
            String line;
            line = fileReader.readLine();
            if (line == null){
                variable = new IntegerValue(0);
            }
            else{
                variable = new IntegerValue(Integer.parseInt(line));
            }
            state.symbolTable().updateValue(var_name, variable);
        }
        catch (Exception e) {
            throw new InvalidFileExpression("Cannot read file");
        }

        return state;
    }
}
