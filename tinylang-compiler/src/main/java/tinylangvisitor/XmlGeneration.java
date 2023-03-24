package tinylangvisitor;
import tinylangparser.TinyLangAst;
import tinylangparser.TinyLangAstNodes;
public class XmlGeneration implements Visitor {
	private String xmlRepresentation = "";
	private int indentation = 0;
	private String getCurrentIndentationLevel() {
		String indentation = "";
		for(int i=0;i<this.indentation;i++)
			//add indentation
			indentation+="    ";//(char)0x09;
		return indentation;
	}
	
	//method which runs statement type visit method based on node type
	public void visitStatement(TinyLangAst tinyLangAst) {
		switch(tinyLangAst.getAssociatedNodeType()) {
		case AST_VARIABLE_DECLARATION_NODE:
			visitVariableDeclarationNode(tinyLangAst);
			break;
		case AST_ASSIGNMENT_NODE:
			visitAssignmentNode(tinyLangAst);
			break;
		case AST_PRINT_STATEMENT_NODE:
			visitPrintStatementNode(tinyLangAst);
			break;
		case AST_IF_STATEMENT_NODE:
			visitIfStatementNode(tinyLangAst);
			break;
		case AST_FOR_STATEMENT_NODE:
			visitForStatementNode(tinyLangAst);
			break;
		case AST_WHILE_STATEMENT_NODE:
			visitWhileStatementNode(tinyLangAst);
			break;
		case AST_RETURN_STATEMENT_NODE:
			visitReturnStatementNode(tinyLangAst);
			break;
		case AST_FUNCTION_DECLARATION_NODE:
			visitFunctionDeclarationNode(tinyLangAst);
			break;
		case AST_BLOCK_NODE:
			visitBlockNode(tinyLangAst);
			break;
		default:
			throw new java.lang.RuntimeException("Unrecognised statement of  type "+tinyLangAst.getAssociatedNodeType());
		}	
	}
	
	private void visitExpression(TinyLangAst tinyLangAst){
		switch(tinyLangAst.getAssociatedNodeType()) {
		case AST_BINARY_OPERATOR_NODE:
			visitBinaryOperatorNode(tinyLangAst);
			break;
		case AST_UNARY_OPERATOR_NODE:
			visitUnaryOperatorNode(tinyLangAst);
			break;
		case AST_BOOLEAN_LITERAL_NODE:
			visitBooleanLiteralNode(tinyLangAst);
			break;
		case AST_INTEGER_LITERAL_NODE:
			visitIntegerLiteralNode(tinyLangAst);
			break;
		case AST_FLOAT_LITERAL_NODE:
			visitFloatLiteralNode(tinyLangAst);
			break;
		case AST_CHAR_LITERAL_NODE:
			visitCharLiteralNode(tinyLangAst);
			break;
		case AST_IDENTIFIER_NODE:
			visitIdentifierNode(tinyLangAst);
			break;
		case AST_FUNCTION_CALL_NODE:
			visitFunctionCallNode(tinyLangAst);
			break;
		default:
			throw new java.lang.RuntimeException("Unrecognised expression node of type "+tinyLangAst.getAssociatedNodeType());
		}	
	}
	

	
	public XmlGeneration(TinyLangAst tinyLangAst) {
		visitTinyLangProgram(tinyLangAst);
	}
	
	
	
	@Override
	public void visitTinyLangProgram(TinyLangAst tinyLangAst) {
		xmlRepresentation+=getCurrentIndentationLevel()+"<TinyLangProgram>\n";
		//indent
		indentation++;
		for(TinyLangAst child : tinyLangAst.getChildren())
			visitStatement(child);
		//unindent
		indentation--;
		xmlRepresentation+=getCurrentIndentationLevel()+"<\\TinyLangProgram>\n";		
	}

