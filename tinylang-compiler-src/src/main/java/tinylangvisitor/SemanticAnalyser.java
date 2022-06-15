package tinylangvisitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import tinylangparser.TinyLangAst;
import tinylangparser.TinyLangAstNodes;
import tinylangparser.Type;

public class SemanticAnalyser implements Visitor {
	/**
	 * Constructor for semantic analysis,
	 * pass in AST of TinyLang program
	 * to semantically analyse it.
	 * @param programTree
	 */
	public SemanticAnalyser(TinyLangAst programTree) {
		//create global scope
		st.push();
		//traverse program
		visitTinyLangProgram(programTree);
		//confirmation
		st.pop();
		System.out.println("Note: program is semantically correct");	
	}
	//this is used to analyse types of expressions
	private Type currentExpressionType;
	//set symbol table
	private SymbolTable st = new SymbolTable();
	//get a hold of current function types
	Stack<Type> function = new Stack<Type>();
	//get a hold of current function parameters
	Map<String,Type> currentFunctionParameters = new HashMap<String,Type>();

	//Map<String,Type> currentFunctionParameters = new HashMap<String,Type>();
	
	//method which runs statement visit method based on node type
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
	//visit expression based on node type 
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
	@Override
	public void visitTinyLangProgram(TinyLangAst tinyLangAst) {
		//traverse all statements
		for(TinyLangAst statement : tinyLangAst.getChildren()) 
			//visit statement
			visitStatement(statement);
	}

	@Override
	public void visitVariableDeclarationNode(TinyLangAst tinyLangAst) {
		//get expression
		TinyLangAst identifier = tinyLangAst.getChildren().get(1);
		TinyLangAst expression = tinyLangAst.getChildren().get(2);
		//if identifier is already declared -> ERROR
		if(st.isVariableNameBinded(identifier.getAssociatedNodeValue())==true)
			throw new java.lang.RuntimeException("variable "+identifier.getAssociatedNodeValue()+" in line "
															+identifier.getLineNumber()+" was already declared previously");	
		//visit expression -> update current expression type
		visitExpression(expression);
						
		/* type checking */
		//we allow type(variable)=float and type(expression)=int (since int can resolve to float)
		Type varType = Type.valueOf(tinyLangAst.getChildren().get(0).getAssociatedNodeValue());
		if(varType==Type.FLOAT && getCurrentExpressionType()==Type.INTEGER) 
			//name binding
			st.insertVariableDeclaration(identifier.getAssociatedNodeValue(), varType);
		else if(varType==getCurrentExpressionType())
			//name binding
			st.insertVariableDeclaration(identifier.getAssociatedNodeValue(), varType);
		else
			throw new java.lang.RuntimeException("type mismatch, identifier in line"
												+identifier.getLineNumber()
												+"of type"+varType
												+" and expression in line"
												+expression.getLineNumber()
												+" of type"+getCurrentExpressionType());
	}

	@Override
	public void visitAssignmentNode(TinyLangAst tinyLangAst) {
		//get identifier name
		String variableName = tinyLangAst.getChildren().get(0).getAssociatedNodeValue();
		//visit expression
		TinyLangAst expression = tinyLangAst.getChildren().get(1);
		//get a hold of all scopes
		Stack<Scope> scopes  = st.getScopes();
		int i = 0;
		/*
		 * start traversing from inner scope to outer scope to find in
		 * which innermost scope variable is declared
		 */
		for(i=scopes.size()-1;i>=0;i--) {
			if(scopes.get(i).isVariableNameBinded(variableName))
				break;
		}
		if(i<0)
			throw new java.lang.RuntimeException("identifier "+variableName+" was never declared");
		//obtain type from scope 
		Type type = scopes.get(i).getVariableType(variableName);
		//visit expression & update the current expression type
		visitExpression(expression);
		
		//handle assignment type mismatch
		
		//allow integer to resolve to float
		if(type==Type.FLOAT && getCurrentExpressionType()==Type.INTEGER);
		else if(type!=getCurrentExpressionType())
			throw new java.lang.RuntimeException("type mismatch : variable "+variableName
															+" in line "
															+  tinyLangAst.getChildren()
															.get(0).getLineNumber()
															+ " of type "+type.toString()
															+ "assigned to expression of type"
															+ getCurrentExpressionType().toString());
	}

