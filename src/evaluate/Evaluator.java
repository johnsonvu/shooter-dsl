package evaluate;

import ast.ASTNode;
import ast.Program;
import game.Game;

public class Evaluator {
    ASTNode ast;
    public Evaluator(ASTNode astNode){
        ast = astNode;
    }

    public Game evaluate(){
        EvaluateVisitor evalVisitorBuddy = new EvaluateVisitor(ast);
        Program programBuddy = (Program) ast;
        return programBuddy.accept(evalVisitorBuddy);
    }
}