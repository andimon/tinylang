package tinylangparser;

import java.util.ArrayList;

import tinylanglexer.TinyLangLexer;
import tinylanglexer.Token;
import tinylanglexer.TokenType;

public class TinyLangParser {
	// root of ast -> describes ast capturing all the program
	private TinyLangAst tinyLangProgramAbstractSyntaxTree;
	// list of tokens
	private ArrayList<Token> tokens;
	// current token index
	private int currentTokenIndex = 0;
	// method for obtaining current token 
	private Token getCurrentToken(){
		return tokens.get(currentTokenIndex);
	}
	// method for obtaining next token 
	private Token getNextToken(){
		currentTokenIndex++;
		return getCurrentToken();	
	}
	// method for obtaining previous token 
	private Token getPrevToken(){
		currentTokenIndex--;
		return getCurrentToken();	
	}
	/**
	 * Constructor for TinyLangParserClass
	 * @param tinyLangLexer
	 */
	public TinyLangParser(TinyLangLexer tinyLangLexer) {
		tokens = tinyLangLexer.getTokens();
		tinyLangProgramAbstractSyntaxTree = parseTinyLangProgram();
	}
	/**
	 * Parse whole TinyLangProgram using recursive descent
	 * to call other sub parsers until TOK_EOF is reached.   
	 */
	private TinyLangAst parseTinyLangProgram() {
		//program tree capturing whole syntax of tiny lang program
		TinyLangAst programTree = new TinyLangAst(TinyLangAstNodes.TINY_LANG_PROGRAM_NODE,getCurrentToken().getLineNumber());
		// traverse until current token reach EOF i.e. no more tokens to process 
		while(getCurrentToken().getTokenType()!=TokenType.TOK_EOF) {
			// parse statement one by one
			programTree.addSubtree(parseStatement());
			// get next token
			getNextToken();
		}
		return programTree;
	}
	

	/**
  	 * Parse a statement
 	 * <Statement> -> <VariableDecl> ';'
     *<Statement> -> <Assignment> ';'
     *<Statement> -> <PrintStatement> ';'
     *<Statement> -> <IfStatement>  ';'
     *<Statement> -> <ForStatement> ';'
     *<Statement> -> <WhileStatement> ';'
     *<Statement> -> <RtrnStatement> ';'
     *<Statement> -> <FunctionDecl>
     *<Statement> -> <Block>
     *described by an  LL(1) grammar i.e. decide immediately which grammar rule to use with TokenTypes 
     *	TOK_LET,TOK_IDENTIFIER,TOK_PRINT,TOK_WHILE,TOK_RETURN,TOK_FN,TOK_LEFT_CURLY otherwise undefined.
	 * @param lookAhead
	 * @param parent
	 */
	public TinyLangAst parseStatement() {
		TinyLangAst statementTree;
		switch(getCurrentToken().getTokenType()){
		// if lookAhead = TOK_LET Statement leads to variable declaration
		case TOK_LET:
			//parse variable declaration
			statementTree =parseVariableDeclaration();
			//get next token
			getNextToken();
			//expecting ;
			if(getCurrentToken().getTokenType()!=TokenType.TOK_SEMICOLON)
				//not as expected
				throw new java.lang.RuntimeException("expected semicolon,; , in line " + getCurrentToken().getLineNumber());			
			return statementTree;
		case TOK_IDENTIFIER:
			//parse assignment
			statementTree =  parseAssignment();
			//get next token 
			getNextToken();
			//expecting ;
			if(getCurrentToken().getTokenType()!=TokenType.TOK_SEMICOLON)
				//not as expected
				throw new java.lang.RuntimeException("expected semicolon,; , in line " + getCurrentToken().getLineNumber());			
			return statementTree;
		case TOK_PRINT:
			statementTree = parsePrintStatement();
			//expecting ;
			if(getNextToken().getTokenType()!=TokenType.TOK_SEMICOLON)
				//not as expected
				throw new java.lang.RuntimeException("expected semicolon,; , in line " + getCurrentToken().getLineNumber());			
			return statementTree;
		case TOK_IF:
			return parseIfStatement();
		case TOK_FOR:
			return parseForStatement();
		case TOK_WHILE:
			return parseWhileStatement();
		case TOK_RETURN:
			statementTree = parseReturnStatement();
			//get next token
			getNextToken();
			//expecting ;
			if(getCurrentToken().getTokenType()!=TokenType.TOK_SEMICOLON)
				//not as expected
				throw new java.lang.RuntimeException("expected semicolon,; , in line " + getCurrentToken().getLineNumber());			
			return statementTree;
		case TOK_FN:
			return parseFunctionDeclaration();
		case TOK_LEFT_CURLY_BRACKET:
			return parseBlock();
		default:
			throw new java.lang.RuntimeException("  in line "+getCurrentToken().getLineNumber()
			+". No statement begins with "+getCurrentToken().getLexeme());
		}
	}
	
