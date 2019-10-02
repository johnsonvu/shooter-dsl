package visitor;

import ast.*;

public interface Visitor<T> {
    public T visit(ASTNode v);

    public T visit(Program p);

    public T visit(GameDef gd);

    public T visit(ObjectDef gd);

    public T visit(GameStatement gs);

    public T visit(MakeStatement ms);

    public T visit(FunctionDec fd);

    public T visit(FunctionCall fc);
}
