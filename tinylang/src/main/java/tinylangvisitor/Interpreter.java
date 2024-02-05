package tinylangvisitor;

import java.util.Stack;

import tinylangparser.TinyLangAst;
import tinylangparser.TinyLangAstNodes;
import tinylangparser.Type;

/**
 * The Interpreter class ...
 *
 * @author Andimon
 * @version 1.0
 */
public class Interpreter implements Visitor {
    //create a symbol table
    private final SymbolTable st = new SymbolTable();
    //save current expression type for evaluation
    private Type currentExpressionType;
    //save current expression value for evaluation
    private String currentExpressionValue;

    //save temporary information on function call parameters
    private final Stack<Type> parameterTypes = new Stack<Type>();
    private final Stack<String> parameterNames = new Stack<String>();
    private final Stack<String> parameterValues = new Stack<String>();


    public Interpreter(TinyLangAst intermediateRepresentation) {
        //analyse the representation semantically
        new SemanticAnalyser(intermediateRepresentation);
        //push global scope
        st.push();
        //interpret tinyLangProgram
        System.out.println("|------------------------------------|");
        System.out.println(" Interpreter Output                  ");
        System.out.println("|------------------------------------|");
        visitTinyLangProgram(intermediateRepresentation);
        System.out.println("|-------------------------------------|");
    }

    //method which runs statement visit method based on node type
    private void visitStatement(TinyLangAst tinyLangAst) {
        switch (tinyLangAst.getAssociatedNodeType()) {
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
                throw new java.lang.RuntimeException("Unrecognised statement of  type " + tinyLangAst.getAssociatedNodeType());
        }
    }

    //visit expression based on node type
    private void visitExpression(TinyLangAst tinyLangAst) {
        switch (tinyLangAst.getAssociatedNodeType()) {
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
                throw new java.lang.RuntimeException("Unrecognised expression node of type " + tinyLangAst.getAssociatedNodeType());
        }
    }

    @Override
    public void visitTinyLangProgram(TinyLangAst tinyLangAst) {
        //program ≡ sequence of statements : traverse all statement nodes
        for (TinyLangAst statement : tinyLangAst.getChildren())
            visitStatement(statement);
    }

    @Override
    public void visitVariableDeclarationNode(TinyLangAst tinyLangAst) {
        //get variable type
        Type varType = Type.valueOf(tinyLangAst.getChildren().get(0).getAssociatedNodeValue());
        //get hold on identifier
        String varName = tinyLangAst.getChildren().get(1).getAssociatedNodeValue();
        //visit expression and update current expression value
        TinyLangAst expression = tinyLangAst.getChildren().get(2);
        visitExpression(expression);
        //add variable declaration in current scope
        st.insertVariableDeclaration(varName, varType);
        //add value assigned to variable
        st.insertVariableValue(varName, currentExpressionValue);
    }

    @Override
    public void visitAssignmentNode(TinyLangAst tinyLangAst) {
        //get identifier name
        String varName = tinyLangAst.getChildren().get(0).getAssociatedNodeValue();
        //update current expression value
        TinyLangAst expression = tinyLangAst.getChildren().get(1);
        visitExpression(expression);
        int i;
        /*
         * start traversing from inner scope to outer scope to find in
         * which innermost scope variable is declared
         */
        for (i = st.getScopes().size() - 1; i >= 0; i--) {
            if (st.getScopes().get(i).isVariableNameBinded(varName))
                break;
        }
        /*
         * go in that innermost scope and update the value
         */
        st.getScopes().get(i).addVariableValue(varName, currentExpressionValue);
    }

    @Override
    public void visitPrintStatementNode(TinyLangAst tinyLangAst) {
        visitExpression(tinyLangAst.getChildren().get(0));
        System.out.println(currentExpressionValue);
    }

    @Override
    public void visitIfStatementNode(TinyLangAst tinyLangAst) {
        TinyLangAst expression = tinyLangAst.getChildren().get(0);
        //evaluate if condition
        visitExpression(expression);
        //check condition
        if (currentExpressionValue.equals("true"))
            visitBlockNode(tinyLangAst.getChildren().get(1));
            //if we have an else block
        else if (currentExpressionValue.equals("false") && tinyLangAst.getChildren().size() == 3)
            visitBlockNode(tinyLangAst.getChildren().get(2));

    }

