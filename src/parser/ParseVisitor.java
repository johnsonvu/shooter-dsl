package parser;

import ast.*;
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
    public ASTNode visit(Program p) {
        GameDef gd = new GameDef();
        p.game = (GameDef) gd.accept(this);
        return p;
    }

    @Override
    public ASTNode visit(GameDef gd) {

        tokenizer.getAndCheckNext("make");
        tokenizer.getAndCheckNext("game");

        gd.name = tokenizer.getNext();

        tokenizer.getAndCheckNext("height");
        tokenizer.getAndCheckNext("=");

        gd.height = Integer.valueOf(tokenizer.getNext());
        return gd;
    }

    @Override
    public ASTNode visit(ObjectDef gd) {
        return null;
    }

    @Override
    public ASTNode visit(GameStatement gs) {
        return null;
    }

    @Override
    public ASTNode visit(MakeStatement ms) {
        return null;
    }

    @Override
    public ASTNode visit(FunctionDec fd) {
        return null;
    }

    @Override
    public ASTNode visit(FunctionCall fc) {
        return null;
    }
}