	//parse variable declaration
	private TinyLangAst parseVariableDeclaration() {
		//create variable declaration syntax tree
		TinyLangAst variableDeclarationTree = new TinyLangAst(TinyLangAstNodes.AST_VARIABLE_DECLARATION_NODE,getCurrentToken().getLineNumber());
		//expect token let
		if(getCurrentToken().getTokenType()!=TokenType.TOK_LET)
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+ " in line " + getCurrentToken().getLineNumber());
		
		//expect that next token is identifier
		Token identifier = getNextToken();
		//check if identifier
		if(getCurrentToken().getTokenType()!=TokenType.TOK_IDENTIFIER)
			throw new java.lang.RuntimeException(getCurrentToken().getLexeme()+ " in line " + getCurrentToken().getLineNumber()+" is not a valid variable name");
		//get next token
		getNextToken();
		//expect :
		if(getCurrentToken().getTokenType()!=TokenType.TOK_COLON)
			throw new java.lang.RuntimeException("expect colon in line " + getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//expect type tree
		variableDeclarationTree.addSubtree(parseType());
		
		//add identifier
		variableDeclarationTree.addChild(TinyLangAstNodes.AST_IDENTIFIER_NODE,identifier.getLexeme(),identifier.getLineNumber());
		//get next token
		getNextToken();
		//expect =
		if(getCurrentToken().getTokenType()!=TokenType.TOK_EQUAL)
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+ " in line " + getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		variableDeclarationTree.addSubtree(parseExpression());
		return variableDeclarationTree;
	}
	//parse assignment 
	private TinyLangAst parseAssignment() {
		//create assignment syntax tree
		TinyLangAst assignmentTree = new TinyLangAst(TinyLangAstNodes.AST_ASSIGNMENT_NODE,getCurrentToken().getLineNumber());
		//expect identifier 
		if(getCurrentToken().getTokenType()!=TokenType.TOK_IDENTIFIER) {
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+ " in line " + getCurrentToken().getLineNumber());
		}
			//add identifier node
		assignmentTree.addChild(TinyLangAstNodes.AST_IDENTIFIER_NODE,getCurrentToken().getLexeme(),getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//expect equal
		if(getCurrentToken().getTokenType()!=TokenType.TOK_EQUAL)
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+ " in line " + getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//expect expression
		assignmentTree.addSubtree(parseExpression());
		return assignmentTree;
	}
	