    @Override
    public void visitForStatementNode(TinyLangAst tinyLangAst) {
        //we have a list of possibilities for a for loop statement

        //no variable declaration and no assignment

        /*
         * 				for loop
         * 				   / \
         * 			  	  /   \
         * 		expression	   block
         */

        //this can be encoded as a while loop statement
        if (tinyLangAst.getChildren().size() == 2) {
            TinyLangAst expression = tinyLangAst.getChildren().get(0);
            TinyLangAst block = tinyLangAst.getChildren().get(1);
            visitExpression(expression);
            while (currentExpressionValue.equals("true")) {
                //visit block
                visitBlockNode(block);
                //update current expression value
                visitExpression(expression);
            }
        }
        //if we have both variable declaration and assignment

        /*
         * 				    for loop---\
         * 			   	    / /    \    \
         * 		           / /      \    \
         * 				  /  |       \   block
         * 	             / expression \
         *          variable           \
         *          declaration        updation/assignment
         *
         */
        else if (tinyLangAst.getChildren().size() == 4) {
            TinyLangAst variableDeclaration = tinyLangAst.getChildren().get(0);
            //visit variable declaration
            visitVariableDeclarationNode(variableDeclaration);
            //visit expression and update current expression value
            visitExpression(tinyLangAst.getChildren().get(1));
            while (currentExpressionValue.equals("true")) {
                //visit block
                visitBlockNode(tinyLangAst.getChildren().get(3));
                //carry out updation/assignment
                visitAssignmentNode(tinyLangAst.getChildren().get(2));
                //update current expression value
                visitExpression(tinyLangAst.getChildren().get(1));
            }
            st.deleteVariable(variableDeclaration.getChildren().get(1).getAssociatedNodeValue());
        }
        //if we have variable declaration and no assignment

        /*
         * 				    for loop
         * 		           /  /     \
         * 				  /  /       \
         * 	             / expression \
         *          variable           \
         *          declaration        block
         *
         */
        else if (tinyLangAst.getChildren().get(0).getAssociatedNodeType() == TinyLangAstNodes.AST_VARIABLE_DECLARATION_NODE) {
            TinyLangAst variableDeclaration = tinyLangAst.getChildren().get(0);
            //declare variable
            visitVariableDeclarationNode(variableDeclaration);
            //update current expression value
            visitExpression(tinyLangAst.getChildren().get(0));
            while (currentExpressionValue.equals("true")) {
                //execute statements
                visitBlockNode(tinyLangAst.getChildren().get(2));
                //update current expression value
                visitExpression(tinyLangAst.getChildren().get(0));

            }
            st.deleteVariable(variableDeclaration.getChildren().get(1).getAssociatedNodeValue());
        }
        //if we have assignment and no variable declaration

        /*
         * 				    for loop
         * 		           /    /    \
         * 				  /    /      \
         * 	             /    /        \
         *              /    assignment \
         *      expression               \
         *                              block
         *
         */
        else if (tinyLangAst.getChildren().get(1).getAssociatedNodeType() == TinyLangAstNodes.AST_ASSIGNMENT_NODE) {
            //visit expression and update current expression value
            visitExpression(tinyLangAst.getChildren().get(0));
            while (currentExpressionValue.equals("true")) {
                //visit block
                visitBlockNode(tinyLangAst.getChildren().get(2));
                //carry out update/assignment
                visitAssignmentNode(tinyLangAst.getChildren().get(1));
                //update current expression value
                visitExpression(tinyLangAst.getChildren().get(0));
            }
        } else
            throw new java.lang.RuntimeException("unexpected for loop case in line " + tinyLangAst.getLineNumber());
    }

    @Override
    public void visitWhileStatementNode(TinyLangAst tinyLangAst) {
        //get a hold on block of while loop
        TinyLangAst block = tinyLangAst.getChildren().get(1);
        //update current expression value
        TinyLangAst expression = tinyLangAst.getChildren().get(0);
        visitExpression(expression);
        //while current expression value is true
        //keep on looping
        while (currentExpressionValue.equals("true")) {
            //visit block
            visitBlockNode(block);
            //update current expression value
            visitExpression(expression);
        }
    }

