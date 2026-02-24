package model.statement;

import model.types.Type;
import state.*;

import java.util.Map;

public record ForkStatement(Statement statement) implements Statement{
    @Override
    public ProgramState execute(ProgramState state) {
        ExecutionStack newStack = new ListExecutionStack();
        newStack.push(statement);
        SymbolTable newSymTable = new MapSymbolTable();

        for (var entry : state.symbolTable().getContents().entrySet()) {
            newSymTable.updateValue(entry.getKey(), entry.getValue().deepCopy());
        }

        return new ProgramState(newStack,
                newSymTable,
                state.out(),
                state.fileTable(),
                state.heap(),
                state.lockTable(),
                state.latchTable(),
                state.barrierTable(),
                state.semaphoreTable());
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws Exception {
        return statement.typeCheck(typeEnv);
    }
}
