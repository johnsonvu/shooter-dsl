package parser;

import ast.*;
import ast.Number;
import lib.enums.Comparator;
import lib.enums.Property;
import lib.enums.Type;
import tokenizer.Tokenizer;
import visitor.Visitor;

import java.util.ArrayList;

import static lib.enums.Comparator.*;

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

        gd.statements = new ArrayList<Statement>();

        // TODO: Add support for LOOPS
        while(!tokenizer.checkNext("\\}")){
            if (tokenizer.checkNext("make"))
                gd.statements.add((GameStatement) (new GameStatement()).accept(this));
            else if (tokenizer.checkNext("new"))
                gd.statements.add((VarDec) (new VarDec()).accept(this));
            else if (tokenizer.checkNext("[A-Z|a-z][A-Z|a-z|0-9]*"))
                gd.statements.add((VarSet) (new VarSet()).accept(this));
        }
        tokenizer.getAndCheckNext("}");
        return gd;
    }

    @Override
    public ASTNode visit(ObjectModifier om) {
        if(tokenizer.getAndCheckNext("set")){
            if(tokenizer.checkNext("[A-Z|a-z][A-Z|a-z|0-9]*")){
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

        if(tokenizer.checkNext("make") || tokenizer.checkNext("if")){
            GameStatement gs = new GameStatement();
            s =  (GameStatement) gs.accept(this);
        }
        else if(tokenizer.checkNext("move") || tokenizer.checkNext("shoot")){
            BehaveStatement bs = new BehaveStatement();
            s = (BehaveStatement) bs.accept(this);
        }
        else if(tokenizer.checkNext("damage") || tokenizer.checkNext("health")){
            PropertyStatement ps = new PropertyStatement();
            s = (PropertyStatement) ps.accept(this);
        }
        else if(tokenizer.checkNext("[A-Z|a-z][A-Z|a-z|0-9]*") && tokenizer.checkNext("=",2)){
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
        else if(tokenizer.checkNext("if")){
            IfStatement ifStatement = new IfStatement();
            gs = (IfStatement) ifStatement.accept(this);
        }
        return gs;
    }

    @Override
    public ASTNode visit(MakeStatement ms) {
        tokenizer.getAndCheckNext("make");
        if(!tokenizer.checkNext("enemy|player|item|projectile")){
            ms.number = (Expression) (new Expression()).accept(this);
        }

        ast.Type type = new ast.Type(tokenizer.getNext());
        ms.type = type;

        Identifier id = new Identifier(tokenizer.getNext());
        ms.identifier = id;
        return ms;
    }

    @Override
    public ASTNode visit(PropertyStatement ps) {
        String property = tokenizer.getNext();
        ps.property = new ast.Property(property);
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

        fd.functionBlock = new FunctionBlock();
        fd.functionBlock.params = new ArrayList<>();
        while (!tokenizer.checkNext("\\)")) {
            Identifier param = new Identifier(tokenizer.getNext());
            fd.functionBlock.params.add(param);
            if (!tokenizer.checkNext(",")) break;
        }

        tokenizer.getAndCheckNext(")");

        Block block = new Block();

        fd.functionBlock.block = (Block) block.accept(this);

        if(tokenizer.checkNext("return")) {
            tokenizer.getAndCheckNext("return");
            Expression ex = new Expression();
            fd.functionBlock.retExpr = (Expression) ex.accept(this);
        }

        return fd;
    }

    @Override
    public ASTNode visit(FunctionBlock fb) {
        return null;
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
        while (!tokenizer.checkNext("\\}")) {
            if(tokenizer.checkNext("return")) return b;
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
            Expression ex = new Expression();
            ms.expr = (Expression) ex.accept(this);
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
    public ASTNode visit(IfStatement iffy) {
        tokenizer.getAndCheckNext("if");
        tokenizer.getAndCheckNext("(");

        iffy.condition = (Condition) (new Condition()).accept(this);

        tokenizer.getAndCheckNext(")");

        iffy.block = (Block) (new Block()).accept(this);

        return iffy;
    }

    @Override
    public ASTNode visit(Condition cond) {
        cond.ex1 = (Expression) (new Expression()).accept(this);
        String comp = tokenizer.getNext();

        switch (comp){
            case ">":
                cond.comparator = GREATER_THAN;
                break;
            case "<":
                cond.comparator = LESS_THAN;
                break;
            case "=":
                cond.comparator = EQUAL;
                break;
        }

        cond.ex2 = (Expression) (new Expression()).accept(this);

        if(tokenizer.checkNext("and")){
            tokenizer.getNext();

            Condition cond2 = new Condition();
            cond.andCondition = (Condition) cond2.accept(this);
        }

        return cond;
    }

    @Override
    public ASTNode visit(ast.Property p) {
        String property = tokenizer.getNext().toLowerCase();
        if (property.equals("health")) {
            p.property = Property.HEALTH;
        } else if (property.equals("damage")) {
            p.property = Property.DAMAGE;
        }

        return p;
    }

    @Override
    public ASTNode visit(ast.Type t) {
        String type = tokenizer.getNext().toLowerCase();
        if (type.equals("player")) {
            t.type = Type.PLAYER;
        } else if (type.equals("projectile")) {
            t.type = Type.PROJECTILE;
        } else if (type.equals("enemy")) {
            t.type = Type.ENEMY;
        } else if (type.equals("item")) {
            t.type = Type.ITEM;
        }

        return t;
    }

    @Override
    public ASTNode visit(Identifier id) {
        if(tokenizer.checkNext(" [A-Z|a-z][A-Z|a-z|0-9]*")){
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
        if(tokenizer.checkNext("\\d+")){
            expr.ex1 = new Number(Integer.valueOf(tokenizer.getNext()));
        }

        else if(tokenizer.checkNext("call")) {
            FunctionCall fc = new FunctionCall();
            expr.ex1 = (FunctionCall) fc.accept(this);
        }

        else if(tokenizer.checkNext("[A-Z|a-z][A-Z|a-z|0-9]*")){
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
