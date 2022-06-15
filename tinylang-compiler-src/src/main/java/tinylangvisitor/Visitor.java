package tinylangvisitor;
import tinylangparser.TinyLangAst;
public interface Visitor {
	 void visitTinyLangProgram(TinyLangAst tinyLangAst);
	 void visitVariableDeclarationNode(TinyLangAst tinyLangAst);
	 void visitAssignmentNode(TinyLangAst tinyLangAst);
	 void visitPrintStatementNode(TinyLangAst tinyLangAst);
	 void visitIfStatementNode(TinyLangAst tinyLangAst);
	 void visitForStatementNode(TinyLangAst tinyLangAst);
	 void visitWhileStatementNode(TinyLangAst tinyLangAst);
	 void visitReturnStatementNode(TinyLangAst tinyLangAst);
	 void visitFunctionDeclarationNode(TinyLangAst tinyLangAst);
	 void visitFunctionCallNode(TinyLangAst tinyLangAst);
	 void visitBlockNode(TinyLangAst tinyLangAst);
//	 void visitElseBlockNode(TinyLangAst tinyLangAst);
	 //expression
	 void visitBinaryOperatorNode(TinyLangAst tinyLangAst);
	 void visitUnaryOperatorNode(TinyLangAst tinyLangAst);
	 void visitIdentifierNode(TinyLangAst tinyLangAst);
	 //literals
	 void visitBooleanLiteralNode(TinyLangAst tinyLangAst);
	 void visitIntegerLiteralNode(TinyLangAst tinyLangAst);
	 void visitFloatLiteralNode(TinyLangAst tinyLangAst);
	 void visitCharLiteralNode(TinyLangAst tinyLangAst);
}
