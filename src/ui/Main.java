package ui;

import ast.ASTNode;
import ast.Block;
import ast.FunctionDec;
import evaluate.Evaluator;
import game.GameObject;
import parser.Parser;
import tokenizer.Tokenizer;
import java.util.HashMap;

public class Main {
    public static HashMap<String, GameObject> gameObjectTable = new HashMap<>();
    public static HashMap<String, Integer> varTable = new HashMap<>();
    public static HashMap<String, FunctionDec> blockTable = new HashMap<>();

    public static void main(String[] args) {
        String[] reservedWords = {"\\(", "\\)", "\\{", "\\}", ","};
        Tokenizer tokenizer = Tokenizer.getInstance("game.txt", reservedWords);
        tokenizer.tokenize();
        Parser parser = new Parser();
        ASTNode ast = parser.parse();

        Evaluator evaluate = new Evaluator(ast);
        evaluate.evaluate();
        System.out.println("dones");
    }
}