	@Override
	public void visitVariableDeclarationNode(TinyLangAst tinyLangAst) {
	
		xmlRepresentation+=getCurrentIndentationLevel()+"<variable declaration>\n";
		//indent
		indentation++;
		//visit children
		//add function identifier
		xmlRepresentation+=getCurrentIndentationLevel()+"<id type=\""+tinyLangAst.getChildren().get(0).getAssociatedNodeValue()+"\">"+tinyLangAst.getChildren().get(1).getAssociatedNodeValue()+"<\\id>\n";
		//add expression tag
		visitExpression(tinyLangAst.getChildren().get(2));
		//unindent
		indentation--;
		xmlRepresentation+=getCurrentIndentationLevel()+"<\\variable declaration>\n";	
	}

	@Override
	public void visitPrintStatementNode(TinyLangAst tinyLangAst) {
		xmlRepresentation+=getCurrentIndentationLevel()+"<print statement>\n";
		//indent
		indentation++;
		visitExpression(tinyLangAst.getChildren().get(0));
		//unindent
		indentation--;
		xmlRepresentation+=getCurrentIndentationLevel()+"<\\print statement>\n";			
	}

	@Override
	public void visitIfStatementNode(TinyLangAst tinyLangAst) {
		xmlRepresentation+=getCurrentIndentationLevel()+"<if statement>\n";
		//indent
		indentation++;
		//expect first child to be expression
		visitExpression(tinyLangAst.getChildren().get(0));
		//expect second child to be block 
		visitBlockNode(tinyLangAst.getChildren().get(1));
		//check if we have else block
		if(tinyLangAst.getChildren().size()==3)
			visitBlockNode(tinyLangAst.getChildren().get(2));	
		//unindent
		indentation--;
		xmlRepresentation+=getCurrentIndentationLevel()+"<\\if statement>\n";			
	}

	@Override
	public void visitForStatementNode(TinyLangAst tinyLangAst) {
		//add for statement tag
		xmlRepresentation+=getCurrentIndentationLevel()+"<for statement>\n";
		//indent
		indentation++;
		
		//first child must be variable declaration or expression
		if(tinyLangAst.getChildren().get(0).getAssociatedNodeType()==TinyLangAstNodes.AST_VARIABLE_DECLARATION_NODE)
			visitVariableDeclarationNode(tinyLangAst.getChildren().get(0));	
		else 
			visitExpression(tinyLangAst.getChildren().get(0));
		
		//second child must be exp,assignment or block
		if(tinyLangAst.getChildren().get(1).getAssociatedNodeType()==TinyLangAstNodes.AST_ASSIGNMENT_NODE)
			visitAssignmentNode(tinyLangAst.getChildren().get(1));
		else if(tinyLangAst.getChildren().get(1).getAssociatedNodeType()==TinyLangAstNodes.AST_BLOCK_NODE)
			visitBlockNode(tinyLangAst.getChildren().get(1));
		else 
			visitExpression(tinyLangAst.getChildren().get(1));
		
		
		//if we have 3  children third child must be assignment or block 
		if(tinyLangAst.getChildren().size()==3 && tinyLangAst.getChildren().get(2).getAssociatedNodeType()==TinyLangAstNodes.AST_ASSIGNMENT_NODE)
			visitAssignmentNode(tinyLangAst.getChildren().get(2));
		else if(tinyLangAst.getChildren().size()==3 && tinyLangAst.getChildren().get(2).getAssociatedNodeType()==TinyLangAstNodes.AST_BLOCK_NODE)
			visitBlockNode(tinyLangAst.getChildren().get(2));
		
		//if we have 4 children
		if(tinyLangAst.getChildren().size()==4) 
			visitBlockNode(tinyLangAst.getChildren().get(3));
		indentation--;
		xmlRepresentation+=getCurrentIndentationLevel()+"<\\VariableDeclaration>\n";			
	}

	@Override
	public void visitWhileStatementNode(TinyLangAst tinyLangAst) {
		xmlRepresentation+=getCurrentIndentationLevel()+"<while statement>\n";
		//indent
		indentation++;
		//expected 2 children expression and nodes
		if(tinyLangAst.getChildren().size()!=2)
			throw new java.lang.RuntimeException("while statement node has "+tinyLangAst.getChildren().size()+" expected 2");
		if(tinyLangAst.getChildren().get(1).getAssociatedNodeType()!=TinyLangAstNodes.AST_BLOCK_NODE)
			throw new java.lang.RuntimeException("second child of while statement is "+tinyLangAst.getChildren().get(1).getAssociatedNodeType()+" expected AST_BLOCK_NODE");
		//visit expression and block
		visitExpression(tinyLangAst.getChildren().get(0));
		visitBlockNode(tinyLangAst.getChildren().get(1));
		indentation--;
		xmlRepresentation+=getCurrentIndentationLevel()+"<\\while statement>\n";			
	}

