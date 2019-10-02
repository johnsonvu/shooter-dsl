package tokenizer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Tokenizer {
    private static Tokenizer tokenizer;
    private String filePath;
    private String program;
    private String[] reservedWords;
    private String[] tokens;

    private Tokenizer(Path filePath, String[] reservedWords)
    {
        this.reservedWords = reservedWords;
        try{
            program = Files.readString(filePath, StandardCharsets.UTF_8);
        }
        catch(IOException ioEx)
        {
            System.out.printf("Error: could not open file.");
        }
    }

    public void tokenize()
    {
        program = program.replaceAll("\\s", " ");

//        for(String resWord: reservedWords){
//            program.replace(resWord, resWord.replace(" ", ""));
//        }
        tokens = program.split("\\s");

    }

    public static Tokenizer getInstance() {
        return tokenizer;
    }

    public static Tokenizer getInstance(String fileName, String[] reservedWords)
    {
        if(tokenizer == null) {
            Path path = Paths.get(fileName);
            return new Tokenizer(path, reservedWords);
        }
        else {
            return tokenizer;
        }
    }
}