	//parse print statement
	private TinyLangAst parsePrintStatement() {
		//create assignment syntax tree
		TinyLangAst printStatementTree = new TinyLangAst(TinyLangAstNodes.AST_PRINT_STATEMENT_NODE,getCurrentToken().getLineNumber());
		//expect print keyword
		if(getCurrentToken().getTokenType()!=TokenType.TOK_PRINT)
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+ " in line " + getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//expect expression
		printStatementTree.addSubtree(parseExpression());
		return printStatementTree;
	}
	//parse if statement
	private TinyLangAst parseIfStatement() {
		//create if statement syntax tree
		TinyLangAst ifStatementTree = new TinyLangAst(TinyLangAstNodes.AST_IF_STATEMENT_NODE,getCurrentToken().getLineNumber());
		//expect if keyword
		if(getCurrentToken().getTokenType()!=TokenType.TOK_IF)
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+ " in line " + getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//expect (
		if(getCurrentToken().getTokenType()!=TokenType.TOK_LEFT_ROUND_BRACKET)
			throw new java.lang.RuntimeException("expected left round bracket,( , in line "+getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//add expression subtree to if statement tree
		ifStatementTree.addSubtree(parseExpression());
		//get next token
		getNextToken();
		//expected )
		if(getCurrentToken().getTokenType()!=TokenType.TOK_RIGHT_ROUND_BRACKET)
			throw new java.lang.RuntimeException("expected right round bracket,) , in line "+getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//parse block
		ifStatementTree.addSubtree(parseBlock());
		//getNextToken()
		getNextToken();
		//if we have else condition
		if(getCurrentToken().getTokenType()==TokenType.TOK_ELSE) {
			//get next token
			getNextToken();
			//get else block
			ifStatementTree.addSubtree(parseElseBlock());
		}
		else 
			//get previous token
			getPrevToken();
		//return if statement tree
		return ifStatementTree;
	}
	//parse for statement
	private TinyLangAst parseForStatement() {
		//create block syntax tree
		TinyLangAst forStatementTree = new TinyLangAst(TinyLangAstNodes.AST_FOR_STATEMENT_NODE,getCurrentToken().getLineNumber());
		//expect for keywordF
		if(getCurrentToken().getTokenType()!=TokenType.TOK_FOR)
			//not as expected
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+ " in line " + getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//expect (
		if(getCurrentToken().getTokenType()!=TokenType.TOK_LEFT_ROUND_BRACKET)
			//not as expected
			throw new java.lang.RuntimeException("expected left round bracket,( , in line "+ getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//expect semicolon or variable declaration
		if(getCurrentToken().getTokenType()!=TokenType.TOK_SEMICOLON)
		{
			//expect variable declaration
			forStatementTree.addSubtree(parseVariableDeclaration());
			//consume variable declaration 
			getNextToken();
		}
		//expect ;
		if(getCurrentToken().getTokenType()!=TokenType.TOK_SEMICOLON)		
			throw new java.lang.RuntimeException("expected semicolon,; , in line "+ getCurrentToken().getLineNumber());
		//get next token 
		getNextToken();
		//expect expression
		forStatementTree.addSubtree(parseExpression());
		//expect ;
		if(getNextToken().getTokenType()!=TokenType.TOK_SEMICOLON)		
			throw new java.lang.RuntimeException("expected semicolon,; , in line "+ getCurrentToken().getLineNumber());
		//expect right round bracket or assignment
		if(getNextToken().getTokenType()!=TokenType.TOK_RIGHT_ROUND_BRACKET)
		{
			//expect variable declaration
			forStatementTree.addSubtree(parseAssignment());
			//consume variable declaration 
			getNextToken();
		}
		
		if(getCurrentToken().getTokenType()!=TokenType.TOK_RIGHT_ROUND_BRACKET)		
			throw new java.lang.RuntimeException("expected right round bracket,) , in line "+ getCurrentToken().getLineNumber());
		
		
		//get next token
		getNextToken();
		//expect block
		forStatementTree.addSubtree(parseBlock());
		//return for statement tree
		return forStatementTree;
	}
	//parse while statement
	private TinyLangAst parseWhileStatement() {
		//create while statement syntax tree syntax tree
		TinyLangAst whileStatementTree = new TinyLangAst(TinyLangAstNodes.AST_WHILE_STATEMENT_NODE,getCurrentToken().getLineNumber());
		//expect while keyword
		if(getCurrentToken().getTokenType()!=TokenType.TOK_WHILE)
			//not as expected
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+ " in line " + getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//expect (
		if(getCurrentToken().getTokenType()!=TokenType.TOK_LEFT_ROUND_BRACKET)
			//not as expected
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+ " in line " + getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//expect expression
		whileStatementTree.addSubtree(parseExpression());
		//get next token
		getNextToken();
		//expect )
		if(getCurrentToken().getTokenType()!=TokenType.TOK_RIGHT_ROUND_BRACKET)
			//not as expected
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+ " in line " + getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//expect block
		whileStatementTree.addSubtree(parseBlock());
		//return syntax tree
		return whileStatementTree;
	}
	//parse return statement
	private TinyLangAst parseReturnStatement() {
		//create while statement syntax tree syntax tree
		TinyLangAst returnStatementTree = new TinyLangAst(TinyLangAstNodes.AST_RETURN_STATEMENT_NODE,getCurrentToken().getLineNumber());

		//expect return keyword
		if(getCurrentToken().getTokenType()!=TokenType.TOK_RETURN)
			//not as expected
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+ " in line " + getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//expect expression
		returnStatementTree.addSubtree(parseExpression());
		//return syntax tree
		return returnStatementTree;
	}
	//parse function declaration
	private TinyLangAst parseFunctionDeclaration() {
		//create function declaration syntax tree syntax tree
		TinyLangAst functionDeclarationTree = new TinyLangAst(TinyLangAstNodes.AST_FUNCTION_DECLARATION_NODE,getCurrentToken().getLineNumber());
		//expect return keyword
		if(getCurrentToken().getTokenType()!=TokenType.TOK_FN)
			//not as expected
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+ " in line " + getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//expect expression
		Token identifier;
		if(getCurrentToken().getTokenType()==TokenType.TOK_IDENTIFIER)
			identifier = getCurrentToken();
		else 
			//not valid function name
			throw new java.lang.RuntimeException(getCurrentToken().getLexeme()+ " in line " + getCurrentToken().getLineNumber()+" not a valid funciton name");
		//get next token 
		getNextToken();
		//expect (
		if(getCurrentToken().getTokenType()!=TokenType.TOK_LEFT_ROUND_BRACKET)
			//not as expected
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+ " in line " + getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//expect 0 or more formal parameters
		TinyLangAst formalParamsSubtree;
		//if not right round bracket -> we have parameters
		if(getCurrentToken().getTokenType()!=TokenType.TOK_RIGHT_ROUND_BRACKET){
			formalParamsSubtree = parseFormalParams();
			//get next token (expected round bracket in next token)
			getNextToken();
		}
		else
			//add parameter node
			formalParamsSubtree = new TinyLangAst(TinyLangAstNodes.AST_FORMAL_PARAMETERS_NODE,getCurrentToken().getLineNumber());
		//expect )

		if(getCurrentToken().getTokenType()!=TokenType.TOK_RIGHT_ROUND_BRACKET)
			//not as expected
			throw new java.lang.RuntimeException("expected right round bracket,) ,"+" in line "+getCurrentToken().getLineNumber());	
		//get next token
		getNextToken();

		//expect ->
		if(getCurrentToken().getTokenType()!=TokenType.TOK_RIGHT_ARROW)
			//not as expected
			throw new java.lang.RuntimeException("expected right arrow,-> ,in line "+getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//parse type

		TinyLangAst typeSubtree = parseType();
		//get next token
		getNextToken();

		//parse block
		TinyLangAst blockSubtree = parseBlock();
		//add type subtree to function declaration tree
		functionDeclarationTree.addSubtree(typeSubtree);
		//add identifier node to function declaration tree
		functionDeclarationTree.addChild(TinyLangAstNodes.AST_IDENTIFIER_NODE,identifier.getLexeme(),identifier.getLineNumber());
		//add formal parameters subtree to function declaration tree
		functionDeclarationTree.addSubtree(formalParamsSubtree);
		//add block subtree to function declaration tree
		functionDeclarationTree.addSubtree(blockSubtree);
		//return function declaration tree
		return functionDeclarationTree;
	}
	
	//parse block
	private TinyLangAst parseBlock() {
		//create block syntax tree
		TinyLangAst blockTree = new TinyLangAst(TinyLangAstNodes.AST_BLOCK_NODE,getCurrentToken().getLineNumber());
		//expected {
		//set line number 
		blockTree.setLineNumber(getCurrentToken().getLineNumber());
		if(getCurrentToken().getTokenType() != TokenType.TOK_LEFT_CURLY_BRACKET) 
			//not as expected
			throw new java.lang.RuntimeException("expected { in line "+getCurrentToken().getLineNumber() );
		// get next token
		getNextToken();
		// we may have one or more statements
		// block ends using }
		while(getCurrentToken().getTokenType()!=TokenType.TOK_RIGHT_CURLY_BRACKET &&
				getCurrentToken().getTokenType()!=TokenType.TOK_EOF) {
			// parse statement one by one

			blockTree.addSubtree(parseStatement());
			// get next token
			getNextToken();
		}
		//expected }
		if(getCurrentToken().getTokenType()!=TokenType.TOK_RIGHT_CURLY_BRACKET) 
			//not as expected
			throw new java.lang.RuntimeException("expected } in line "+getCurrentToken().getLineNumber());
		//return block tree
		return blockTree;
	}
	//parse else block
	private TinyLangAst parseElseBlock() {
		//create block syntax tree
		TinyLangAst elseBlockTree = new TinyLangAst(TinyLangAstNodes.AST_ELSE_BLOCK_NODE,getCurrentToken().getLineNumber());
		//expected {
		if(getCurrentToken().getTokenType() != TokenType.TOK_LEFT_CURLY_BRACKET) 
			//not as expected
			throw new java.lang.RuntimeException("expected { in line "+getCurrentToken().getLineNumber() );
		// get next token
		getNextToken();
		// we may have one or more statements
		// block ends using }
		while(getCurrentToken().getTokenType()!=TokenType.TOK_RIGHT_CURLY_BRACKET &&
				getCurrentToken().getTokenType()!=TokenType.TOK_EOF) {
			// parse statement one by one
			elseBlockTree.addSubtree(parseStatement());
			// get next token
			getNextToken();

		}
		//expected }
		if(getCurrentToken().getTokenType()!=TokenType.TOK_RIGHT_CURLY_BRACKET) 
			//not as expected
			throw new java.lang.RuntimeException("expected } in line "+getCurrentToken().getLineNumber());	
		//return else block syntax tree
		return elseBlockTree;
	}

	//parse type
	private TinyLangAst parseType() {
		// add node
		switch(getCurrentToken().getTokenType()) {
			case TOK_BOOL_TYPE:
				return new TinyLangAst(TinyLangAstNodes.AST_TYPE_NODE,Type.BOOL.toString(),getCurrentToken().getLineNumber());
			case TOK_INT_TYPE:
				return new TinyLangAst(TinyLangAstNodes.AST_TYPE_NODE,Type.INTEGER.toString(),getCurrentToken().getLineNumber());
			case TOK_FLOAT_TYPE:
				return new TinyLangAst(TinyLangAstNodes.AST_TYPE_NODE,Type.FLOAT.toString(),getCurrentToken().getLineNumber());
			case TOK_CHAR_TYPE:
				return new TinyLangAst(TinyLangAstNodes.AST_TYPE_NODE,Type.CHAR.toString(),getCurrentToken().getLineNumber());
			default:
				throw new java.lang.RuntimeException(getCurrentToken().getLexeme()+ " in line " + getCurrentToken().getLineNumber() +" is not a valid type" );
		}
	}	
	//parse expression
	private TinyLangAst parseExpression() { 
		//parse simple expression
		TinyLangAst left = parseSimpleExpression();
		//get next token
		getNextToken();
		//expecting 0 or more expressions separated by a relational operator
		if(getCurrentToken().getTokenType()==TokenType.TOK_RELATIONAL_OP) {
			//create a binary expression tree with root node containing current binary operator
			TinyLangAst binaryExpressionTree = new TinyLangAst(TinyLangAstNodes.AST_BINARY_OPERATOR_NODE,getCurrentToken().getLexeme(),getCurrentToken().getLineNumber()); 
			//add left operand of the binary operator
			binaryExpressionTree.addSubtree(left);
			//move to next token
			getNextToken();
			//add right operand 
			binaryExpressionTree.addSubtree(parseExpression());	
			return binaryExpressionTree;
		}
		getPrevToken();
		//case of no relational operator
		return left;
	}

	//parse simple expression
	private TinyLangAst parseSimpleExpression() { 
		//parse simple expression
		TinyLangAst left = parseTerm();
		//get next token
		getNextToken();
		//expecting 0 or more expressions separated by a relational operator
		if(getCurrentToken().getTokenType()==TokenType.TOK_ADDITIVE_OP) {
			//create a binary expression tree with root node containing current binary operator
			TinyLangAst binaryExpressionTree = new TinyLangAst(TinyLangAstNodes.AST_BINARY_OPERATOR_NODE,getCurrentToken().getLexeme(),getCurrentToken().getLineNumber()); 
			//add left operand of the binary operator
			binaryExpressionTree.addSubtree(left);
			//move to next token
			getNextToken();
			//add right operand 
			binaryExpressionTree.addSubtree(parseSimpleExpression());	
			return binaryExpressionTree;
		}
		getPrevToken();
		//case of no relational operator
		return left;
	}
	
	//parse term
	private TinyLangAst parseTerm() { 
		//parse factor
		TinyLangAst left = parseFactor();
		//get next token
		getNextToken();
		//expecting 0 or more expressions separated by a multiplicative operator
		if(getCurrentToken().getTokenType()==TokenType.TOK_MULTIPLICATIVE_OP) {
			//create a binary expression tree with root node containing current binary operator
			TinyLangAst binaryExpressionTree = new TinyLangAst(TinyLangAstNodes.AST_BINARY_OPERATOR_NODE,getCurrentToken().getLexeme(),getCurrentToken().getLineNumber()); 
			//add left operand of the binary operator
			binaryExpressionTree.addSubtree(left);
			//move to next token
			getNextToken();
			//add right operand 
			binaryExpressionTree.addSubtree(parseTerm());	
			return binaryExpressionTree;
		}
		getPrevToken();
		//case of no relational operator
		return left;
	}
	
	//parse term
	private TinyLangAst parseFactor() { 
		switch(getCurrentToken().getTokenType()) {
		//literals
		case TOK_BOOL_LITERAL:
			return new TinyLangAst(TinyLangAstNodes.AST_BOOLEAN_LITERAL_NODE,getCurrentToken().getLexeme(),getCurrentToken().getLineNumber());
		case TOK_INT_LITERAL:
			return new TinyLangAst(TinyLangAstNodes.AST_INTEGER_LITERAL_NODE,getCurrentToken().getLexeme(),getCurrentToken().getLineNumber());
		case TOK_FLOAT_LITERAL:
			return new TinyLangAst(TinyLangAstNodes.AST_FLOAT_LITERAL_NODE,getCurrentToken().getLexeme(),getCurrentToken().getLineNumber());
		case TOK_CHAR_LITERAL:
			return new TinyLangAst(TinyLangAstNodes.AST_CHAR_LITERAL_NODE,getCurrentToken().getLexeme(),getCurrentToken().getLineNumber());
		//identifier or function call 
		case TOK_IDENTIFIER:
			getNextToken();
			if(getCurrentToken().getTokenType()==TokenType.TOK_LEFT_ROUND_BRACKET){
				getPrevToken();
				return parseFunctionCall();	
			}
			else {
				getPrevToken();
				return new TinyLangAst(TinyLangAstNodes.AST_IDENTIFIER_NODE,getCurrentToken().getLexeme(),getCurrentToken().getLineNumber());
			}
		case TOK_LEFT_ROUND_BRACKET:
			return parseSubExpression();
		case TOK_ADDITIVE_OP:
		case TOK_NOT:
			return parseUnary();
		default:
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+" in line"+getCurrentToken().getLineNumber());
		
		}
	}
	private TinyLangAst parseSubExpression() {
		//expect left round bracket
		if(getCurrentToken().getTokenType()!=TokenType.TOK_LEFT_ROUND_BRACKET)
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+" in line"+getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//expect expression
		TinyLangAst expressionTree = parseExpression();
		
		//get next token
		getNextToken();
		//expect right round bracket
		if(getCurrentToken().getTokenType()!=TokenType.TOK_RIGHT_ROUND_BRACKET)
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+" in line"+getCurrentToken().getLineNumber());
		//return expression tree
		return expressionTree;
		
		
	}
	
	private TinyLangAst parseUnary() {

		//expect not or additive
		if(getCurrentToken().getTokenType()!=TokenType.TOK_ADDITIVE_OP && getCurrentToken().getTokenType()!=TokenType.TOK_NOT)
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+" in line"+getCurrentToken().getLineNumber());
		//create unary tree with unary operator 
		TinyLangAst unaryTree = new TinyLangAst(TinyLangAstNodes.AST_UNARY_OPERATOR_NODE,getCurrentToken().getLexeme(),getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		//expect expression
		unaryTree.addSubtree(parseExpression());
		
		return unaryTree;
	}
	
	
	private TinyLangAst parseFunctionCall() { 
		TinyLangAst functionCallTree = new TinyLangAst(TinyLangAstNodes.AST_FUNCTION_CALL_NODE,getCurrentToken().getLineNumber());
		if(getCurrentToken().getTokenType()!=TokenType.TOK_IDENTIFIER)
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+" in line"+getCurrentToken().getLineNumber());
		//add identifier node
		functionCallTree.addChild(TinyLangAstNodes.AST_IDENTIFIER_NODE,getCurrentToken().getLexeme(),getCurrentToken().getLineNumber());		
		getNextToken();
		if(getCurrentToken().getTokenType()!=TokenType.TOK_LEFT_ROUND_BRACKET)
			//not as expected
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+" in line"+getCurrentToken().getLineNumber());
		
		getNextToken();
		//if not right round bracket -> we have parameters 
		if(getCurrentToken().getTokenType()!=TokenType.TOK_RIGHT_ROUND_BRACKET){
			functionCallTree.addSubtree(parseActualParams());
			//get next token (expected round bracket in next token)
			getNextToken();
		}
		else
			//add parameter node
			functionCallTree.addChild(TinyLangAstNodes.AST_ACTUAL_PARAMETERS_NODE,getCurrentToken().getLineNumber());


		if(getCurrentToken().getTokenType()!=TokenType.TOK_RIGHT_ROUND_BRACKET)
			//not as expected
			throw new java.lang.RuntimeException("expected right round bracket,) ,"+getCurrentToken().getLexeme()+" in line"+getCurrentToken().getLineNumber());	
		return functionCallTree;
	}
	
