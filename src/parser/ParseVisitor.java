package parser;

import ast.*;
import ast.Number;
import tokenizer.Tokenizer;
import visitor.Visitor;

import java.util.ArrayList;

public class ParseVisitor implements Visitor<ASTNode> {
    Tokenizer tokenizer;

    public ParseVisitor()
    {
        tokenizer = Tokenizer.getInstance();
    }

    @Override
    public ASTNode visit(ASTNode v) {
        return null;
    }

    @Override
    public ASTNode visit(Program p) {
        GameDef gd = new GameDef();
        p.game = (GameDef) gd.accept(this);
        return p;
    }

    @Override
    public ASTNode visit(GameDef gd) {
        tokenizer.getAndCheckNext("make");
        tokenizer.getAndCheckNext("game");

        gd.name = tokenizer.getNext();

        tokenizer.getAndCheckNext("{");
        tokenizer.getAndCheckNext("height");
        tokenizer.getAndCheckNext("=");

        gd.height = Integer.valueOf(tokenizer.getNext());

        tokenizer.getAndCheckNext(",");
        tokenizer.getAndCheckNext("width");
        tokenizer.getAndCheckNext("=");

        gd.width = Integer.valueOf(tokenizer.getNext());

        tokenizer.getAndCheckNext("}");
        tokenizer.getAndCheckNext("{");

        gd.statements = new ArrayList<GameStatement>();

        while(tokenizer.checkNext("make")){
            GameStatement gs = new GameStatement();
            gd.statements.add((GameStatement) gs.accept(this));
        }
        tokenizer.getAndCheckNext("}");
        return gd;
    }

    @Override
    public ASTNode visit(ObjectModifier gd) {
        return null;
    }

    @Override
    public ASTNode visit(Statement s) {
        if(tokenizer.checkNext("set")){
            GameStatement gs = new GameStatement();
            s =  (GameStatement) gs.accept(this);
        }
        if(tokenizer.checkNext("move")||tokenizer.checkNext("shoot")){
            BehaveStatement bs = new BehaveStatement();
            s = (BehaveStatement) bs.accept(this);
        }
        if(tokenizer.checkNext("damage")||tokenizer.checkNext("health")){
            PropertyStatement ps = new PropertyStatement();
            s = (PropertyStatement) ps.accept(this);
        }

        return s;
    }

    @Override
    public ASTNode visit(GameStatement gs) {
        if(tokenizer.checkNext("make")){
            MakeStatement msPaint = new MakeStatement();
            gs = (MakeStatement) msPaint.accept(this);
        }
        return gs;
    }

    @Override
    public ASTNode visit(MakeStatement ms) {
        tokenizer.getAndCheckNext("make");
        if(tokenizer.checkNext("\\d+")){
            tokenizer.getNext();
        }

        //Type t = new Type();
        //ms.type = tokenizer.getNext();
        Identifier id = new Identifier(tokenizer.getNext());
        ms.identifier = id;
        return ms;
    }

    @Override
    public ASTNode visit(PropertyStatement ps) {
        String property = tokenizer.getNext();
        ps.property = new Property(property);
        tokenizer.getAndCheckNext("=");
        // TODO: Add support for DIRECTION
        if (tokenizer.checkNext("[0-9]+")) {
            ps.value = new Number(Integer.valueOf(tokenizer.getNext()));
        }
        return ps;
    }

    @Override
    public ASTNode visit(FunctionDec fd) {
        tokenizer.getAndCheckNext("define");
        fd.name = new Identifier(tokenizer.getNext());
        // TODO: FINISH THIS LATER HERGH
        return null;
    }

    @Override
    public ASTNode visit(FunctionCall fc) {
        return null;
    }

    @Override
    public ASTNode visit(Block b) {
        return null;
    }

    @Override
    public ASTNode visit(BehaveStatement bs) {
        return null;
    }

    @Override
    public ASTNode visit(MovementStatement ms) {
        return null;
    }

    @Override
    public ASTNode visit(ShootStatement ss) {
        return null;
    }

    @Override
    public ASTNode visit(Property p) {
        return null;
    }

    @Override
    public ASTNode visit(Type t) {
        return null;
    }

    @Override
    public ASTNode visit(Identifier id) {
        return null;
    }

    @Override
    public ASTNode visit(Number n) {
        return null;
    }
}