    @Override
    public void visitReturnStatementNode(TinyLangAst tinyLangAst) {
        //update current expression value
        visitExpression(tinyLangAst.getChildren().get(0));
    }

    @Override
    public void visitFunctionDeclarationNode(TinyLangAst tinyLangAst) {
        //add function definition and values to symbol table
        //get function block ast
        TinyLangAst functionBlock = tinyLangAst.getChildren().get(3);
        //get variable type
        Type functionType = Type.valueOf(tinyLangAst.getChildren().get(0).getAssociatedNodeValue());
        //get hold on identifier
        String functionName = tinyLangAst.getChildren().get(1).getAssociatedNodeValue();
        //get function parameter types
        Stack<Type> functionParameterTypes = new Stack<Type>();
        Stack<String> functionParameterNames = new Stack<String>();
        //add parameters types and values
        for (TinyLangAst formalParameter : tinyLangAst.getChildren().get(2).getChildren()) {
            functionParameterTypes.push(Type.valueOf(formalParameter.getChildren().get(0).getAssociatedNodeValue()));
            functionParameterNames.push(formalParameter.getChildren().get(1).getAssociatedNodeValue());
        }
        //add function parameter types and names to st
        st.insertFunctionDeclaration(new FunctionSignature(functionName, functionParameterTypes), functionType);
        st.insertFunctionParameterNames(new FunctionSignature(functionName, functionParameterTypes), functionParameterNames);
        st.insertFunctionBlock(new FunctionSignature(functionName, functionParameterTypes), functionBlock);
    }

    @Override
    public void visitFunctionCallNode(TinyLangAst tinyLangAst) {

        //function name
        String functionName = tinyLangAst.getChildren().get(0).getAssociatedNodeValue();
        for (TinyLangAst expression : tinyLangAst.getChildren().get(1).getChildren()) {
            visitExpression(expression);
            parameterTypes.push(currentExpressionType);
            parameterValues.push(currentExpressionValue);
        }
        //function signature types of parameters
        int i;
        for (i = st.getScopes().size() - 1; i >= 0; i--)
            if (st.getScopes().get(i).isFunctionAlreadyDefined(new FunctionSignature(functionName, parameterTypes)))
                break;
        //add temporary function parameters names
        parameterNames.addAll(st.getScopes().get(i).getParameterNames(new FunctionSignature(functionName, parameterTypes)));
        //visit corresponding function block
        visitBlockNode(st.getScopes().get(i).getBlock(new FunctionSignature(functionName, parameterTypes)));

    }

    @Override
    public void visitBlockNode(TinyLangAst tinyLangAst) {
        //enter a new scope
        st.push();
        //check all temporary function parameter stacks are of the same size
        if (!(parameterTypes.size() == parameterNames.size() && parameterNames.size() == parameterValues.size()))
            throw new java.lang.RuntimeException("error with function call handling");
        //add parameters of functions if any in scope
        for (int i = 0; i < parameterTypes.size(); i++) {
            //add variable declaration in current scope
            st.insertVariableDeclaration(parameterNames.get(i), parameterTypes.get(i));
            //add value assigned to variable
            st.insertVariableValue(parameterNames.get(i), parameterValues.get(i));
        }
        //clear temporary function parameters data
        parameterTypes.clear();
        parameterNames.clear();
        parameterValues.clear();
        //traverse statements in block
        for (TinyLangAst statement : tinyLangAst.getChildren())
            visitStatement(statement);
        //leave scope
        st.pop();
    }