	TinyLangAst parseActualParams() {
		//parse expression
		TinyLangAst actualParamsTree = new TinyLangAst(TinyLangAstNodes.AST_ACTUAL_PARAMETERS_NODE,getCurrentToken().getLineNumber());	
		//add expression tree
		actualParamsTree.addSubtree(parseExpression());
		//get next token
		getNextToken();

		while(getCurrentToken().getTokenType()==TokenType.TOK_COMMA && getCurrentToken().getTokenType()!=TokenType.TOK_EOF )
		{
			//get next token
			getNextToken();
			actualParamsTree.addSubtree(parseExpression());
			//get next token
			getNextToken();
		}
		getPrevToken();
		return actualParamsTree;
	}
	//parse formal parameters
	TinyLangAst parseFormalParams() {
		//parse expression
		TinyLangAst formalParamsTree = new TinyLangAst(TinyLangAstNodes.AST_FORMAL_PARAMETERS_NODE,getCurrentToken().getLineNumber());	
		//add formal param tree
		formalParamsTree.addSubtree(parseFormalParam());
		//get next token
		getNextToken();

		while(getCurrentToken().getTokenType()==TokenType.TOK_COMMA)
		{
			//get next token
			getNextToken();
			formalParamsTree.addSubtree(parseFormalParam());
			//get next token
			getNextToken();
		}
		getPrevToken();

		return formalParamsTree;
	}
	//parse formal parameter
	TinyLangAst parseFormalParam() {
		//parse expression
		TinyLangAst formalParamTree = new TinyLangAst(TinyLangAstNodes.AST_FORMAL_PARAMETER_NODE,getCurrentToken().getLineNumber());	
		//expect identifier
		if(getCurrentToken().getTokenType()!=TokenType.TOK_IDENTIFIER)
			throw new java.lang.RuntimeException(getCurrentToken().getLexeme()+" in line "+getCurrentToken().getLineNumber()+" is not a valid parameter name");
		//add identifier node
		Token identifier = getCurrentToken();
		//get next token
		getNextToken();
		// expect :
		if(getCurrentToken().getTokenType()!=TokenType.TOK_COLON)
			//not as expected
			throw new java.lang.RuntimeException("unexpected "+getCurrentToken().getLexeme()+" in line"+getCurrentToken().getLineNumber());
		//get next token
		getNextToken();
		formalParamTree.addSubtree(parseType());	
		formalParamTree.addChild(TinyLangAstNodes.AST_IDENTIFIER_NODE,identifier.getLexeme(),identifier.getLineNumber());
		return formalParamTree;
	}
	public TinyLangAst getTinyLangAbstraxSyntaxTree() {
		return tinyLangProgramAbstractSyntaxTree;
	}
}