	@Override
	public void visitReturnStatementNode(TinyLangAst tinyLangAst) {
		xmlRepresentation+=getCurrentIndentationLevel()+"<return statement>\n";
		//indent
		indentation++;
		//visit expression
		visitExpression(tinyLangAst.getChildren().get(0));
		indentation--;
		xmlRepresentation+=getCurrentIndentationLevel()+"<\\return statement>\n";			
	}

	@Override
	public void visitFunctionDeclarationNode(TinyLangAst tinyLangAst) {
		xmlRepresentation+=getCurrentIndentationLevel()+"<function declaration>\n";
		//expected 4 children of types identifier,formal parameters,type and block
		//indent
		indentation++;
		//add function identifier
		xmlRepresentation+=getCurrentIndentationLevel()+"<id type=\""+tinyLangAst.getChildren().get(0).getAssociatedNodeValue()+"\">"+tinyLangAst.getChildren().get(1).getAssociatedNodeValue()+"<\\id>\n";
		//add parameters
		xmlRepresentation+=getCurrentIndentationLevel()+"<parameters>\n";
			indentation++;
			for(TinyLangAst child : tinyLangAst.getChildren().get(2).getChildren()) {
				xmlRepresentation+=getCurrentIndentationLevel()+"<parameters>\n";
				indentation++;
				xmlRepresentation+=getCurrentIndentationLevel()+"<id type=\""+child.getChildren().get(0).getAssociatedNodeValue()+">"+child.getChildren().get(1).getAssociatedNodeValue()+"<\\id>\n";
				indentation--;
				xmlRepresentation+=getCurrentIndentationLevel()+"<\\parameters>\n";
			}
			indentation--;
		xmlRepresentation+=getCurrentIndentationLevel()+"<\\parameters>\n";

			visitBlockNode(tinyLangAst.getChildren().get(3));
		//unindent
		indentation--;
		xmlRepresentation+=getCurrentIndentationLevel()+"<\\function declaration>\n";			
				
	}
	
	@Override
	public void visitFunctionCallNode(TinyLangAst tinyLangAst) {
		xmlRepresentation+=getCurrentIndentationLevel()+"<function call>\n";
		//expected 4 children of types identifier,formal parameters,type and block
		//indent
		indentation++;
		//add function identifier
		visitIdentifierNode(tinyLangAst.getChildren().get(0));
		//add parameters
		xmlRepresentation+=getCurrentIndentationLevel()+"<parameters>\n";
			indentation++;
			for(TinyLangAst child : tinyLangAst.getChildren().get(1).getChildren()) {
				xmlRepresentation+=getCurrentIndentationLevel()+"<actual parameter>\n";
				indentation++;
				visitExpression(child);
				indentation--;
				xmlRepresentation+=getCurrentIndentationLevel()+"<\\actual parameter>\n";
			}
			indentation--;
		xmlRepresentation+=getCurrentIndentationLevel()+"<\\parameters>\n";

		//unindent
		indentation--;
		xmlRepresentation+=getCurrentIndentationLevel()+"<\\function call>\n";			
				
	}

	@Override
	public void visitBlockNode(TinyLangAst tinyLangAst) {
		if(tinyLangAst.getAssociatedNodeType()==TinyLangAstNodes.AST_ELSE_BLOCK_NODE)
			xmlRepresentation+=getCurrentIndentationLevel()+"<else block>\n";			
		else
			xmlRepresentation+=getCurrentIndentationLevel()+"<block>\n";
		//indent
		indentation++;
		//children are statements
		for(TinyLangAst child:tinyLangAst.getChildren())
			visitStatement(child);
		indentation--;
		if(tinyLangAst.getAssociatedNodeType()==TinyLangAstNodes.AST_ELSE_BLOCK_NODE)
			xmlRepresentation+=getCurrentIndentationLevel()+"<\\else block>\n";			
		else 
			xmlRepresentation+=getCurrentIndentationLevel()+"<\\block>\n";			
				
	}