	@Override
	public void visitPrintStatementNode(TinyLangAst tinyLangAst) {
		//visit expression -> update current expression type
		visitExpression(tinyLangAst.getChildren().get(0));
	}

	@Override
	public void visitIfStatementNode(TinyLangAst tinyLangAst) {
		//get expression
		TinyLangAst expression = tinyLangAst.getChildren().get(0);
		//visit expression and update expression current type
		visitExpression(expression);
		//check that expression is boolean
		if(getCurrentExpressionType()!=Type.BOOL)
			throw new java.lang.RuntimeException("if condition in line "
												+tinyLangAst.getLineNumber()
												+" is not a predicate expression");
		//visit if block 
		visitBlockNode(tinyLangAst.getChildren().get(1));
		//if exists an else block visit it
		if(tinyLangAst.getChildren().size()==3)
			visitBlockNode(tinyLangAst.getChildren().get(2));
	}

	@Override
	public void visitForStatementNode(TinyLangAst tinyLangAst) {
		//first child is variable declaration or expression
		if(tinyLangAst.getChildren().get(0).getAssociatedNodeType()==TinyLangAstNodes.AST_VARIABLE_DECLARATION_NODE)
			visitVariableDeclarationNode(tinyLangAst.getChildren().get(0));
		else
			visitExpression(tinyLangAst.getChildren().get(0));
		
		//second child is assignment or block or expression
		if(tinyLangAst.getChildren().get(1).getAssociatedNodeType()==TinyLangAstNodes.AST_ASSIGNMENT_NODE)
			visitAssignmentNode(tinyLangAst.getChildren().get(1));
		else if(tinyLangAst.getChildren().get(1).getAssociatedNodeType()==TinyLangAstNodes.AST_BLOCK_NODE)
			visitBlockNode(tinyLangAst.getChildren().get(1)); 
		else
			visitExpression(tinyLangAst.getChildren().get(1));
		
		//if we have 3 children
		//third child is assignment or block
		if(tinyLangAst.getChildren().size()==3&&tinyLangAst.getChildren().get(2).getAssociatedNodeType()==TinyLangAstNodes.AST_ASSIGNMENT_NODE)
			visitAssignmentNode(tinyLangAst.getChildren().get(2));
		else if(tinyLangAst.getChildren().size()==3&&tinyLangAst.getChildren().get(2).getAssociatedNodeType()==TinyLangAstNodes.AST_BLOCK_NODE)
			visitBlockNode(tinyLangAst.getChildren().get(2));
		//if we have 4 children 
		//fourth child is block
		if(tinyLangAst.getChildren().size()==4)
			visitBlockNode(tinyLangAst.getChildren().get(3));
	}

	@Override
	public void visitWhileStatementNode(TinyLangAst tinyLangAst) {
		//got a hold on expression condition
		TinyLangAst expression  = tinyLangAst.getChildren().get(0);
		//get a hold on block node
		TinyLangAst	 block = tinyLangAst.getChildren().get(1);
		//visit expression and update current value expression type 
		visitExpression(expression);
		//expect that the expression is a predicate
		if(getCurrentExpressionType()!=Type.BOOL)
			throw new java.lang.RuntimeException("expected while condition to be a predicate in line "+tinyLangAst.getLineNumber());
		//visit block
		visitBlockNode(block);
	}

	@Override
	public void visitReturnStatementNode(TinyLangAst tinyLangAst) {
		//get expression 
		TinyLangAst expression = tinyLangAst.getChildren().get(0);
		//visit expression and update current expression time
		visitExpression(expression);
		//we allow to return integer if function is of type float
		if(!function.empty() && getCurrentExpressionType()==Type.INTEGER && function.peek()==Type.FLOAT);
		//check that expression is has the same type as the function
		else if(!function.empty() && getCurrentExpressionType()!=function.peek())
			throw new java.lang.RuntimeException("return in line "
					+tinyLangAst.getLineNumber()+" returns expression of type "+
					getCurrentExpressionType()+" expected type "+function.peek());
	}

