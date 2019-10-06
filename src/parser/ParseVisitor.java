package parser;

import ast.*;
import ast.Number;
import lib.PROPERTY;
import lib.TYPE;
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

        // TODO: Add support for LOOPS
        while(tokenizer.checkNext("make")){
            GameStatement gs = new MakeStatement();
            gd.statements.add((MakeStatement) gs.accept(this));
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
        if(tokenizer.checkNext("make")){
            GameStatement gs = new MakeStatement();
            s =  (MakeStatement) gs.accept(this);
        }
        else if(tokenizer.checkNext("move")){
            BehaveStatement bs = new MovementStatement();
            s = (MovementStatement) bs.accept(this);
        }
        else if(tokenizer.checkNext("shoot")){
            BehaveStatement bs = new ShootStatement();
            s = (ShootStatement) bs.accept(this);
        }
        else if(tokenizer.checkNext("damage")){
            PropertyStatement ps = new PropertyStatement();
            s = (PropertyStatement) ps.accept(this);
        }
        else if(tokenizer.checkNext("health")){
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
        tokenizer.getAndCheckNext("{");

        Statement statement;
        while (!tokenizer.checkNext("}")) {
            if (tokenizer.checkNext("make")) {
                statement = new MakeStatement();
                b.statements.add((MakeStatement) statement.accept(this));
            } else if (tokenizer.checkNext("move")) {
                statement = new MovementStatement();
                b.statements.add((MovementStatement) statement.accept(this));
            } else if (tokenizer.checkNext("shoot")) {
                statement = new ShootStatement();
                b.statements.add((ShootStatement) statement.accept(this));
            } else if (tokenizer.checkNext("health") || tokenizer.checkNext("damage")) {
                statement = new PropertyStatement();
                b.statements.add((PropertyStatement) statement.accept(this));
            }
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
        return null;
    }

    @Override
    public ASTNode visit(Number n) {
        return null;
    }
}