	@Override
	public void visitBinaryOperatorNode(TinyLangAst tinyLangAst) {
		xmlRepresentation+=getCurrentIndentationLevel()+"<binary Op=\""+tinyLangAst.getAssociatedNodeValue()+"\">\n";
		//expected binary operator -> 2 children expression
		if(tinyLangAst.getChildren().size()!=2)
			throw new java.lang.RuntimeException("binary node has "+tinyLangAst.getChildren().size()+" child(ren) expected 2");
		//indent
		indentation++;
		//visit expression
		visitExpression(tinyLangAst.getChildren().get(0));
		visitExpression(tinyLangAst.getChildren().get(1));

		indentation--;
		xmlRepresentation+=getCurrentIndentationLevel()+"<\\binary>\n";			
				
	}

	@Override
	public void visitUnaryOperatorNode(TinyLangAst tinyLangAst) {
		xmlRepresentation+=getCurrentIndentationLevel()+"<unary Op=\""+tinyLangAst.getAssociatedNodeValue()+"\">\n";
		//expected unary expression node -> one child
		if(tinyLangAst.getChildren().size()!=1)
			throw new java.lang.RuntimeException("unary node has "+tinyLangAst.getChildren().size()+" children expected 1");
		//indent
		indentation++;
		//visit expression
			visitExpression(tinyLangAst.getChildren().get(0));
		//unindent
		indentation--;
		xmlRepresentation+=getCurrentIndentationLevel()+"<\\unary>\n";					
	}

	@Override
	public void visitBooleanLiteralNode(TinyLangAst tinyLangAst) {
		xmlRepresentation+=getCurrentIndentationLevel()+"<boolean literal>"+tinyLangAst.getAssociatedNodeValue()+"<\\boolean literal>\n";			
	}

	@Override
	public void visitIntegerLiteralNode(TinyLangAst tinyLangAst) {
		xmlRepresentation+=getCurrentIndentationLevel()+"<integer literal>"+tinyLangAst.getAssociatedNodeValue()+"<\\integer literal>\n";			

	}

	@Override
	public void visitFloatLiteralNode(TinyLangAst tinyLangAst) {
		xmlRepresentation+=getCurrentIndentationLevel()+"<float literal>"+tinyLangAst.getAssociatedNodeValue()+"<\\float literal>\n";			
	}

	@Override
	public void visitCharLiteralNode(TinyLangAst tinyLangAst) {
		xmlRepresentation+=getCurrentIndentationLevel()+"<char literal>"+tinyLangAst.getAssociatedNodeValue()+"<\\char literal>\n";			
	}

	@Override
	public void visitIdentifierNode(TinyLangAst tinyLangAst) {
		xmlRepresentation+=getCurrentIndentationLevel()+"<id>"+tinyLangAst.getAssociatedNodeValue()+"<\\id>\n";							
	}
	public void printXmlTree() {
		System.out.println(xmlRepresentation);
	}
//	@Override
//	public void visitElseBlockNode(TinyLangAst tinyLangAst) {
//		xmlRepresentation+=getCurrentIndentationLevel()+"<else block>\n";			
//		//indent
//		indentation++;
//		//children are statements
//		for(TinyLangAst child:tinyLangAst.getChildren())
//			visitStatement(child);
//		indentation--;
//		xmlRepresentation+=getCurrentIndentationLevel()+"<\\else block>\n";			
//				
//	}
//

	public void visitAssignmentNode(TinyLangAst tinyLangAst) {		
		xmlRepresentation+=getCurrentIndentationLevel()+"<assignment>\n";			
		indentation++;
			//add identifier and expression tags
			visitIdentifierNode(tinyLangAst.getChildren().get(0));
			visitExpression(tinyLangAst.getChildren().get(0));
		indentation--;
		xmlRepresentation+=getCurrentIndentationLevel()+"<\\assignment>\n";					
	}
}
