package evaluate;

import ast.ASTNode;
import ast.Identifier;
import ast.Program;
import game.model.GameObject;

public class Evaluator {
    ASTNode ast;
    private static Evaluator evaluator;
    private EvaluateVisitor evalVisitorBuddy;

    private Evaluator(ASTNode astNode){
        ast = astNode;
    }

    public void evaluate(){
        evalVisitorBuddy = new EvaluateVisitor();
        Program programBuddy = (Program) ast;
        programBuddy.accept(evalVisitorBuddy);
    }

    public static Evaluator getInstance(ASTNode ast){
        if(evaluator == null) {
            evaluator = new Evaluator(ast);
        }
        return evaluator;
    }

    public static Evaluator getInstance(){
        return evaluator;
    }

    public void run(Identifier behaviour, GameObject go){
        evalVisitorBuddy.run(behaviour, go);
    }
}