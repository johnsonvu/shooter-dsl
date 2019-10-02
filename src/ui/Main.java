package ui;

import parser.Parser;
import tokenizer.Tokenizer;

public class Main {
    public static void main(String[] args) {
        String[] reservedWords = {};
        Tokenizer tokenizer = Tokenizer.getInstance("game.txt", reservedWords);
        Parser parser = new Parser();
        parser.makeAST();

    }
}
