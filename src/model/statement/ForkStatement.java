package model.statement;

import state.ExecutionStack;
import state.ListExecutionStack;
import state.MapSymbolTable;
import state.ProgramState;
import state.SymbolTable;

public record ForkStatement(Statement statement) implements Statement{
    @Override
    public ProgramState execute(ProgramState state) {
        ExecutionStack newStack = new ListExecutionStack();
        newStack.push(statement);
        SymbolTable newSymTable = new MapSymbolTable();

        for (var entry : state.symbolTable().getContents().entrySet()) {
            newSymTable.updateValue(entry.getKey(), entry.getValue().deepCopy());
        }

        return new ProgramState(newStack, newSymTable, state.out(), state.fileTable(), state.heap());
    }
}
