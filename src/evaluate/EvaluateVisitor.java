package evaluate;

import ast.*;
import ast.Number;
import evaluate.protoypes.*;
import game.model.*;
import game.view.Game;

import visitor.Visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EvaluateVisitor implements Visitor<Integer> {
    private Game game;

    private static HashMap<String, Integer> varTable = null;
    private static HashMap<String, Integer> globalVarTable = new HashMap<>();

    private static GameObjectProto objectPrototype = null; //Blueprints for an instance's properties (kind of like js prototypes?)
    private static HashMap<String, GameObjectProto> objectProtoTable = new HashMap<>();

    private static HashMap<String, FunctionBlock> blockTable = new HashMap<>();

    private static List<GameObject> gameObjects = new ArrayList<>(); //The actual instances of gameObjects

    private EvaluateVisitor(){
    }

    @Override
    public Integer visit(ASTNode v) {
        return 0;
    }

    @Override
    //PROGRAM ::= GAME_DEF OBJECT_MODIFIER* FUNCTION_DEC*
    public Integer visit(Program p) {
        game = new Game();
        for(FunctionDec fn : p.functions){
            fn.accept(this);
        }
        p.game.accept(this);
        for(ObjectModifier om : p.objects){
            om.accept(this);
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
        objectPrototype = objectProtoTable.get(om.identifier.name);

        for(PropertyStatement ps : om.propertyStatements){
            ps.accept(this);
        }

        objectPrototype.behaviour = om.behave;

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
    //MAKE_STATEMENT ::=  "make" EXPR? TYPE IDENTIFIER
    public Integer visit(MakeStatement ms) {

        int number = ms.number == null? 1 : ms.number.accept(this);

        switch(ms.type.type){
            case PLAYER:
                PlayerProto playerProto = new PlayerProto(ms.identifier.name,1, 1);
                objectProtoTable.put(ms.identifier.name, playerProto);

                for(int i =0; i< number; i++){
                    Player player = new Player(playerProto, ms.identifier.name);
                    gameObjects.add(player);
                }
                break;
            case ENEMY:
                EnemyProto enemyProto = new EnemyProto(ms.identifier.name, 1, 1);
                objectProtoTable.put(ms.identifier.name, enemyProto);  //enemy should implement GameObject

                for(int i =0; i< number; i++){
                    Enemy enemy = new Enemy(enemyProto, ms.identifier.name);
                    gameObjects.add(enemy);
                }
                break;
            case PROJECTILE:
                ProjectileProto projectileProto = new ProjectileProto(ms.identifier.name,1, 1);
                objectProtoTable.put(ms.identifier.name, projectileProto);

                for(int i =0; i< number; i++){
                    Projectile projectile = new Projectile(projectileProto, ms.identifier.name);
                    gameObjects.add(projectile);
                }
                break;

            case ITEM:
                ItemProto itemProto = new ItemProto(ms.identifier.name,1, 1);
                objectProtoTable.put(ms.identifier.name, itemProto);

                for(int i =0; i< number; i++){
                    Item item = new Item(itemProto, ms.identifier.name);
                    gameObjects.add(item);
                }
                break;
        }

        return 0;
    }

    @Override
    public Integer visit(PropertyStatement ps) {
        GameObjectProto go = objectProtoTable.get(ps.property.toString());

        switch (ps.property.property){
            case DAMAGE:
                go.damage = ps.expr.accept(this);
                break;
            case HEALTH:
                go.damage = ps.expr.accept(this);
                break;
        }

        return null;
    }

    @Override
    public Integer visit(FunctionDec fd) {
        blockTable.put(fd.name.name, fd.functionBlock);
        return null;
    }

    @Override
    public Integer visit(FunctionBlock fb) {
        fb.block.accept(this);
        return fb.retExpr.accept(this);
    }

    @Override
    public Integer visit(FunctionCall fc) {
        HashMap<String, Integer> currentScope = varTable;
        varTable = new HashMap<>();
        FunctionBlock fb =  blockTable.get(fc.name.name);
        String key;
        Integer value;
        for (int i=0; i<fc.arguments.size(); i++) {
            key = fb.params.get(i).name;
            value = fc.arguments.get(i).accept(this);
            store(key, value);
        }

        Integer retVal = fb.accept(this);

        varTable = currentScope;
        return retVal;
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
        gameObjects.stream()
                .filter(go -> go.proto.equals(objectPrototype))
                .forEach(go -> go.move(ms.direction.direction, ms.number.number));
        return null;
    }

    @Override
    public Integer visit(ShootStatement ss) {
        gameObjects.stream()
                .filter(go -> go.proto.equals(objectPrototype))
                .forEach(go -> go.shoot(ss.direction.direction));
        return null;
    }

    @Override
    public Integer visit(Property p) {
        return null;
    }

    @Override
    public Integer visit(VarDec vd) {
        store(vd.name, null);
        return 0;
    }

    @Override
    public Integer visit(VarSet vs) {
        store(vs.id, vs.value.accept(this));
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
        return lookup(id);
    }

    @Override
    public Integer visit(Number n) {
        return n.number;
    }

    private Integer lookup(String key){
        Integer value = varTable.get(key);
        if(value == null) value = globalVarTable.get(key);

        return value;
    }

    private Integer lookup(Identifier id){
        Integer value = varTable.get(id.name);
        if(value == null) value = globalVarTable.get(id.name);

        return value;
    }

    private void store(String key, Integer value){
        if(varTable == null) globalVarTable.put(key, value);
        else varTable.put(key, value);
    }

    private void store(Identifier key, Integer value){
        if(varTable == null) globalVarTable.put(key.name, value);
        else varTable.put(key.name, value);
    }

}