	@Override
	public void visitFunctionDeclarationNode(TinyLangAst tinyLangAst) {
		//get function type
		Type functionType = Type.valueOf(tinyLangAst.getChildren().get(0).getAssociatedNodeValue());

		//get function identifier
		String functionName = tinyLangAst.getChildren().get(1).getAssociatedNodeValue();
		
		//get function parameter types 
		Stack<Type> functionParameterTypes = new Stack<Type>();
		//get stack of names to check for duplicate parameter names
		Stack<String> functionParameterNames = new Stack<String>();
		//add types
		
		//get current paramater name
		TinyLangAst parameterName;
		for(TinyLangAst formalParameterTypes : tinyLangAst.getChildren().get(2).getChildren()) { 
			parameterName = formalParameterTypes.getChildren().get(1);
			functionParameterTypes.push(Type.valueOf(
					formalParameterTypes.getChildren()
					.get(0).getAssociatedNodeValue()));
			//if parameter name is duplicate throw exception
			if(functionParameterNames.contains(parameterName.getAssociatedNodeValue()))
				throw new java.lang.RuntimeException("function parameter name "+parameterName.getAssociatedNodeValue()+ 
						" already defined in line "+parameterName.getLineNumber());
			functionParameterNames.push(parameterName.getAssociatedNodeValue());
		}

		//check in all scopes that the function is not already defined
		for(Scope scope : st.getScopes())
			if(scope.isFunctionAlreadyDefined(new FunctionSignature(functionName, functionParameterTypes))) 
				throw new java.lang.RuntimeException("function "+functionName+" in line "+tinyLangAst.getChildren().get(1).getLineNumber()+" with the same parameter types already defined previously");
		//add function to st
		st.insertFunctionDeclaration(new FunctionSignature(functionName,functionParameterTypes), functionType);
		//record current function in stack
		function.push(functionType);
		//empty current function parameters
		currentFunctionParameters.clear();
		for(TinyLangAst formalParameter : tinyLangAst.getChildren().get(2).getChildren()) 
			currentFunctionParameters.put(formalParameter.getChildren().get(1).getAssociatedNodeValue(),
											Type.valueOf(formalParameter.getChildren()
											.get(0).getAssociatedNodeValue()));
		//visit block
		visitBlockNode(tinyLangAst.getChildren().get(3));
		//pop type
		function.pop();
		//check if function returns
		if(!returns(tinyLangAst.getChildren().get(3)))
			throw new java.lang.RuntimeException("function "+functionName+" in line "+tinyLangAst.getLineNumber()+" not expected to return");
	}

	@Override
	public void visitFunctionCallNode(TinyLangAst tinyLangAst) {
		//determine the signature of the function
		Stack<Type> parameterTypes = new Stack<Type>();
		String functionIdentifier = tinyLangAst.getChildren().get(0).getAssociatedNodeValue();
		//identify the expressions and update  stack current expression types
		for(TinyLangAst expression :tinyLangAst.getChildren().get(1).getChildren()) {
			visitExpression(expression);
			parameterTypes.push(getCurrentExpressionType());
		}
		Stack<Scope> scopes = st.getScopes();
		int i;

		for(i=scopes.size()-1;i>=0;i--)
			if(scopes.get(i).isFunctionAlreadyDefined(new FunctionSignature(functionIdentifier,parameterTypes)))
				break;
		
		if(i<0)
			throw new java.lang.RuntimeException("function "+functionIdentifier+" in line "+tinyLangAst.getLineNumber()+" is not defined");
		
		//if defined set current expression type to return value of the function
		setCurrentExpressionType(scopes.get(i).getFunctionType(new FunctionSignature(functionIdentifier,parameterTypes)));
	}

