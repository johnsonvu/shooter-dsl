package evaluate;

import ast.*;
import ast.Number;
import game.model.*;
import game.view.Game;

import lib.OPERATION;
import ui.Main;
import visitor.Visitor;

import java.util.HashMap;
import java.util.function.Function;

public class EvaluateVisitor implements Visitor<Integer> {
    private ASTNode ast;
    private Game game;
    private String scope = "";
    public static HashMap<String, Integer> varTable = new HashMap<>();

    public EvaluateVisitor(ASTNode astNode){
        ast = astNode;
    }

    @Override
    public Integer visit(ASTNode v) {
        return 0;
    }

    @Override
    //PROGRAM ::= GAME_DEF OBJECT_MODIFIER* FUNCTION_DEC*
    public Integer visit(Program p) {
        game = new Game();
        p.game.accept(this);
        for(ObjectModifier om : p.objects){
            om.accept(this);
        }
        for(FunctionDec fn : p.functions){
            fn.accept(this);
        }
        return 0;
    }

    @Override
    //GAME_DEF ::= "make game" IDENTIFIER "{" "height = " NUMBER ", width = " NUMBER" "}" "{" GAME_STATEMENT* "}"
    public Integer visit(GameDef gd) {
        game.setWindow(gd.name, gd.height, gd.width);
        for(GameStatement gs : gd.statements) {
            gs.accept(this);
        }
        return 0;
    }

    @Override
    public Integer visit(ObjectModifier om) {
        for(PropertyStatement ps : om.propertyStatements){
            ps.accept(this);
        }
        return 0;
    }


    @Override
    public Integer visit(Statement s) {
        return 0;
    }

    @Override
    //GAME_STATEMENT ::= MAKE_STATEMENT | FOR_LOOP
    public Integer visit(GameStatement gs) {
        gs.accept(this);
        return 0;
    }

    @Override
    //MAKE_STATEMENT ::=  "make" NUMBER? TYPE IDENTIFIER
    public Integer visit(MakeStatement ms) {
        switch(ms.type.type){
            case PLAYER:
                Player play = new Player(ms.identifier.name);
                break;
            case ENEMY:
                Enemy enemy = new Enemy(ms.identifier.name, ms.number == null? 1 : ms.number.number);
                Main.gameObjectTable.put(ms.identifier.name, enemy);  //enemy should implement GameObject
                break;
            case PROJECTILE:
                Projectile projectile = new Projectile(ms.identifier.name, ms.number == null? 1 : ms.number.number);
                Main.gameObjectTable.put(ms.identifier.name, projectile);
                break;
            case ITEM:
                Item item = new Item(ms.identifier.name, ms.number == null? 1: ms.number.number);
                Main.gameObjectTable.put(ms.identifier.name, item);
                break;
            default:
                //TODO: Default case

        }
        return 0;
    }

    @Override
    public Integer visit(PropertyStatement ps) {
        switch(ps.property.property){
            case DAMAGE:
                varTable.put(scope + "_damage", ps.expr.accept(this));
            case HEALTH:
                varTable.put(scope + "_health", ps.expr.accept(this));
            default:
                //TODO: default case
        }
        return null;
    }

    @Override
    public Integer visit(FunctionDec fd) {
        Main.blockTable.put(fd.name.name, fd.functionBlock);
        return null;
    }

    @Override
    public Integer visit(FunctionBlock fb) {
        fb.block.accept(this);
        return fb.retExpr.accept(this);
    }

    @Override
    public Integer visit(FunctionCall fc) {
        FunctionBlock fb =  Main.blockTable.get(fc.name.name);
        String key;
        Integer value;
        for (int i=0; i<fc.arguments.size(); i++) {
            key = fb.params.get(i).name;
            value = fc.arguments.get(i).accept(this);
            varTable.put(key, value);
        }

        return fb.accept(this);
    }

    @Override
    public Integer visit(Block b) {
        for (Statement s: b.statements) {
            s.accept(this);
        }
        return null;
    }

    @Override
    public Integer visit(BehaveStatement bs) {
        return null;
    }

    @Override
    public Integer visit(MovementStatement ms) {
        GameObject go = Main.gameObjectTable.get(scope);
        go.move(ms.direction.direction, ms.number.number);
        return null;
    }

    @Override
    public Integer visit(ShootStatement ss) {
        GameObject go = Main.gameObjectTable.get(scope);
        go.shootDirection(ss.direction.direction);
        return null;
    }

    @Override
    public Integer visit(Property p) {
        return null;
    }

    @Override
    public Integer visit(VarDec vd) {
        varTable.put(vd.name, null);
        return 0;
    }

    @Override
    public Integer visit(VarSet vs) {
        varTable.put(vs.id.name, vs.value.accept(this));
        return 0;
    }

    @Override
    public Integer visit(Expression expr) {
        if (expr.op == null) {
            return expr.ex1.accept(this);
        }

        if(expr.op != null){
            Expression myExpr = new Expression();
            switch (expr.op.operation) {
                case PLUS:
                    return expr.ex1.accept(this) + expr.ex2.accept(this);
                case MINUS:
                    return expr.ex1.accept(this) - expr.ex2.accept(this);
                case MULTIPLY:
                    myExpr.ex1 = new Number(expr.ex1.accept(this) * expr.ex2.ex1.accept(this));
                    myExpr.op = expr.ex2.op;
                    myExpr.ex2 = expr.ex2.ex2;
                    return myExpr.accept(this);
                case DIVIDE:
                    myExpr.ex1 = new Number(expr.ex1.accept(this) / expr.ex2.ex1.accept(this));
                    myExpr.op = expr.ex2.op;
                    myExpr.ex2 = expr.ex2.ex2;
                    return myExpr.accept(this);
                }
        }
        return null;
    }



    @Override
    public Integer visit(Type t) {
        return null;
    }

    @Override
    public Integer visit(Identifier id) {
        return varTable.get(id.name);
    }

    @Override
    public Integer visit(Number n) {
        return n.number;
    }
}