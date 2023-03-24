package tinylanglexer;
/**
 * Infinite amount of possible lexemes are 
 * categorised into a finite amount of groups.
 * Therefore a lexeme is a string with an 
 * identified meaning in the language.
 * @author andre
 */
public enum TokenType {
	/**
	 * Syntax Error Handler
	 * Identifies lexemes which are not accepted by TinyLang's grammar.
	 */
	INVALID,
	/**
	 *Control Flow Keyword
	 *Value(s) : if
	 */
	TOK_IF,
	/**
	 * Control Flow Keyword
	 * Value(s) : else
	 */
	TOK_ELSE,
	/**
	 * Iteration Keyword 
	 * Value(s) : for
	 */
	TOK_FOR,
	/**
	 * Iteration Keyword
	 * Value(s) : while
	 */
	TOK_WHILE,
	/**
	 * Structure Keyword
	 * Value(s) : fn
	 */
	TOK_FN,
	/**
	 * Returning Keyword 
	 * Value(s) : fn
	 */
	TOK_RETURN,
	/**
	 * Data Type Keyword 
	 * Value(s) : int
	 */
	TOK_INT_TYPE,
	/**
	 * Data Type Keyword 
	 * Value(s) : float
	 */
	TOK_FLOAT_TYPE,
	/**
	 * Data Type Keyword 
	 * Value(s) : not
	 */
	TOK_NOT,
	/**
	 * Data Type Keyword 
	 * Value(s) : bool
	 */
	TOK_BOOL_TYPE,
	/**
	 * Data Type Keyword 
	 * Value(s) : char
	 */
	TOK_CHAR_TYPE,
	/**
	 * Keyword Token 
	 * Value(s) : let
	 * identify variable declaration
	 */
	TOK_LET,
	/**
	 * Keyword Token
	 * Value(s) : ->
	 * specify return type of a function
	 */
	TOK_RIGHT_ARROW,
	
	/**
	 * Keyword Token
	 * Value(s) : print
	 * identify print statement
	 */
	TOK_PRINT,
	/**
	 * Punctuation 
	 * Value(s) : (
	 */
	TOK_LEFT_ROUND_BRACKET,
	/**
	 * Punctuation 
	 * Value(s) : )
	 */
	TOK_RIGHT_ROUND_BRACKET,
	/**
	 * Punctuation 
	 * Value(s) : {
	 */
	TOK_LEFT_CURLY_BRACKET,
	/**
	 * Punctuation
	 * Value(s) : }
	 */
	TOK_RIGHT_CURLY_BRACKET,
	/**
	 * Punctuation 
	 * Value(s) : ,
	 */
	TOK_COMMA,
	/**
	 * Punctuation 
	 * Value(s) : 
	 */
	TOK_DOT,
	/**
	 * Punctuation 
	 * Value(s) : :
	 */
	TOK_COLON,
	/**
	 * Punctuation 
	 * Value(s) : ;
	 */
	TOK_SEMICOLON,
	
	/**
	 * Punctuation 
	 * Value(s) : ;
	 */
	TOK_MULTIPLICATIVE_OP,
	/**
	 * 
	 * Value(s) : (
	 */
	TOK_ADDITIVE_OP,
	/**
	 * Operation Token Name
	 * Value(s) : =
	 */
	TOK_EQUAL,
	/**
	 * Operation Token Name
	 * Value(s) : '<' '>' '==' '!=' '<=' '>='
	 */
	TOK_RELATIONAL_OP,
	/**
	 * Token Name
	 */
	TOK_IDENTIFIER,
	/**
	 * Token Name
	 * Value(s) : true , false
	 */
	TOK_BOOL_LITERAL,
	/**
	 * Token Name
	 */
	TOK_INT_LITERAL,
	/**
	 * Token Name
	 */
	TOK_FLOAT_LITERAL,
	/**
	 * Token Name
	 */
	TOK_CHAR_LITERAL,

	/**
	 * Special Token 
	 */
	TOK_SKIP,
	/**
	 * Special Token 
	 * Used to identify end of program
	 */
	TOK_EOF
}