package model.statement;

import state.ExecutionStack;
import state.ListExecutionStack;
import state.MapSymbolTable;
import state.ProgramState;
import state.SymbolTable;

public record ForkStatement(Statement statement) implements Statement{
    @Override
    public ProgramState execute(ProgramState state) {
        ExecutionStack executionStack = new ListExecutionStack();
        executionStack.push(statement);
        
        SymbolTable clonedSymbolTable = new MapSymbolTable();
        state.symbolTable().getContents().forEach(clonedSymbolTable::updateValue);

        return new ProgramState(executionStack,
                clonedSymbolTable,
                state.out(),
                state.fileTable(),
                state.heap());
    }
}
