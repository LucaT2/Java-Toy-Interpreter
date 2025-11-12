package view;

import controller.Controller;
import model.Type;
import model.expression.ArithmeticExpression;
import model.expression.ValueExpression;
import model.expression.VariableExpression;
import model.statement.*;
import model.value.BooleanValue;
import model.value.IntegerValue;
import repository.ListRepository;
import repository.Repository;
import state.*;

class Interpreter {
    public static void main(String[] args) {
        Statement ex1 = new CompoundStatement(
                new VariableDeclarationStatement(Type.INTEGER, "v"),
                new CompoundStatement(new AssignmentStatement("v",
                        new ValueExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableExpression("v")))
        );
        ExecutionStack executionStack1 = new ListExecutionStack();
        executionStack1.push(ex1);
        ProgramState prg1 = new ProgramState(
                executionStack1,
                new MapSymbolTable(),
                new ListOut(),
                new MapFileTable());
        Repository repo1 = new ListRepository("log1.txt");
        repo1.addProgramState(prg1);
        Controller ctr1 = new Controller(repo1);

        Statement ex2 = new CompoundStatement( new VariableDeclarationStatement(Type.INTEGER,"a"),
                new CompoundStatement(new VariableDeclarationStatement(Type.INTEGER,"b"),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression
                                (new ValueExpression(new IntegerValue(2)),
                                        new ArithmeticExpression(new ValueExpression(new IntegerValue(3)),
                                                new ValueExpression(new IntegerValue(5)),'*'),'+')),
                                new CompoundStatement(new AssignmentStatement
                                        ("b",new ArithmeticExpression(new VariableExpression("a"), new ValueExpression
                                                (new IntegerValue(1)),'+')), new PrintStatement(new VariableExpression("b"))))));

        ExecutionStack executionStack2 = new ListExecutionStack();
        executionStack2.push(ex2);
        ProgramState prg2 = new ProgramState(
                executionStack2,
                new MapSymbolTable(),
                new ListOut(),
                new MapFileTable());
        Repository repo2 = new ListRepository("log2.txt");
        repo2.addProgramState(prg2);
        Controller ctr2 = new Controller(repo2);
        Statement ex3 = new CompoundStatement(new VariableDeclarationStatement(Type.BOOLEAN,"a"),
                new CompoundStatement(new VariableDeclarationStatement(Type.INTEGER,"v"),
                        new CompoundStatement(new AssignmentStatement
                                ("a", new ValueExpression(new BooleanValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),
                                        new AssignmentStatement
                                                ("v",new ValueExpression(new IntegerValue(2))),
                                        new AssignmentStatement("v", new ValueExpression
                                                (new IntegerValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));
        ExecutionStack executionStack3 = new ListExecutionStack();
        executionStack3.push(ex3);
        ProgramState prg3 = new ProgramState(
                executionStack3,
                new MapSymbolTable(),
                new ListOut(),
                new MapFileTable());
        Repository repo3 = new ListRepository("log3.txt");
        repo3.addProgramState(prg3);

        Controller ctr3 = new Controller(repo3);
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1","First example",ctr1));
        menu.addCommand(new RunExample("2","Second example",ctr2));
        menu.addCommand(new RunExample("3","Third example",ctr3));
        menu.show();
    }
}

