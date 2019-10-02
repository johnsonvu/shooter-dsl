package visitor;

import ast.*;

public interface Visitor<T> {
    public T visit(ASTNode v);

    public T visit(Program p);

    public T visit(GameDef gd);
}
