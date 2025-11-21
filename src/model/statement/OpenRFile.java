package model.statement;

import model.types.Type;
import model.exception.InvalidFileExpression;
import model.expression.Expression;
import model.value.StringValue;
import model.value.Value;
import state.ProgramState;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public record OpenRFile(Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        Value filename = expression.evaluate(state.symbolTable(), state.heap());
        if (filename.getType() != Type.STRING) {
            throw new InvalidFileExpression("Expected a string for opening the file" +
                    ", got " + filename.getType());
        }
        if (state.fileTable().fileExists(filename)) {
            throw new InvalidFileExpression("File already exists: " + filename.getType());
        }
        
        // Extract the actual string value properly
        String filePath;
        if (filename instanceof StringValue stringValue) {
            filePath = stringValue.value();
        } else {
            filePath = filename.getValue().toString();
        }
        
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            state.fileTable().addFile(filename, bufferedReader);
        }
        catch (FileNotFoundException e){
            throw new InvalidFileExpression("File not found: " + filePath);
        }
        catch (IOException e){
            throw new InvalidFileExpression("Could not open file due to IO error: " + filePath);
        }
        return state;
    }
    @Override
    public String toString() {
        return "Open (" + expression.toString() +");";
    }
}
