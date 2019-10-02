package ast;

import visitor.Visitor;

public class GameState extends ASTNode{
    public String state;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