	@Override
	public void visitBlockNode(TinyLangAst tinyLangAst) {
		//create new scope 
		st.push();
		//add parameters of functions if any in scope
		for(String variableName:currentFunctionParameters.keySet()) 
			st.insertVariableDeclaration(variableName,currentFunctionParameters.get(variableName));
		//clear parameter map
		currentFunctionParameters.clear();
		//traverse statements in block
		for(TinyLangAst statement:tinyLangAst.getChildren())
			visitStatement(statement);
		//visit statements in block
		//end scope
		st.pop();
	}

//	@Override
//	public void visitElseBlockNode(TinyLangAst tinyLangAst) {
//		visitBlockNode(tinyLangAst);
//	}

	@Override
	public void visitBinaryOperatorNode(TinyLangAst tinyLangAst) {
		//get operator 
		String operator = tinyLangAst.getAssociatedNodeValue();
		//get left node  (left operand)
		TinyLangAst leftOperand = tinyLangAst.getChildren().get(0);
		//visit expression to update current char type
		visitExpression(leftOperand);
		//obtain the type of the left operand
		Type leftOperandType = getCurrentExpressionType();
		
		//REDO for right node (right operand)
		//get left node  (left operand)
		TinyLangAst rightOperand = tinyLangAst.getChildren().get(1);
		//visit expression to update current char type
		visitExpression(rightOperand);
		//obtain the type of the left operand
		Type rightOperandType = getCurrentExpressionType();
		
		/* 
		 * Operators 
		 * 
		 * Operators 'and | 'or' must have operands of type bool
		 * 
		 * Operator '+' | '-' | '/' | '*' | '<' | '>' | '<=' | '>=' work on numeric operators  
		 *
		 * Operators '==' | '!=' operates on any 2 operands of the same type both numeric or both boolean or both char
		 */
		if(operator.equals("and")||operator.equals("or")) {
			if(leftOperandType==Type.BOOL && rightOperandType==Type.BOOL)
				setCurrentExpressionType(Type.BOOL);
			
			else 
				throw new java.lang.RuntimeException("expected 2 operands of boolean type for operator "
													 +operator+" in line "+tinyLangAst.getLineNumber());
		}
		else if(operator.equals("+")||operator.equals("-")||operator.equals("/")||operator.equals("*")){
			if(!isNumericType(leftOperandType)||!isNumericType(rightOperandType))
				throw new java.lang.RuntimeException("expected 2 operands of numeric type for operator "
						 +operator+" in line "+tinyLangAst.getLineNumber());
			
			//if both are numeric if one of them is float the operator returns float otherwise returns integer
			if(leftOperandType==Type.FLOAT||rightOperandType==Type.FLOAT)
				setCurrentExpressionType(Type.FLOAT);
			else 
				setCurrentExpressionType(Type.INTEGER);
		
		}
		else if(operator.equals("<")||operator.equals(">")||operator.equals("<=")||operator.equals(">=")) {
			if(!isNumericType(leftOperandType)||!isNumericType(rightOperandType))
				throw new java.lang.RuntimeException("expected 2 operands of numeric type for operator "
						 +operator+" in line "+tinyLangAst.getLineNumber());
			//if both are numeric set relation operators returns a boolean value
			setCurrentExpressionType(Type.BOOL);
		}
	
		else if(operator.equals("==")||operator.equals("!=")) {
			//handle mismatch not that float and integers are
			//both considered as one numerical type
			if((leftOperandType!=rightOperandType) && 
					(!isNumericType(leftOperandType)||!isNumericType(rightOperandType)))
				throw new java.lang.RuntimeException("operand mismatch in line "+tinyLangAst.getLineNumber());
			//if operands match 
			setCurrentExpressionType(Type.BOOL);
		}
		else 
			throw new java.lang.RuntimeException("binary operator "+operator+" unrecognised");
	}

