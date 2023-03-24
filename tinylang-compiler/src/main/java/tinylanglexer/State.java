package tinylanglexer;
public enum State {
	/**
	 * The starting state of representing TinyLang's grammar.
	 */
	STARTING_STATE (StateType.REJECTING),
	STATE_1 (StateType.REJECTING),
	STATE_2 (StateType.REJECTING),
	/* Lexemes leading to STATE_3 -> Lexeme of type TOK_CHAR_LITERAL */
	STATE_3 (StateType.ACCEPTING),
	/* Lexemes leading to STATE_4 -> Lexeme of type TOK_IDENTIFIER_LITERAL or other KEYWORD type */
	STATE_4 (StateType.ACCEPTING),
	/* Lexemes leading to STATE_5 -> Lexeme of type TOK_ MULTIPLICATIVE_OP[*/	
	STATE_5 (StateType.ACCEPTING), 
	/* Lexemes leading to STATE_6 -> Lexeme of type TOK_SKIP */	
	STATE_6 (StateType.ACCEPTING),
	/* Lexemes leading to STATE_7 -> Lexeme of type TOK_SKIP */	
	STATE_7 (StateType.REJECTING),
	STATE_8 (StateType.REJECTING),
	/* Lexemes leading to STATE_9 -> Lexeme of type TOK_SKIP */	
	STATE_9 (StateType.ACCEPTING),
	/* Lexemes leading to STATE_10 -> Lexeme of some PUNCTUATION type*/	
	STATE_10 (StateType.ACCEPTING),
	/* Lexemes leading to STATE_11 -> Lexeme of type TOK_INTEGER_LITERAL*/	
	STATE_11 (StateType.ACCEPTING),
	STATE_12 (StateType.REJECTING),
	/* Lexemes leading to STATE_13 -> Lexeme of type TOK_FLOAT_LITERAL*/	
	STATE_13 (StateType.ACCEPTING),
	/* Lexemes leading to STATE_14 -> Lexeme of type TOK_MUTIPLICATIVE_OP or TOK_ADDITIVE_OP*/	
	STATE_14 (StateType.ACCEPTING),
	/* Lexemes leading to STATE_15 -> Lexeme of type TOK_ADDITIVE_OP*/	
	STATE_15 (StateType.ACCEPTING),
	/* Lexemes leading to STATE_16 -> Lexeme of type TOK_RELATIONAL_OP*/	
	STATE_16 (StateType.ACCEPTING),
	STATE_17 (StateType.REJECTING),
	/* Lexemes leading to STATE_18 -> Lexeme of type TOK_RELATIONAL_OP*/	
	STATE_18 (StateType.ACCEPTING),
	
	/* Lexemes leading to STATE_18 -> Lexeme of type TOK_RELATIONAL_OP*/	
	STATE_19 (StateType.ACCEPTING),
	STATE_ERROR(StateType.REJECTING),
	/* STATE_BAD USED IN ALGORITHM OF GENERATING TOKENS FROM TRANSITION TABLE */
	STATE_BAD(StateType.REJECTING);
	private final StateType stateType;
	/**
	 * 
	 * @param stateId
	 */
	State(StateType stateType) {
		this.stateType = stateType;
	}
	
	/**
	 * Getter method for getting a state's id
	 * @return
	 */
	public StateType getStateType() {
		return this.stateType;
	}
	public TokenType getTokenType(String lexeme){
		
		switch(this) {
		case STATE_3:
			return TokenType.TOK_CHAR_LITERAL;
		
		case STATE_4:
			switch(lexeme) {
				case "fn":
					return TokenType.TOK_FN;
				case "bool":
					return TokenType.TOK_BOOL_TYPE;
				case "int":
					return TokenType.TOK_INT_TYPE;
				case "float":
					return TokenType.TOK_FLOAT_TYPE;
				case "false":
				case "true":
					return TokenType.TOK_BOOL_LITERAL;
				case "not":
					return TokenType.TOK_NOT;
				case "let":
					return TokenType.TOK_LET;
				case "char":
					return TokenType.TOK_CHAR_TYPE;
				case "if":
					return TokenType.TOK_IF;		
				case "else":
					return TokenType.TOK_ELSE;
				case "while":
					return TokenType.TOK_WHILE;	
				case "for":
					return TokenType.TOK_FOR;	
				case "print":
					return TokenType.TOK_PRINT;
				case "return":
					return TokenType.TOK_RETURN;
				case "and":
					return TokenType.TOK_MULTIPLICATIVE_OP;
				case "or":
					return TokenType.TOK_ADDITIVE_OP;
					
				default:
					return TokenType.TOK_IDENTIFIER;
			}
		
		case STATE_5:
			return TokenType.TOK_MULTIPLICATIVE_OP;
			
		case STATE_6:
			return TokenType.TOK_SKIP;
		
		case STATE_9:
			return TokenType.TOK_SKIP;
		
		case STATE_10:
			switch(lexeme) {
			case ":":
				return TokenType.TOK_COLON;
			case ";":
				return TokenType.TOK_SEMICOLON;
			case "(":
				return TokenType.TOK_LEFT_ROUND_BRACKET;
			case ")":
				return TokenType.TOK_RIGHT_ROUND_BRACKET;
			case "{":
				return TokenType.TOK_LEFT_CURLY_BRACKET;
			case "}":
				return TokenType.TOK_RIGHT_CURLY_BRACKET;
			case ",":
				return TokenType.TOK_COMMA;
			case ".":
				return TokenType.TOK_DOT;
			default:
				return TokenType.INVALID;
			
			}
		case STATE_11:
			return TokenType.TOK_INT_LITERAL;
		
		case STATE_13:
			return TokenType.TOK_FLOAT_LITERAL;
		case STATE_14:
			switch(lexeme) {
				case "*":
					return TokenType.TOK_MULTIPLICATIVE_OP;
				case "+":
					return TokenType.TOK_ADDITIVE_OP;
				default:
					return TokenType.INVALID;
			}
		case STATE_15:
			
			return TokenType.TOK_ADDITIVE_OP;
		
		case STATE_16:
			switch(lexeme) {
			case "=":
				return TokenType.TOK_EQUAL;
			default:
				return TokenType.TOK_RELATIONAL_OP;
			}
		case STATE_18:
			return TokenType.TOK_RIGHT_ARROW;
		
		case STATE_19:
			return TokenType.TOK_RELATIONAL_OP;	
		default:
			return TokenType.INVALID;
		}
	}
}