    @Override
    public void visitBinaryOperatorNode(TinyLangAst tinyLangAst) {
        //get operator

        String operator = tinyLangAst.getAssociatedNodeValue();

        //get left node  (left operand)
        TinyLangAst leftOperand = tinyLangAst.getChildren().get(0);
        //visit expression to update current char type
        visitExpression(leftOperand);
        //obtain the type of the left operand
        Type leftOperandType = currentExpressionType;
        //obtain the value of the left operand
        String leftOperandValue = currentExpressionValue;

        //redo for right operand
        TinyLangAst rightOperand = tinyLangAst.getChildren().get(1);
        visitExpression(rightOperand);
        Type rightOperandType = currentExpressionType;
        String rightOperandValue = currentExpressionValue;
        if (operator.equals("+")) {
            //check operand type
            if (leftOperandType.equals(Type.INTEGER) && rightOperandType.equals(Type.INTEGER)) {
                //int+int -> int
                currentExpressionType = Type.INTEGER;
                currentExpressionValue = String.valueOf(Integer.parseInt(leftOperandValue) + Integer.parseInt(rightOperandValue));
            }
            //if one is floating
            else if (leftOperandType.equals(Type.FLOAT) || rightOperandType.equals(Type.FLOAT)) {
                //int+int -> int
                currentExpressionType = Type.FLOAT;
                currentExpressionValue = String.valueOf(Float.parseFloat(leftOperandValue) + Float.parseFloat(rightOperandValue));
            } else {
                throw new java.lang.RuntimeException("unexpected operator processing exception in line " + tinyLangAst.getLineNumber());
            }
        } else if (operator.equals("-")) {
            //check operand type
            if (leftOperandType.equals(Type.INTEGER) && rightOperandType.equals(Type.INTEGER)) {
                //int+int -> int
                currentExpressionType = Type.INTEGER;
                currentExpressionValue = String.valueOf(Integer.parseInt(leftOperandValue) - Integer.parseInt(rightOperandValue));
            }
            //if one is floating
            else if (leftOperandType.equals(Type.FLOAT) || rightOperandType.equals(Type.FLOAT)) {
                currentExpressionType = Type.FLOAT;
                currentExpressionValue = String.valueOf(Float.parseFloat(leftOperandValue) - Float.parseFloat(rightOperandValue));
            } else
                throw new java.lang.RuntimeException("unexpected operator processing exception in line " + tinyLangAst.getLineNumber());
        } else if (operator.equals("*")) {
            //check operand type
            if (leftOperandType.equals(Type.INTEGER) && rightOperandType.equals(Type.INTEGER)) {
                //int+int -> int
                currentExpressionType = Type.INTEGER;
                currentExpressionValue = String.valueOf(Integer.parseInt(leftOperandValue) * Integer.parseInt(rightOperandValue));
            }
            //if one is floating
            else if (leftOperandType.equals(Type.FLOAT) || rightOperandType.equals(Type.FLOAT)) {
                currentExpressionType = Type.FLOAT;
                currentExpressionValue = String.valueOf(Float.parseFloat(leftOperandValue) * Float.parseFloat(rightOperandValue));
            } else
                throw new java.lang.RuntimeException("unexpected operator processing exception in line " + tinyLangAst.getLineNumber());
        } else if (operator.equals("/")) {
            //check if right operand is 0
            if (Float.parseFloat(rightOperandValue) == 0)
                throw new java.lang.RuntimeException("division by 0 undefined in line " + tinyLangAst.getLineNumber());
            //check operand type
            if (leftOperandType.equals(Type.INTEGER) && rightOperandType.equals(Type.INTEGER)) {
                //int+int -> int
                currentExpressionType = Type.INTEGER;
                currentExpressionValue = String.valueOf(Integer.parseInt(leftOperandValue) / Integer.parseInt(rightOperandValue));
            }
            //if one is floating
            else if (leftOperandType.equals(Type.FLOAT) || rightOperandType.equals(Type.FLOAT)) {
                currentExpressionType = Type.FLOAT;
                currentExpressionValue = String.valueOf(Float.parseFloat(leftOperandValue) / Float.parseFloat(rightOperandValue));
            } else
                throw new java.lang.RuntimeException("unexpected runtime exception in line " + tinyLangAst.getLineNumber());
        }
        //boolean operators
        else if (operator.equals("and")) {
            currentExpressionType = Type.BOOL;
            if (leftOperandValue.equals("true") && rightOperandValue.equals("true"))
                currentExpressionValue = "true";
            else
                currentExpressionValue = "false";
        } else if (operator.equals("or")) {
            currentExpressionType = Type.BOOL;
            if (leftOperandValue.equals("true") || rightOperandValue.equals("true"))
                currentExpressionValue = "true";
            else
                currentExpressionValue = "false";
        }
        //comparison types
        else if (operator.equals("==")) {
            currentExpressionType = Type.BOOL;
            if (leftOperandValue.equals(rightOperandValue))
                currentExpressionValue = "true";
            else
                currentExpressionValue = "false";
        } else if (operator.equals("!=")) {
            currentExpressionType = Type.BOOL;
            if (!leftOperandValue.equals(rightOperandValue))
                currentExpressionValue = "true";
            else
                currentExpressionValue = "false";
        } else if (operator.equals("<")) {
            currentExpressionType = Type.BOOL;
            if (Float.parseFloat(leftOperandValue) < Float.parseFloat(rightOperandValue))
                currentExpressionValue = "true";
            else
                currentExpressionValue = "false";
        } else if (operator.equals("<=")) {
            currentExpressionType = Type.BOOL;
            if (Float.parseFloat(leftOperandValue) <= Float.parseFloat(rightOperandValue))
                currentExpressionValue = "true";
            else
                currentExpressionValue = "false";
        } else if (operator.equals(">")) {
            currentExpressionType = Type.BOOL;
            if (Float.parseFloat(leftOperandValue) > Float.parseFloat(rightOperandValue))
                currentExpressionValue = "true";
            else
                currentExpressionValue = "false";
        } else if (operator.equals(">=")) {
            currentExpressionType = Type.BOOL;
            if (Float.parseFloat(leftOperandValue) >= Float.parseFloat(rightOperandValue))
                currentExpressionValue = "true";
            else
                currentExpressionValue = "false";
        } else {
            throw new java.lang.RuntimeException("unexcepted binary operator error in line " + tinyLangAst.getLineNumber());
        }
    }

