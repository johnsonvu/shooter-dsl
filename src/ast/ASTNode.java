package ast;

import visitor.Visitor;

public abstract class ASTNode {
    public abstract <T> T accept(Visitor<T> v);
}
