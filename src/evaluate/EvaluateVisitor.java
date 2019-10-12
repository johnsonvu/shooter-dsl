package evaluate;

import ast.*;
import ast.Number;
import game.view.Game;
import game.model.Enemy;
import game.model.Item;
import game.model.Player;
import game.model.Projectile;

import lib.OPERATION;
import ui.Main;
import visitor.Visitor;

import java.util.HashMap;
import java.util.function.Function;

public class EvaluateVisitor implements Visitor<Integer> {
    ASTNode ast;
    Game game;

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
    public Integer visit(ObjectModifier gd) {
        for(PropertyStatement ps : gd.propertyStatements){
            switch(ps.property.property){
                case DAMAGE:
                    //TODO: search in hashmap and change the GameObject of the object with the same identifier name
                    //game.modifyDamage(gd.identifier.name, ps.value.number);
                case HEALTH:
                    //TODO: search in hashmap and change the GameObject of the object with the same identifier name
                    //game.modifyHealth(gd.identifier.name, ps.value.number);
                default:
                    //TODO: default case
            }
        }
        //TODO: set behaviour
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
                for(int i = 0; i < ms.number.number; i++) {
                    Enemy enemy = new Enemy(ms.identifier.name);
                    Main.gameObjectTable.put(ms.identifier.name + Integer.toString(i),  enemy);  //enemy should implement GameObject
                }
                break;
            case PROJECTILE:
                for(int i = 0; i< ms.number.number; i++) {
                    Projectile projectile = new Projectile(ms.identifier.name);
                    Main.gameObjectTable.put(ms.identifier.name + Integer.toString(i) ,projectile);
                }
                break;
            case ITEM:
                for(int i = 0; i< ms.number.number; i++) {
                    Item item = new Item(ms.identifier.name);
                    Main.gameObjectTable.put(ms.identifier.name + Integer.toString(i) ,item);
                }
                break;
            default:

        }
        return 0;
    }

    @Override
    public Integer visit(PropertyStatement ps) {
        return null;
    }

    @Override
    public Integer visit(FunctionDec fd) {
        return null;
    }

    @Override
    public Integer visit(FunctionCall fc) {

        return null;
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
        return null; //TODO
    }

    @Override
    public Integer visit(ShootStatement ss) {
        return null; //TODO
    }

    @Override
    public Integer visit(Property p) {
        return null;
    }

    @Override
    public Integer visit(VarDec vd) {
        Main.varTable.put(vd.name, null);
        return 0;
    }

    @Override
    public Integer visit(VarSet vs) {
        Main.varTable.put(vs.id.name, vs.value.accept(this));
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
        return Main.varTable.get(id.name);
    }

    @Override
    public Integer visit(Number n) {
        return n.number;
    }
}