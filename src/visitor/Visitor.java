package visitor;

import ast.*;
import ast.Number;

public interface Visitor<T> {
    public T visit(ASTNode v);

    public T visit(Program p);

    public T visit(GameDef gd);

    public T visit(ObjectModifier gd);

    public T visit(Statement s);

    public T visit(GameStatement gs);

    public T visit(MakeStatement ms);

    public T visit(PropertyStatement ps);

    public T visit(FunctionDec fd);

    public T visit(FunctionCall fc);

    public T visit(Block b);

    public T visit(BehaveStatement bs);

    public T visit(MovementStatement ms);

    public T visit(ShootStatement ss);

//    public T visit(IfStatement is);

    public T visit(Property p);

    public T visit(VarDec vd);

    public T visit(VarSet vs);

    public T visit(Expression expr);

    public T visit(Type t);

    public T visit(Identifier id);

    public T visit(Number n);
}
