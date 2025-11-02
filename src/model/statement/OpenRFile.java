package model.statement;

import model.Type;
import model.exception.InvalidFileExpression;
import model.expression.Expression;
import model.value.Value;
import state.ProgramState;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public record OpenRFile(Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        Value filename = expression.evaluate(state.symbolTable());
        if (filename.getType() != Type.STRING) {
            throw new InvalidFileExpression("Expected a string for opening the file" +
                    ", got " + filename.getType());
        }
        if (state.fileTable().fileExists(filename)) {
            throw new InvalidFileExpression("File already exists: " + filename.getType());
        }
        String filePath = filename.getValue().toString();
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            state.fileTable().addFile(filename, bufferedReader);
        }
        catch (FileNotFoundException e){
            throw new InvalidFileExpression("File not found: " + filename.getValue());
        }
        catch (IOException e){
            throw new InvalidFileExpression("Could not open file due to IO error: " + filePath);
        }
        return state;
    }
}
