//package evaluate;
//
//import ast.*;
//import visitor.Visitor;
//
//import java.util.function.Function;
//
//public class EvaluateVisitor implements Visitor<Function<T,R>> {
//    @Override
//    public Function<T, R> visit(ASTNode v) {
//        return null;
//    }
//
//    @Override
//    public Function<T, R> visit(Program p) {
//        p.game.accept(this);
//        return null;
//    }
//
//    @Override
//    public Function<T, R> visit(GameDef gd) {
//        //gd.height;
//        return null;
//    }
//
//    @Override
//    public Function<T, R> visit(ObjectDef gd) {
//        return null;
//    }
//
//    @Override
//    public Function<T, R> visit(GameStatement gs) {
//        return null;
//    }
//
//    @Override
//    public Function<T, R> visit(MakeStatement ms) {
//        return null;
//    }
//
//    @Override
//    public Function<T, R> visit(FunctionDec fd) {
//        return null;
//    }
//
//    @Override
//    public Function<T, R> visit(FunctionCall fc) {
//        return null;
//    }
//}
