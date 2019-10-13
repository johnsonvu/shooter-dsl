package ui;

import ast.ASTNode;
import ast.Block;
import ast.FunctionBlock;
import ast.FunctionDec;
import evaluate.Evaluator;
import game.model.GameObject;
import game.model.GameObject;
import parser.Parser;
import tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static List<GameObject> gameObjects = new ArrayList<>(); //The actual instances of gameObjects

    public static void main(String[] args) {
        String[] reservedWords = {"\\(", "\\)", "\\{", "\\}", ",","\\+", "\\-", "\\*", "\\/"};
        Tokenizer tokenizer = Tokenizer.getInstance("game.txt", reservedWords);
        tokenizer.tokenize();
        Parser parser = new Parser();
        ASTNode ast = parser.parse();

        Evaluator evaluate = Evaluator.getInstance(ast);
        evaluate.evaluate();
        System.out.println("dones");
    }
}
