package ast;

import visitor.Visitor;

public class GameDef extends ASTNode{
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
