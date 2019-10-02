package parser;

import ast.ASTNode;
import ast.Program;
import tokenizer.Tokenizer;
import visitor.Visitor;

public class ParseVisitor implements Visitor<ASTNode> {
    Tokenizer tokenizer;

    public ParseVisitor()
    {
        tokenizer = Tokenizer.getInstance();
    }

    @Override
    public ASTNode visit(ASTNode v) {
        return null;
    }

    @Override
    public ASTNode visit(GameDef gd) {

        tokenizer.getAndCheckNext("make");
        tokenizer.getAndCheckNext("game");

        gd.name = tokenizer.getNext();


        tokenizer.getAndCheckNext("height");
        tokenizer.getAndCheckNext("=");

        gd.height = tokenizer.getNext(); //convert to int
        return gd;
    }
}
