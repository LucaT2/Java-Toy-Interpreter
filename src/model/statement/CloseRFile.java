package model.statement;

import model.Type;
import model.exception.InvalidFileExpression;
import model.exception.InvalidTypeException;
import model.value.Value;
import state.ProgramState;

import model.expression.Expression;

import java.io.BufferedReader;
import java.io.IOException;

public record CloseRFile(Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        Value filename = expression.evaluate(state.symbolTable());
        if (filename.getType()!= Type.STRING){
            throw new InvalidTypeException("Filename for closing must be a string");
        }
        if (state.fileTable().lookUp(filename) instanceof BufferedReader reader){
            try {
                reader.close();
                state.fileTable().removeFile(filename);
            }
            catch (IOException e){
                throw new InvalidFileExpression("Cannot close file");
            }
        }
        else{
            throw new InvalidFileExpression("Closing Filename not in file table");
        }
        return state;
    }
}
