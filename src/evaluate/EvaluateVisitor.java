package evaluate;

import ast.*;
import ast.Number;
import game.view.Game;
import game.model.Enemy;
import game.model.Item;
import game.model.Player;
import game.model.Projectile;

import ui.Main;
import visitor.Visitor;

import java.util.HashMap;
import java.util.function.Function;

public class EvaluateVisitor implements Visitor<Game> {
    ASTNode ast;
    Game game;

    public EvaluateVisitor(ASTNode astNode){
        ast = astNode;
    }

    @Override
    public Game visit(ASTNode v) {
        return null;
    }

    @Override
    //PROGRAM ::= GAME_DEF OBJECT_MODIFIER* FUNCTION_DEC*
    public Game visit(Program p) {
        game = new Game();
        p.game.accept(this);
        for(ObjectModifier om : p.objects){
            om.accept(this);
        }
        for(FunctionDec fn : p.functions){
            fn.accept(this);
        }
        return game;
    }

    @Override
    //GAME_DEF ::= "make game" IDENTIFIER "{" "height = " NUMBER ", width = " NUMBER" "}" "{" GAME_STATEMENT* "}"
    public Game visit(GameDef gd) {
        game.setWindow(gd.name, gd.height, gd.width);
        for(GameStatement gs : gd.statements) {
            gs.accept(this);
        }

        return null;
    }

    @Override
    public Game visit(ObjectModifier gd) {
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
        return null;
    }

    @Override
    public Game visit(Statement s) {
        return null;
    }

    @Override
    //GAME_STATEMENT ::= MAKE_STATEMENT | FOR_LOOP
    public Game visit(GameStatement gs) {
        gs.accept(this);
        return null;
    }

    @Override
    //MAKE_STATEMENT ::=  "make" NUMBER? TYPE IDENTIFIER
    public Game visit(MakeStatement ms) {
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
        return null;
    }

    @Override
    public Game visit(PropertyStatement ps) {
        return null;
    }

    @Override
    public Game visit(FunctionDec fd) {
        return null;
    }

    @Override
    public Game visit(FunctionCall fc) {
        return null;
    }

    @Override
    public Game visit(Block b) {
        return null;
    }

    @Override
    public Game visit(BehaveStatement bs) {
        return null;
    }

    @Override
    public Game visit(MovementStatement ms) {
        return null;
    }

    @Override
    public Game visit(ShootStatement ss) {
        return null;
    }

    @Override
    public Game visit(Property p) {
        return null;
    }

    @Override
    public Game visit(Type t) {
        return null;
    }

    @Override
    public Game visit(Identifier id) {
        return null;
    }

    @Override
    public Game visit(Number n) {
        return null;
    }
}