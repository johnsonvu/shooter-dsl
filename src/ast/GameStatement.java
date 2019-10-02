package ast;

import visitor.Visitor;

public class GameStatement extends ASTNode{
    public String statement;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
