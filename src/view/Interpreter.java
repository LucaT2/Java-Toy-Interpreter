package view;

import javafx.application.Application;
import javafx.stage.Stage;
import model.expression.*;
import model.statement.*;
import model.types.RefType;
import model.types.Type;
import model.value.BooleanValue;
import model.value.IntegerValue;

import java.util.HashMap;
public class Interpreter extends Application {

    @Override
    public void start(Stage primaryStage) {
        Statement ex1 = new CompoundStatement(
                new VariableDeclarationStatement(Type.INTEGER, "v"),
                new CompoundStatement(new AssignmentStatement("v",
                        new ValueExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableExpression("v")))
        );

        Statement ex2 = new CompoundStatement(new VariableDeclarationStatement(Type.INTEGER, "a"),
                new CompoundStatement(new VariableDeclarationStatement(Type.INTEGER, "b"),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression
                                (new ValueExpression(new IntegerValue(2)),
                                        new ArithmeticExpression(new ValueExpression(new IntegerValue(3)),
                                                new ValueExpression(new IntegerValue(5)), '*'), '+')),
                                new CompoundStatement(new AssignmentStatement
                                        ("b", new ArithmeticExpression(new VariableExpression("a"), new ValueExpression
                                                (new IntegerValue(1)), '+')), new PrintStatement(new VariableExpression("b"))))));

        Statement ex3 = new CompoundStatement(new VariableDeclarationStatement(Type.BOOLEAN, "a"),
                new CompoundStatement(new VariableDeclarationStatement(Type.INTEGER, "v"),
                        new CompoundStatement(new AssignmentStatement
                                ("a", new ValueExpression(new BooleanValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),
                                        new AssignmentStatement
                                                ("v", new ValueExpression(new IntegerValue(2))),
                                        new AssignmentStatement("v", new ValueExpression
                                                (new IntegerValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));

        Statement ex4 = new CompoundStatement(
                new VariableDeclarationStatement(Type.INTEGER, "v"),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntegerValue(0)),
                                                ">"
                                        ),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignmentStatement("v",
                                                        new ArithmeticExpression(
                                                                new VariableExpression("v"),
                                                                new ValueExpression(new IntegerValue(1)),
                                                                '-'
                                                        )
                                                )
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );

        Statement ex5 = new CompoundStatement(
                new VariableDeclarationStatement(Type.INTEGER, "v"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new RefType(Type.INTEGER), "a"),
                        new CompoundStatement(
                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(10))),
                                new CompoundStatement(
                                        new HeapAllocStatement("a", new ValueExpression(new IntegerValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new HeapWriteStatement("a", new ValueExpression(new IntegerValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new PrintStatement(new HeapReadExpression(new VariableExpression("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new HeapReadExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );

        Statement ex6 = new CompoundStatement(
                new VariableDeclarationStatement(new RefType(Type.INTEGER), "a"),
                new CompoundStatement(new VariableDeclarationStatement(new RefType(Type.INTEGER), "b"),
                        new CompoundStatement(new VariableDeclarationStatement(Type.INTEGER, "v"),
                                new CompoundStatement(new HeapAllocStatement("a", new ValueExpression(new IntegerValue(0))),
                                        new CompoundStatement(new HeapAllocStatement("b", new ValueExpression(new IntegerValue(0))),
                                                new CompoundStatement(new HeapWriteStatement("a", new ValueExpression(new IntegerValue(1))),
                                                        new CompoundStatement(new HeapWriteStatement("b", new ValueExpression(new IntegerValue(2))),
                                                                new CompoundStatement(
                                                                        new ConditionalAssignmentStatement("v",
                                                                                new RelationalExpression(new HeapReadExpression(new VariableExpression("a")), new HeapReadExpression(new VariableExpression("b")), "<"),
                                                                                new ValueExpression(new IntegerValue(100)),
                                                                                new ValueExpression(new IntegerValue(200))),
                                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                                new CompoundStatement(
                                                                                        new ConditionalAssignmentStatement("v",
                                                                                                new RelationalExpression(
                                                                                                        new ArithmeticExpression(new HeapReadExpression(new VariableExpression("b")), new ValueExpression(new IntegerValue(2)), '-'),
                                                                                                        new HeapReadExpression(new VariableExpression("a")),
                                                                                                        ">"),
                                                                                                new ValueExpression(new IntegerValue(100)),
                                                                                                new ValueExpression(new IntegerValue(200))),
                                                                                        new PrintStatement(new VariableExpression("v"))))))))))));

        Statement ex7 = new CompoundStatement(
                new VariableDeclarationStatement(new RefType(Type.INTEGER), "v1"),
                new CompoundStatement(new VariableDeclarationStatement(new RefType(Type.INTEGER), "v2"),
                        new CompoundStatement(new VariableDeclarationStatement(new RefType(Type.INTEGER), "v3"),
                                new CompoundStatement(new VariableDeclarationStatement(Type.INTEGER, "cnt"),

                                        new CompoundStatement(new HeapAllocStatement("v1", new ValueExpression(new IntegerValue(2))),
                                                new CompoundStatement(new HeapAllocStatement("v2", new ValueExpression(new IntegerValue(3))),
                                                        new CompoundStatement(new HeapAllocStatement("v3", new ValueExpression(new IntegerValue(4))),

                                                                new CompoundStatement(new NewLatchStatement("cnt", new HeapReadExpression(new VariableExpression("v2"))),

                                                                        new CompoundStatement(
                                                                                new ForkStatement(
                                                                                        new CompoundStatement(new HeapWriteStatement("v1", new ArithmeticExpression(new HeapReadExpression(new VariableExpression("v1")), new ValueExpression(new IntegerValue(10)), '*')),
                                                                                                new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v1"))),
                                                                                                        new CountDownStatement("cnt")))
                                                                                ),

                                                                                new CompoundStatement(
                                                                                        new ForkStatement(
                                                                                                new CompoundStatement(new HeapWriteStatement("v2", new ArithmeticExpression(new HeapReadExpression(new VariableExpression("v2")), new ValueExpression(new IntegerValue(10)), '*')),
                                                                                                        new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v2"))),
                                                                                                                new CountDownStatement("cnt")))
                                                                                        ),

                                                                                        new CompoundStatement(
                                                                                                new ForkStatement(
                                                                                                        new CompoundStatement(new HeapWriteStatement("v3", new ArithmeticExpression(new HeapReadExpression(new VariableExpression("v3")), new ValueExpression(new IntegerValue(10)), '*')),
                                                                                                                new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v3"))),
                                                                                                                        new CountDownStatement("cnt")))
                                                                                                ),

                                                                                                new CompoundStatement(new LatchAwaitStatement("cnt"),
                                                                                                        new CompoundStatement(new PrintStatement(new ValueExpression(new IntegerValue(100))),
                                                                                                                new CompoundStatement(new CountDownStatement("cnt"),
                                                                                                                        new PrintStatement(new ValueExpression(new IntegerValue(100))))))
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )))
                                )))
        );



        Statement ex8 = new CompoundStatement(
                new VariableDeclarationStatement(Type.INTEGER, "v"),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(10))),
                        new CompoundStatement(
                                new ForkStatement(
                                        new CompoundStatement(
                                                new AssignmentStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(1)), '-')),
                                                new CompoundStatement(
                                                        new AssignmentStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(1)), '-')),
                                                        new PrintStatement(new VariableExpression("v"))
                                                )
                                        )
                                ),
                                new CompoundStatement(
                                        new SleepStatement(10),
                                        new PrintStatement(new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(10)), '*'))
                                )
                        )
                )
        );

        Statement ex9 = new CompoundStatement(
                new VariableDeclarationStatement(Type.INTEGER, "v"),
                new CompoundStatement(
                        new VariableDeclarationStatement(Type.INTEGER, "x"),
                        new CompoundStatement(
                                new VariableDeclarationStatement(Type.INTEGER, "y"),
                                new CompoundStatement(
                                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(0))),
                                        new CompoundStatement(
                                                new RepeatUntilStatement(
                                                        new CompoundStatement(
                                                                new ForkStatement(
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new AssignmentStatement("v",
                                                                                        new ArithmeticExpression(
                                                                                                new VariableExpression("v"),
                                                                                                new ValueExpression(new IntegerValue(1)),
                                                                                                '-'
                                                                                        )
                                                                                )
                                                                        )
                                                                ),
                                                                new AssignmentStatement("v",
                                                                        new ArithmeticExpression(
                                                                                new VariableExpression("v"),
                                                                                new ValueExpression(new IntegerValue(1)),
                                                                                '+'
                                                                        )
                                                                )
                                                        ),
                                                        new RelationalExpression(
                                                                new VariableExpression("v"),
                                                                new ValueExpression(new IntegerValue(3)),
                                                                "=="
                                                        )),
                                                new CompoundStatement(
                                                        new AssignmentStatement("x",
                                                                new ValueExpression(new IntegerValue(1))),
                                                        new CompoundStatement(
                                                                new NoOperationStatement(),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("y",
                                                                                new ValueExpression(new IntegerValue(3))),
                                                                        new CompoundStatement(
                                                                                new NoOperationStatement(),
                                                                                new PrintStatement(new ArithmeticExpression(
                                                                                        new VariableExpression("v"),
                                                                                        new ValueExpression(new IntegerValue(10)),
                                                                                        '*'
                                                                                ))
                                                                        )
                                                                )
                                                        )
                                                ))
                                )
                        )
                )
        );
        Statement ex10 = new CompoundStatement(
                new VariableDeclarationStatement(new RefType(Type.INTEGER), "v1"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new RefType(Type.INTEGER), "v2"),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new RefType(Type.INTEGER), "v3"),
                                new CompoundStatement(
                                        new VariableDeclarationStatement(Type.INTEGER, "cnt"),
                                        new CompoundStatement(
                                                new HeapAllocStatement("v1", new ValueExpression(new IntegerValue(2))),
                                                new CompoundStatement(
                                                        new HeapAllocStatement("v2", new ValueExpression(new IntegerValue(3))),
                                                        new CompoundStatement(
                                                                new HeapAllocStatement("v3", new ValueExpression(new IntegerValue(4))),
                                                                new CompoundStatement(
                                                                        new NewBarrierStatement("cnt", new HeapReadExpression(new VariableExpression("v2"))),
                                                                        new CompoundStatement(
                                                                                new ForkStatement(
                                                                                        new CompoundStatement(
                                                                                                new AwaitStatement("cnt"),
                                                                                                new CompoundStatement(
                                                                                                        new HeapWriteStatement("v1", new ArithmeticExpression(
                                                                                                                new HeapReadExpression(new VariableExpression("v1")),
                                                                                                                new ValueExpression(new IntegerValue(10)),
                                                                                                                '*'
                                                                                                        )),
                                                                                                        new PrintStatement(new HeapReadExpression(new VariableExpression("v1")))
                                                                                                )
                                                                                        )
                                                                                ),
                                                                                new CompoundStatement(
                                                                                        new ForkStatement(
                                                                                                new CompoundStatement(
                                                                                                        new AwaitStatement("cnt"),
                                                                                                        new CompoundStatement(
                                                                                                                new HeapWriteStatement("v2", new ArithmeticExpression(
                                                                                                                        new HeapReadExpression(new VariableExpression("v2")),
                                                                                                                        new ValueExpression(new IntegerValue(10)),
                                                                                                                        '*'
                                                                                                                )),
                                                                                                                new CompoundStatement(
                                                                                                                        new HeapWriteStatement("v2", new ArithmeticExpression(
                                                                                                                                new HeapReadExpression(new VariableExpression("v2")),
                                                                                                                                new ValueExpression(new IntegerValue(10)),
                                                                                                                                '*'
                                                                                                                        )),
                                                                                                                        new PrintStatement(new HeapReadExpression(new VariableExpression("v2")))
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompoundStatement(
                                                                                                new AwaitStatement("cnt"),
                                                                                                new PrintStatement(new HeapReadExpression(new VariableExpression("v3")))
                                                                                        )
                                                                                )

                                                                        )
                                                                )
                                                        )
                                                )

                                        )
                                )
                        )
                )
        );

        Statement ex11 = new CompoundStatement(
                new VariableDeclarationStatement(Type.INTEGER, "a"),
                new CompoundStatement(new VariableDeclarationStatement(Type.INTEGER, "b"),
                        new CompoundStatement(new VariableDeclarationStatement(Type.INTEGER, "c"),
                                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntegerValue(1))),
                                        new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new IntegerValue(2))),
                                                new CompoundStatement(new AssignmentStatement("c", new ValueExpression(new IntegerValue(5))),
                                                        new CompoundStatement(
                                                                new SwitchStatement(
                                                                        new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntegerValue(10)), '*'),
                                                                        new ArithmeticExpression(new VariableExpression("b"), new VariableExpression("c"), '*'),
                                                                        new ValueExpression(new IntegerValue(10)),
                                                                        new CompoundStatement(new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))),
                                                                        new CompoundStatement(new PrintStatement(new ValueExpression(new IntegerValue(100))), new PrintStatement(new ValueExpression(new IntegerValue(200)))),
                                                                        new PrintStatement(new ValueExpression(new IntegerValue(300)))
                                                                ),
                                                                new PrintStatement(new ValueExpression(new IntegerValue(300)))
                                                        )))))));


        Statement ex12 = new CompoundStatement(
                new VariableDeclarationStatement(new RefType(Type.INTEGER), "v1"),
                new CompoundStatement(new VariableDeclarationStatement(Type.INTEGER, "cnt"),
                        new CompoundStatement(new HeapAllocStatement("v1", new ValueExpression(new IntegerValue(1))),
                                new CompoundStatement(new CreateSemaphore("cnt", new HeapReadExpression(new VariableExpression("v1"))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(new Acquire("cnt"),
                                                                new CompoundStatement(new HeapWriteStatement("v1", new ArithmeticExpression(new HeapReadExpression(new VariableExpression("v1")), new ValueExpression(new IntegerValue(10)), '*')),
                                                                        new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v1"))),
                                                                                new Release("cnt"))))
                                                ),
                                                new CompoundStatement(
                                                        new ForkStatement(
                                                                new CompoundStatement(new Acquire("cnt"),
                                                                        new CompoundStatement(new HeapWriteStatement("v1", new ArithmeticExpression(new HeapReadExpression(new VariableExpression("v1")), new ValueExpression(new IntegerValue(10)), '*')),
                                                                                new CompoundStatement(new HeapWriteStatement("v1", new ArithmeticExpression(new HeapReadExpression(new VariableExpression("v1")), new ValueExpression(new IntegerValue(2)), '*')),
                                                                                        new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v1"))),
                                                                                                new Release("cnt")))))
                                                        ),
                                                        new CompoundStatement(new Acquire("cnt"),
                                                                new CompoundStatement(new PrintStatement(new ArithmeticExpression(new HeapReadExpression(new VariableExpression("v1")), new ValueExpression(new IntegerValue(1)), '-')),
                                                                        new Release("cnt")))
                                                ))))));


        try {
            ex1.typeCheck(new HashMap<String, Type>());
            ex2.typeCheck(new HashMap<String, Type>());
            ex3.typeCheck(new HashMap<String, Type>());
            ex4.typeCheck(new HashMap<String, Type>());
            ex5.typeCheck(new HashMap<String, Type>());
            ex6.typeCheck(new HashMap<String, Type>());
            ex7.typeCheck(new HashMap<String, Type>());
            ex8.typeCheck(new HashMap<String, Type>());
            ex9.typeCheck(new HashMap<String, Type>());
            ex10.typeCheck(new HashMap<String, Type>());
            ex11.typeCheck(new HashMap<String, Type>());
            ex12.typeCheck(new HashMap<String, Type>());

            IO.println("All typecheckers for all examples passed");
        }
        catch (Exception e) {
            e.printStackTrace();
            IO.println(e.getMessage());
        }
        java.util.List<Statement> programs = java.util.Arrays.asList(ex1, ex2, ex3, ex4, ex5,ex6,ex7, ex8, ex9, ex10,ex11, ex12);
        SelectWindow selectWindow = new SelectWindow(programs);
        selectWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
