package tinylanglexer;
public class Token {
	//attribute associated with token type
	private String lexeme;
	//tokenType
	private TokenType tokenType;
	//line number where lexeme resided
	private int lineNumber;
	public Token(TokenType tokenType,String lexeme) {
		this.tokenType = tokenType;
		this.lexeme = lexeme;
	}
	// setters and getters
	public String getLexeme() {
		return lexeme;
	}
	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}
	public TokenType getTokenType() {
		return this.tokenType;
	}
	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;	
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
}