package ui;

import ast.ASTNode;
import ast.Block;
import ast.FunctionBlock;
import ast.FunctionDec;
import evaluate.Evaluator;
import evaluate.protoypes.PlayerProto;
import game.model.GameObject;
import game.model.GameObject;
import game.model.Player;
import game.view.Game;
import game.view.Window;
import parser.Parser;
import tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static List<GameObject> gameObjects = new ArrayList<>(); //The actual instances of gameObjects
    public static Game game;

    public static void main(String[] args) {
        String[] reservedWords = {"\\(", "\\)", "\\{", "\\}", ",","\\+", "\\-", "\\*", "\\/"};
        Tokenizer tokenizer = Tokenizer.getInstance("game.txt", reservedWords);
        tokenizer.tokenize();
        Parser parser = new Parser();
        ASTNode ast = parser.parse();

        game = new Game();

        Evaluator evaluate = Evaluator.getInstance(ast);
        evaluate.evaluate();

        Player player = new Player(new PlayerProto("jkwansa", 100, 100), "jkwansa");
        game.getHandler().objects.add(player);

        new Window(1200, 800, "TEST GAME!", game);
        System.out.println("dones");
    }
}
