package ast;

import visitor.Visitor;

public class Statement extends ASTNode {
    public String value;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
