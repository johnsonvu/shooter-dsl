package parser;

import ast.*;
import ast.Number;
import lib.OPERATION;
import lib.PROPERTY;
import lib.TYPE;
import tokenizer.Tokenizer;
import visitor.Visitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        p.objects = new ArrayList<>();
        while(tokenizer.checkNext("set"))
        {
            ObjectModifier om = new ObjectModifier();
            p.objects.add((ObjectModifier) om.accept(this));
        }

        p.functions = new ArrayList<>();
        while(tokenizer.checkNext("define"))
        {
            FunctionDec fd = new FunctionDec();
            p.functions.add((FunctionDec) fd.accept(this));
        }

        return p;
    }

    @Override
    public ASTNode visit(GameDef gd) {
        tokenizer.getAndCheckNext("make");
        tokenizer.getAndCheckNext("game");

        gd.name = tokenizer.getNext();

        tokenizer.getAndCheckNext("{");
        tokenizer.getAndCheckNext("width");
        tokenizer.getAndCheckNext("=");

        gd.width = Integer.valueOf(tokenizer.getNext());

        tokenizer.getAndCheckNext("height");
        tokenizer.getAndCheckNext("=");

        gd.height = Integer.valueOf(tokenizer.getNext());

        tokenizer.getAndCheckNext("}");
        tokenizer.getAndCheckNext("{");

        gd.statements = new ArrayList<GameStatement>();

        // TODO: Add support for LOOPS
        while(!tokenizer.checkNext("\\}")){
            GameStatement gs = new GameStatement();
            gd.statements.add((GameStatement) gs.accept(this));
        }
        tokenizer.getAndCheckNext("}");
        return gd;
    }

    @Override
    public ASTNode visit(ObjectModifier om) {
        if(tokenizer.getAndCheckNext("set")){
            if(tokenizer.checkNext("[A-Z|a-z|0-9]+")){
                Identifier id = new Identifier(tokenizer.getNext());
                om.identifier = id;

                tokenizer.getAndCheckNext("{");
                om.propertyStatements = new ArrayList<>();
                while(tokenizer.checkNext("damage") || tokenizer.checkNext("health")){
                    PropertyStatement ps = new PropertyStatement();
                    om.propertyStatements.add((PropertyStatement) ps.accept(this));
                }

                if(tokenizer.checkNext("behave")){
                    tokenizer.getAndCheckNext("behave");
                    tokenizer.getAndCheckNext("=");
                    om.behave = new Identifier(tokenizer.getNext());
                }

                tokenizer.getAndCheckNext("}");
            }
        }
        return om;
    }

    @Override
    public ASTNode visit(Statement s) {

        if(tokenizer.checkNext("make")){
            GameStatement gs = new MakeStatement();
            s =  (MakeStatement) gs.accept(this);
        }
        else if(tokenizer.checkNext("move") || tokenizer.checkNext("shoot")){
            BehaveStatement bs = new BehaveStatement();
            s = (BehaveStatement) bs.accept(this);
        }
        else if(tokenizer.checkNext("damage") || tokenizer.checkNext("health")){
            PropertyStatement ps = new PropertyStatement();
            s = (PropertyStatement) ps.accept(this);
        }
        else if(tokenizer.checkNext("[A-Z|a-z|0-9]+") && tokenizer.checkNext("=",2)){
            VarSet vs = new VarSet();
            s = (VarSet) vs.accept(this);
        }
        else if(tokenizer.checkNext("new")) {
            VarDec vd = new VarDec();
            s = (VarDec) vd.accept(this);
        }

        return s;
    }

    @Override
    public ASTNode visit(GameStatement gs) {
        if(tokenizer.checkNext("make")){
            MakeStatement makeStatement = new MakeStatement();
            gs = (MakeStatement) makeStatement.accept(this);
        }
        return gs;
    }

    @Override
    public ASTNode visit(MakeStatement ms) {
        tokenizer.getAndCheckNext("make");
        if(tokenizer.checkNext("\\d+")){
            tokenizer.getNext();
        }

        Type type = new Type(tokenizer.getNext());
        ms.type = type;

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
//        if (tokenizer.checkNext("[0-9]+")) {
            Expression e = new Expression();
            ps.expr = (Expression) e.accept(this);
//        }

        return ps;
    }

    @Override
    public ASTNode visit(FunctionDec fd) {
        tokenizer.getAndCheckNext("define");
        fd.name = new Identifier(tokenizer.getNext());
        tokenizer.getAndCheckNext("(");

        fd.parameters = new ArrayList<>();
        while (!tokenizer.checkNext("\\)")) {
            Identifier param = new Identifier(tokenizer.getNext());
            fd.parameters.add(param);
            if (!tokenizer.checkNext(",")) break;
        }

        tokenizer.getAndCheckNext(")");

        Block block = new Block();

        fd.block = (Block) block.accept(this);
        return fd;
    }

    @Override
    public ASTNode visit(FunctionCall fc) {
        tokenizer.getAndCheckNext("call");
        fc.name = new Identifier(tokenizer.getNext());
        tokenizer.getAndCheckNext("(");

        fc.arguments = new ArrayList<Expression>();
        while (!tokenizer.checkNext("\\)")) {
            Expression expr = new Expression();
            fc.arguments.add((Expression) expr.accept(this));
            if (!tokenizer.checkNext(",")) break;
        }
        tokenizer.getAndCheckNext(")");

        return fc;
    }

    @Override
    public ASTNode visit(Block b) {
        tokenizer.getAndCheckNext("{");

        b.statements = new ArrayList<>();
        while (!tokenizer.checkNext("}")) {
            Statement statement = new Statement();
            b.statements.add((Statement) statement.accept(this));
        }

        tokenizer.getAndCheckNext("}");
        return b;
    }

    @Override
    public ASTNode visit(BehaveStatement bs) {
        if (tokenizer.checkNext("move")) {
            MovementStatement ms = new MovementStatement();
            bs = (MovementStatement) ms.accept(this);
        } else if (tokenizer.checkNext("shoot")) {
            ShootStatement ss = new ShootStatement();
            bs = (ShootStatement) ss.accept(this);
        }

        return bs;
    }

    @Override
    public ASTNode visit(MovementStatement ms) {
        tokenizer.getAndCheckNext("move");
        if (tokenizer.checkNext("[0-9]+")) {
            ms.number = new Number(Integer.valueOf(tokenizer.getNext()));
        }
        ms.direction = new Direction(tokenizer.getNext());

        return ms;
    }

    @Override
    public ASTNode visit(ShootStatement ss) {
        tokenizer.getAndCheckNext("shoot");
        ss.direction = new Direction(tokenizer.getNext());

        return ss;
    }

    @Override
    public ASTNode visit(Property p) {
        String property = tokenizer.getNext().toLowerCase();
        if (property.equals("health")) {
            p.property = PROPERTY.HEALTH;
        } else if (property.equals("damage")) {
            p.property = PROPERTY.DAMAGE;
        }

        return p;
    }

    @Override
    public ASTNode visit(Type t) {
        String type = tokenizer.getNext().toLowerCase();
        if (type.equals("player")) {
            t.type = TYPE.PLAYER;
        } else if (type.equals("projectile")) {
            t.type = TYPE.PROJECTILE;
        } else if (type.equals("enemy")) {
            t.type = TYPE.ENEMY;
        } else if (type.equals("item")) {
            t.type = TYPE.ITEM;
        }

        return t;
    }

    @Override
    public ASTNode visit(Identifier id) {
        if(tokenizer.checkNext(" [A-Z|a-z|0-9]+")){
            id.name = tokenizer.getNext();
        }
        return id;
    }

    @Override
    public ASTNode visit(Number n) {
        if(tokenizer.checkNext("[0-9]+")){
            n.number = Integer.parseInt(tokenizer.getNext());
        }
        return n;
    }

    public ASTNode visit(VarDec vd) {
        tokenizer.getAndCheckNext("new");
        vd.name = tokenizer.getNext();
        return vd;
    }

    public ASTNode visit(VarSet vs) {
        vs.id = new Identifier(tokenizer.getNext());
        tokenizer.getAndCheckNext("=");
        Expression expression = new Expression();
        vs.value = (Expression) expression.accept(this);
        return vs;
    }

    public ASTNode visit(Expression expr) {
        if(tokenizer.checkNext("\\d")){
            expr.ex1 = new Number(Integer.valueOf(tokenizer.getNext()));
        }

        else if(tokenizer.checkNext("call")) {
            FunctionCall fc = new FunctionCall();
            expr.ex1 = (FunctionCall) fc.accept(this);
        }

        else if(tokenizer.checkNext("[A-Z|a-z|0-9]*")){
            expr.ex1 = new Identifier(tokenizer.getNext());
        }

        if(tokenizer.checkNext("\\+|\\-|\\*|\\/")){
//            if(tokenizer.checkNext("[0-9]+")){
                expr.op = new Operation(tokenizer.getNext());
//            }
            Expression secondExpr = new Expression();
            expr.ex2 = (Expression) secondExpr.accept(this);
        }
        return expr;
    }
}
