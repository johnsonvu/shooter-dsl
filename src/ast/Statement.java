package ast;

import visitor.Visitor;

public abstract class Statement extends ASTNode {

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
