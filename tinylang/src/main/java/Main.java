
import tinylanglexer.TinyLangProgramToString;
import tinylanglexer.Token;

import java.io.FileNotFoundException;
import java.util.Scanner;

import tinylanglexer.TinyLangLexer;
import tinylangparser.TinyLangParser;
import tinylangvisitor.Interpreter;
import tinylangvisitor.XmlGeneration;

public class Main {
    public static void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new FileNotFoundException("No program found");
        }
        System.out.println("Program in consideration: " + args[0] + ".tl");
        String program = new TinyLangProgramToString(args[0] + ".tl").getTinyLangProgramString();
        TinyLangLexer lexer = new TinyLangLexer(program);
        TinyLangParser parser = new TinyLangParser(lexer);
        XmlGeneration xml = new XmlGeneration(parser.getTinyLangAbstraxSyntaxTree());
        String[] options = {"1- Produce tokens of program (lexer)",
                "2- Produce an XML representation of program (parser+xml generation pass)",
                "3- Interpret program",
                "q- Exit",
        };
        Scanner scanner = new Scanner(System.in);
        printMenu(options);
        char c = '1';
        c = scanner.next().charAt(0);
        ;
        while (c != 'q') {
            if (c != '1' && c != '2' && c != '3') {
                System.out.println("Error: Input " + c + " unrecognised");
            } else if (c == '1') {
                for (Token token : lexer.getTokens()) {
                    System.out.println("<" + token.getTokenType() + ", (lexeme:\"" + token.getLexeme() + "\", line number:" + token.getLineNumber() + ")>");
                }

            } else if (c == '2') {
                xml.printXmlTree();
            } else {
                new Interpreter(parser.getTinyLangAbstraxSyntaxTree());
            }
            printMenu(options);
            c = scanner.next().charAt(0);
        }
    }
}