    @Override
    public void visitUnaryOperatorNode(TinyLangAst tinyLangAst) {
        TinyLangAst expression = tinyLangAst.getChildren().get(0);
        visitExpression(expression);
        String operator = tinyLangAst.getAssociatedNodeValue();
        if (currentExpressionType == Type.FLOAT) {
            if (operator.equals("-"))
                currentExpressionValue = String.valueOf(-1 * Float.parseFloat(currentExpressionValue));
        } else if (currentExpressionType == Type.INTEGER) {
            if (operator.equals("-")) {
                currentExpressionValue = String.valueOf(-1 * Integer.parseInt(currentExpressionValue));
            }
        } else if (currentExpressionType == Type.BOOL) {
            if (operator.equals("not")) {
                if (currentExpressionValue.equals("true"))
                    currentExpressionValue = "false";
                else
                    currentExpressionValue = "true";
            }
        } else
            throw new java.lang.RuntimeException
                    ("unexpected error when handling unary opertor in line " + tinyLangAst.getLineNumber());
    }

    @Override
    public void visitIdentifierNode(TinyLangAst tinyLangAst) {
        //Identifier name
        String identifier = tinyLangAst.getAssociatedNodeValue();
        //traverse the scopes to find the identifier type and value
        int i;
        for (i = st.getScopes().size() - 1; i >= 0; i--) {
            if (st.getScopes().get(i).isVariableNameBinded(identifier))
                break;
        }
        currentExpressionType = st.getScopes().get(i).getVariableType(identifier);
        currentExpressionValue = st.getScopes().get(i).getVariableValue(identifier);
    }

    @Override
    public void visitBooleanLiteralNode(TinyLangAst tinyLangAst) {
        String boolIdentifier = tinyLangAst.getAssociatedNodeValue();
        currentExpressionType = Type.BOOL;
        currentExpressionValue = boolIdentifier;
    }

    @Override
    public void visitIntegerLiteralNode(TinyLangAst tinyLangAst) {
        String integerIdentifier = tinyLangAst.getAssociatedNodeValue();
        currentExpressionType = Type.INTEGER;
        currentExpressionValue = integerIdentifier;
    }

    @Override
    public void visitFloatLiteralNode(TinyLangAst tinyLangAst) {
        String floatIdentifier = tinyLangAst.getAssociatedNodeValue();
        currentExpressionType = Type.FLOAT;
        currentExpressionValue = floatIdentifier;
    }

    @Override
    public void visitCharLiteralNode(TinyLangAst tinyLangAst) {
        String charIdentifier = tinyLangAst.getAssociatedNodeValue();
        currentExpressionType = Type.CHAR;
        currentExpressionValue = charIdentifier;
    }
}