	@Override
	public void visitUnaryOperatorNode(TinyLangAst tinyLangAst) {
		//unary operator
		String operator = tinyLangAst.getAssociatedNodeValue();
		//visit expression
		visitExpression(tinyLangAst.getChildren().get(0));
		//if current expression is numerical 
		if(getCurrentExpressionType()==Type.INTEGER || getCurrentExpressionType()==Type.FLOAT) 
			//check if operator is '-' | '+'
			if(!operator.equals("-")&&!operator.equals("+")) 
				throw new java.lang.RuntimeException("operator "+operator+" not allowed in front of numerical expression in line "+tinyLangAst.getChildren().get(0).getLineNumber());

		else if(getCurrentExpressionType()==Type.BOOL ) 
			//check if operator is not
			if(!operator.equals("not")) 
					throw new java.lang.RuntimeException("operator "+operator+" not allowed in front of predicate expreesion in line "+tinyLangAst.getChildren().get(0).getLineNumber());
		else 
			throw new java.lang.RuntimeException("unary operator "+operator+" is incompatible with expression in line "+tinyLangAst.getLineNumber());
	}

	@Override
	public void visitIdentifierNode(TinyLangAst tinyLangAst) {
		//find scope where identifier is defined
		Stack<Scope> scopes = st.getScopes();
		int i;
		for(i=scopes.size()-1;i>=0;i--)
			if(scopes.get(i).isVariableNameBinded(tinyLangAst.getAssociatedNodeValue()))
				break;
		if(i<0)
			throw new java.lang.RuntimeException("variable name "+tinyLangAst.getAssociatedNodeValue()+" in line "+tinyLangAst.getLineNumber()+" is not defined");
		setCurrentExpressionType(scopes.get(i).getVariableType(tinyLangAst.getAssociatedNodeValue()));
	}

	@Override
	public void visitBooleanLiteralNode(TinyLangAst tinyLangAst) {
		setCurrentExpressionType(Type.BOOL);
	}

	@Override
	public void visitIntegerLiteralNode(TinyLangAst tinyLangAst) {
		setCurrentExpressionType(Type.INTEGER);
	}

	@Override
	public void visitFloatLiteralNode(TinyLangAst tinyLangAst) {
		setCurrentExpressionType(Type.FLOAT);		
	}

	@Override
	public void visitCharLiteralNode(TinyLangAst tinyLangAst) {
		setCurrentExpressionType(Type.CHAR);
	}
	private boolean isNumericType(Type type) {
		if(type ==Type.INTEGER||type ==Type.FLOAT)
			return true;
		else 
			return false;
		
	}

	private boolean returns(TinyLangAst tinyLangAst) {
		//if given statement is a return statement 
		//then obviously we have that the function returns
		if(tinyLangAst.getAssociatedNodeType()==TinyLangAstNodes.AST_RETURN_STATEMENT_NODE)
			return true;
		//given a block we check if one of the statement returns
		if(tinyLangAst.getAssociatedNodeType()==TinyLangAstNodes.AST_BLOCK_NODE) { 
			for(TinyLangAst statement:tinyLangAst.getChildren())
				if(returns(statement)) 
					return true;
		}
		//given a block we check if one of the statement returns
		if(tinyLangAst.getAssociatedNodeType()==TinyLangAstNodes.AST_ELSE_BLOCK_NODE) { 
			for(TinyLangAst statement:tinyLangAst.getChildren())
				if(returns(statement)) 
					return true;
		}
		//if statement with an else block returns if both statement returns
		if(tinyLangAst.getAssociatedNodeType()==TinyLangAstNodes.AST_IF_STATEMENT_NODE) 
			//if statement has else block
			if(tinyLangAst.getChildren().size()==3) {
				//block and else block both return
				return returns(tinyLangAst.getChildren().get(1)) && returns(tinyLangAst.getChildren().get(2));
			}		
		//if statement with an for block returns if both statement returns
		if(tinyLangAst.getAssociatedNodeType()==TinyLangAstNodes.AST_FOR_STATEMENT_NODE) 
				return returns(tinyLangAst.getChildren().get(tinyLangAst.getChildren().size()-1));
		
		//if statement with an else block returns if both statement returns
		if(tinyLangAst.getAssociatedNodeType()==TinyLangAstNodes.AST_WHILE_STATEMENT_NODE) 
			return returns(tinyLangAst.getChildren().get(1));
		else
			//in all other cases the function do not return 
			return false;
	}		
	public void setCurrentExpressionType(Type currentExpressionType) {
		this.currentExpressionType=currentExpressionType;
	}
	public Type getCurrentExpressionType() {
		return currentExpressionType;
}
}
