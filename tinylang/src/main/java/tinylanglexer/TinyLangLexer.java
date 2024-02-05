package tinylanglexer;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

/**
 * Class for lexer implementation of TinyLang
 * extends TransitionTable
 *
 * @author andre
 */
public class TinyLangLexer extends TransitionTable {
    // Obtain transition table from class TransitionTable
    private Map<TransitionInput, State> transitionTable = buildTransitionTable();
    // List of tokens
    private ArrayList<Token> tokens = new ArrayList<>();
    // Scanning -> traverse program char by char -> keep track of current char
    private int currentCharIndex = 0;
    // Keep track of line number
    private int lineNumber = 0;

    /**
     * Constructor for class TinyLangLexer
     *
     * @param TinyLangProgram
     * @throws Exception
     */
    public TinyLangLexer(String tinyLangProgram) {
        // build transition table
        this.buildTransitionTable();
        // program is empty -> only one EOF token
        if (tinyLangProgram.length() == 0)
            this.tokens.add(new Token(TokenType.TOK_EOF, ""));
        // if program is not empty -> loop until current char is not at the end of file
        while (currentCharIndex < tinyLangProgram.length()) {
            // obtain next token
            Token nextToken = getNextToken(tinyLangProgram);
            // set line number
            nextToken.setLineNumber(getLineNumber(tinyLangProgram));
            // if token is not of type TOK_SKIP add to list of tokens
            if (nextToken.getTokenType() != TokenType.TOK_SKIP) {
                this.tokens.add(nextToken);
            }
        }
    }

    /**
     * Table Driven Analysis Algorithm -> Cooper & Torczon Engineer a Compiler.
     *
     * @param TinyLangProgram
     */
    private Token getNextToken(String tinyLangProgram) {
        /* start initialisation stage */
        // Set current state to start state
        State state = State.STARTING_STATE;
        // Current lexeme
        String lexeme = "";
        // Create Stack Of States
        Stack<State> stack = new Stack<State>();
        // Push BAD state to the stack
        stack.add(State.STATE_BAD);
        /* end initialisation stage */
        while (tinyLangProgram.charAt(currentCharIndex) == 0x0a || tinyLangProgram.charAt(currentCharIndex) == 0x20 || tinyLangProgram.charAt(currentCharIndex) == 0x09) {
            if (tinyLangProgram.charAt(currentCharIndex) == 0x0a)
                lineNumber++;
            // increment char number
            this.currentCharIndex++;
            // detect EOF
            if (currentCharIndex == tinyLangProgram.length())
                return new Token(TokenType.TOK_EOF, "");
        }
        InputCategory inputCategory;
        char currentChar;
        while (state != State.STATE_ERROR && currentCharIndex < tinyLangProgram.length()) {
            // obtain current CHAR
            currentChar = tinyLangProgram.charAt(currentCharIndex);
            // char to lexeme
            lexeme += currentChar;
            // if state is accepting clear stack
            if (state.getStateType() == StateType.ACCEPTING) {
                stack.clear();
            }
            // push state to stack
            stack.add(state);
            if (isLetter(currentChar)) {
                inputCategory = InputCategory.LETTER;
            } else if (isDigit(currentChar)) {
                inputCategory = InputCategory.DIGIT;
            } else if (isUnderscore(currentChar)) {
                inputCategory = InputCategory.UNDERSCORE;
            } else if (isSlashDivide(currentChar)) {
                inputCategory = InputCategory.SLASH_DIVIDE;
            } else if (isAsterisk(currentChar)) {
                inputCategory = InputCategory.ASTERISK;
            } else if (isLessThan(currentChar)) {
                inputCategory = InputCategory.LESS_THAN;
            } else if (isGreaterThan(currentChar)) {
                inputCategory = InputCategory.GREATER_THAN;
            } else if (isPlus(currentChar)) {
                inputCategory = InputCategory.PLUS;
            } else if (isHyphenMinus(currentChar)) {
                inputCategory = InputCategory.HYPHEN_MINUS;
            } else if (isEqual(currentChar)) {
                inputCategory = InputCategory.EQUAL;
            } else if (isExclamationMark(currentChar)) {
                inputCategory = InputCategory.EXCLAMATION_MARK;
            } else if (isDot(currentChar)) {
                inputCategory = InputCategory.DOT;
            } else if (isSingleQuote(currentChar)) {
                inputCategory = InputCategory.SINGLE_QUOTE;
            } else if (isPunctuation(currentChar)) {
                inputCategory = InputCategory.PUNCT;
            } else if (isOtherPrintable(currentChar)) {
                inputCategory = InputCategory.OTHER_PRINTABLE;
            } else if (isLineFeed(currentChar)) {
                inputCategory = InputCategory.LINE_FEED;
            } else {
                throw new java.lang.RuntimeException("char " + currentChar + " in line " + lineNumber + " not recognised by TinyLang's grammar");
            }
            // get next transition as per transition table

            state = deltaFunction(state, inputCategory);
            // move to next char
            currentCharIndex++;


        }
        /*          begin rollback loop              */
        while (state != State.STATE_BAD && state.getStateType() != StateType.ACCEPTING) {
            // pop state
            state = stack.pop();
            //truncate string
            lexeme = (lexeme == null || lexeme.length() == 0) ? null : (lexeme.substring(0, lexeme.length() - 1));
            // move char index one step backwards
            currentCharIndex--;
        }
        if (state.getTokenType(lexeme) == TokenType.INVALID)
            throw new java.lang.RuntimeException(tokens.get(tokens.size() - 1).getLexeme() + tinyLangProgram.charAt(currentCharIndex + 1) + " in line " + lineNumber + " not recognised by TinyLang's grammar");
        else
            return new Token(state.getTokenType(lexeme), lexeme);
        // end lineNumber
    }

    // predicate functions to check input category
    private boolean isLetter(char input) {
        return ((0x41 <= input && input <= 0x5a) || (0x61 <= input && input <= 0x7a));
    }

    private boolean isDigit(char input) {
        return (0x30 <= input && input <= 0x39);
    }

    private boolean isUnderscore(char input) {
        return (input == 0x5f);
    }

    private boolean isSlashDivide(char input) {
        return (input == 0x2f);
    }

    private boolean isAsterisk(char input) {
        return (input == 0x2a);
    }

    private boolean isLessThan(char input) {
        return (input == 0x3c);
    }

    private boolean isGreaterThan(char input) {
        return (input == 0x3e);
    }

    private boolean isPlus(char input) {
        return (input == 0x2b);
    }

    private boolean isHyphenMinus(char input) {
        return (input == 0x2d);
    }

    private boolean isEqual(char input) {
        return (input == 0x3d);
    }

    private boolean isExclamationMark(char input) {
        return (input == 0x21);
    }

    private boolean isDot(char input) {
        return (input == 0x2e);
    }

    private boolean isSingleQuote(char input) {
        return (input == 0x27);
    }

    private boolean isPunctuation(char input) {
        return (input == 0x28 || input == 0x29 || input == 0x2c || input == 0x3a || input == 0x3b || input == 0x7b || input == 0x7d);
    }

    private boolean isOtherPrintable(char input) {
        return (0x20 <= input && input <= 0x7e && !isLetter(input) && !isDigit(input) && !isUnderscore(input) &&
                !isSlashDivide(input) && !isAsterisk(input) && !isLessThan(input) && !isGreaterThan(input)
                && !isPlus(input) && !isHyphenMinus(input) && !isEqual(input) && !isExclamationMark(input)
                && !isDot(input) && !isSingleQuote(input)) && !isPunctuation(input);
    }

    private boolean isLineFeed(char input) {
        lineNumber++;
        return (input == 0x0a);
    }

    private State deltaFunction(State state, InputCategory inputCategory) {
        return transitionTable.get(new TransitionInput(state, inputCategory));
    }

    // setter and getter methods
    public ArrayList<Token> getTokens() {
        return tokens;
    }


    private int getLineNumber(String tinyLangProgram) {
        lineNumber = 1;
        for (int i = 0; i < currentCharIndex; i++)
            if (tinyLangProgram.charAt(i) == 0x0a)
                lineNumber++;
        return lineNumber;


    }

}